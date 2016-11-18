package mx.com.gseguros.portal.despachador.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.mesacontrol.dao.FlujoMesaControlDAO;
import mx.com.gseguros.mesacontrol.service.FlujoMesaControlManager;
import mx.com.gseguros.portal.consultas.dao.ConsultasDAO;
import mx.com.gseguros.portal.despachador.dao.DespachadorDAO;
import mx.com.gseguros.portal.despachador.model.ConstantesDespachador;
import mx.com.gseguros.portal.despachador.model.RespuestaDespachadorVO;
import mx.com.gseguros.portal.despachador.model.RespuestaTurnadoVO;
import mx.com.gseguros.portal.despachador.service.DespachadorManager;
import mx.com.gseguros.portal.endosos.dao.EndososDAO;
import mx.com.gseguros.portal.general.model.PolizaVO;
import mx.com.gseguros.portal.general.util.EstatusTramite;
import mx.com.gseguros.portal.general.util.RolSistema;
import mx.com.gseguros.portal.general.util.TipoRamo;
import mx.com.gseguros.portal.mesacontrol.dao.MesaControlDAO;
import mx.com.gseguros.portal.siniestros.dao.SiniestrosDAO;
import mx.com.gseguros.utils.Utils;
import mx.com.gseguros.ws.autosgs.dao.AutosSIGSDAO;

@Service
public class DespachadorManagerImpl implements DespachadorManager {
	private static final Logger logger = LoggerFactory.getLogger(DespachadorManagerImpl.class);
	
	@Autowired
	private SiniestrosDAO siniestrosDAO;
	
	@Autowired
	private DespachadorDAO despachadorDAO;
	
	@Autowired
	private EndososDAO endososDAO;
	
	@Autowired
	private FlujoMesaControlDAO flujoMesaControlDAO;
	
	@Autowired
	private MesaControlDAO mesaControlDAO;
	
	@Autowired
	private ConsultasDAO consultasDAO;
	
	@Autowired
	private AutosSIGSDAO autosSIGSDAO;
	
	@Autowired
	private FlujoMesaControlManager flujoMesaControlManager;
	
	@Override
	public RespuestaDespachadorVO despachar (String ntramite, String status) throws Exception {
	    logger.debug(Utils.log(
	            "\n@@@@@@@@@@@@@@@@@@@@@@@",
	            "\n@@@@@@ despachar @@@@@@",
	            "\n@@@@@@ ntramite = " , ntramite,
	            "\n@@@@@@ status   = " , status));
	    String paso = null;
	    RespuestaDespachadorVO result = null;
	    try {
	        paso = "Recuperando datos de tr\u00e1mite";
            logger.debug(paso);
            Map<String, String> datosTramite = siniestrosDAO.obtenerTramiteCompleto(ntramite);
            String cdtipflu   = datosTramite.get("CDTIPFLU"),
                   cdflujomc  = datosTramite.get("CDFLUJOMC"),
                   cdramo     = datosTramite.get("CDRAMO"),
                   cdtipsit   = datosTramite.get("CDTIPSIT"),
                   cdsucadm   = datosTramite.get("CDSUCADM"),
                   cdunidspch = datosTramite.get("CDUNIDSPCH");
            if (StringUtils.isBlank(cdunidspch)) { // Si no tengo sucursal de despacho tomo el cdunieco para iniciar
                cdunidspch = cdsucadm;
            }
            paso = "Recuperando tipo de producto";
            logger.debug(paso);
            String cdtipram = despachadorDAO.recuperarCdtipramFlujo(cdtipflu, cdflujomc);
            paso = "Recuperando datos de clasificaci\u00f3n de sucursal";
            logger.debug(paso);
            Map<String, String> datosSucursal = despachadorDAO.recuperarDatosClasificacionSucursal(cdunidspch);
            String nivel     = datosSucursal.get("CDNIVEL"),
                   zona      = datosSucursal.get("CDUNIZON"),
                   capacidad = datosSucursal.get("NMCAPACI");
            if (ConstantesDespachador.NIVEL_MATRIZ.equals(nivel)) {
                logger.debug("El nivel matriz se cambia por nivel primario");
                nivel = ConstantesDespachador.NIVEL_PRIMARIO;
            }
            paso = "Recuperando rol destino";
            logger.debug(paso);
            String cdsisrol = despachadorDAO.recuperarRolTrabajoEstatus(cdtipflu, cdflujomc, status);
            Utils.validate(cdsisrol, "El estatus no tiene un rol encargado");
            paso = "Entrando al algoritmo";
            logger.debug(paso);
            StringBuilder sb = new StringBuilder(Utils.log(
                    "\n================================================",
                    "\n= Entrando a algoritmo de despacho, originales:",
                    "\n= cdtipram:  " , cdtipram,
                    "\n= cdtipflu:  " , cdtipflu,
                    "\n= cdflujomc: " , cdflujomc,
                    "\n= ntramite:  " , ntramite,
                    "\n= status:    " , status,
                    "\n= cdsisrol:  " , cdsisrol,
                    "\n= cdunieco:  " , cdunidspch,
                    "\n= cdramo:    " , cdramo,
                    "\n= cdtipsit:  " , cdtipsit,
                    "\n= nivel:     " , nivel,
                    "\n= zona:      " , zona));
            Map<String, Map<String, Boolean>> quemados = new LinkedHashMap<String, Map<String, Boolean>>();
            quemados.put(ConstantesDespachador.ZONA_MATRIZ          , new LinkedHashMap<String, Boolean>());
            quemados.put(ConstantesDespachador.ZONA_NORESTE         , new LinkedHashMap<String, Boolean>());
            quemados.put(ConstantesDespachador.ZONA_NOROESTE        , new LinkedHashMap<String, Boolean>());
            quemados.put(ConstantesDespachador.ZONA_CENTRO          , new LinkedHashMap<String, Boolean>());
            quemados.put(ConstantesDespachador.ZONA_ACC_BAJIO       , new LinkedHashMap<String, Boolean>());
            quemados.put(ConstantesDespachador.ZONA_SUR             , new LinkedHashMap<String, Boolean>());
            quemados.put(ConstantesDespachador.ZONA_COMODIN_USUARIO , new LinkedHashMap<String, Boolean>());
            result = this.despachar(cdtipram, cdtipflu, cdflujomc, ntramite, cdramo, cdtipsit, zona, nivel, cdunidspch, status, cdsisrol,
                    zona, nivel, cdunidspch, status, cdsisrol, quemados, sb);
            if (result.isEncolado()) {
                
            } else {
                
            }
            logger.debug(sb.toString());
	    } catch (Exception ex) {
	        Utils.generaExcepcion(ex, paso);
	    }
        logger.debug(Utils.log(
                "\n@@@@@@ result = ", result,
                "\n@@@@@@ despachar @@@@@@",
                "\n@@@@@@@@@@@@@@@@@@@@@@@"));
        return result;
	}
	
