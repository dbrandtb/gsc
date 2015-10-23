package mx.com.gseguros.portal.consultas.controller;

import java.util.List;
import java.util.Map;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.consultas.service.ExplotacionDocumentosManager;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.utils.Utils;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
@ParentPackage(value="default")
@Namespace("/consultas")
public class ExplotacionDocumentosAction extends PrincipalCoreAction
{
	private final static Logger logger = LoggerFactory.getLogger(ExplotacionDocumentosAction.class);
	
	private Map<String,String>       params;
	private List<Map<String,String>> list;
	private boolean                  success;
	private String                   message;
	private Map<String,Item>         items;
	
	@Autowired
	private ExplotacionDocumentosManager explotacionDocumentosManager;
	
	public ExplotacionDocumentosAction()
	{
		this.session=ActionContext.getContext().getSession();
	}
	
	@Action(value   = "pantallaExplotacionDocumentos",
	        results = {
			    @Result(name="error"   , location="/jsp-script/general/errorPantalla.jsp"),
                @Result(name="success" , location="/jsp-script/consultas/pantallaExplotacionDocumentos.jsp")
            }
	)
	public String pantallaExplotacionDocumentos()
	{
		logger.debug(Utils.log(
				 "\n###########################################"
				,"\n###### pantallaExplotacionDocumentos ######"
				));
		
		String result = ERROR;
		
		try
		{
			UserVO usuario = Utils.validateSession(session);
			
			items = explotacionDocumentosManager.pantallaExplotacionDocumentos(usuario.getUser(),usuario.getRolActivo().getClave());
			
			result = SUCCESS;
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(
				 "\n###### pantallaExplotacionDocumentos ######"
				,"\n###########################################"
				));
		return result;
	}
	
	@Action(value           = "generarLote",
			results         = { @Result(name="success", type="json") },
            interceptorRefs = {
			    @InterceptorRef(value = "json", params = {"enableSMD", "true", "ignoreSMDMethodInterfaces", "false" })
			})
	public String generarLote()
	{
		logger.debug(Utils.log(
				 "\n#########################"
				,"\n###### generarLote ######"
				,"\n###### params=" , params
				,"\n###### list="   , list
				));
		
		try
		{
			UserVO usuario = Utils.validateSession(session);
			
			Utils.validate(params , "No se recibieron par\u00E1metros");
			
			String cdtipram  = params.get("cdtipram")
			       ,cdtipimp = params.get("cdtipimp")
			       ,tipolote = params.get("tipolote");
			
			Utils.validate(
					cdtipram  , "No se recibi\u00F3 el tipo de ramo"
					,cdtipimp , "No se recibi\u00F3 el tipo de impresi\u00F3n"
					,tipolote , "No se recibi\u00F3 el tipo de lote"
					);
			
			if(!"P".equals(tipolote)
					&&!"R".equals(tipolote))
			{
				throw new ApplicationException("Tipo de lote incorrecto");
			}
			
			Utils.validate(list , "No se recibieron movimientos");
			
			for(Map<String,String>mov:list)
			{
				Utils.validate(
						mov.get("cdunieco")  , "Los movimientos no tienen sucursal"
						,mov.get("cdramo")   , "Los movimientos no tienen producto"
						,mov.get("estado")   , "Los movimientos no tienen estado"
						,mov.get("nmpoliza") , "Los movimientos no tienen p\u00F3liza"
						,mov.get("nmsuplem") , "Los movimientos no tienen suplemento"
						,mov.get("cdagente") , "Los movimientos no tienen agente"
						);
				
				if("P".equals(tipolote))
				{
					Utils.validate(mov.get("ntramite") , "Los movimientos no tienen tr\u00E1mite");
				}
				else
				{
					Utils.validate(mov.get("nmrecibo") , "Los movimientos no tienen n\u00FAmero de recibo");
				}
				
			}
			
			String lote = explotacionDocumentosManager.generarLote(
					usuario.getUser()
					,usuario.getRolActivo().getClave()
					,cdtipram
					,cdtipimp
					,tipolote
					,list
					);
			
			params.put("lote" , lote);
			
			success = true;
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(
				 "\n###### generarLote ######"
				,"\n#########################"
				));
		
		return SUCCESS;
	}
	
	public String imprimirLote()
	{
		logger.debug(Utils.log(
				 "\n##########################"
				,"\n###### imprimirLote ######"
				));
		
		try
		{
			UserVO usuario = Utils.validateSession(session);
			String cdusuari = usuario.getUser();
			String cdsisrol = usuario.getRolActivo().getClave();
			
			Utils.validate(params , "No se recibieron datos");
			
			String lote     = params.get("lote");
			String hoja     = params.get("hoja");
			String peso     = params.get("peso");
			String cdtipram = params.get("cdtipram");
			String cdtipimp = params.get("cdtipimp");
			String tipolote = params.get("tipolote");
			String cdunieco = params.get("cdunieco");
			String ip       = params.get("ip");
			String nmimpres = params.get("nmimpres");
			
			Utils.validate(
					lote      , "No se recibi\u00F3 el lote"
					,hoja     , "No se recibi\u00F3 el tipo de hoja"
					,peso     , "No se recibi\u00F3 el peso"
					,cdtipram , "No se recibi\u00F3 el tipo de ramo"
					,cdtipimp , "No se recibi\u00F3 el tipo de impresi\u00F3n"
					,tipolote , "No se recibi\u00F3 el tipo de lote"
					,cdunieco , "No se recibi\u00F3 la sucursal de la impresora"
					,ip       , "No se recibi\u00F3 la ip de la impresora"
					,nmimpres , "No se recibi\u00F3 el ordinal de impresora"
					);
			
			explotacionDocumentosManager.imprimirLote(
					lote
					,hoja
					,peso
					,cdtipram
					,cdtipimp
					,tipolote
					,cdunieco
					,ip
					,nmimpres
					);
			
			success = true;
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(
				 "\n###### imprimirLote ######"
				,"\n##########################"
				));
		return SUCCESS;
	}
	
