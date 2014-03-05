package mx.com.gseguros.portal.siniestros.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mx.com.aon.core.web.PrincipalCoreAction;
import mx.com.aon.portal.model.UserVO;
import mx.com.gseguros.portal.cotizacion.model.Item;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import mx.com.gseguros.portal.general.service.PantallasManager;
import mx.com.gseguros.portal.general.util.GeneradorCampos;
import mx.com.gseguros.portal.general.util.RolSistema;
import mx.com.gseguros.portal.siniestros.model.HistorialSiniestroVO;
import mx.com.gseguros.portal.siniestros.service.SiniestrosManager;
import mx.com.gseguros.utils.Constantes;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.JSONUtil;

public class DetalleSiniestroAction extends PrincipalCoreAction {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(DetalleSiniestroAction.class);

	private transient SiniestrosManager siniestrosManager;
	private PantallasManager       pantallasManager;
	
	private DateFormat renderFechas = new SimpleDateFormat("dd/MM/yyyy");

	private boolean success;

	private HashMap<String, String> loadForm;
	
	private List<HashMap<String, String>> loadList;
    private List<HashMap<String, String>> saveList;
    private List<HashMap<String, String>> deleteList;
	
	private HashMap<String, String> params;
	private HashMap<String,Object> paramsO;
	
	private Map<String,Item> imap;
	
	private List<Map<String, String>> siniestro;
	
	private List<HistorialSiniestroVO> historialSiniestro;
	
	
	public String execute() throws Exception
	{
		logger.debug(""
				+ "\n####################################"
				+ "\n####################################"
				+ "\n###### DetalleSiniestroAction ######"
				+ "\n######                        ######"
				);
		logger.debug("params:"+params);
		
		if(!params.containsKey("nmsinies"))
		{
			try{
				String ntramite = params.get("ntramite");
				Map<String,String> paramsRes = (HashMap<String, String>) siniestrosManager.obtenerLlaveSiniestroReembolso(ntramite);
				
				for(Entry<String,String>en:paramsRes.entrySet()){
					params.put(en.getKey().toLowerCase(),en.getValue());
				}
				
			}catch(Exception ex){
				logger.error("error al obtener clave de siniestro para la pantalla del tabed panel",ex);
			}
		}
		
		logger.debug("params obtenidos:"+params);
		logger.debug(""
				+ "\n######                        ######"
				+ "\n###### DetalleSiniestroAction ######"
				+ "\n####################################"
				+ "\n####################################"
				);
    	success = true;
    	return SUCCESS;
    }
	
	
	public String loadInfoGeneralReclamacion() {
		success = true;
		return SUCCESS;
	}
	
	
	public String entradaRevisionAdmin(){
	   	
	   	try {
	   		logger.debug("Obteniendo Columnas dinamicas de Revision Administrativa");
	   		
	   		UserVO usuario  = (UserVO)session.get("USUARIO");
	    	String cdrol    = usuario.getRolActivo().getObjeto().getValue();
	    	String pantalla = "AFILIADOS_AGRUPADOS";
	    	String seccion  = "COLUMNAS";
	    	
	    	List<ComponenteVO> componentes = pantallasManager.obtenerComponentes(
	    			null, null, null, null, null, cdrol, pantalla, seccion, null);
	    	
	    	for(ComponenteVO com:componentes)
	    	{
	    		com.setWidth(100);
	    	}
	    	
	    	GeneradorCampos gc=new GeneradorCampos(ServletActionContext.getServletContext().getServletContextName());
	    	
	    	gc.generaComponentes(componentes, true, false, false, true,false, false);
	    	
	    	imap = new HashMap<String,Item>();
	    	imap.put("gridColumns",gc.getColumns());
	    	
	    	pantalla = "DETALLE_FACTURA";
	    	seccion  = "BOTONES_CONCEPTOS";
	    	
	    	componentes = pantallasManager.obtenerComponentes(
	    			null, null, null, null, null, cdrol, pantalla, seccion, null);
	    	
	    	gc.generaComponentes(componentes, true, false, false, false,false, true);
	    	
	    	imap.put("conceptosButton",gc.getButtons());
	    	
	    	seccion = "FORM_EDICION";
	    	componentes = pantallasManager.obtenerComponentes(
	    			null, null, null, null, null, cdrol, pantalla, seccion, null);
	    	gc.generaComponentes(componentes, true, false, true, false, false, false);
	    	imap.put("itemsEdicion",gc.getItems());
	   		
	   		logger.debug("Resultado: "+imap);
	   		//siniestrosManager.guardaListaTramites(params, deleteList, saveList);
	   	}catch( Exception e){
	   		logger.error("Error en guardaListaTramites",e);
	   		success =  false;
	   		return SUCCESS;
	   	}
	   	success = true;
	   	return SUCCESS;
	   }
	