	private RespuestaDespachadorVO despachar (
	        String cdtipram,
	        String cdtipflu,
	        String cdflujomc,
	        String ntramite,
	        String cdramo,
	        String cdtipsit,
	        String zonaOri,
	        String nivelOri,
	        String cduniecoOri,
	        String statusOri,
	        String cdsisrolOri,
	        String zona,
	        String nivel,
	        String cdunieco,
            String status,
            String cdsisrol,
            Map<String, Map<String, Boolean>> quemados,
            StringBuilder sb) throws Exception {
	/*
	   JTEZVA 27 OCTUBRE 2016
	   ALGORITMO:
	SI EL DESTINO ES AGENTE:
	    RECUPERO EL AGENTE Y SU SUCURSAL
	    ENCONTRO: FIN
	    NO ENCONTRO: ERROR!
    SINO, SI NO ESTA QUEMADA LA BUSQUEDA DE USUARIO ANTERIOR      
        BUSCO USUARIO ANTERIOR ACTIVO CON ROL ACTIVO CON SUCURSAL ACTIVA    
        ENCONTRO: FIN   
        NO ENCONTRO:    
            QUEMO LA BUSQUEDA
L           REINICIO
    SINO, SI TENGO SUCURSAL                       
        BUSCO EN ESA SUCURSAL                   
        ENCONTRO: FIN                   
        NO ENCONTRO:                    
            SI SOY MATRIZ               
A               LES DELEGO A NIVEL PRIMARIO ZONA C ENCONTRAR USUARIO Y SUCURSAL         
            SINO, SI EXISTE ESTATUS SUSTITUTO               
B               CAMBIO ESTATUS Y ROL            
                VUELVO A INTENTAR EN LA SUCURSAL            
            SINO, SI TENGO NIVEL INFERIOR               
C               LES DELEGO A NIVEL INFERIOR ENCONTRAR USUARIO Y SUCURSAL            
                CON ESTATUS Y ROL ORIGINAL          
            SINO                
D               PIDO AYUDA AL MISMO NIVEL PARA ENCONTRAR USUARIO Y SUCURSAL         
                CON ESTATUS Y ROL ORIGINAL          
    SINO                        
        BUSCO USUARIO Y SUCURSAL EN EL NIVEL ACTUAL SI NO ESTA QUEMADO                  
        ENCONTRO: FIN                   
        NO ENCONTRO:                    
            SI SOY MATRIZ Y NIVEL PRIMARIO ZONA C NO ESTA QUEMADO               
E               LES DELEGO A NIVEL PRIMARIO ZONA C ENCONTRAR USUARIO Y SUCURSAL         
            SINO, SI EXISTE ESTATUS SUSTITUTO Y EL NIVEL NO ESTA QUEMADO                
F               CAMBIO ESTATUS Y ROL            
                VUELVO A INTENTAR EN EL NIVEL           
            SINO                
                QUEMO EL NIVEL ACTUAL           
                SI NO SOY MATRIZ Y TENGO NIVEL INFERIOR NO QUEMADO          
G                   LES DELEGO A NIVEL INFERIOR ENCONTRAR USUARIO Y SUCURSAL        
                    CON ESTATUS Y ROL ORIGINAL      
                SINO, SI TENGO NIVEL SUPERIOR           
                    SI EL NIVEL SUPERIOR ES MATRIZ      
                        SI NIVEL PRIMARIO ZONA C NO ESTA QUEMADO    
H                           DELEGO AL NIVEL PRIMARIO ZONA C CON ESTATUS Y ROL ORIGINAL
                        SINO    
I                           ESCALO AL NIVEL SUPERIOR CON ESTATUS Y ROL ORIGINAL SIN ZONA
                    SINO        
J                       ESCALO AL NIVEL SUPERIOR CON ESTATUS Y ROL ORIGINAL 
                SINO            
K                   ENCOLAR CON DATOS ORIGINALES        
	*/
	    RespuestaDespachadorVO result = null;
	    String paso = "Ejecutando despachador";
	    Map<String, String> sustituto = null;
	    try {
	        sb.append(Utils.log(
	                "\n=== despachar => zona: ", zona, ", nivel: ", nivel, ", cdunieco: ", cdunieco, ", status: ", status, ", cdsisrol: ", cdsisrol
	                ));
	        if (RolSistema.AGENTE.getCdsisrol().equals(cdsisrolOri)) {
	            Map<String, String> agenteDestino = despachadorDAO.recuperarAgenteDestino(ntramite);
	            result = new RespuestaDespachadorVO();
	            result.setStatus(statusOri);
	            result.setCdsisrol(cdsisrolOri);
	            result.setCdusuari(agenteDestino.get("CDUSUARI"));
	            result.setCdtipasig(ConstantesDespachador.TIPO_ASIGNACION_AGENTE);
	            if (TipoRamo.SALUD.getCdtipram().equals(agenteDestino.get("CDTIPRAM"))) {
	                result.setCdunieco(agenteDestino.get("CDUNISLD"));
	            } else if (TipoRamo.AUTOS.getCdtipram().equals(agenteDestino.get("CDTIPRAM"))) {
                    result.setCdunieco(agenteDestino.get("CDUNIECO"));
                } else {
                    throw new ApplicationException("El tipo de ramo no es salud ni autos");
                }
	        } else if (!Boolean.TRUE.equals(quemados.get(ConstantesDespachador.ZONA_COMODIN_USUARIO).get(ConstantesDespachador.NIVEL_COMODIN_USUARIO))) {
	            result = this.buscarUsuarioAnteriorActivo(cdtipram, cdsisrol, ntramite);
	            if (result == null) {
	                sb.append("\n=== despachar: L (no es regreso, reinicio)");
	                quemados.get(ConstantesDespachador.ZONA_COMODIN_USUARIO).put(ConstantesDespachador.NIVEL_COMODIN_USUARIO, Boolean.TRUE);
	                result = this.despachar(cdtipram, cdtipflu, cdflujomc, ntramite, cdramo, cdtipsit, zonaOri,
                            nivelOri, cduniecoOri, statusOri, cdsisrolOri, zona,
                            nivel, cdunieco, status, cdsisrol, quemados, sb);
	            } else {
	                result.setStatus(status);
	                result.setCdsisrol(cdsisrol);
	            }
	        } else if (StringUtils.isNotBlank(cdunieco)) {
	            result = this.buscarUsuarioParaAsignacion(cdtipram, zona, nivel, cdunieco, status, cdsisrol, cdtipflu, cdflujomc, ntramite,
	                    cdramo, cdtipsit);
	            if (result == null) {
	                if (ConstantesDespachador.NIVEL_MATRIZ.equals(nivel)) {
	                    sb.append("\n=== despachar: A (=> C-1 sin sucursal)");
	                    zona     = ConstantesDespachador.ZONA_CENTRO;
	                    nivel    = ConstantesDespachador.NIVEL_PRIMARIO;
	                    cdunieco = null;
	                    result   = this.despachar(cdtipram, cdtipflu, cdflujomc, ntramite, cdramo, cdtipsit, zonaOri,
	                            nivelOri, cduniecoOri, statusOri, cdsisrolOri, zona,
	                            nivel, cdunieco, status, cdsisrol, quemados, sb);
	                } else if ((sustituto = this.recuperarSustituto(cdtipflu, cdflujomc, zona, status)) != null) {
	                    sb.append("\n=== despachar: B (sustituyo y reinteno sucursal)");
	                    status   = sustituto.get("STATUS");
	                    cdsisrol = sustituto.get("CDSISROL");
	                    result   = this.despachar(cdtipram, cdtipflu, cdflujomc, ntramite, cdramo, cdtipsit, zonaOri,
                                nivelOri, cduniecoOri, statusOri, cdsisrolOri, zona,
                                nivel, cdunieco, status, cdsisrol, quemados, sb);
	                } else if (!ConstantesDespachador.NIVEL_OFICINA.equals(nivel)) {
	                    sb.append("\n=== despachar: C (nivel inferior sin sucursal)");
	                    status   = statusOri;
	                    cdsisrol = cdsisrolOri;
	                    cdunieco = null;
	                    nivel    = String.valueOf(Integer.parseInt(nivel) + 1);
	                    result   = this.despachar(cdtipram, cdtipflu, cdflujomc, ntramite, cdramo, cdtipsit, zonaOri,
                                nivelOri, cduniecoOri, statusOri, cdsisrolOri, zona,
                                nivel, cdunieco, status, cdsisrol, quemados, sb);
	                } else {
	                    sb.append("\n=== despachar: D (mismo nivel sin sucursal)");
	                    status   = statusOri;
                        cdsisrol = cdsisrolOri;
                        cdunieco = null;
                        result   = this.despachar(cdtipram, cdtipflu, cdflujomc, ntramite, cdramo, cdtipsit, zonaOri,
                                nivelOri, cduniecoOri, statusOri, cdsisrolOri, zona,
                                nivel, cdunieco, status, cdsisrol, quemados, sb);
	                }
	            } else {
	                result.setStatus(status);
	                result.setCdsisrol(cdsisrol);
	            }
	        } else {
	            result = this.buscarUsuarioParaAsignacion(cdtipram, zona, nivel, cdunieco, status, cdsisrol, cdtipflu, cdflujomc, ntramite,
	                    cdramo, cdtipsit);
	            if (result == null) {
	                if (ConstantesDespachador.NIVEL_MATRIZ.equals(nivel)
	                        && !Boolean.TRUE.equals(quemados.get(ConstantesDespachador.ZONA_CENTRO).get(ConstantesDespachador.NIVEL_PRIMARIO))) {
	                    sb.append("\n=== despachar: E (=> C-1)");
	                    zona   = ConstantesDespachador.ZONA_CENTRO;
                        nivel  = ConstantesDespachador.NIVEL_PRIMARIO;
                        result = this.despachar(cdtipram, cdtipflu, cdflujomc, ntramite, cdramo, cdtipsit, zonaOri,
                                nivelOri, cduniecoOri, statusOri, cdsisrolOri, zona,
                                nivel, cdunieco, status, cdsisrol, quemados, sb);
	                } else if ((sustituto = this.recuperarSustituto(cdtipflu, cdflujomc, zona, status)) != null &&
	                        !Boolean.TRUE.equals(quemados.get(zona).get(nivel))) {
	                    sb.append("\n=== despachar: F (sustituyo y reintento nivel)");
	                    status   = sustituto.get("STATUS");
	                    cdsisrol = sustituto.get("CDSISROL");
	                    result   = this.despachar(cdtipram, cdtipflu, cdflujomc, ntramite, cdramo, cdtipsit, zonaOri,
                                nivelOri, cduniecoOri, statusOri, cdsisrolOri, zona,
                                nivel, cdunieco, status, cdsisrol, quemados, sb);
	                } else {
	                    sb.append(Utils.log("\n=== quemo ", zona, "-", nivel)); 
	                    quemados.get(zona).put(nivel, Boolean.TRUE);
	                    if (!ConstantesDespachador.NIVEL_MATRIZ.equals(nivel) &&
	                            !ConstantesDespachador.NIVEL_OFICINA.equals(nivel) &&
	                            !Boolean.TRUE.equals(quemados.get(zona).get(String.valueOf(Integer.parseInt(nivel) + 1)))) {
	                        sb.append("\n=== despachar: G (bajo nivel)");
	                        status   = statusOri;
	                        cdsisrol = cdsisrolOri;
	                        nivel    = String.valueOf(Integer.parseInt(nivel) + 1);
	                        result   = this.despachar(cdtipram, cdtipflu, cdflujomc, ntramite, cdramo, cdtipsit, zonaOri,
	                                nivelOri, cduniecoOri, statusOri, cdsisrolOri, zona,
	                                nivel, cdunieco, status, cdsisrol, quemados, sb);
	                    } else if (!ConstantesDespachador.NIVEL_MATRIZ.equals(nivel)) {
	                        if (ConstantesDespachador.NIVEL_PRIMARIO.equals(nivel)) {
	                            if (!Boolean.TRUE.equals(quemados.get(ConstantesDespachador.ZONA_CENTRO).get(ConstantesDespachador.NIVEL_PRIMARIO))) {
	                                sb.append("\n=== despachar: H (=> C-1)");
	                                status   = statusOri;
	                                cdsisrol = cdsisrolOri;
	                                zona     = ConstantesDespachador.ZONA_CENTRO;
	                                nivel    = ConstantesDespachador.NIVEL_PRIMARIO;
	                                result   = this.despachar(cdtipram, cdtipflu, cdflujomc, ntramite, cdramo, cdtipsit, zonaOri,
	                                        nivelOri, cduniecoOri, statusOri, cdsisrolOri, zona,
	                                        nivel, cdunieco, status, cdsisrol, quemados, sb);
	                            } else {
	                                sb.append("\n=== despachar: I (=> X-0)");
	                                status   = statusOri;
                                    cdsisrol = cdsisrolOri;
                                    zona     = ConstantesDespachador.ZONA_MATRIZ;
                                    nivel    = String.valueOf(Integer.parseInt(nivel) - 1);
                                    result   = this.despachar(cdtipram, cdtipflu, cdflujomc, ntramite, cdramo, cdtipsit, zonaOri,
                                            nivelOri, cduniecoOri, statusOri, cdsisrolOri, zona,
                                            nivel, cdunieco, status, cdsisrol, quemados, sb);
	                            }
	                        } else {
	                            sb.append("\n=== despachar: J (subo nivel)");
	                            status   = statusOri;
                                cdsisrol = cdsisrolOri;
                                nivel    = String.valueOf(Integer.parseInt(nivel) - 1);
                                result   = this.despachar(cdtipram, cdtipflu, cdflujomc, ntramite, cdramo, cdtipsit, zonaOri,
                                        nivelOri, cduniecoOri, statusOri, cdsisrolOri, zona,
                                        nivel, cdunieco, status, cdsisrol, quemados, sb);
	                        }
	                    } else {
	                        sb.append("\n=== despachar: K (encolar)");
	                        result = new RespuestaDespachadorVO();
	                        result.setEncolado(true);
	                    }
	                }
	            } else {
	                result.setStatus(status);
	                result.setCdsisrol(cdsisrol);
	            }
	        }
	    } catch (Exception ex) {
	        if (sb.toString().length() > 0) { // para imprimir solo una vez (porque es recursivo y puede que avienta excepcion a si mismo N veces)
	            logger.debug(sb.toString());
	            sb.setLength(0);
	        }
	        Utils.generaExcepcion(ex, paso);
	    }
	    return result;
	}
	
