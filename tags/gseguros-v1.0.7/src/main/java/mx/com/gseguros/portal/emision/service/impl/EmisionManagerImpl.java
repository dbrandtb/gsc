package mx.com.gseguros.portal.emision.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaBaseVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaImapVO;
import mx.com.gseguros.portal.cotizacion.model.ManagerRespuestaVoidVO;
import mx.com.gseguros.portal.emision.service.EmisionManager;
import mx.com.gseguros.portal.general.dao.PantallasDAO;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.utils.Utilerias;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

public class EmisionManagerImpl implements EmisionManager
{
	private Map<String,Object> session;
	
	private static Logger logger = Logger.getLogger(EmisionManagerImpl.class);
	
	private PantallasDAO pantallasDAO;
	
	/*
	 * Utilitarios
	 */
	private void setCheckpoint(String checkpoint)
	{
		logger.debug(new StringBuilder("checkpoint-->").append(checkpoint).toString());
		session.put("checkpoint",checkpoint);
	}

	private String getCheckpoint()
	{
		return (String)session.get("checkpoint");
	}
	
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
	
	private boolean isBlank(String mensaje)
	{
		return StringUtils.isBlank(mensaje);
	}
	
	private void throwExc(String mensaje) throws ApplicationException
	{
		throw new ApplicationException(mensaje);
	}
	
	private void checkBlank(String cadena,String mensaje)throws ApplicationException
	{
		if(isBlank(cadena))
		{
			throwExc(mensaje);
		}
	}
	
	private void checkNull(Object objeto,String mensaje)throws ApplicationException
	{
		if(objeto==null)
		{
			throwExc(mensaje);
		}
	}
	
	private void checkList(List<?>lista,String mensaje)throws ApplicationException
	{
		checkNull(lista,mensaje);
		if(lista.size()==0)
		{
			throwExc(mensaje);
		}
	}
	/*
	 * Utilitarios
	 */
	
	@Override
	public ManagerRespuestaImapVO construirPantallaClausulasPoliza()
	{
		logger.info(Utilerias.join(
				 "\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ construirPantallaClausulasPoliza @@@@@@"
				));
		
		ManagerRespuestaImapVO resp=new ManagerRespuestaImapVO(true);
		resp.setImap(new HashMap<String,Item>());
		
		try
		{
			setCheckpoint("Recuperando combo de clausulas por poliza");
			List<ComponenteVO>comboClausulas=pantallasDAO.obtenerComponentes(
					null  //cdtiptra
					,null //cdunieco
					,null //cdramo
					,null //cdtipsit
					,null //estado
					,null //cdsisrol
					,"PANTALLA_CLAUSULAS_POLIZA"
					,"COMBO_CLAUSULAS"
					,null //orden
					);
			checkList(comboClausulas , "Error al obtener el combo de clausulas por poliza");
			GeneradorCampos gc = new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
			gc.generaComponentes(comboClausulas, true, false, true, false, false, false);
			resp.getImap().put("comboClausulas" , gc.getItems());
		}
		catch(Exception ex)
		{
			manejaException(ex, resp);
		}
		
		logger.info(Utilerias.join(
				 "\n@@@@@@ ",resp
				,"\n@@@@@@ construirPantallaClausulasPoliza @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return resp;
	}
	
	@Override
	public ManagerRespuestaVoidVO guardarClausulasPoliza(List<Map<String,String>>clausulas)
	{
		logger.info(Utilerias.join(
				"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				,"\n@@@@@@ guardarClausulasPoliza @@@@@@"
				,"\n@@@@@@ clausulas=",clausulas
				));
		
		ManagerRespuestaVoidVO resp=new ManagerRespuestaVoidVO(true);
		
		logger.info(Utilerias.join(
				 "\n@@@@@@ ",resp
				,"\n@@@@@@ guardarClausulasPoliza @@@@@@"
				,"\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
				));
		return resp;
	}
	
	/*
	 * Getters y setters
	 */
	
	@Override
	public void setSession(Map<String,Object>session)
	{
		this.session=session;
	}

	public void setPantallasDAO(PantallasDAO pantallasDAO) {
		this.pantallasDAO = pantallasDAO;
	}
}