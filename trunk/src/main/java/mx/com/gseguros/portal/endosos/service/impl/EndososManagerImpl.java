package mx.com.gseguros.portal.endosos.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.catalogos.dao.PersonasDAO;
import mx.com.gseguros.portal.cotizacion.dao.CotizacionDAO;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaBaseVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaImapSmapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSmapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaVoidVO;
import mx.com.gseguros.portal.cotizacion.model.ParametroEndoso;
import mx.com.gseguros.portal.endosos.dao.EndososDAO;
import mx.com.gseguros.portal.endosos.service.EndososManager;
import mx.com.gseguros.portal.general.dao.PantallasDAO;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.model.RespuestaVO;
import mx.com.gseguros.portal.general.util.EstatusTramite;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.portal.general.util.Ramo;
import mx.com.gseguros.portal.general.util.TipoEndoso;
import mx.com.gseguros.portal.general.util.TipoSituacion;
import mx.com.gseguros.portal.general.util.TipoTramite;
import mx.com.gseguros.portal.mesacontrol.dao.MesaControlDAO;
import mx.com.gseguros.utils.HttpUtil;
import mx.com.gseguros.utils.Utilerias;
import mx.com.gseguros.ws.ice2sigs.service.Ice2sigsService;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

public class EndososManagerImpl implements EndososManager
{
    private static final Logger logger = Logger.getLogger(EndososManagerImpl.class);
    
    private Map<String,Object> session;
	private EndososDAO      endososDAO;
	private CotizacionDAO   cotizacionDAO;
	private PantallasDAO    pantallasDAO;
	private MesaControlDAO  mesaControlDAO;
	private PersonasDAO     personasDAO;
	@Autowired
	private Ice2sigsService ice2sigsService;
	
	private static final SimpleDateFormat renderFechas = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public List<Map<String, String>> obtenerEndosos(Map<String, String> params) throws Exception
	{
		logger.debug("EndososManager obtenerEndosos params: "+params);
		List<Map<String,String>> lista=endososDAO.obtenerEndosos(params);
		lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
		logger.debug("EndososManager obtenerEndosos lista size: "+lista.size());
		return lista;
	}
	
	@Override
	public Map<String, String> guardarEndosoNombres(Map<String, Object> params) throws Exception
	{
		logger.debug("EndososManager guardarEndosoNombres params: "+params);
		Map<String,String> mapa=endososDAO.guardarEndosoNombres(params);
		logger.debug("EndososManager guardarEndosoNombres response map: "+mapa);
        return mapa;
	}
	
	@Override
	public Map<String, String> confirmarEndosoB(Map<String, String> params) throws Exception
	{
		logger.debug("EndososManager confirmarEndosoB params: "+params);
		Map<String,String> mapa=endososDAO.confirmarEndosoB(params);
		logger.debug("EndososManager confirmarEndosoB response map: "+mapa);
        return mapa;
	}
	
	@Override
	public Map<String, String> guardarEndosoDomicilio(Map<String, Object> params) throws Exception
	{
		logger.debug("EndososManager guardarEndosoDomicilio params: "+params);
		Map<String,String> mapa=endososDAO.guardarEndosoNombres(params);
		logger.debug("EndososManager guardarEndosoDomicilio response map: "+mapa);
        return mapa;
	}
	