	private RespuestaDespachadorVO buscarUsuarioAnteriorActivo (String cdtipram, String cdsisrol, String ntramite) throws Exception {
	    logger.debug(Utils.log(
                "\n@@@@@@ buscarUsuarioAnteriorActivo ",
                "cdtipram: " , cdtipram , ", ",
                "ntramite: " , ntramite));
        String paso = null;
        RespuestaDespachadorVO result = null;
        try {
            Map<String, String> usuarioAnterior = despachadorDAO.recuperarUsuarioRegreso(cdtipram, cdsisrol, ntramite);
            if (usuarioAnterior != null) {
                result = new RespuestaDespachadorVO();
                result.setCdunieco(usuarioAnterior.get("CDUNIECO"));
                result.setCdusuari(usuarioAnterior.get("CDUSUARI"));
                result.setCdtipasig(ConstantesDespachador.TIPO_ASIGNACION_REGRESO);
            }
        } catch (Exception ex) {
            Utils.generaExcepcion(ex, paso);
        }
        logger.debug("\n@@@@@@ buscarUsuarioAnteriorActivo => {}", result);
        return result;
	}
	
	/**
	 * 1. BUSCO UNA SUCURSAL ACTIVA EN EL NIVEL - ZONA (SI RECIBO CDUNIECO SOLO BUSCO EN ESA)
	 * QUE TENGA USUARIOS ACTIVOS CON EL ROL ACTIVO
	 * 2. TOMO LA QUE TENGA MENOS CAPACIDAD (SI HAY)
	 * 3. BUSCO POR CARRUSEL O POR CARGA EL USUARIO
	 * 4. REGRESO EL RESULTADO
	 * SI NO ENCUENTRO REGRESO NULL
	 * @return ZONA, CDUNIECO, CDUSUARI
	 */
	private RespuestaDespachadorVO buscarUsuarioParaAsignacion (String cdtipram, String zona, String nivel, String cdunieco, String status,
	        String cdsisrol, String cdtipflu, String cdflujomc, String ntramite, String cdramo, String cdtipsit) throws Exception {
	    logger.debug(Utils.log(
	            "\n@@@@@@ buscarUsuarioParaAsignacion ",
	            "cdtipram: "  , cdtipram , ", ",
	            "zona: "      , zona     , ", ",
	            "nivel: "     , nivel    , ", ",
	            "cdunieco: "  , cdunieco , ", ",
	            "status: "    , status   , ", ",
	            "cdsisrol: "  , cdsisrol , ", ",
	            "cdtipflu: "  , cdtipflu , ", ",
	            "cdflujomc: " , cdflujomc, ", ",
	            "ntramite: "  , ntramite, ", ",
                "cdramo: "    , cdramo, ", ",
                "cdtipsit: "  , cdtipsit));
	    String paso = null;
	    RespuestaDespachadorVO result = null;
	    try {
	        paso = "Recuperando sucursales para despachar";
	        List<Map<String, String>> sucursales = despachadorDAO.recuperarSucursalesParaDespachar(cdtipram, zona, nivel, cdunieco,
	                cdsisrol, ntramite, cdtipflu, cdflujomc, cdramo, cdtipsit);
	        if (sucursales.size () > 0) {
	            int indice = -1;
	            for (int i = 0; i < sucursales.size(); i++) {
	                Map<String, String> sucursal = sucursales.get(i);
	                int capacidad = Integer.parseInt(sucursal.get("CAPACIDAD")),
	                    tramites  = Integer.parseInt(sucursal.get("CAPACIDAD_OCUPADA"));
	                if (tramites < capacidad) { // El primero que tenga espacio libre
	                    indice = i;
	                    break;
	                }
	            }
	            if (indice != -1) { // Encontre una sucursal
	                Map<String, String> sucursal = sucursales.get(indice);
	                String cdunidspch = sucursal.get("CDUNIECO");
	                paso = "Recuperando tipo de turnado";
	                String cdtipasig = despachadorDAO.recuperarTipoTurnadoEstatus(cdtipflu, cdflujomc, status);
	                if (!ConstantesDespachador.TIPO_ASIGNACION_CARGA.equals(cdtipasig)) {
	                    cdtipasig = ConstantesDespachador.TIPO_ASIGNACION_CARRUSEL; // Si no es carga es carrusel
	                }
	                String cdusuari = null;
	                if (ConstantesDespachador.TIPO_ASIGNACION_CARGA.equals(cdtipasig)) {
	                    paso = "Recuperando siguiente usuario por carga";
	                    cdusuari = despachadorDAO.recuperarSiguienteUsuarioCarga(cdunidspch, cdsisrol);
	                } else if (ConstantesDespachador.TIPO_ASIGNACION_CARRUSEL.equals(cdtipasig)) {
	                    paso = "Recuperando siguiente usuario por carrusel";
	                    cdusuari = despachadorDAO.recuperarSiguienteUsuarioCarrusel(cdunidspch, cdsisrol);
	                } else {
	                    throw new ApplicationException("El tipo de turnado no es v\u00e1lido");
	                }
	                Utils.validate(cdusuari, "No se encontr\u00f3 usuario para asignaci\u00f3n");
	                result = new RespuestaDespachadorVO();
	                result.setCdunieco(cdunidspch);
	                result.setCdusuari(cdusuari);
	                result.setCdtipasig(cdtipasig);
	            }
	        }
	    } catch (Exception ex) {
	        Utils.generaExcepcion(ex, paso);
	    }
	    logger.debug("\n@@@@@@ buscarUsuarioParaAsignacion => {}", result);
	    return result;
	}
	
