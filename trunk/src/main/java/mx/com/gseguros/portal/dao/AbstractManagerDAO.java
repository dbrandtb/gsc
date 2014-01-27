package mx.com.gseguros.portal.dao;

import java.util.Map;

import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.exception.DaoException;
import mx.com.gseguros.portal.general.model.BaseVO;
import mx.com.gseguros.utils.Constantes;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.StoredProcedure;

import com.opensymphony.xwork2.ActionSupport;

public abstract class AbstractManagerDAO extends JdbcDaoSupport {

	private static Logger logger = Logger.getLogger(AbstractManagerDAO.class);
	
	private ProcesoResultadoDAO procesoResultadoDAO;

	
    /**
     * Invocaci&oacute;n a un Stored Procedure
     * @param storedProcedure Entidad que representa al SP a invocar  
     * @param parameters      Par&aactue;metros para invocar del SP
     * @return Map con los objetos recuperados de la consulta al SP
     * @throws DaoException
     */
    public Map<String, Object> ejecutaSP(StoredProcedure storedProcedure, Map parameters) throws DaoException {
		try {
			Map<String, Object> mapResult = storedProcedure.execute(parameters);
			
	        BaseVO mensajeRespuesta = traduceMensaje(mapResult);
	        mapResult.put("msg_id", mensajeRespuesta.getKey());
	        mapResult.put("msg_title", mensajeRespuesta.getValue());
	        
	        return mapResult;
	        
		} catch (Exception ex) {
			throw new DaoException(ex.getMessage(), ex);
		}
    }
    
    
    /**
     * 
     * @param mapResult
     * @return
     * @throws Exception
     */
    private BaseVO traduceMensaje(Map<String, Object> mapResult) throws Exception {
    	
    	String msgId = mapResult.get("pv_msg_id_o") != null ? mapResult.get("pv_msg_id_o").toString() : "";  
        String msgTitle = mapResult.get("pv_title_o")  != null ? mapResult.get("pv_title_o").toString()  : "";
        logger.info(new StringBuilder("MsgId=").append(msgId).append(" ").append("MsgTitle=").append(msgTitle));
    	
        if(StringUtils.isNotBlank(msgId) ) {
        	// Buscar el mensaje en properties, sino en BD:
        	ActionSupport actionSupport = new ActionSupport();
            if (!actionSupport.getText(msgId).equals(msgId)) {
            	logger.info( new StringBuilder("MsgText=").append(actionSupport.getText(msgId)) );
            	return new BaseVO(msgId, actionSupport.getText(msgId));
            } else {
            	
            	BaseVO mensajeRespuesta = procesoResultadoDAO.obtieneMensaje(msgId, "0", null, null);
            	
            	if (mensajeRespuesta == null || StringUtils.isBlank(mensajeRespuesta.getKey()) || StringUtils.isBlank(mensajeRespuesta.getValue())) {
    				String msgException = "No se encontró el mensaje de respuesta del servicio de datos, verifique los parámetros de salida";
    				logger.error(msgException);
    				throw new ApplicationException(msgException);
    			}
            	logger.info( new StringBuilder("MsgText=").append(mensajeRespuesta.getValue()) );
    			if (mensajeRespuesta.getKey().equals(Constantes.MSG_TITLE_ERROR)) {
    				String msgException = mensajeRespuesta.getValue(); 
    				logger.error("Error de SP: " + msgException);
    				throw new ApplicationException(msgException);
    			}
    			return new BaseVO(mensajeRespuesta.getKey(), mensajeRespuesta.getValue());
            }
        } else {
        	logger.info("Parametros de estatus de salida vacios");
        	return new BaseVO(msgId, msgTitle);
        }
    }
    
    
    /**
     * 
     * @param procesoResultadoDAO
     */
	public void setProcesoResultadoDAO(ProcesoResultadoDAO procesoResultadoDAO) {
		this.procesoResultadoDAO = procesoResultadoDAO;
	}

}