package mx.com.gseguros.portal.renovacion.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaImapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaSlistVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaVoidVO;
import mx.com.gseguros.portal.renovacion.service.RenovacionManager;
import mx.com.gseguros.utils.Utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionContext;

public class RenovacionAction extends PrincipalCoreAction
{
	private static final long        serialVersionUID = 6666057023524182296L;
	private static final Logger      logger           = Logger.getLogger(RenovacionAction.class);
	private boolean                  success          = true;
	private boolean                  exito            = false;
	private Map<String,String>       smap1            = null;
	private Map<String,String>       params           = null;
	private String                   respuesta;
	private String                   respuestaOculta  = null;
	private Map<String,Item>         imap             = null;
	private List<Map<String,String>> slist1           = null;
	
	//Dependencias inyectadas
	private RenovacionManager renovacionManager;
	
	public String pantallaRenovacion()
	{
		logger.info(
				new StringBuilder()
				.append("\n################################")
				.append("\n###### pantallaRenovacion ######")
				.toString()
				);
		
		exito = true;
		
		String cdsisrol = null;
		
		//sesion
		try
		{
			if(session==null)
			{
				throw new ApplicationException("No hay sesion");
			}
			UserVO usuario = (UserVO)session.get("USUARIO");
			if(usuario==null)
			{
				throw new ApplicationException("No hay usuario en sesion");
			}
			cdsisrol = usuario.getRolActivo().getClave();
			if(StringUtils.isBlank(cdsisrol))
			{
				throw new ApplicationException("No hay rol en la sesion");
			}
		}
		catch(ApplicationException ax)
		{
			long timestamp  = System.currentTimeMillis();
			exito           = false;
			respuesta       = new StringBuilder(ax.getMessage()).append(" #").append(timestamp).toString();
			respuestaOculta = ax.getMessage();
			logger.error(respuesta,ax);
		}
		catch(Exception ex)
		{
			long timestamp  = System.currentTimeMillis();
			exito           = false;
			respuesta       = new StringBuilder("Error al obtener datos de sesion #").append(timestamp).toString();
			respuestaOculta = ex.getMessage();
			logger.error(respuesta,ex);
		}
		
		//proceso
		if(exito)
		{
			ManagerRespuestaImapVO managerResponse = renovacionManager.pantallaRenovacion(cdsisrol);
			exito           = managerResponse.isExito();
			respuesta       = managerResponse.getRespuesta();
			respuestaOculta = managerResponse.getRespuestaOculta();
			if(exito)
			{
				imap = managerResponse.getImap();
			}
		}
			
		logger.info(
				new StringBuilder()
				.append("\n###### pantallaRenovacion ######")
				.append("\n################################")
				.toString()
				);
		
		String result = SUCCESS;
		if(!exito)
		{
			result = ERROR;
		}
		return result;
	}
	
	/**
	 * Busca polizas renovables
	 * @param smap1.anio
	 * @param smap1.mes
	 * @return success
	 * @return slist1
	 */
	public String buscarPolizasRenovables()
	{
		logger.info(
				new StringBuilder()
				.append("\n#####################################")
				.append("\n###### buscarPolizasRenovables ######")
				.append("\n###### smap1=").append(smap1)
				.toString()
				);
		
		success = true;
		
		String cdunieco = null;
		String cdramo   = null;
		String anio     = null;
		String mes      = null;
		
		//datos completos
		try
		{
			if(smap1==null)
			{
				throw new ApplicationException("No se recibieron datos");
			}
			cdunieco = smap1.get("cdunieco");
			cdramo   = smap1.get("cdramo");
			anio     = smap1.get("anio");
			mes      = smap1.get("mes");
			if(StringUtils.isBlank(anio))
			{
				throw new ApplicationException("No se recibio el año");
			}
			if(StringUtils.isBlank(mes))
			{
				throw new ApplicationException("No se recibio el mes");
			}
		}
		catch(ApplicationException ax)
		{
			long timestamp  = System.currentTimeMillis();
			success         = false;
			respuesta       = new StringBuilder(ax.getMessage()).append(" #").append(timestamp).toString();
			respuestaOculta = ax.getMessage();
			logger.error(respuesta,ax);
		}
		catch(Exception ex)
		{
			long timestamp  = System.currentTimeMillis();
			success         = false;
			respuesta       = new StringBuilder("Error al obtener datos para buscar polizas #").append(timestamp).toString();
			respuestaOculta = ex.getMessage();
			logger.error(respuesta,ex);
		}
		
		//proceso
		if(success)
		{
			ManagerRespuestaSlistVO managerResp = renovacionManager.buscarPolizasRenovables(cdunieco,cdramo,anio,mes);
			success         = managerResp.isExito();
			respuesta       = managerResp.getRespuesta();
			respuestaOculta = managerResp.getRespuestaOculta();
			if(success)
			{
				slist1 = managerResp.getSlist();
			}
		}
		
		logger.info(
				new StringBuilder()
				.append("\n###### slist1=").append(slist1)
				.append("\n###### buscarPolizasRenovables ######")
				.append("\n#####################################")
				.toString()
				);
		return SUCCESS;
	}
	