	/**
	 * BUSCO SI HAY UN SUSTITUTO PARA UN STATUS EN UN FLUJO Y PARA UNA ZONA
	 */
	private Map<String, String> recuperarSustituto(String cdtipflu, String cdflujomc, String zona, String status) throws Exception {
	    logger.debug(Utils.log(
                "\n@@@@@@ recuperarSustituto ",
                "cdtipflu: "  , cdtipflu  , ", ",
                "cdflujomc: " , cdflujomc , ", ",
                "zona: "      , zona      , ", ",
                "status: "    , status));
	    Map<String, String> sustituto = null;
	    String paso = null;
	    try {
	        paso = "Recuperando estatus sustituto";
	        String statusSustituto = despachadorDAO.recuperarStatusSustituto(cdtipflu, cdflujomc, zona, status);
	        if (StringUtils.isNotBlank(statusSustituto)) {
	            paso = "Recuperando rol encargado del estatus sustituto";
	            String cdsisrol = despachadorDAO.recuperarRolTrabajoEstatus(cdtipflu, cdflujomc, statusSustituto);
	            sustituto = new HashMap<String, String>();
	            sustituto.put("STATUS", statusSustituto);
	            sustituto.put("CDSISROL", cdsisrol);
	        }
	    } catch (Exception ex) {
	        Utils.generaExcepcion(ex, paso);
	    }
        logger.debug("\n@@@@@@ recuperarSustituto => {}", sustituto);
        return sustituto;
	}
	
