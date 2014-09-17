package mx.com.gseguros.portal.consultas.dao;

import java.util.List;

import mx.com.gseguros.portal.consultas.model.ConsultaDatosPolizaVO;
import mx.com.gseguros.portal.consultas.model.ConsultaPolizaAseguradoVO;


public interface IConsultasPolizaDAO {
		
	/**
	 * Obtiene los datos de la p&oacute;liza
	 * @param cdunieco
	 * @param cdramo
	 * @param estado
	 * @param nmpoliza
	 * @return
	 * @throws Exception
	 */
    public List<ConsultaDatosPolizaVO> consultaDatosPoliza(String cdunieco, String cdramo, String estado, String nmpoliza) throws Exception;
	
    /**
     * Obtiene las polizas asociadas al asegurado
     * @param rfc RFC del asegurado
     * @param cdperson C&oacute;digo de persona del asegurado
     * @param nombre Nombre del asegurado
     * @return Polizas asociadas al asegurado
     * @throws Exception
     */
	public List<ConsultaPolizaAseguradoVO> obtienePolizasAsegurado(String rfc, String cdperson, String nombre) throws Exception;
	
	
}