	public String loadListaFacturasTramite(){
	   	try {
		   		List<Map<String, String>> result = siniestrosManager.P_GET_FACTURAS_SINIESTRO(params.get("cdunieco"), params.get("cdramo"), params.get("estado"), params.get("nmpoliza"), params.get("nmsuplem"), params.get("nmsituac"), params.get("aaapertu"), params.get("status"), params.get("nmsinies")); 
		   		loadList = new ArrayList<HashMap<String, String>>();
		   		
		   		HashMap<String, String> mapa =null;
		   		for(Map item: result){
		   			mapa =  new HashMap<String, String>();
		   			mapa.putAll(item);
		   			loadList.add(mapa);
		   		}
	   		
	   	}catch( Exception e){
	   		logger.error("Error en loadListaFacturasTramite",e);
	   		success =  false;
	   		return SUCCESS;
	   	}
	   	success = true;
	   	return SUCCESS;
	}
	
	public String guardaFacturaTramite(){
		
		String cdunieco  = params.get("cdunieco");
		String cdramo    = params.get("cdramo");
		String estado    = params.get("estado");
		String nmpoliza  = params.get("nmpoliza");
		String nmsituac  = params.get("nmsituac");
		String nmsuplem  = params.get("nmsuplem");
		String status    = params.get("status");
		String aaapertu  = params.get("aaapertu");
		String nmsinies  = params.get("nmsinies");
		String nfactura  = params.get("nfactura");
		
		String autrecla = params.get("autrecla");
		String commenar = params.get("commenar");
		String autmedic = params.get("autmedic");
		String commenme = params.get("commenme");
		
		UserVO usuario  = (UserVO)session.get("USUARIO");
		String cdrol    = usuario.getRolActivo().getObjeto().getValue();
		
		logger.debug("Guarda Factura, Rol Sistema: "+cdrol);
	   	
	   	try {
	   		siniestrosManager.guardaListaFacMesaControl(params.get("ntramite"), params.get("nfactura"), params.get("fefactura"), params.get("cdtipser"), params.get("cdpresta"), params.get("ptimport"), params.get("cdgarant"), params.get("cdconval"), params.get("descporc"), params.get("descnume"));
	   		
	   		if(RolSistema.COORDINADOR_SINIESTROS.getCdsisrol().equals(cdrol) || RolSistema.OPERADOR_SINIESTROS.getCdsisrol().equals(cdrol)){
	   			siniestrosManager.P_MOV_MAUTSINI(cdunieco, cdramo, estado, nmpoliza, nmsuplem, nmsituac, aaapertu, status, nmsinies, nfactura,
	    				Constantes.MAUTSINI_AREA_RECLAMACIONES, autrecla, Constantes.MAUTSINI_FACTURA, commenar, Constantes.INSERT_MODE);
	   		} else if(RolSistema.COORDINADOR_MEDICO.getCdsisrol().equals(cdrol) || RolSistema.MEDICO.getCdsisrol().equals(cdrol)){
	   			siniestrosManager.P_MOV_MAUTSINI(cdunieco, cdramo, estado, nmpoliza, nmsuplem, nmsituac, aaapertu, status, nmsinies, nfactura,
	    				Constantes.MAUTSINI_AREA_MEDICA, autmedic, Constantes.MAUTSINI_FACTURA, commenme, Constantes.INSERT_MODE);
	   		}
	   		
	   	}catch( Exception e){
	   		logger.error("Error en guardaListaTramites",e);
	   		success =  false;
	   		return SUCCESS;
	   	}
	   	success = true;
	   	return SUCCESS;
	}
	