	@Action(value   = "pantallaExplotacionRecibos",
	        results = {
			    @Result(name="error"   , location="/jsp-script/general/errorPantalla.jsp"),
                @Result(name="success" , location="/jsp-script/consultas/pantallaExplotacionRecibos.jsp")
            }
	)
	public String pantallaExplotacionRecibos()
	{
		logger.debug(Utils.log(
				 "\n########################################"
				,"\n###### pantallaExplotacionRecibos ######"
				));
		
		String result = ERROR;
		
		try
		{
			UserVO usuario = Utils.validateSession(session);
			
			items = explotacionDocumentosManager.pantallaExplotacionRecibos(usuario.getUser(),usuario.getRolActivo().getClave());
			
			result = SUCCESS;
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(
				 "\n###### pantallaExplotacionRecibos ######"
				,"\n########################################"
				));
		return result;
	}
	
	@Action(value   = "pantallaPermisosImpresion",
	        results = {
			    @Result(name="error"   , location="/jsp-script/general/errorPantalla.jsp"),
                @Result(name="success" , location="/jsp-script/consultas/pantallaPermisosImpresion.jsp")
            }
	)
	public String pantallaPermisosImpresion()
	{
		logger.debug(Utils.log(
				 "\n#######################################"
				,"\n###### pantallaPermisosImpresion ######"
				));
		
		String result = ERROR;
		
		try
		{
			UserVO usuario = Utils.validateSession(session);
			
			items = explotacionDocumentosManager.pantallaPermisosImpresion(usuario.getUser(),usuario.getRolActivo().getClave());
			
			result = SUCCESS;
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(
				 "\n###### pantallaPermisosImpresion ######"
				,"\n#######################################"
				));
		return result;
	}
	
	@Action(value   = "movPermisoImpresion",
			results = { @Result(name="success", type="json") }
	)
	public String movPermisoImpresion()
	{
		logger.debug(Utils.log(
				 "\n#################################"
				,"\n###### movPermisoImpresion ######"
				,"\n###### params=",params
				));
		
		try
		{
			Utils.validateSession(session);
			
			Utils.validate(params , "No se recibieron datos");
			
			String tipo     = params.get("tipo");
			String cdusuari = params.get("cdusuari");
			String cdunieco = params.get("cdunieco");
			String cdtipram = params.get("cdtipram");
			String clave    = params.get("clave");
			String funcion  = params.get("funcion");
			String accion   = params.get("accion");
			
			Utils.validate(
					tipo      , "No se recibi\u00F3 el tipo"
					,cdusuari , "No se recibi\u00F3 el usuario"
					,cdunieco , "No se recibi\u00F3 la sucursal"
					,cdtipram , "No se recibi\u00F3 el tipo de ramo"
					,clave    , "No se recibi\u00F3 la clave"
					,funcion  , "No se recibi\u00F3 el switch"
					,accion   , "No se recibi\u00F3 la operaci\u00F3n"
					);
			
			explotacionDocumentosManager.movPermisoImpresion(
					tipo
					,cdusuari
					,cdunieco
					,cdtipram
					,clave
					,funcion
					,accion
					);
			
			success = true;
		}
		catch(Exception ex)
		{
			message = Utils.manejaExcepcion(ex);
		}
		
		logger.debug(Utils.log(
				 "\n###### movPermisoImpresion ######"
				,"\n#################################"
				));
		return SUCCESS;
	}
	
	////////////////// Getters y setters ///////////////////
	                                                      //
	public Map<String, String> getParams() {              //
		return params;                                    //
	}                                                     //
	                                                      //
	public void setParams(Map<String, String> params) {   //
		this.params = params;                             //
	}                                                     //
	                                                      //
	public List<Map<String, String>> getList() {          //
		return list;                                      //
	}                                                     //
	                                                      //
	public void setList(List<Map<String, String>> list) { //
		this.list = list;                                 //
	}                                                     //
	                                                      //
	public boolean isSuccess() {                          //
		return success;                                   //
	}                                                     //
	                                                      //
	public void setSuccess(boolean success) {             //
		this.success = success;                           //
	}                                                     //
	                                                      //
	public String getMessage() {                          //
		return message;                                   //
	}                                                     //
	                                                      //
	public void setMessage(String message) {              //
		this.message = message;                           //
	}                                                     //
	                                                      //
	public Map<String, Item> getItems() {                 //
		return items;                                     //
	}                                                     //
                                                          //
	public void setItems(Map<String, Item> items) {       //
		this.items = items;                               //
	}                                                     //
                                                          //
	////////////////////////////////////////////////////////
}