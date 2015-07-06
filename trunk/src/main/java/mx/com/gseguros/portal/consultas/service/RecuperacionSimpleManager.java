package mx.com.gseguros.portal.consultas.service;

import java.util.Map;

import mx.com.gseguros.portal.consultas.model.RecuperacionSimple;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSlist2VO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSmapVO;

public interface RecuperacionSimpleManager
{
	public void setSession(Map<String,Object>session);
	
	public ManagerRespuestaSmapVO recuperacionSimple(
			RecuperacionSimple procedimiento
			,Map<String,String>parametros
			,String cdsisrol
			,String cdusuari
			);
	
	public ManagerRespuestaSlist2VO recuperacionSimpleLista(
			RecuperacionSimple procedimiento
			,Map<String,String>parametros
			,String cdsisrol
			,String cdusuari
			);
}