	public String actualizaFacturaTramite(){
		String cdunieco  = params.get("cdunieco");
		String cdramo    = params.get("cdramo");
		String estado    = params.get("estado");
		String nmpoliza  = params.get("nmpoliza");
		String nmsituac  = params.get("nmsituac");
		String nmsuplem  = params.get("nmsuplem");
		String status    = params.get("status");
		String aaapertu  = params.get("aaapertu");
		String nmsinies  = params.get("nmsinies");
		String nfactura  = params.get("nfactura");
		
		String autrecla = params.get("autrecla");
		String commenar = params.get("commenar");
		String autmedic = params.get("autmedic");
		String commenme = params.get("commenme");
		
		UserVO usuario  = (UserVO)session.get("USUARIO");
		String cdrol    = usuario.getRolActivo().getObjeto().getValue();
		
		logger.debug("Actuliza Factura, Rol Sistema: "+cdrol);
		try {
			siniestrosManager.movFacMesaControl(params.get("ntramite"), params.get("nfactura"), params.get("fefactura"), params.get("cdtipser"), params.get("cdpresta"), params.get("ptimport"), params.get("cdgarant"), params.get("cdconval"), params.get("descporc"), params.get("descnume"), Constantes.UPDATE_MODE);
			
			if(RolSistema.COORDINADOR_SINIESTROS.getCdsisrol().equals(cdrol) || RolSistema.OPERADOR_SINIESTROS.getCdsisrol().equals(cdrol)){
				siniestrosManager.P_MOV_MAUTSINI(cdunieco, cdramo, estado, nmpoliza, nmsuplem, nmsituac, aaapertu, status, nmsinies, nfactura,
	    				Constantes.MAUTSINI_AREA_RECLAMACIONES, autrecla, Constantes.MAUTSINI_FACTURA, commenar, Constantes.UPDATE_MODE);
			} else if(RolSistema.COORDINADOR_MEDICO.getCdsisrol().equals(cdrol) || RolSistema.MEDICO.getCdsisrol().equals(cdrol)){
				siniestrosManager.P_MOV_MAUTSINI(cdunieco, cdramo, estado, nmpoliza, nmsuplem, nmsituac, aaapertu, status, nmsinies, nfactura,
	    				Constantes.MAUTSINI_AREA_MEDICA, autmedic, Constantes.MAUTSINI_FACTURA, commenme, Constantes.UPDATE_MODE);
			}
    		
		}catch( Exception e){
			logger.error("Error en actualizaFacturaTramite",e);
			success =  false;
			return SUCCESS;
		}
		success = true;
		return SUCCESS;
	}