	/**
     * SE TURNA/RECHAZA/REASIGNA UN TRAMITE. SE MODIFICA TMESACONTROL (STATUS, FECSTATU, CDUSUARI, CDUNIDSPCH, CDRAZRECHA),
     * THMESACONTROL (SE CIERRA EL HISTORIAL ANTERIOR, SE ABRE EL HISTORIAL NUEVO),
     * TDMESACONTROL (SE INSERTA DETALLE). SE ENVIAN CORREOS DE AVISOS Y SE RECHAZA EN SIGS 
     * @return String message, boolean encolado
     */
	@Override
	public RespuestaTurnadoVO turnarTramite (String cdusuariSes, String cdsisrolSes, String ntramite, String status, String comments,
	        String cdrazrecha, String cdusuariDes, String cdsisrolDes, boolean permisoAgente, boolean porEscalamiento, Date fechaHoy,
	        boolean sinGrabarDetalle) throws Exception {
	    logger.debug(Utils.log(
	            "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@",
	            "\n@@@@@@ turnarTramite @@@@@@",
	            "\n@@@@@@ cdusuariSes      = " , cdusuariSes,
	            "\n@@@@@@ cdsisrolSes      = " , cdsisrolSes,
	            "\n@@@@@@ ntramite         = " , ntramite,
	            "\n@@@@@@ status           = " , status,
	            "\n@@@@@@ comments         = " , comments,
	            "\n@@@@@@ cdrazrecha       = " , cdrazrecha,
	            "\n@@@@@@ cdusuariDes      = " , cdusuariDes,
	            "\n@@@@@@ cdsisrolDes      = " , cdsisrolDes,
	            "\n@@@@@@ permisoAgente    = " , permisoAgente,
	            "\n@@@@@@ porEscalamiento  = " , porEscalamiento,
                "\n@@@@@@ fechaHoy         = " , fechaHoy,
                "\n@@@@@@ sinGrabarDetalle = " , sinGrabarDetalle));
	    String paso = null;
        RespuestaTurnadoVO result = new RespuestaTurnadoVO();
	    try {
	        if (StringUtils.isBlank(comments)) {
	            comments = "(sin observaciones)";
	        }
	        
	        //////////////////////////////
	        ////// SI ES UN RECHAZO //////
	        if (EstatusTramite.RECHAZADO.getCodigo().equals(status)) {
                paso = "Validando cambios pendientes de endoso";
                logger.debug(paso);
                endososDAO.validarTramiteSinCambiosEndosoPendiente(ntramite);
                
                paso = "Rechazando tr\u00e1mite";
                logger.debug(paso);
                flujoMesaControlDAO.actualizarStatusTramite(ntramite, status, fechaHoy, null, null);
                
                if (StringUtils.isNotBlank(cdrazrecha)) {
                    paso = "Marcando motivo de rechazo";
                    logger.debug(paso);
                    flujoMesaControlDAO.guardarMotivoRechazoTramite(ntramite, cdrazrecha);
                }
                
                paso = "Borrando usuario encargado";
                logger.debug(paso);
                despachadorDAO.borrarUsuarioYSucursalEncargado(ntramite);
                
                paso = "Cerrando historial anterior";
                logger.debug(paso);
                despachadorDAO.cerrarHistorialTramite(ntramite, fechaHoy, cdusuariSes, cdsisrolSes, status);
                
                result.setMessage(Utils.join("Tr\u00e1mite rechazado con las siguientes observaciones: ", comments));
                
                if (sinGrabarDetalle == false) {
                    paso = "Guardando detalle";
                    logger.debug(paso);
                    mesaControlDAO.movimientoDetalleTramite(
                            ntramite,
                            fechaHoy,
                            null, // cdclausu
                            result.getMessage(),
                            cdusuariSes,
                            null, // cdmotivo
                            cdsisrolSes,
                            permisoAgente ? "S" : "N",
                            null,
                            null,
                            status,
                            true // cerrado
                            );
                }
                
                paso = "Enviando correos configurados";
                logger.debug(paso);
                flujoMesaControlManager.mandarCorreosStatusTramite(ntramite, cdsisrolSes, porEscalamiento);
                
                /////////////////////////////////////////////////////////////////////
                ////// JAIME: ACTUALIZA EN SIGS QUE LA POLIZA SE PUEDA RENOVAR //////
                try {
                    paso = "Recuperando tr\u00e1mite completo";
                    logger.debug(paso);
                    Map<String,String> tramite = siniestrosDAO.obtenerTramiteCompleto(ntramite);
                    if (tramite != null && !tramite.isEmpty() && tramite.size() > 0 && StringUtils.isNotBlank(tramite.get("RENPOLIEX"))) {
                        paso = "Actualizando p\u00f3liza de SIGS";
                        logger.debug(Utils.log(
                                "\n###########################################",
                                "\n###### actualizaEstatusTramiteMCsigs ######",
                                "\n###### Poliza extraida del sigs: ",
                                tramite.get("RENUNIEXT") , "/",
                                tramite.get("RENRAMO")   , "/",
                                tramite.get("RENPOLIEX")));
                        autosSIGSDAO.actualizaTramiteMC(new PolizaVO(tramite.get("RENUNIEXT"), tramite.get("RENRAMO"), null,
                                tramite.get("RENPOLIEX"), ntramite), "0");//0 para que aparezaca como posible a renovar
                        logger.debug(Utils.log(
                                "\n###### actualizaEstatusTramiteMCsigs ######",
                                "\n###########################################"));
                    }
                } catch (Exception e) {
                    logger.error("Error al actulizar estatus de tramite Mc", e);
                    paso = Utils.manejaExcepcion(e);
                }
                ////// JAIME: ACTUALIZA EN SIGS QUE LA POLIZA SE PUEDA RENOVAR //////
                /////////////////////////////////////////////////////////////////////
	        }
            ////// SI ES UN RECHAZO //////
            //////////////////////////////
                
            ////////////////////////////////
            ////// SI ES REASIGNACION //////
            else if (StringUtils.isNotBlank(cdusuariDes) && StringUtils.isNotBlank(cdsisrolDes)) {
                paso = "Recuperando sucursal del usuario";
                logger.debug(paso);
                String cdunieco = despachadorDAO.recuperarSucursalUsuarioPorTramite(cdusuariDes, ntramite);
                
                paso = "Actualizando tr\u00e1mite";
                logger.debug(paso);
                flujoMesaControlDAO.actualizarStatusTramite(
                        ntramite,
                        null, //status nulo para que mantenga el mismo
                        fechaHoy,
                        cdusuariDes,
                        cdunieco);
                
                paso = "Cerrando historial anterior";
                logger.debug(paso);
                despachadorDAO.cerrarHistorialTramite(ntramite, fechaHoy, cdusuariSes, cdsisrolSes, status);
                
                paso = "Abriendo historial nuevo";
                logger.debug(paso);
                flujoMesaControlDAO.guardarHistoricoTramite(
                        fechaHoy,
                        ntramite,
                        cdusuariDes,
                        cdsisrolDes,
                        status,
                        cdunieco,
                        ConstantesDespachador.TIPO_ASIGNACION_REASIGNA);
                
                paso = "Recuperando nombre de usuario";
                logger.debug(paso);
                String dsusuari = despachadorDAO.recuperarNombreUsuario(cdusuariDes);
                
                result.setMessage(Utils.join("Tr\u00e1mite asignado a ", dsusuari, " (", cdusuariDes.toUpperCase(),
                        ") con las siguientes observaciones: ", comments));
                
                if (sinGrabarDetalle == false) {
                    paso = "Guardando detalle";
                    logger.debug(paso);
                    mesaControlDAO.movimientoDetalleTramite(
                            ntramite,
                            fechaHoy,
                            null, // cdclausu
                            result.getMessage(),
                            cdusuariSes,
                            null, // cdmotivo
                            cdsisrolSes,
                            permisoAgente ? "S" : "N",
                            cdusuariDes,
                            cdsisrolDes,
                            status,
                            false // cerrado
                            );
                }
            }
            ////// SI ES REASIGNACION //////
            ////////////////////////////////
                
            ///////////////////////////
            ////// SI ES TURNADO //////
            else {
                /*
                 * Un turnado puede ser a un estatus de captura (agente, mesa) o a un estatus de proceso (cotizador, suscriptor)
                 * o a un estatus final (emitido, impreso).
                 * Si el estatus no es final entonces invocamos al depachador para que nos diga a que usuario le toca el tramite
                 */
                paso = "Recuperando tipo de estatus";
                logger.debug(paso);
                boolean esFinal = despachadorDAO.esStatusFinal(ntramite, status);
                
                RespuestaDespachadorVO destino = null;
                if (!esFinal) {
                    paso = "Invocando despachador";
                    logger.debug(paso);
                    destino = this.despachar(ntramite, status);
                }
                
                if (!esFinal && destino.isEncolado()) { // SI NADIE PUDO ATENDER LO ENCOLAMOS
                    paso = "Encolando tr\u00e1mite";
                    logger.debug(paso);
                    throw new ApplicationException("No hay usuarios disponibles para atender el tr\u00e1mite");
                } else {
                    if (esFinal) { // EMITIDO
                        paso = "Actualizando tr\u00e1mite";
                        logger.debug(paso);
                        flujoMesaControlDAO.actualizarStatusTramite(
                                ntramite,
                                status,
                                fechaHoy,
                                null, // sin usuario (se mantiene)
                                null  // sin sucursal (se mantiene)
                                );
                        
                        paso = "Borrando usuario encargado";
                        logger.debug(paso);
                        despachadorDAO.borrarUsuarioYSucursalEncargado(ntramite);
                        
                        paso = "Cerrando historial anterior";
                        logger.debug(paso);
                        despachadorDAO.cerrarHistorialTramite(ntramite, fechaHoy, cdusuariSes, cdsisrolSes, status);
                        
                        paso = "Recuperando descripci\u00f3n de estatus";
                        logger.debug(paso);
                        String dsstatus = this.recuperarDescripcionEstatus(status);
                        
                        result.setMessage(Utils.join("Tr\u00e1mite turnado a estatus \"", dsstatus,
                                "\" con las siguientes observaciones: ", comments));
                        
                        if (sinGrabarDetalle == false) {
                            paso = "Guardando detalle";
                            logger.debug(paso);
                            mesaControlDAO.movimientoDetalleTramite(
                                    ntramite,
                                    fechaHoy,
                                    null, // cdclausu
                                    result.getMessage(),
                                    cdusuariSes,
                                    null, // cdmotivo
                                    cdsisrolSes,
                                    permisoAgente ? "S" : "N",
                                    null,
                                    null,
                                    status,
                                    true // cerrado
                                    );
                        }
                        
                        paso = "Enviando correos configurados";
                        logger.debug(paso);
                        flujoMesaControlManager.mandarCorreosStatusTramite(ntramite, cdsisrolSes, porEscalamiento);
                    } else { // EN SUSCRIPCION o APROBADO
                        paso = "Actualizando tr\u00e1mite";
                        logger.debug(paso);
                        flujoMesaControlDAO.actualizarStatusTramite(
                                ntramite,
                                destino.getStatus(),
                                fechaHoy,
                                destino.getCdusuari(),
                                destino.getCdunieco()
                                );
                        
                        paso = "Cerrando historial anterior";
                        logger.debug(paso);
                        despachadorDAO.cerrarHistorialTramite(ntramite, fechaHoy, cdusuariSes, cdsisrolSes, destino.getStatus());
                        
                        paso = "Abriendo historial nuevo";
                        logger.debug(paso);
                        flujoMesaControlDAO.guardarHistoricoTramite(
                                fechaHoy,
                                ntramite,
                                destino.getCdusuari(),
                                destino.getCdsisrol(),
                                destino.getStatus(),
                                destino.getCdunieco(),
                                destino.getCdtipasig());
                        
                        paso = "Recuperando nombre de usuario";
                        logger.debug(paso);
                        String dsusuari = despachadorDAO.recuperarNombreUsuario(destino.getCdusuari());
                        
                        paso = "Recuperando descripci\u00f3n de estatus";
                        logger.debug(paso);
                        String dsstatus = this.recuperarDescripcionEstatus(destino.getStatus());
                        
                        paso = "Recuperando descripci\u00f3n de rol";
                        logger.debug(paso);
                        String dssisrol = this.recuperarDescripcionRol(destino.getCdsisrol());
                        
                        result.setMessage(Utils.join("Tr\u00e1mite enviado a ", dsusuari, " (", destino.getCdusuari().toUpperCase(),
                                ", sucursal ", destino.getCdunieco(), ", rol ", dssisrol,") en estatus \"",
                                dsstatus, "\"  con las siguientes observaciones: ", comments));
                        
                        if (sinGrabarDetalle == false) {
                            paso = "Guardando detalle";
                            logger.debug(paso);
                            mesaControlDAO.movimientoDetalleTramite(
                                    ntramite,
                                    fechaHoy,
                                    null, // cdclausu
                                    result.getMessage(),
                                    cdusuariSes,
                                    null, // cdmotivo
                                    cdsisrolSes,
                                    permisoAgente ? "S" : "N",
                                    destino.getCdusuari(),
                                    destino.getCdsisrol(),
                                    status,
                                    false // cerrado
                                    );
                        }
                        
                        paso = "Enviando correos configurados";
                        logger.debug(paso);
                        flujoMesaControlManager.mandarCorreosStatusTramite(ntramite, cdsisrolSes, porEscalamiento);
                    }
                }
            }
	        ////// SI ES TURNADO //////
	        ///////////////////////////
	    } catch (Exception ex) {
	        Utils.generaExcepcion(ex, paso);
	    }
        logger.debug(Utils.log(
                "\n@@@@@@ result = ", result,
                "\n@@@@@@ turnarTramite @@@@@@",
                "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@"));
	    return result;
	}
	
