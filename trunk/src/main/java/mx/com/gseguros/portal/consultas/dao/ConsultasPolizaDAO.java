package mx.com.gseguros.portal.consultas.dao;

import java.util.List;

import mx.com.gseguros.portal.consultas.model.AseguradoDetalleVO;
import mx.com.gseguros.portal.consultas.model.AseguradoVO;
import mx.com.gseguros.portal.consultas.model.CoberturaBasicaVO;
import mx.com.gseguros.portal.consultas.model.ContratanteVO;
import mx.com.gseguros.portal.consultas.model.CopagoVO;
import mx.com.gseguros.portal.consultas.model.DatosComplementariosVO;
import mx.com.gseguros.portal.consultas.model.HistoricoFarmaciaVO;
import mx.com.gseguros.portal.consultas.model.HistoricoVO;
import mx.com.gseguros.portal.consultas.model.PeriodoVigenciaVO;
import mx.com.gseguros.portal.consultas.model.PlanVO;
import mx.com.gseguros.portal.consultas.model.PolizaAseguradoVO;
import mx.com.gseguros.portal.consultas.model.PolizaDTO;
import mx.com.gseguros.portal.consultas.model.ReciboAgenteVO;
import mx.com.gseguros.portal.consultas.model.SuplementoVO;
import mx.com.gseguros.portal.consultas.model.TarifaVO;
import mx.com.gseguros.portal.cotizacion.model.AgentePolizaVO;
import mx.com.gseguros.portal.general.model.ClausulaVO;
import mx.com.gseguros.portal.general.model.DetalleReciboVO;
import mx.com.gseguros.portal.general.model.PolizaVO;
import mx.com.gseguros.portal.general.model.ReciboVO;


public interface ConsultasPolizaDAO {
	
	/**
     * Obtiene las polizas del Promotor que tenga asociadas a los asegurados de sus Agentes
     * @param user Usuario actual activo
     * @param rfc RFC del asegurado
     * @param cdperson C&oacute;digo de persona del asegurado
     * @param nombre Nombre del asegurado
     * @return Polizas asociadas al asegurado
     * @throws Exception
     */
	
	public List<PolizaAseguradoVO> obtienePolizasAsegPromotor(String user, String rfc, String cdperson, String nombre) throws Exception;
	
	/**
     * Obtiene las polizas asociadas al asegurado
     * @param user Usuario actual activo
     * @param rfc RFC del asegurado
     * @param cdperson C&oacute;digo de persona del asegurado
     * @param nombre Nombre del asegurado
     * @return Polizas asociadas al asegurado
     * @throws Exception
     */
	
	public List<PolizaAseguradoVO> obtienePolizasAsegurado(String user, String rfc, String cdperson, String nombre) throws Exception;
	
	
	/*
	 * Obtiene el hist&oacute;rico (suplementos) de una p&oacute;liza
	 * @param nmpoliex N&uacute;mero externo/completo de la p&oacute;liza
	 * @return El hist&oacute;rico (suplementos) de la p&oacute;liza
	 * @throws Exception
	 *
	public List<ConsultaDatosSuplementoVO> obtieneHistoricoPoliza(String nmpoliex, String icodpoliza) throws Exception;
	*/
	
	
	/**
	 * 
	 * @param polizaAsegurado
	 * @return
	 * @throws Exception
	 */
	public List<SuplementoVO> obtieneHistoricoPoliza(PolizaAseguradoVO polizaAsegurado) throws Exception;
	
	/**
	 * 
	 * @param polizaAsegurado
	 * @return
	 * @throws Exception
	 */
	public List<HistoricoVO> obtieneHistoricoPolizaSISA(PolizaAseguradoVO polizaAsegurado) throws Exception;
	
	
	/*
	 * Obtiene los datos generales de la p&oacute;liza
	 * @param cdunieco
	 * @param cdramo
	 * @param estado
	 * @param nmpoliza
	 * @return
	 * @throws Exception
	 *
    public List<PolizaVO> obtieneDatosPoliza(String cdunieco, String cdramo, String estado, String nmpoliza, String icodpoliza) throws Exception;
    */
    
    
    /**
     * Obtiene los datos generales de la p&oacute;liza
     * @param polizaAsegurado
     * @return
     * @throws Exception
     */
    public List<PolizaDTO> obtieneDatosPoliza(PolizaAseguradoVO polizaAsegurado) throws Exception;
    
    
    /**
     * Obtiene los datos complementarios de la p&oacute;liza
     * @param asegurado
     * @return
     * @throws Exception
     */
    public List<DatosComplementariosVO> obtieneDatosComplementarios(PolizaVO poliza, AseguradoVO asegurado) throws Exception;
    
