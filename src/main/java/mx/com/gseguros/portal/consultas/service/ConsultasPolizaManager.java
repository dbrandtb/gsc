package mx.com.gseguros.portal.consultas.service;

import java.util.List;

import mx.com.gseguros.portal.consultas.model.AseguradoDetalleVO;
import mx.com.gseguros.portal.consultas.model.AseguradoVO;
import mx.com.gseguros.portal.consultas.model.CoberturasBasicasVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosComplementariosVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosContratanteVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosHistoricoVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosPlanVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosPolizaVO;
import mx.com.gseguros.portal.consultas.model.ConsultaDatosSuplementoVO;
import mx.com.gseguros.portal.consultas.model.ConsultaPeriodosVigenciaVO;
import mx.com.gseguros.portal.consultas.model.ConsultaPolizaAseguradoVO;
import mx.com.gseguros.portal.consultas.model.ConsultaReciboAgenteVO;
import mx.com.gseguros.portal.consultas.model.CopagoVO;
import mx.com.gseguros.portal.consultas.model.HistoricoFarmaciaVO;
import mx.com.gseguros.portal.cotizacion.model.AgentePolizaVO;
import mx.com.gseguros.portal.general.model.ClausulaVO;
import mx.com.gseguros.portal.general.model.DetalleReciboVO;
import mx.com.gseguros.portal.general.model.PolizaVO;
import mx.com.gseguros.portal.general.model.ReciboVO;


public interface ConsultasPolizaManager {
		
    /**
     * Obtiene las polizas asociadas al asegurado
     * @param rfc RFC del asegurado
     * @param cdperson C&oacute;digo de persona del asegurado
     * @param nombre Nombre del asegurado
     * @return Polizas asociadas al asegurado
     * @throws Exception
     */
	public List<ConsultaPolizaAseguradoVO> obtienePolizasAsegurado(String rfc, String cdperson, String nombre) throws Exception;
	
	
	/**
	 * 
	 * @param polizaAsegurado
	 * @return
	 * @throws Exception
	 */
	public List<ConsultaDatosSuplementoVO> obtieneHistoricoPoliza(ConsultaPolizaAseguradoVO polizaAsegurado) throws Exception;
	
	/**
	 * 
	 * @param polizaAsegurado
	 * @return
	 * @throws Exception
	 */
	public List<ConsultaDatosHistoricoVO> obtieneHistoricoPolizaSISA(ConsultaPolizaAseguradoVO polizaAsegurado) throws Exception;
	
    
    /**
     * Obtiene los datos generales de la p&oacute;liza
     * @param polizaAsegurado
     * @return
     * @throws Exception
     */
    public List<ConsultaDatosPolizaVO> obtieneDatosPoliza(ConsultaPolizaAseguradoVO polizaAsegurado) throws Exception;
    
    
    /**
     * Obtiene los datos complementarios de la p&oacute;liza
     * @param poliza
     * @param asegurado
     * @return
     * @throws Exception
     */
    public List<ConsultaDatosComplementariosVO> obtieneDatosComplementarios(PolizaVO poliza, AseguradoVO asegurado) throws Exception;
    
    
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
    public List<CoberturasBasicasVO> obtieneCoberturasPoliza(PolizaVO poliza) throws Exception;
    
    /**
     * Obtiene las coberturas b�sicas
     * @param poliza
     * @return
     */
    public List<CoberturasBasicasVO> obtieneCoberturasBasicas(PolizaVO poliza) throws Exception;
    
    /**
     * Obtiene los datos del plan
     * @param poliza
     * @return
     */
    public List<ConsultaDatosPlanVO> obtieneDatosPlan(PolizaVO poliza) throws Exception;
    
    /**
     * Obtiene los datos del contratante
     * @param poliza
     * @return
     * @throws Exception
     */
    public List<ConsultaDatosContratanteVO> obtieneDatosContratante(PolizaVO poliza) throws Exception;
    
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
     * Obtiene las clausulas de la poliza
     * @param poliza
     * @param asegurado
     * @return
     * @throws Exception
     */
    public List<ClausulaVO> obtieneClausulasPoliza(PolizaVO poliza, AseguradoVO asegurado) throws Exception;
    
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
    public List<ConsultaPeriodosVigenciaVO> obtienePeriodosVigencia(PolizaVO poliza, AseguradoVO asegurado) throws Exception;
    
    /**
     * 
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
    public List<ConsultaReciboAgenteVO> obtieneRecibosAgente(PolizaVO poliza) throws Exception;
	
}