	private String recuperarDescripcionEstatus (String status) throws Exception {
	    String desc = null, paso = null;
	    try {
	        paso = "Recuperando los estatus";
	        List<Map<String, String>> lista = flujoMesaControlDAO.recuperaTestadomc(status);
	        Utils.validate(lista, "No hay descripci\u00f3n del estatus");
	        for (Map<String, String> statusIte : lista) {
	            if (statusIte.get("CDESTADOMC").equals(status)) {
	                desc = statusIte.get("DSESTADOMC");
	                break;
	            }
	        }
	        Utils.validate(desc, "No hay descripci\u00f3n del estatus");
	    } catch (Exception ex) {
	        Utils.generaExcepcion(ex, paso);
	    }
	    return desc;
	}
    
    private String recuperarDescripcionRol (String cdsisrol) throws Exception {
        String desc = null, paso = null;
        try {
            paso = "Recuperando los roles";
            List<Map<String, String>> lista = consultasDAO.recuperarRolesTodos();
            Utils.validate(lista, "No hay descripci\u00f3n del rol");
            for (Map<String, String> rolIte : lista) {
                if (rolIte.get("CDSISROL").equals(cdsisrol)) {
                    desc = rolIte.get("DSSISROL");
                    break;
                }
            }
            Utils.validate(desc, "No hay descripci\u00f3n del rol");
        } catch (Exception ex) {
            Utils.generaExcepcion(ex, paso);
        }
        return desc;
    }
    
    @Override
    @Deprecated
    public String recuperarRolTrabajoEstatus (String cdtipflu, String cdflujomc, String estatus) throws Exception {
        return despachadorDAO.recuperarRolTrabajoEstatus(cdtipflu, cdflujomc, estatus);
    }
}