	public String renovarPolizas()
	{
		logger.info(
				new StringBuilder()
				.append("\n############################")
				.append("\n###### renovarPolizas ######")
				.append("\n###### slist1=").append(slist1)
				.toString()
				);
		
		exito = true;
		
		String anio     = null;
		String mes      = null;
		String cdusuari = null;
		
		UserVO usuario = null;
		
		//datos completos
		try
		{
			session=ActionContext.getContext().getSession();
			if(session==null)
			{
				throw new ApplicationException("No hay sesion");
			}
			if(session.get("USUARIO")==null)
			{
				throw new ApplicationException("No hay usuario en la sesion");
			}
			usuario = (UserVO)session.get("USUARIO");
			cdusuari       = usuario.getUser();
			if(StringUtils.isBlank(cdusuari))
			{
				throw new ApplicationException("No hay clave de usuario");
			}
			if(slist1==null||slist1.size()==0)
			{
				throw new ApplicationException("No se recibieron polizas");
			}
			if(smap1==null)
			{
				throw new ApplicationException("No se recibieron datos de busqueda");
			}
			anio = smap1.get("anio");
			mes  = smap1.get("mes");
			if(StringUtils.isBlank(anio))
			{
				throw new ApplicationException("No hay año");
			}
			if(StringUtils.isBlank(mes))
			{
				throw new ApplicationException("No hay mes");
			}
		}
		catch(ApplicationException ax)
		{
			long timestamp  = System.currentTimeMillis();
			exito           = false;
			respuesta       = new StringBuilder(ax.getMessage()).append(" #").append(timestamp).toString();
			respuestaOculta = ax.getMessage();
			logger.error(respuesta,ax);
		}
		catch(Exception ex)
		{
			long timestamp  = System.currentTimeMillis();
			exito           = false;
			respuesta       = new StringBuilder("Error inesperado al obtener datos #").append(timestamp).toString();
			respuestaOculta = ex.getMessage();
			logger.error(respuesta,ex);
		}
		
		//proceso
		if(exito)
		{
			ManagerRespuestaVoidVO resp = renovacionManager.renovarPolizas(
					slist1
					,cdusuari
					,anio
					,mes
					,getText("ruta.documentos.poliza")
					,getText("ruta.servidor.reports")
					,getText("pass.servidor.reports")
					,usuario
					);
			exito           = resp.isExito();
			respuesta       = resp.getRespuesta();
			respuestaOculta = resp.getRespuestaOculta();
		}
		
		logger.info(
				new StringBuilder()
				.append("\n###### renovarPolizas ######")
				.append("\n############################")
				.toString()
				);
		return SUCCESS;
	}

	public String renovacionIndividual()
	{
		logger.info(
				new StringBuilder()
				.append("\n#####################################")
				.append("\n###### renovacionIndividual ######")
				.append("\n###### smap1=").append(smap1)
				.toString()
				);
		
		success = true;
		
		String cdsisrol = null;
		
		//datos completos
		try
		{
			UserVO usuario = (UserVO)session.get("USUARIO");
			cdsisrol = usuario.getRolActivo().getClave();
			ManagerRespuestaImapVO managerResponse = renovacionManager.pantallaRenovacionIndividual(cdsisrol);
			exito           = managerResponse.isExito();
			respuesta       = managerResponse.getRespuesta();
			respuestaOculta = managerResponse.getRespuestaOculta();
			if(exito)
			{
				imap = managerResponse.getImap();
			}
		}
		catch(Exception ex)
		{
			long timestamp  = System.currentTimeMillis();
			success         = false;
			respuesta       = new StringBuilder("Error al obtener atributos de pantalla #").append(timestamp).toString();
			respuestaOculta = ex.getMessage();
			logger.error(respuesta,ex);
		}
		logger.info(
				new StringBuilder()
				.append("\n###### slist1=").append(slist1)
				.append("\n###### renovacionIndividual ######")
				.append("\n#####################################")
				.toString()
				);
		return SUCCESS;
	}
	