    /**
     * Obtiene los copagos de la p&oacute;liza
     * @param poliza
     * @return
     */
    public List<CopagoVO> obtieneCopagosPoliza(PolizaVO poliza) throws Exception;
    
    /**
     * Obtiene las coberturas
     * @param poliza
     * @return
     */
    public List<CoberturaBasicaVO> obtieneCoberturasPoliza(PolizaVO poliza) throws Exception;
    
    /**
     * Obtiene las coberturas b�sicas
     * @param poliza
     * @return
     */
    public List<CoberturaBasicaVO> obtieneCoberturasBasicas(PolizaVO poliza) throws Exception;
    
    /**
     * Obtiene los datos del plan
     * @param poliza
     * @return
     */
    public List<PlanVO> obtieneDatosPlan(PolizaVO poliza) throws Exception;
    
    /**
     * Obtiene datos del contratante
     * @param poliza
     * @return
     */
    public List<ContratanteVO> obtieneDatosContratante(PolizaVO poliza) throws Exception;
    
    /**
     * Obtiene los asegurados de la p&oacute;liza
     * @param poliza
     * @return
     * @throws Exception
     */
    public List<AseguradoVO> obtieneAsegurados(PolizaVO poliza) throws Exception;
    

    /**
     * Obtiene las exclusiones/clausulas de la poliza
     * @param poliza
     * @return
     * @throws Exception
     */
    public List<ClausulaVO> obtieneEndososPoliza(PolizaVO poliza, AseguradoVO asegurado) throws Exception;
    
    /**
     * Obtiene un mensaje para el agente dependiendo la poliza recibida
     * @param poliza
     * @return
     * @throws Exception
     */
    public String obtieneMensajeAgente (PolizaVO poliza) throws Exception;
    
    /**
     * Obtiene las tarifas de una poliza
     * @param poliza
     * @return
     * @throws Exception
     */
    public List<TarifaVO> obtieneTarifasPoliza(PolizaVO poliza) throws Exception;
    
    /**
     * Obtiene hist�rico de farmacia
     * @param poliza
     * @return
     * @throws Exception
     */
    public List<HistoricoFarmaciaVO> obtieneHistoricoFarmacia(PolizaVO poliza, AseguradoVO asegurado) throws Exception;
    
    /**
     * Obtiene periodos de vigencia
     * @param poliza
     * @return
     * @throws Exception
     */
    public List<PeriodoVigenciaVO> obtienePeriodosVigencia(PolizaVO poliza, AseguradoVO asegurado) throws Exception;
    
    /**
     * Obtiene el detalle del asegurado
     * @param asegurado
     * @return
     * @throws Exception
     */
    public List<AseguradoDetalleVO> obtieneAseguradoDetalle(AseguradoVO asegurado) throws Exception;
    
    
    /**
     * Obtiene los recibos asociados a una p&oacute;liza
     * @param poliza
     * @return
     * @throws Exception
     */
    public List<ReciboVO> obtieneRecibosPoliza(PolizaVO poliza) throws Exception;
    
    
    /**
     * Obtiene los datos de un recibo asociado a una p&oacute;liza
     * @param poliza
     * @param recibo
     * @return
     * @throws Exception
     */
    public List<DetalleReciboVO> obtieneDetalleRecibo(PolizaVO poliza, ReciboVO recibo) throws Exception;
    
    
    /**
     * Obtiene los agentes asociados a una p&oacute;liza
     * @param poliza
     * @return
     * @throws Exception
     */
    public List<AgentePolizaVO> obtieneAgentesPoliza(PolizaVO poliza) throws Exception;
    
    
    /**
     * Obtiene los recibos del agente asociados a una p&oacute;liza
     * @param poliza
     * @return
     * @throws Exception
     */
    public List<ReciboAgenteVO> obtieneRecibosAgente(PolizaVO poliza) throws Exception;
    
    
    
	
}