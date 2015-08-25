package mx.com.gseguros.portal.emision.dao;

import java.util.Date;

import mx.com.gseguros.portal.emision.model.EmisionVO;


public interface EmisionDAO {
	
	/**
	 * Proceso de emision general
	 * @param cdusuari
	 * @param cdunieco
	 * @param cdramo
	 * @param estado
	 * @param nmpoliza
	 * @param nmsituac
	 * @param nmsuplem
	 * @param cdelemento
	 * @param cdcia
	 * @param cdplan
	 * @param cdperpag
	 * @param cdperson
	 * @param fecha
	 * @param ntramite
	 * @return Datos de la emision
	 * @throws Exception
	 */
	public EmisionVO emitir(String cdusuari, String cdunieco, String cdramo, String estado, String nmpoliza, 
			String nmsituac, String nmsuplem, String cdelemento, String cdcia, String cdplan, String cdperpag, 
			String cdperson, Date fecha, String ntramite) throws Exception;
	

	/**
	 * Actualiza el numero de poliza externo para una poliza de autos
	 * @param cdunieco
	 * @param cdramo
	 * @param estado
	 * @param nmpoliza
	 * @param nmsuplem
	 * @param nmpoliex
	 * @param cduniext
	 * @param ramoGS
	 * @throws Exception
	 */
	public void actualizaNmpoliexAutos(String cdunieco, String cdramo, String estado, String nmpoliza, 
			String nmsuplem, String nmpoliex, String cduniext, String ramoGS) throws Exception;
	
}