	/**
	 * PKG_CONSULTA.P_reImp_documentos
	 */
	@Override
	public List<Map<String, String>> reimprimeDocumentos(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String tipmov
			)throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		params.put("pv_nmsuplem_i" , nmsuplem);
		params.put("pv_tipmov_i"   , tipmov);
		logger.debug("EndososManager reimprimeDocumentos params: "+params);
		List<Map<String,String>> lista=endososDAO.reimprimeDocumentos(params);
		lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
		logger.debug("EndososManager reimprimeDocumentos lista size: "+lista.size());
		return lista;
	}
	
	@Override
	public List<Map<String, String>> obtieneCoberturasDisponibles(Map<String, String> params) throws Exception
	{
		logger.debug("EndososManager obtieneCoberturasDisponibles params: "+params);
		List<Map<String,String>> lista=endososDAO.obtieneCoberturasDisponibles(params);
		lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
		logger.debug("EndososManager obtieneCoberturasDisponibles lista size: "+lista.size());
		return lista;
	}
	
	@Override
	public Map<String, String> guardarEndosoCoberturas(Map<String, Object> params) throws Exception
	{
		logger.debug("EndososManager guardarEndosoCoberturas params: "+params);
		Map<String,String> mapa=endososDAO.guardarEndosoCoberturas(params);
		logger.debug("EndososManager guardarEndosoCoberturas response map: "+mapa);
        return mapa;
	}
	
	@Override
	public List<Map<String, String>> obtenerAtributosCoberturas(Map<String, String> params) throws Exception
	{
		logger.debug("EndososManager obtenerAtributosCoberturas params: "+params);
		List<Map<String,String>> lista=endososDAO.obtenerAtributosCoberturas(params);
		lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
		logger.debug("EndososManager obtenerAtributosCoberturas lista size: "+lista.size());
		return lista;
	}
	
	//PKG_COTIZA.P_EJECUTA_SIGSVALIPOL_END
	@Override
	public Map<String,Object> sigsvalipolEnd(Map<String, String> params) throws Exception
	{
		logger.debug("EndososManager sigsvalipolEnd params: "+params);
		Map<String,Object> mapa=endososDAO.sigsvalipolEnd(params);
		logger.debug("EndososManager sigsvalipolEnd response map: "+mapa);
        return mapa;
	}
	
	@Override
	public Map<String,Object> sigsvalipolEnd(
			String cdusuari
			,String cdelemento
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,String cdtipsit
			,String cdtipsup
			) throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>(0);
		params.put("pv_cdusuari_i" , cdusuari);
		params.put("pv_cdelemen_i" , cdelemento);
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		params.put("pv_nmsituac_i" , nmsituac);
		params.put("pv_nmsuplem_i" , nmsuplem);
		params.put("pv_cdtipsit_i" , cdtipsit);
		params.put("pv_cdtipsup_i" , cdtipsup);
		return this.sigsvalipolEnd(params);
	}
	
	@Override
	public Map<String, String> guardarEndosoClausulas(Map<String, Object> params) throws Exception
	{
		logger.debug("EndososManager guardarEndosoClausulas params: "+params);
		Map<String,String> mapa=endososDAO.guardarEndosoClausulas(params);
		logger.debug("EndososManager guardarEndosoClausulas response map: "+mapa);
        return mapa;
	}
	
	//PKG_ENDOSOS.P_CALC_VALOR_ENDOSO
	@Override
	public Map<String,String> calcularValorEndoso(Map<String,Object>params) throws Exception
	{
		logger.debug("EndososManager calcularValorEndoso params: "+params);
		Map<String,String> mapa=endososDAO.calcularValorEndoso(params);
		logger.debug("EndososManager calcularValorEndoso response map: "+mapa);
        return mapa;
	}
	
	@Override
	public Map<String,String> calcularValorEndoso(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,Date   feinival
			,String cdtipsup) throws Exception
	{
		Map<String,Object>params=new HashMap<String,Object>();
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		params.put("pv_nmsituac_i" , nmsituac);
		params.put("pv_nmsuplem_i" , nmsuplem);
		params.put("pv_feinival_i" , feinival);
		params.put("pv_cdtipsup_i" , cdtipsup);
        return this.calcularValorEndoso(params);
	}
	
	//PKG_ENDOSOS.P_ENDOSO_INICIA
	@Override
	public Map<String,String> iniciarEndoso(Map<String, String> params) throws Exception
	{
		logger.debug("EndososManager iniciarEndoso params: "+params);
		Map<String,String> mapa=endososDAO.iniciarEndoso(params);
		logger.debug("EndososManager iniciarEndoso response map: "+mapa);
        return mapa;
	}
	
	/**
	 * PKG_ENDOSOS.P_ENDOSO_INICIA
	 */
	@Override
	public Map<String,String> iniciarEndoso(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String fecha
			,String cdelemento
			,String cdusuari
			,String proceso
			,String cdtipsup) throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		params.put("pv_fecha_i"    , fecha);
		params.put("pv_cdelemen_i" , cdelemento);
		params.put("pv_cdusuari_i" , cdusuari);
		params.put("pv_proceso_i"  , proceso);
		params.put("pv_cdtipsup_i" , cdtipsup);
        return this.iniciarEndoso(params);
	}
	
	@Override
	public void insertarTworksupEnd(Map<String, String> params) throws Exception
	{
		logger.debug("EndososManager insertarTworksupEnd params: "+params);
		endososDAO.insertarTworksupEnd(params);
		logger.debug("EndososManager insertarTworksupEnd end");
	}
	
	@Override
	public void insertarTworksupEnd(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdtipsup
			,String nmsuplem
			,String nmsituac) throws Exception
	{
		Map<String,String>params=new LinkedHashMap<String,String>(0);
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		params.put("pv_cdtipsup_i" , cdtipsup);
		params.put("pv_nmsuplem_i" , nmsuplem);
		params.put("pv_nmsituac_i" , nmsituac);
        this.insertarTworksupEnd(params);
	}
	
	//PKG_SATELITES.P_INSERTA_TWORKSUP_SIT_TODAS
	@Override
	public void insertarTworksupSitTodas(Map<String, String> params) throws Exception
	{
		logger.debug("EndososManager insertarTworksupSitTodas params: "+params);
		endososDAO.insertarTworksupSitTodas(params);
		logger.debug("EndososManager insertarTworksupSitTodas end");
	}
	
	//PKG_SATELITES.P_OBTIENE_DATOS_MPOLISIT
	@Override
	public Map<String, String> obtieneDatosMpolisit(Map<String, String> params) throws Exception
	{
		logger.debug("EndososManager obtieneDatosMpolisit params: "+params);
		Map<String,String> mapa=endososDAO.obtieneDatosMpolisit(params);
		logger.debug("EndososManager obtieneDatosMpolisit response map: "+mapa);
        return mapa;
	}
	
	//PKG_SATELITES.P_OBTIENE_DATOS_MPOLISIT
	@Override
	public Map<String, String> obtieneDatosMpolisit(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza) throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
        return this.obtieneDatosMpolisit(params);
	}
	
	@Override
	public List<Map<String, String>> obtenerNombreEndosos(String cdsisrol) throws Exception
	{
		logger.debug("EndososManager obtenerNombreEndosos");
		List<Map<String,String>> lista=endososDAO.obtenerNombreEndosos(cdsisrol);
		lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
		logger.debug("EndososManager obtenerNombreEndosos lista size: "+lista.size());
		return lista;
	}
	
	@Override
	public void actualizarFenacimi(Map<String, String> params) throws Exception
	{
		logger.debug("EndososManager actualizarFenacimi params: "+params);
		endososDAO.actualizarFenacimi(params);
		logger.debug("EndososManager actualizarFenacimi end");
	}
	
	@Override
	public void actualizarSexo(Map<String, String> params) throws Exception
	{
		logger.debug("EndososManager actualizarSexo params: "+params);
		endososDAO.actualizarSexo(params);
		logger.debug("EndososManager actualizarSexo end");
	}
	
	@Override
	public List<Map<String, String>> obtenerCdpersonMpoliper(Map<String, String> params) throws Exception
	{
		logger.debug("EndososManager obtenerCdpersonMpoliper params: "+params);
		List<Map<String,String>> lista=endososDAO.obtenerCdpersonMpoliper(params);
		lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
		logger.debug("EndososManager obtenerCdpersonMpoliper lista size: "+lista.size());
		return lista;
	}
	
	@Override
	public String obtenerNtramiteEmision(String cdunieco,String cdramo,String estado,String nmpoliza) throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		logger.debug("EndososManager obtenerNtramiteEmision params: "+params);
		List<Map<String,String>> lista=endososDAO.obtenerNtramiteEmision(params);
		lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
		logger.debug("EndososManager obtenerNtramiteEmision lista: "+lista);
		String ntramite=lista.size()>0?lista.get(0).get("NTRAMITE"):"";
		logger.debug("EndososManager obtenerNtramiteEmision ntramite: "+ntramite);
		return ntramite;
	}
	
	
	@Override
	public RespuestaVO validaEndosoAnterior(String cdunieco, String cdramo, String estado, String nmpoliza, String cdtipsup) {
		
		RespuestaVO resp = new RespuestaVO();
		try {
			Map<String,String> params = new HashMap<String,String>();
			params.put("pv_cdunieco_i", cdunieco);
			params.put("pv_cdramo_i"  , cdramo);
			params.put("pv_estado_i"  , estado);
			params.put("pv_nmpoliza_i", nmpoliza);
			params.put("pv_cdtipsup_i", cdtipsup);
			logger.debug(new StringBuilder("EndososManager validaEndosoAnterior params: ").append(params).toString());
			endososDAO.validaEndosoAnterior(params);
			resp.setSuccess(true);
		} catch(Exception ex) {
			logger.error(new StringBuilder().append("Error tratando de acceder a pantalla de endoso: ").append(cdtipsup).toString(), ex);
			//resp.setSuccess(false); //No es necesario asignarle valor, un atributo boolean de una clase por default es false
			resp.setMensaje(ex.getMessage());
		}
		return resp;
	}
	
	
	//PKG_ENDOSOS.P_INS_NEW_DEDUCIBLE_TVALOSIT
	@Override
	public void actualizaDeducibleValosit(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String deducible) throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("pv_cdunieco_i"  , cdunieco);
		params.put("pv_cdramo_i"    , cdramo);
		params.put("pv_estado_i"    , estado);
		params.put("pv_nmpoliza_i"  , nmpoliza);
		params.put("pv_nmsuplem_i"  , nmsuplem);
		params.put("pv_deducible_i" , deducible);
		logger.debug("EndososManager actualizaDeducibleValosit params: "+params);
		endososDAO.actualizaDeducibleValosit(params);
		logger.debug("EndososManager actualizaDeducibleValosit end");
	}
	
	//PKG_ENDOSOS.P_INS_NEW_DEDUCIBLE_TVALOSIT
	@Override
	public void actualizaCopagoValosit(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String deducible) throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("pv_cdunieco_i"  , cdunieco);
		params.put("pv_cdramo_i"    , cdramo);
		params.put("pv_estado_i"    , estado);
		params.put("pv_nmpoliza_i"  , nmpoliza);
		params.put("pv_nmsuplem_i"  , nmsuplem);
		params.put("pv_deducible_i" , deducible);
		logger.debug("EndososManager actualizaCopagoValosit params: "+params);
		endososDAO.actualizaCopagoValosit(params);
		logger.debug("EndososManager actualizaCopagoValosit end");
	}
	
	//P_CLONAR_POLIZA_REEXPED
	@Override
	public Map<String,String> pClonarPolizaReexped(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String fecha
			,String cdplan) throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		params.put("pv_feinival_i" , fecha);
		params.put("pv_cdplan_i"   , cdplan);
		logger.debug("EndososManager pClonarPolizaReexped params: "+params);
		Map<String,String> mapa=endososDAO.pClonarPolizaReexped(params);
		logger.debug("EndososManager pClonarPolizaReexped response map: "+mapa);
        return mapa;
	}
	
	//PKG_CONSULTA.P_OBT_VALOSIT_ULTIMA_IMAGEN
	/*
	CDUNIECO,CDRAMO,ESTADO,NMPOLIZA,NMSITUAC,NMSUPLEM,STATUS,CDTIPSIT,OTVALOR01,OTVALOR02
	,OTVALOR03,OTVALOR04,OTVALOR05,OTVALOR06,OTVALOR07,OTVALOR08,OTVALOR09,OTVALOR10,OTVALOR11
	,OTVALOR12,OTVALOR13,OTVALOR14,OTVALOR15,OTVALOR16,OTVALOR17,OTVALOR18,OTVALOR19,OTVALOR20
	,OTVALOR21,OTVALOR22,OTVALOR23,OTVALOR24,OTVALOR25,OTVALOR26,OTVALOR27,OTVALOR28,OTVALOR29
	,OTVALOR30,OTVALOR31,OTVALOR32,OTVALOR33,OTVALOR34,OTVALOR35,OTVALOR36,OTVALOR37,OTVALOR38
	,OTVALOR39,OTVALOR40,OTVALOR41,OTVALOR42,OTVALOR43,OTVALOR44,OTVALOR45,OTVALOR46,OTVALOR47
	,OTVALOR48,OTVALOR49,OTVALOR50
	*/
	@Override
	public List<Map<String, String>> obtenerValositUltimaImagen(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem) throws Exception
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ obtenerValositUltimaImagen @@@@@@")
				.append("\n@@@@@@ cdunieco=").append(cdunieco)
				.append("\n@@@@@@ cdramo=")  .append(cdramo)
				.append("\n@@@@@@ estado=")  .append(estado)
				.append("\n@@@@@@ nmpoliza=").append(nmpoliza)
				.append("\n@@@@@@ nmsuplem=").append(nmsuplem)
				.toString()
				);
		Map<String,String>params=new HashMap<String,String>();
		logger.debug("EndososManager obtenerValositUltimaImagen params: "+params);
		List<Map<String,String>> lista=endososDAO.obtenerValositUltimaImagen(cdunieco,cdramo,estado,nmpoliza,nmsuplem);
		lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
		logger.debug("EndososManager obtenerValositUltimaImagen lista size: "+lista.size());
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ lista=").append(lista)
				.append("\n@@@@@@ obtenerValositUltimaImagen @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return lista;
	}
	
	//PKG_ENDOSOS.P_INS_NEW_EXTRAPRIMA_TVALOSIT
	@Override
	public void actualizaExtraprimaValosit(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,String nmsuplem
			,String extraprima) throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("pv_cdunieco_i"   , cdunieco);
		params.put("pv_cdramo_i"     , cdramo);
		params.put("pv_estado_i"     , estado);
		params.put("pv_nmpoliza_i"   , nmpoliza);
		params.put("pv_nmsituac_i"   , nmsituac);
		params.put("pv_nmsuplem_i"   , nmsuplem);
		params.put("pv_extraprima_i" , extraprima);
		logger.debug("EndososManager actualizaExtraprimaValosit params: "+params);
		endososDAO.actualizaExtraprimaValosit(params);
		logger.debug("EndososManager actualizaExtraprimaValosit end");
	}
	
	@Override
	public void insertarPolizaCdperpag(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdperpag) throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("pv_cdunieco_i",cdunieco);
		params.put("pv_cdramo_i",cdramo);
		params.put("pv_estado_i",estado);
		params.put("pv_nmpoliza_i",nmpoliza);
		params.put("pv_nmsuplem_i",nmsuplem);
		params.put("pv_cdperpag_i",cdperpag);
		logger.debug("EndososManager insertaPolizaCdperpag params: "+params);
		endososDAO.insertarPolizaCdperpag(params);
		logger.debug("EndososManager insertaPolizaCdperpag end");
	}
	
	/**
	 * PKG_ENDOSOS.P_GET_FEINIVAL_END_FP
	 */
	@Override
	public Date obtenerFechaEndosoFormaPago(String cdunieco,String cdramo,String estado,String nmpoliza) throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		logger.debug("EndososManager obtenerFechaEndosoFormaPago params: "+params);
		Date fecha=endososDAO.obtenerFechaEndosoFormaPago(params);
		logger.debug("EndososManager obtenerFechaEndosoFormaPago fecha: "+fecha);
		return fecha;
	}
	
	/**
	 * P_CALC_RECIBOS_SUB_ENDOSO_FP
	 */
	@Override
	public void calcularRecibosEndosoFormaPago(String cdunieco,String cdramo,
			String estado,String nmpoliza,String nmsuplem) throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("pv_cdunieco" , cdunieco);
		params.put("pv_cdramo"   , cdramo);
		params.put("pv_estado"   , estado);
		params.put("pv_nmpoliza" , nmpoliza);
		params.put("pv_nmsuplem" , nmsuplem);
		logger.debug("EndososManager calcularRecibosEndosoFormaPago params: "+params);
		endososDAO.calcularRecibosEndosoFormaPago(params);
		logger.debug("EndososManager calcularRecibosEndosoFormaPago fin");
	}
	
	/**
	 * P_CALCULA_COMISION_BASE
	 */
	@Override
	public void calcularComisionBase(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem) throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		params.put("pv_nmsuplem_i" , nmsuplem);
		logger.debug("EndososManager calcularComisionBase params: "+params);
		endososDAO.calcularComisionBase(params);
		logger.debug("EndososManager calcularComisionBase fin");
	}
	
	/**
	 * PKG_CONSULTA.P_GET_AGENTE_POLIZA
	 * @return a.cdunieco,
			a.cdramo,
			a.estado,
			a.nmpoliza,
			a.cdagente,
			a.nmsuplem,
			a.status,
			a.cdtipoag,
			porredau,
			a.porparti,
			nombre,
			cdsucurs,
			nmcuadro
	 */
	@Override
	public List<Map<String,String>> obtenerAgentesEndosoAgente(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem) throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("PV_CDUNIECO_I" , cdunieco);
		params.put("PV_CDRAMO_I"   , cdramo);
		params.put("PV_ESTADO_I"   , estado);
		params.put("PV_NMPOLIZA_I" , nmpoliza);
		params.put("PV_NMSUPLEM_I" , nmsuplem);
		logger.debug("EndososManager obtenerAgentesEndosoAgente params: "+params);
		List<Map<String,String>>lista=endososDAO.obtenerAgentesEndosoAgente(params);
		lista=lista!=null?lista:new ArrayList<Map<String,String>>();
		logger.debug("EndososManager obtenerAgentesEndosoAgente lista size: "+lista.size());
		return lista;
	}
	
	/**
	 * PKG_SATELITES.P_MOV_MPOLIAGE
	 */
	@Override
	public void pMovMpoliage(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String cdagente
			,String nmsuplem
			,String status
			,String cdtipoag
			,String porredau
			,String nmcuadro
			,String cdsucurs
			,String accion
			,String ntramite
			,String porparti
			) throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		params.put("pv_cdagente_i" , cdagente);
		params.put("pv_nmsuplem_i" , nmsuplem);
		params.put("pv_status_i"   , status);
		params.put("pv_cdtipoag_i" , cdtipoag);
		params.put("pv_porredau_i" , porredau);
		params.put("pv_nmcuadro_i" , nmcuadro);
		params.put("pv_cdsucurs_i" , cdsucurs);
		params.put("pv_accion_i"   , accion);
		params.put("pv_ntramite_i" , ntramite);
		params.put("pv_porparti_i" , porparti);
		logger.debug("EndososManager pMovMpoliage params: "+params);
		endososDAO.pMovMpoliage(params);
		logger.debug("EndososManager pMovMpoliage fin");
	}
	
	/**
	 * PKG_SATELITES.P_GET_NMSUPLEM_EMISION
	 */
	@Override
	public String pGetSuplemEmision(String cdunieco,String cdramo,String estado,String nmpoliza) throws Exception
	{
		String nmsuplem = "";
		Map<String,String>params=new HashMap<String,String>();
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		logger.debug("EndososManager pGetSuplemEmision params: "+params);
		nmsuplem = endososDAO.pGetSuplemEmision(params);
		logger.debug("EndososManager pGetSuplemEmision nmsuplem: "+nmsuplem);
		return nmsuplem;
	}
	
	@Override
	public String obtieneFechaInicioVigenciaPoliza
	(
		String cdunieco,
		String cdramo,
		String estado,
		String nmpoliza
		) throws Exception
	{
		return endososDAO.obtieneFechaInicioVigenciaPoliza(cdunieco,cdramo,estado,nmpoliza);
	}
	
	@Override
	public boolean validaEndosoSimple
	(
			String cdunieco,
			String cdramo,
			String estado,
			String nmpoliza
			) throws Exception
	{
		return endososDAO.validaEndosoSimple(cdunieco,cdramo,estado,nmpoliza);
	}
	
	@Override
	public void validaNuevaCobertura(String cdgarant, Date fenacimi) throws Exception
	{
		logger.info(""
				+ "\n##################################"
				+ "\n###### validaNuevaCobertura ######"
				);
		endososDAO.validaNuevaCobertura(cdgarant,fenacimi);
		logger.info(""
				+ "\n###### validaNuevaCobertura ######"
				+ "\n##################################"
				);
	}
	
	@Override
	public void calcularRecibosCambioAgente(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdagente) throws Exception
	{
		logger.info(""
				+ "\n#########################################"
				+ "\n###### calcularRecibosCambioAgente ######"
				);
		endososDAO.calcularRecibosCambioAgente(cdunieco,cdramo,estado,nmpoliza,nmsuplem,cdagente);
		logger.info(""
				+ "\n###### calcularRecibosCambioAgente ######"
				+ "\n#########################################"
				);
	}
	
	@Override
	public List<Map<String,String>> habilitaRecibosSubsecuentes(
			Date fechaDeInicio
			,Date fechaDeFin
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza) throws Exception
	{
		return endososDAO.habilitaRecibosSubsecuentes(fechaDeInicio,fechaDeFin,cdunieco,cdramo,estado,nmpoliza);
	}
	
	@Override
	public void validaEstadoCodigoPostal(Map<String,String>params) throws Exception{
		endososDAO.validaEstadoCodigoPostal(params);
	}
	
	@Override
	public void actualizaTvalositCoberturasAdicionales(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdtipsit
			,String cdtipsup) throws Exception
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ actualizaTvalositCoberturasAdicionales @@@@@@")
				.append("\n@@@@@@ cdunieco=").append(cdunieco)
				.append("\n@@@@@@ cdramo=")  .append(cdramo)
				.append("\n@@@@@@ estado=")  .append(estado)
				.append("\n@@@@@@ nmpoliza=").append(nmpoliza)
				.append("\n@@@@@@ nmsuplem=").append(nmsuplem)
				.append("\n@@@@@@ cdtipsit=").append(cdtipsit)
				.append("\n@@@@@@ cdtipsup=").append(cdtipsup)
				.toString()
				);
		
		endososDAO.actualizaTvalositCoberturasAdicionales(cdunieco,cdramo,estado,nmpoliza,nmsuplem,cdtipsit,cdtipsup);
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ actualizaTvalositCoberturasAdicionales @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
	}
	
	@Override
	public ManagerRespuestaImapSmapVO obtenerComponenteSituacionCobertura(String cdramo,String cdtipsit,String cdtipsup,String cdgarant)
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ obtenerComponenteSituacionCobertura @@@@@@")
				.append("\n@@@@@@ cdramo=")  .append(cdramo)
				.append("\n@@@@@@ cdtipsit=").append(cdtipsit)
				.append("\n@@@@@@ cdtipsup=").append(cdtipsup)
				.append("\n@@@@@@ cdgarant=").append(cdgarant)
				.toString()
				);
		
		ManagerRespuestaImapSmapVO resp = new ManagerRespuestaImapSmapVO(true);
		resp.setSmap(new HashMap<String,String>());
		resp.setImap(new HashMap<String,Item>());
		
		ComponenteVO comp = null;
		
		//obtener componente
		try
		{
			comp = endososDAO.obtenerComponenteSituacionCobertura(cdramo,cdtipsit,cdtipsup,cdgarant);
		}
		catch(ApplicationException ax)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(new StringBuilder(ax.getMessage()).append(" #").append(timestamp).toString());
			resp.setRespuestaOculta(ax.getMessage());
			logger.error(resp.getRespuesta(),ax);
		}
		catch(Exception ex)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(new StringBuilder("Error al obtener componente #").append(timestamp).toString());
			resp.setRespuestaOculta(ex.getMessage());
			logger.error(resp.getRespuesta(),ex);
		}
		
		//transformarlo a item
		if(resp.isExito())
		{
			try
			{
				if(comp==null)
				{
					resp.getSmap().put("CONITEM" , "false");
				}
				else
				{
					resp.getSmap().put("CONITEM" , "true");
					
					GeneradorCampos gc = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
					gc.setCdramo(cdramo);
					gc.setCdtipsit(cdtipsit);
					
					List<ComponenteVO>lista=new ArrayList<ComponenteVO>();
					lista.add(comp);
					
					gc.generaComponentes(lista, true, false, true, false, false, false);
					
					resp.getImap().put("item",gc.getItems());
				}
			}
			catch(Exception ex)
			{
				long timestamp = System.currentTimeMillis();
				resp.setExito(false);
				resp.setRespuesta(new StringBuilder("Error al construir componente #").append(timestamp).toString());
				resp.setRespuestaOculta(ex.getMessage());
				logger.error(resp.getRespuesta(),ex);
			}
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ obtenerComponenteSituacionCobertura @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public void actualizaTvalositSitaucionCobertura(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String cdatribu
			,String otvalor)
	{
		try
		{
			endososDAO.actualizaTvalositSitaucionCobertura(cdunieco,cdramo,estado,nmpoliza,nmsuplem,cdatribu,otvalor);
		}
		catch(Exception ex)
		{
			long timestamp = System.currentTimeMillis();
			logger.error(new StringBuilder("Error al actualizar tvalosit situacion cobertura #").append(timestamp).toString(),ex);
		}
	}
	
	@Override
	public ManagerRespuestaImapSmapVO endosoAtributosSituacionGeneral(
			String cdunieco
			,String cdramo
			,String cdtipsit
			,String estado
			,String nmpoliza
			,String cdusuari
			,String cdtipsup)
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ endosoAtributosSituacionGeneral @@@@@@")
				.append("\n@@@@@@ cdunieco=").append(cdunieco)
				.append("\n@@@@@@ cdramo=")  .append(cdramo)
				.append("\n@@@@@@ cdtipsit=").append(cdtipsit)
				.append("\n@@@@@@ estado=")  .append(estado)
				.append("\n@@@@@@ nmpoliza=").append(nmpoliza)
				.append("\n@@@@@@ cdusuari=").append(cdusuari)
				.append("\n@@@@@@ cdtipsup=").append(cdtipsup)
				.toString()
				);
		
		ManagerRespuestaImapSmapVO resp = new ManagerRespuestaImapSmapVO(true);
		resp.setImap(new HashMap<String,Item>());
		
		//validar endoso anterior
		try
		{
			endososDAO.validaEndosoAnterior(cdunieco,cdramo,estado,nmpoliza,cdtipsup);
		}
		catch(Exception ex)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(new StringBuilder(ex.getMessage()).append(" #").append(timestamp).toString());
			resp.setRespuestaOculta(ex.getMessage());
			logger.error(resp.getRespuesta(),ex);
		}
		
		List<ComponenteVO>tatrisit=null;
		
		//obtener tatrisit
		if(resp.isExito())
		{
			try
			{
				tatrisit = cotizacionDAO.cargarTatrisit(cdtipsit, cdusuari);
			}
			catch(Exception ex)
			{
				long timestamp = System.currentTimeMillis();
				resp.setExito(false);
				resp.setRespuesta(new StringBuilder("Error al obtener atributos #").append(timestamp).toString());
				resp.setRespuestaOculta(ex.getMessage());
				logger.error(resp.getRespuesta(),ex);
			}
		}
		
		//obtener relacion atributos endoso
		if(resp.isExito())
		{
			try
			{
				resp.setSmap(endososDAO.obtenerParametrosEndoso(
						ParametroEndoso.RELACION_ENDOSO_ATRIBUTO_SITUACION
						,cdramo
						,cdtipsit
						,cdtipsup
						,null));
			}
			catch(ApplicationException ax)
			{
				long timestamp = System.currentTimeMillis();
				resp.setExito(false);
				resp.setRespuesta(new StringBuilder(ax.getMessage()).append(" #").append(timestamp).toString());
				resp.setRespuestaOculta(ax.getMessage());
				logger.error(resp.getRespuesta(),ax);
			}
			catch(Exception ex)
			{
				long timestamp = System.currentTimeMillis();
				resp.setExito(false);
				resp.setRespuesta(new StringBuilder("Error al obtener relacion endoso - atributos #").append(timestamp).toString());
				resp.setRespuestaOculta(ex.getMessage());
				logger.error(resp.getRespuesta(),ex);
			}
		}
		
		GeneradorCampos gc=null;
		
		//crear componentes de tatrisit
		if(resp.isExito())
		{
			try
			{
				List<ComponenteVO>tatrisitAux = new ArrayList<ComponenteVO>();
				for(ComponenteVO tatri:tatrisit)
				{
					if(resp.getSmap().containsValue(tatri.getNameCdatribu()))
					{
						tatrisitAux.add(tatri);
					}
				}
				tatrisit=tatrisitAux;
				logger.debug(new StringBuilder("Atributos para el endoso=").append(tatrisit).toString());
				
				gc = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
				gc.setCdramo(cdramo);
				gc.setCdtipsit(cdtipsit);
				
				gc.generaComponentes(tatrisit, true, false, true, false, false, false);
				resp.getImap().put("nuevoItems",gc.getItems());

				for(ComponenteVO atributo:tatrisit)
				{
					atributo.setSoloLectura(true);
				}
				gc.generaComponentes(tatrisit, true, true, true, false, false, false);
				resp.getImap().put("actualItems"  , gc.getItems());
				resp.getImap().put("actualFields" , gc.getFields());
			}
			catch(Exception ex)
			{
				long timestamp = System.currentTimeMillis();
				resp.setExito(false);
				resp.setRespuesta(new StringBuilder("Error al construir componentes #").append(timestamp).toString());
				resp.setRespuestaOculta(ex.getMessage());
				logger.error(resp.getRespuesta(),ex);
			}
		}
		
		//componentes ajenos a tatrisit
		if(resp.isExito())
		{
			try
			{
				List<ComponenteVO>componentesAux = pantallasDAO.obtenerComponentes(
						null,null,null
						,null,null,null
						,"ENDOSO_ATRI_GRAL","PANEL_LECTURA",null);
				
				gc.generaComponentes(componentesAux, true, false, true, false, false, false);
				resp.getImap().put("lecturaItems",gc.getItems());
				
				componentesAux = pantallasDAO.obtenerComponentes(
						null,null,null
						,null,null,null
						,"ENDOSO_ATRI_GRAL","ITEMS_ENDOSO",null);
				
				gc.generaComponentes(componentesAux, true, false, true, false, false, false);
				resp.getImap().put("endosoItems",gc.getItems());
			}
			catch(Exception ex)
			{
				long timestamp = System.currentTimeMillis();
				resp.setExito(false);
				resp.setRespuesta(new StringBuilder("Error al obtener componentes generales #").append(timestamp).toString());
				resp.setRespuestaOculta(ex.getMessage());
				logger.error(resp.getRespuesta(),ex);
			}
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ endosoAtributosSituacionGeneral @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public ManagerRespuestaSmapVO cargarTvalositTitular(String cdunieco,String cdramo,String estado,String nmpoliza,String nmsuplem)
	{
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ cargarTvalositTitular @@@@@@")
				.append("\n@@@@@@ cdunieco=").append(cdunieco)
				.append("\n@@@@@@ cdramo=")  .append(cdramo)
				.append("\n@@@@@@ estado=")  .append(estado)
				.append("\n@@@@@@ nmpoliza=").append(nmpoliza)
				.append("\n@@@@@@ nmsuplem=").append(nmsuplem)
				.toString()
				);
		
		ManagerRespuestaSmapVO resp=new ManagerRespuestaSmapVO(true);
		
		try
		{
			List<Map<String,String>>tvalosits = this.obtenerValositUltimaImagen(cdunieco,cdramo,estado,nmpoliza,nmsuplem);
			for(Map<String,String>tvalosit:tvalosits)
			{
				if(tvalosit.get("NMSITUAC").equals("1"))
				{
					resp.setSmap(tvalosit);
				}
			}
			if(resp.getSmap()==null)
			{
				throw new ApplicationException("No hay valores para el titular");
			}
			Map<String,String>valores=new HashMap<String,String>();
			for(Entry<String,String>en:resp.getSmap().entrySet())
			{
				String key = en.getKey();
				if(StringUtils.isNotBlank(key)
						&&key.length()>"OTVALOR".length()
						&&key.substring(0, "OTVALOR".length()).equals("OTVALOR")
						)
				{
					valores.put(
							new StringBuilder(
									"parametros.pv_otvalor")
							.append(key.substring("OTVALOR".length()))
							.toString()
							,en.getValue()
							);
				}
			}
			resp.getSmap().putAll(valores);
		}
		catch(ApplicationException ax)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(new StringBuilder(ax.getMessage()).append(" #").append(timestamp).toString());
			resp.setRespuestaOculta(ax.getMessage());
			logger.error(resp.getRespuesta(),ax);
		}
		catch(Exception ex)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(new StringBuilder("Error al obtener valores de atributos de situacion #").append(timestamp).toString());
			resp.setRespuestaOculta(ex.getMessage());
			logger.error(resp.getRespuesta(),ex);
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp)
				.append("\n@@@@@@ cargarTvalositTitular @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	
	@Override
	public ManagerRespuestaVoidVO guardarEndosoAtributosSituacionGeneral(
			String cdunieco, String cdramo, String estado, String nmpoliza, String nmsuplem,
			String cdtipsit, String cdtipsup, String ntramite, String feefecto, Map<String,String>tvalosit, UserVO usuario,
			String rutaDocsPoliza, String rutaServReports, String passServReports)
	{
		String cdelemen = usuario.getEmpresa().getElementoId();
		String cdusuari = usuario.getUser();
		String nmsolici = null;
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.append("\n@@@@@@ guardarEndosoAtributosSituacionGeneral @@@@@@")
				.append("\n@@@@@@ cdunieco=")       .append(cdunieco)
				.append("\n@@@@@@ cdramo=")         .append(cdramo)
				.append("\n@@@@@@ cdtipsit=")       .append(cdtipsit)
				.append("\n@@@@@@ estado=")         .append(estado)
				.append("\n@@@@@@ nmpoliza=")       .append(nmpoliza)
				.append("\n@@@@@@ nmsuplem=")       .append(nmsuplem)
				.append("\n@@@@@@ cdtipsup=")       .append(cdtipsup)
				.append("\n@@@@@@ feefecto=")       .append(feefecto)
				.append("\n@@@@@@ tvalosit=")       .append(tvalosit)
				.append("\n@@@@@@ cdelemen=")       .append(cdelemen)
				.append("\n@@@@@@ cdusuari=")       .append(cdusuari)
				.append("\n@@@@@@ rutaDocsPoliza=") .append(rutaDocsPoliza)
				.append("\n@@@@@@ rutaServReports=").append(rutaDocsPoliza)
				.append("\n@@@@@@ passServReports=").append(rutaDocsPoliza)
				.toString()
				);
		
		ManagerRespuestaVoidVO resp = new ManagerRespuestaVoidVO(true);
		
		Date fechaEfecto = null;
		TipoEndoso enumTipoEndosoElegido = null;
		
		//procesar datos
		try
		{
			fechaEfecto = renderFechas.parse(feefecto);
			
			//Creamos un enum en base al tipo de endoso elegido:
			for (TipoEndoso te : TipoEndoso.values()) {
			    if( cdtipsup.equals(te.getCdTipSup().toString()) ) {
			    	enumTipoEndosoElegido = te;
			    	break;
			    }
			}
		}
		catch(Exception ex)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(new StringBuilder("Error al procesar datos #").append(timestamp).toString());
			resp.setRespuestaOculta(ex.getMessage());
			logger.error(resp.getRespuesta(),ex);
		}
		
		String nmsuplemEndoso = null;
		String nsuplogi       = null;
		
		//iniciar endoso
		try
		{
			Map<String,String>iniciarEndosoResp=endososDAO.iniciarEndoso(
					cdunieco
					,cdramo
					,estado
					,nmpoliza
					,fechaEfecto
					,cdelemen
					,cdusuari
					,"END"
					,cdtipsup);
			nmsuplemEndoso = iniciarEndosoResp.get("pv_nmsuplem_o");
			nsuplogi       = iniciarEndosoResp.get("pv_nsuplogi_o");
		}
		catch(Exception ex)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(new StringBuilder("Error al iniciar endoso #").append(timestamp).toString());
			resp.setRespuestaOculta(ex.getMessage());
			logger.error(resp.getRespuesta(),ex);
		}
		
		//insertar nuevo tvalosit para todos
		try
		{
			Map<String,String>tvalositNuevo=new HashMap<String,String>();
			for(Entry<String,String>en:tvalosit.entrySet())
			{
				String key=en.getKey();
				if(StringUtils.isNotBlank(key)
						&&key.length()>"parametros.pv_".length()
						&&key.substring(0,"parametros.pv_".length()).equals("parametros.pv_")
						)
				{
					tvalositNuevo.put(key.substring("parametros.pv_".length()),en.getValue());
				}
			}
			endososDAO.guardarAtributosSituacionGeneral(cdunieco,cdramo,estado,nmpoliza,nmsuplemEndoso,tvalositNuevo);
		}
		catch(Exception ex)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(new StringBuilder("Error al guardar valores de atributos #").append(timestamp).toString());
			resp.setRespuestaOculta(ex.getMessage());
			logger.error(resp.getRespuesta(),ex);
		}
		
		Boolean fechaValida = null;
		
		//validar fecha
		try
		{
			long diferenciaFechaActualVSEndoso = new Date().getTime() - fechaEfecto.getTime();
			diferenciaFechaActualVSEndoso = Math.abs(diferenciaFechaActualVSEndoso);
			                          //d   h   m   s   ms
			long maximoDiasPermitidos = 30l*24l*60l*60l*1000l;
			
			fechaValida = diferenciaFechaActualVSEndoso <= maximoDiasPermitidos;
		}
		catch(Exception ex)
		{
			long timestamp = System.currentTimeMillis();
			resp.setExito(false);
			resp.setRespuesta(new StringBuilder("Error al validar fecha de endoso #").append(timestamp).toString());
			resp.setRespuestaOculta(ex.getMessage());
			logger.error(resp.getRespuesta(),ex);
		}
		
		String ntramiteEmision = null;
		
		//obtener tramite de emision
		if(resp.isExito())
		{
			try
			{
				ntramiteEmision = endososDAO.obtenerNtramiteEmision(cdunieco, cdramo, estado, nmpoliza);
			}
			catch(ApplicationException ax)
			{
				long timestamp = System.currentTimeMillis();
				resp.setExito(false);
				resp.setRespuesta(new StringBuilder(ax.getMessage()).append(" #").append(timestamp).toString());
				resp.setRespuestaOculta(ax.getMessage());
				logger.error(resp.getRespuesta(),ax);
			}
			catch(Exception ex)
			{
				long timestamp = System.currentTimeMillis();
				resp.setExito(false);
				resp.setRespuesta(new StringBuilder("Error al obtener tramite de emision #").append(timestamp).toString());
				resp.setRespuestaOculta(ex.getMessage());
				logger.error(resp.getRespuesta(),ex);
			}
		}
		
		String dssuplem = null;
		
		//obtener nombre endoso
		if(resp.isExito())
		{
			try
			{
				dssuplem = "";
				List<Map<String,String>>endosos=endososDAO.obtenerNombreEndosos("");
				for(Map<String,String>endoso:endosos)
				{
					if(endoso.get("CDTIPSUP").equalsIgnoreCase(cdtipsup))
					{
						dssuplem=endoso.get("DSTIPSUP");
					}
				}
			}
			catch(Exception ex)
			{
				long timestamp = System.currentTimeMillis();
				resp.setExito(false);
				resp.setRespuesta(new StringBuilder("Error al obtener tramite de emision #").append(timestamp).toString());
				resp.setRespuestaOculta(ex.getMessage());
				logger.error(resp.getRespuesta(),ex);
			}
		}
		
		String ntramiteEndoso = null;
		
		//tramite mesa de control
		if(resp.isExito())
		{
			try
			{
				String statusTramiteEndoso = EstatusTramite.ENDOSO_CONFIRMADO.getCodigo();
				if(!fechaValida)
				{
					statusTramiteEndoso = EstatusTramite.ENDOSO_EN_ESPERA.getCodigo();
				}
				
				Map<String,String>valores=new HashMap<String,String>();
				valores.put("otvalor01" , ntramiteEmision);
				valores.put("otvalor02" , cdtipsup);
				valores.put("otvalor03" , dssuplem);
				valores.put("otvalor04" , nsuplogi);
				valores.put("otvalor05" , cdusuari);
				
				ntramiteEndoso = mesaControlDAO.movimientoMesaControl(
						cdunieco
						,cdramo
						,estado
						,nmpoliza
						,nmsuplemEndoso
						,cdunieco
						,cdunieco
						,TipoTramite.ENDOSO_PARADO_POR_AUTORIZACION.getCdtiptra()
						,fechaEfecto
						,null
						,null
						,null
						,fechaEfecto
						,statusTramiteEndoso
						,""
						,null
						,cdtipsit
						,valores);
			}
			catch(Exception ex)
			{
				long timestamp = System.currentTimeMillis();
				resp.setExito(false);
				resp.setRespuesta(new StringBuilder("Error al guardar tramite #").append(timestamp).toString());
				resp.setRespuestaOculta(ex.getMessage());
				logger.error(resp.getRespuesta(),ex);
			}
		}
		
		if(resp.isExito()) {
			try {
				// Acciones particulares por Tipo de Endoso:
				if(enumTipoEndosoElegido != null) {
					switch(enumTipoEndosoElegido) {
						case SUMA_ASEGURADA_INCREMENTO:
						case SUMA_ASEGURADA_DECREMENTO:
							//Si cdramo es gastos medicos mayores y cd tipsit es gastos medicos individual insertamos en mpolicap:
							if(cdramo.equals(Ramo.GASTOS_MEDICOS_MAYORES.getCdramo()) &&
									cdtipsit.equals(TipoSituacion.GASTOS_MEDICOS_INDIVIDUAL.getCdtipsit())) {
								Map<String,String>mapaMpolicap=new LinkedHashMap<String,String>(0);
								mapaMpolicap.put("pv_cdunieco_i", cdunieco);
								mapaMpolicap.put("pv_cdramo_i"  , cdramo);
								mapaMpolicap.put("pv_estado_i"  , estado);
								mapaMpolicap.put("pv_nmpoliza_i", nmpoliza);
								mapaMpolicap.put("pv_nmsuplem_i", nmsuplemEndoso);
								mapaMpolicap.put("pv_ptcapita_i", tvalosit.get("parametros.pv_otvalor06"));
								endososDAO.insertarMpolicap(mapaMpolicap);
							}
							break;
	
						default:
							break;
					}
				}
	        	
				
				//////////////////////////////
				////// inserta tworksup //////
				// Se insertan en tworksup TODAS LAS SITUACIONES:
				Map<String,String> mapaTworksupEnd = new LinkedHashMap<String,String>(0);
				mapaTworksupEnd.put("pv_cdunieco_i", cdunieco);
				mapaTworksupEnd.put("pv_cdramo_i"  , cdramo);
				mapaTworksupEnd.put("pv_estado_i"  , estado);
				mapaTworksupEnd.put("pv_nmpoliza_i", nmpoliza);
				mapaTworksupEnd.put("pv_cdtipsup_i", cdtipsup);
				mapaTworksupEnd.put("pv_nmsuplem_i", nmsuplemEndoso);
				endososDAO.insertarTworksupSitTodas(mapaTworksupEnd);
				////// inserta tworksup //////
				//////////////////////////////
				
				//////////////////////////
				////// tarificacion //////
		        Map<String,String>mapaSigsvalipolEnd=new LinkedHashMap<String,String>(0);
				mapaSigsvalipolEnd.put("pv_cdusuari_i", cdusuari);
				mapaSigsvalipolEnd.put("pv_cdelemen_i", cdelemen);
				mapaSigsvalipolEnd.put("pv_cdunieco_i", cdunieco);
				mapaSigsvalipolEnd.put("pv_cdramo_i"  , cdramo);
				mapaSigsvalipolEnd.put("pv_estado_i"  , estado);
				mapaSigsvalipolEnd.put("pv_nmpoliza_i", nmpoliza);
				mapaSigsvalipolEnd.put("pv_nmsituac_i", "0");
				mapaSigsvalipolEnd.put("pv_nmsuplem_i", nmsuplemEndoso);
				mapaSigsvalipolEnd.put("pv_cdtipsit_i", cdtipsit);
				mapaSigsvalipolEnd.put("pv_cdtipsup_i", cdtipsup);
				endososDAO.sigsvalipolEnd(mapaSigsvalipolEnd);
				////// tarificacion //////
			    //////////////////////////
				
				//////////////////////////
				////// valor endoso //////
				//Calcula el valor del endoso:
				Map<String,Object>mapaValorEndoso=new LinkedHashMap<String,Object>(0);
				mapaValorEndoso.put("pv_cdunieco_i" , cdunieco);
				mapaValorEndoso.put("pv_cdramo_i"   , cdramo);
				mapaValorEndoso.put("pv_estado_i"   , estado);
				mapaValorEndoso.put("pv_nmpoliza_i" , nmpoliza);
				mapaValorEndoso.put("pv_nmsituac_i" , "0");
				mapaValorEndoso.put("pv_nmsuplem_i" , nmsuplemEndoso);
				mapaValorEndoso.put("pv_feinival_i" , renderFechas.parse(feefecto));
				mapaValorEndoso.put("pv_cdtipsup_i" , cdtipsup);
				logger.debug("mapaValorEndoso=" + mapaValorEndoso);
				endososDAO.calcularValorEndoso(mapaValorEndoso);
				////// valor endoso //////
				//////////////////////////
				
			} catch(Exception ex) {
				long timestamp = System.currentTimeMillis();
				resp.setExito(false);
				resp.setRespuesta(new StringBuilder("Error #").append(timestamp).toString());
				resp.setRespuestaOculta(ex.getMessage());
				logger.error(resp.getRespuesta(),ex);
			}
		}
		
		//confirmar endoso
		if(resp.isExito()&&fechaValida)
		{
			try
			{
				endososDAO.confirmarEndosoB(cdunieco,cdramo,estado,nmpoliza,nmsuplemEndoso,nsuplogi,cdtipsup,"");
			}
			catch(Exception ex)
			{
				long timestamp = System.currentTimeMillis();
				resp.setExito(false);
				resp.setRespuesta(new StringBuilder("Error al confirmar endoso #").append(timestamp).toString());
				resp.setRespuestaOculta(ex.getMessage());
				logger.error(resp.getRespuesta(),ex);
			}
		}
		
		//reimprimir documentos
		if(resp.isExito()&&fechaValida)
		{
			List<Map<String,String>>listaDocu=null;
			
			try
			{
				listaDocu=endososDAO.reimprimeDocumentos(cdunieco, cdramo, estado, nmpoliza, nmsuplemEndoso, cdtipsup);
			}
			catch(Exception ex)
			{
				long timestamp = System.currentTimeMillis();
				resp.setExito(false);
				resp.setRespuesta(new StringBuilder("Error al obtener lista de documentos #").append(timestamp).toString());
				resp.setRespuestaOculta(ex.getMessage());
				logger.error(resp.getRespuesta(),ex);
			}
			
			String rutaCarpeta=new StringBuilder(rutaDocsPoliza).append("/").append(ntramiteEmision).toString();
			
			//listaDocu contiene: nmsolici,nmsituac,descripc,descripl
			for(Map<String,String> docu:listaDocu)
			{
				logger.debug("docu iterado: "+docu);
				
				String descripc  = docu.get("descripc");
				String descripl  = docu.get("descripl");
				nmsolici = docu.get("nmsolici");
				StringBuilder sb = new StringBuilder();
				
				sb.append(rutaServReports)
				  .append("?destype=cache")
				  .append("&paramform=no")
				  .append("&ACCESSIBLE=YES")
				  .append("&desformat=PDF")
				  .append("&userid=")  .append(passServReports)
				  .append("&report=")  .append(descripl)
				  .append("&p_unieco=").append(cdunieco)
				  .append("&p_ramo=")  .append(cdramo)
				  .append("&p_estado=").append(estado)
				  .append("&p_poliza=").append(nmpoliza)
				  .append("&p_suplem=").append(nmsuplemEndoso)
				  .append("&desname=") .append(rutaCarpeta).append("/").append(descripc)
				  ;
				
				if(descripc.substring(0, 6).equalsIgnoreCase("CREDEN"))
				{
					// C R E D E N C I A L _ X X X X X X . P D F
					//0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
					sb.append("&p_cdperson=").append(descripc.substring(11, descripc.lastIndexOf("_")));
				}
				
				String url = sb.toString();
				
				logger.debug(
						new StringBuilder()
						.append("\n#################################")
						.append("\n###### Se solicita reporte ######")
						.append("\n###### a ").append(url)
						.toString()
						);
				HttpUtil.generaArchivo(url,new StringBuilder(rutaCarpeta).append("/").append(descripc).toString());
				logger.debug(
						new StringBuilder()
						.append("\n###### a ").append(url)
						.append("\n###### reporte solicitado ######")
						.append("\n################################")
						.toString()
						);
			}
		}
		
		if(resp.isExito()) {
			// Ejecutamos el Web Service de Recibos:
			ice2sigsService.ejecutaWSrecibos(cdunieco, cdramo, estado, nmpoliza,
					nmsuplemEndoso, null, cdunieco, nmsolici, ntramite, true, cdtipsup, usuario);
		}
		
		if(resp.isExito()) {
			if(fechaValida) {
				resp.setRespuesta(new StringBuilder("Se ha guardado el endoso ").append(nsuplogi).toString());
			}
			else {
				resp.setRespuesta(
						new StringBuilder("El endoso ").append(nsuplogi)
						.append(" se guard&oacute; en mesa de control para autorizaci&oacute;n ")
						.append("con n&uacute;mero de tr&aacute;mite ").append(ntramiteEndoso)
						.toString());
			}
		}
		
		logger.info(
				new StringBuilder()
				.append("\n@@@@@@ ").append(resp) 
				.append("\n@@@@@@ guardarEndosoAtributosSituacionGeneral @@@@@@")
				.append("\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
				.toString()
				);
		return resp;
	}
	
	@Override
	public ManagerRespuestaVoidVO guardarEndosoBeneficiarios(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsituac
			,List<Map<String,String>>mpoliperMpersona
			,String cdelemen
			,String cdusuari
			,String cdtipsup
			)
	{
		logger.info(Utilerias.join(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ guardarEndosoBeneficiarios @@@@@@"
				,"\n@@@@@@ cdunieco="         , cdunieco
				,"\n@@@@@@ cdramo="           , cdramo
				,"\n@@@@@@ estado="           , estado
				,"\n@@@@@@ nmpoliza="         , nmpoliza
				,"\n@@@@@@ nmsituac="         , nmsituac
				,"\n@@@@@@ mpoliperMpersona=" , mpoliperMpersona
				,"\n@@@@@@ cdelemen="         , cdelemen
				,"\n@@@@@@ cdusuari="         , cdusuari
				,"\n@@@@@@ cdtipsup="         , cdtipsup
				));

		ManagerRespuestaVoidVO resp=new ManagerRespuestaVoidVO(true);
		
		try
		{
			setCheckpoint("Iniciando endoso");
			Map<String,String>iniciarEndosoResp=endososDAO.iniciarEndoso(
					cdunieco
					,cdramo
					,estado
					,nmpoliza
					,new Date()
					,cdelemen
					,cdusuari
					,"END"
					,cdtipsup);
			String nmsuplem = iniciarEndosoResp.get("pv_nmsuplem_o");
			String nsuplogi = iniciarEndosoResp.get("pv_nsuplogi_o");
			
			setCheckpoint("Iterando registros");
			for(Map<String,String>rec:mpoliperMpersona)
			{
				String mov    = rec.get("mov");
				int agregar   = 1;
				int eliminar  = 2;
				int operacion = 0;
				if(StringUtils.isNotBlank(mov))
				{
					if(mov.equals("+"))
					{
						operacion=agregar;
					}
					else if(mov.equals("-"))
					{
						operacion=eliminar;
					}
				}
				
				if(operacion==agregar)
				{
					personasDAO.movimientosMpersona(
							rec.get("CDPERSON")
							,rec.get("CDTIPIDE")
							,rec.get("CDIDEPER")
							,rec.get("DSNOMBRE")
							,rec.get("CDTIPPER")
							,rec.get("OTFISJUR")
							,rec.get("OTSEXO")
							,StringUtils.isNotBlank(rec.get("FENACIMI"))?
									renderFechas.parse(rec.get("FENACIMI"))
									:null
							,rec.get("CDRFC")
							,rec.get("DSEMAIL")
							,rec.get("DSNOMBRE1")
							,rec.get("DSAPELLIDO")
							,rec.get("DSAPELLIDO1")
							,new Date()
							,rec.get("CDNACION")
							,rec.get("CANALING")
							,rec.get("CONDUCTO")
							,rec.get("PTCUMUPR")
							,rec.get("RESIDENCIA")
							,rec.get("NONGRATA")
							,rec.get("CDIDEEXT")
							,rec.get("CDESTCIV")
							,rec.get("CDSUCEMI")
							,"I");
					
					endososDAO.movimientoMpoliperBeneficiario(
							cdunieco
							,cdramo
							,estado
							,nmpoliza
							,nmsituac
							,"3"
							,rec.get("CDPERSON")
							,nmsuplem
							,"V"
							,rec.get("NMORDDOM")
							,rec.get("SWRECLAM")
							,"N" //swexiper
							,rec.get("CDPARENT")
							,rec.get("PORBENEF")
							,"I"
							);
				}
				else if(operacion==eliminar)
				{
					endososDAO.movimientoMpoliperBeneficiario(
							cdunieco
							,cdramo
							,estado
							,nmpoliza
							,nmsituac
							,rec.get("CDROL")
							,rec.get("CDPERSON")
							,nmsuplem
							,rec.get("STATUS")
							,rec.get("NMORDDOM")
							,rec.get("SWRECLAM")
							,rec.get("SWEXIPER")
							,rec.get("CDPARENT")
							,rec.get("PORBENEF")
							,"B"
							);
					
					personasDAO.movimientosMpersona(
							rec.get("CDPERSON")
							,rec.get("CDTIPIDE")
							,rec.get("CDIDEPER")
							,rec.get("DSNOMBRE")
							,rec.get("CDTIPPER")
							,rec.get("OTFISJUR")
							,rec.get("OTSEXO")
							,StringUtils.isNotBlank(rec.get("FENACIMI"))?
									renderFechas.parse(rec.get("FENACIMI"))
									:null
							,rec.get("CDRFC")
							,rec.get("DSEMAIL")
							,rec.get("DSNOMBRE1")
							,rec.get("DSAPELLIDO")
							,rec.get("DSAPELLIDO1")
							,new Date()
							,rec.get("CDNACION")
							,rec.get("CANALING")
							,rec.get("CONDUCTO")
							,rec.get("PTCUMUPR")
							,rec.get("RESIDENCIA")
							,rec.get("NONGRATA")
							,rec.get("CDIDEEXT")
							,rec.get("CDESTCIV")
							,rec.get("CDSUCEMI")
							,"B");
				}
				else
				{
					endososDAO.movimientoMpoliperBeneficiario(
							cdunieco
							,cdramo
							,estado
							,nmpoliza
							,nmsituac
							,rec.get("CDROL")
							,rec.get("CDPERSON")
							,nmsuplem
							,rec.get("STATUS")
							,rec.get("NMORDDOM")
							,rec.get("SWRECLAM")
							,rec.get("SWEXIPER")
							,rec.get("CDPARENT")
							,rec.get("PORBENEF")
							,"U"
							);
					
					personasDAO.movimientosMpersona(
							rec.get("CDPERSON")
							,rec.get("CDTIPIDE")
							,rec.get("CDIDEPER")
							,rec.get("DSNOMBRE")
							,rec.get("CDTIPPER")
							,rec.get("OTFISJUR")
							,rec.get("OTSEXO")
							,StringUtils.isNotBlank(rec.get("FENACIMI"))?
									renderFechas.parse(rec.get("FENACIMI"))
									:null
							,rec.get("CDRFC")
							,rec.get("DSEMAIL")
							,rec.get("DSNOMBRE1")
							,rec.get("DSAPELLIDO")
							,rec.get("DSAPELLIDO1")
							,new Date()
							,rec.get("CDNACION")
							,rec.get("CANALING")
							,rec.get("CONDUCTO")
							,rec.get("PTCUMUPR")
							,rec.get("RESIDENCIA")
							,rec.get("NONGRATA")
							,rec.get("CDIDEEXT")
							,rec.get("CDESTCIV")
							,rec.get("CDSUCEMI")
							,"U");
				}
			}
			
			setCheckpoint("Confirmando endoso");
			endososDAO.confirmarEndosoB(cdunieco,cdramo,estado,nmpoliza,nmsuplem,nsuplogi,cdtipsup,"");
			
			setCheckpoint("0");
		}
		catch(Exception ex)
		{
			manejaException(ex, resp);
		}
		
		logger.info(Utilerias.join(
				 "\n@@@@@@ " , resp
				,"\n@@@@@@ guardarEndosoBeneficiarios @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return resp;
	}
	
	/********************** BASE MANAGER ***********************/
	/**
	 * Guarda el estado actual en sesion
	 */
	private void setCheckpoint(String checkpoint)
	{
		logger.debug(new StringBuilder("checkpoint-->").append(checkpoint).toString());
		session.put("checkpoint",checkpoint);
	}
	
	/**
	 * Obtiene el estado actual de sesion
	 */
	private String getCheckpoint()
	{
		return (String)session.get("checkpoint");
	}
	
	/**
	 * Da valor a los atributos exito, respuesta y respuestaOculta de resp.
	 * Tambien guarda el checkpoint en 0
	 */
	private void manejaException(Exception ex,ManagerRespuestaBaseVO resp)
	{
		long timestamp = System.currentTimeMillis();
		resp.setExito(false);
		resp.setRespuestaOculta(ex.getMessage());
		
		if(ex.getClass().equals(ApplicationException.class))
		{
			resp.setRespuesta(
					new StringBuilder()
					.append(ex.getMessage())
					.append(" #")
					.append(timestamp)
					.toString()
					);
		}
		else
		{
			resp.setRespuesta(
					new StringBuilder()
					.append("Error ")
					.append(getCheckpoint().toLowerCase())
					.append(" #")
					.append(timestamp)
					.toString()
					);
		}
		
		logger.error(resp.getRespuesta(),ex);
		setCheckpoint("0");
	}
	/********************** BASE MANAGER ***********************/
	
	/////////////////////////////////
	////// getters and setters //////
	/*/////////////////////////////*/
	public void setEndososDAO(EndososDAO endososDAO) {
		this.endososDAO = endososDAO;
	}

	public void setCotizacionDAO(CotizacionDAO cotizacionDAO) {
		this.cotizacionDAO = cotizacionDAO;
	}

	public void setPantallasDAO(PantallasDAO pantallasDAO) {
		this.pantallasDAO = pantallasDAO;
	}

	public void setMesaControlDAO(MesaControlDAO mesaControlDAO) {
		this.mesaControlDAO = mesaControlDAO;
	}
	@Override
	public void setSession(Map<String,Object>session)
	{
		this.session=session;
	}

	public void setPersonasDAO(PersonasDAO personasDAO) {
		this.personasDAO = personasDAO;
	}
	
}