	public String borraFacturaTramite(){
		
		try {
			siniestrosManager.movFacMesaControl(params.get("ntramite"), params.get("nfactura"), null, null, null, null, null, null, null, null, Constantes.DELETE_MODE);
		}catch( Exception e){
			logger.error("Error en borraFacturaTramite",e);
			success =  false;
			return SUCCESS;
		}
		success = true;
		return SUCCESS;
	}
	
	
	public String obtieneDatosGeneralesSiniestro() throws Exception {
		try {
			siniestro = siniestrosManager.obtieneDatosGeneralesSiniestro(
					params.get("cdunieco"), params.get("cdramo"),
					params.get("estado"), params.get("nmpoliza"),
					params.get("nmsituac"), params.get("nmsuplem"),
					params.get("status"), params.get("aaapertu"),
					params.get("nmsinies"), params.get("ntramite"));
			success = true;
		}catch(Exception e){
	   		logger.error("Error en actualizaDatosGeneralesSiniestro", e);
	   	}
		return SUCCESS;
	}
	
	
	public String actualizaDatosGeneralesSiniestro() throws Exception {
		try {
			Map<String,Object> pMesaCtrl = new HashMap<String,Object>();
			pMesaCtrl.put("pv_ntramite_i", params.get("ntramite"));
			pMesaCtrl.put("pv_cdsucadm_i", params.get("cdsucadm"));
			pMesaCtrl.put("pv_cdsucdoc_i", params.get("cdsucdoc"));
			logger.debug("pMesaCtrl=" + pMesaCtrl);
    		siniestrosManager.actualizaOTValorMesaControl(pMesaCtrl);
			
			Date dFeocurre = renderFechas.parse(params.get("feocurre"));
            siniestrosManager.actualizaDatosGeneralesSiniestro(
					params.get("cdunieco"), params.get("cdramo"),
					params.get("estado"), params.get("nmpoliza"),
					params.get("nmsuplem"),params.get("aaapertu"),
					params.get("nmsinies"), dFeocurre,
					params.get("nmreclamo"), params.get("cdicd"),
					params.get("cdicd2"), params.get("cdcausa"));
			success = true;
		} catch(Exception e) {
	   		logger.error("Error en actualizaDatosGeneralesSiniestro", e);
	   	}
		return SUCCESS;
	}
	
	
	public String obtieneHistorialReclamaciones() throws Exception {
		try {
			// Dummy data:
			
			// TODO: Terminar cuando este listo el SP
			historialSiniestro = siniestrosManager.obtieneHistorialReclamaciones(
					params.get("cdunieco"), params.get("cdramo"),
					params.get("estado"), params.get("nmpoliza"),
					params.get("nmsituac"), params.get("nmsuplem"),
					params.get("status"), params.get("aaapertu"),
					params.get("nmsinies"), params.get("ntramite"));
			
			success = true;
		} catch(Exception e) {
	   		logger.error("Error en actualizaDatosGeneralesSiniestro", e);
	   	}
		return SUCCESS;
	}
	
	
	
	// Getters and setters:

	public void setSiniestrosManager(SiniestrosManager siniestrosManager) {
		this.siniestrosManager = siniestrosManager;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getLoadForm() {
		try {
			return JSONUtil.serialize(loadForm);
		} catch (Exception e) {
			logger.error("Error al generar JSON de LoadForm",e);
			return null;
		}
	}

	public void setLoadForm(HashMap<String, String> loadForm) {
		this.loadForm = loadForm;
	}

	public HashMap<String, String> getParams() {
		return params;
	}

	public String getParamsJson() {
		try {
			return JSONUtil.serialize(params);
		} catch (Exception e) {
			logger.error("Error al generar JSON de params",e);
			return null;
		}
	}

	public void setParams(HashMap<String, String> params) {
		this.params = params;
	}


	public List<HashMap<String, String>> getLoadList() {
		return loadList;
	}


	public void setLoadList(List<HashMap<String, String>> loadList) {
		this.loadList = loadList;
	}


	public List<HashMap<String, String>> getSaveList() {
		return saveList;
	}


	public void setSaveList(List<HashMap<String, String>> saveList) {
		this.saveList = saveList;
	}


	public HashMap<String, Object> getParamsO() {
		return paramsO;
	}


	public void setParamsO(HashMap<String, Object> paramsO) {
		this.paramsO = paramsO;
	}


	public void setPantallasManager(PantallasManager pantallasManager) {
		this.pantallasManager = pantallasManager;
	}


	public Map<String, Item> getImap() {
		return imap;
	}


	public void setImap(Map<String, Item> imap) {
		this.imap = imap;
	}

	public List<Map<String, String>> getSiniestro() {
		return siniestro;
	}

	public void setSiniestro(List<Map<String, String>> siniestro) {
		this.siniestro = siniestro;
	}


	public List<HistorialSiniestroVO> getHistorialSiniestro() {
		return historialSiniestro;
	}


	public void setHistorialSiniestro(List<HistorialSiniestroVO> historialSiniestro) {
		this.historialSiniestro = historialSiniestro;
	}
	
}