	public String buscarPolizasIndividualesRenovables()
	{
		logger.info(
				new StringBuilder()
				.append("\n#####################################")
				.append("\n###### buscarPolizasIndividualesRenovables ######")
				.append("\n###### smap1=").append(smap1)
				.toString()
				);
		
		success = true;
		
		//datos completos
		try{	
			Utils.validate(smap1, "No se recibieron datos");
			Utils.validate(smap1.get("cdunieco"), "No se recibio la oficina",
						   smap1.get("cdramo")  , "No se recibio el producto",
						   smap1.get("estado")  , "No se recibio el estado",
						   smap1.get("nmpoliza"), "No se recibio la poliza");
			String cdunieco = smap1.get("cdunieco");
			String cdramo   = smap1.get("cdramo");
			String estado   = smap1.get("estado");
			String nmpoliza = smap1.get("nmpoliza");
			
			//proceso
			ManagerRespuestaSlistVO managerResp = renovacionManager.buscarPolizasRenovacionIndividual(cdunieco, cdramo, estado, nmpoliza);
			logger.info(new StringBuilder().append("managerResp ").append(managerResp).toString());
			slist1 			= managerResp.getSlist();
			success         = managerResp.isExito();
			respuesta       = managerResp.getRespuesta();
			respuestaOculta = managerResp.getRespuestaOculta();
			
		}catch(Exception ex){
			respuesta = Utils.manejaExcepcion(ex);
		}
		logger.info(
				new StringBuilder()
				.append("\n###### slist1=").append(slist1)
				.append("\n###### buscarPolizasIndividualesRenovables ######")
				.append("\n#####################################")
				.toString()
				);
		return SUCCESS;
	}
	
	
	public String buscarPolizasIndividualesMasivasRenovables()
	{
		logger.info(
				new StringBuilder()
				.append("\n#####################################")
				.append("\n###### buscarPolizasIndividualesMasivasRenovables ######")
				.append("\n###### smap1=").append(smap1)
				.toString()
				);
		
		success = true;
		ManagerRespuestaSlistVO managerResp = null;
		//datos completos
		try{	
			Utils.validate(smap1, "No se recibieron datos");
			Utils.validate(smap1.get("fecini")  , "No se recibio la fecha inicio",
					   	   smap1.get("fecfin")  , "No se recibio la fecha fin"//,
//						   smap1.get("cdunieco"), "No se recibio la oficina",
//						   smap1.get("cdramo")  , "No se recibio el producto",
//						   smap1.get("estado")  , "No se recibio el estado",
//						   smap1.get("nmpoliza"), "No se recibio la poliza",
//						   smap1.get("cdtipsit"), "No se recibio el subramo",						   
//						   smap1.get("status")  , "No se recibio el status"
					);
			String cdunieco = smap1.get("cdunieco");
			String cdramo   = smap1.get("cdramo");
			String estado   = smap1.get("estado");
			String nmpoliza = null;//smap1.get("nmpoliza");
			String cdtipsit	= smap1.get("cdtipsit");
			String fecini	= smap1.get("fecini");
			String fecfin	= smap1.get("fecfin");
			String status	= smap1.get("status");
			
			//proceso
			managerResp = renovacionManager.buscarPolizasRenovacionIndividualMasiva(cdunieco, cdramo, estado, nmpoliza, cdtipsit, fecini, fecfin, status);
			logger.info(new StringBuilder().append("managerResp ").append(managerResp).toString());
			slist1 			= managerResp.getSlist();
			success         = managerResp.isExito();
			respuesta       = managerResp.getRespuesta();
			respuestaOculta = managerResp.getRespuestaOculta();			
		}catch(Exception ex){
			respuesta 		= Utils.manejaExcepcion(ex);
		}
		logger.info(
				new StringBuilder()
				.append("\n###### slist1=").append(slist1)
				.append("\n###### buscarPolizasIndividualesMasivasRenovables ######")
				.append("\n#####################################")
				.toString()
				);
		return SUCCESS;
	}
	
	public String renovarPolizaIndividual(){
		logger.info(
				new StringBuilder()
				.append("\n###### params=").append(params)
				.append("\n###### Entrando a renovarPolizaIndividual ######")
				.append("\n################################################")
				.toString()
				);
		try{
			Utils.validate(params, "No se recibieron datos");
			Utils.validate(params.get("cdunieco"),"No se recibio oficina",
						   params.get("cdramo")  ,"No se recibio producto",
						   params.get("estado")  ,"No se recibio estado",
						   params.get("nmpoliza"),"No se recibio numero de poliza"
						   );
			String cdunieco = params.get("cdunieco");
			String cdramo   = params.get("cdramo");
			String estado   = params.get("estado");
			String nmpoliza = params.get("nmpoliza");
			String ntramite = String.valueOf(renovacionManager.renuevaPolizaIndividual(cdunieco, cdramo, estado, nmpoliza));
			Map<String, String> result = new HashMap<String, String>();
			result.put("ntramite", ntramite);
			slist1 = new ArrayList<Map<String,String>>();
			slist1.add(result);
		}catch(Exception ex){
			respuesta = Utils.manejaExcepcion(ex);
		}
		logger.info(
				new StringBuilder()
				.append("\n###### Saliendo de renovarPolizaIndividual ######")
				.append("\n#################################################")
				.toString()
				);
		return SUCCESS;
	}
	
	//Getters y setters
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean isExito() {
		return exito;
	}

	public void setExito(boolean exito) {
		this.exito = exito;
	}

	public Map<String, String> getSmap1() {
		return smap1;
	}

	public void setSmap1(Map<String, String> smap1) {
		this.smap1 = smap1;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public String getRespuestaOculta() {
		return respuestaOculta;
	}

	public void setRespuestaOculta(String respuestaOculta) {
		this.respuestaOculta = respuestaOculta;
	}

	public void setRenovacionManager(RenovacionManager renovacionManager) {
		this.renovacionManager = renovacionManager;
	}

	public Map<String, Item> getImap() {
		return imap;
	}

	public void setImap(Map<String, Item> imap) {
		this.imap = imap;
	}

	public List<Map<String, String>> getSlist1() {
		return slist1;
	}

	public void setSlist1(List<Map<String, String>> slist1) {
		this.slist1 = slist1;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}
}