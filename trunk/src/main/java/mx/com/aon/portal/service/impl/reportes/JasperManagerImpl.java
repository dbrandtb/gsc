package mx.com.aon.portal.service.impl.reportes;

import mx.com.aon.portal.service.reportes.JasperManager;
import mx.com.aon.portal.model.reporte.ReporteVO;
import mx.com.aon.portal.model.reporte.AtributosVO;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import mx.com.aon.tmp.Endpoint;
import mx.com.aon.tmp.BackboneApplicationException;
import mx.com.gseguros.exception.ApplicationException;



/**
 * Created by IntelliJ IDEA.
 * User: Oscar Parales
 * Date: 12-jun-2008
 * Time: 16:26:13
 * To change this template use File | Settings | File Templates.
 */

public class JasperManagerImpl implements JasperManager {

	private static Logger logger = Logger.getLogger(JasperManagerImpl.class);


	private Map endpoints;


	public void setEndpoints(Map endpoints) {
		this.endpoints = endpoints;
	}


}