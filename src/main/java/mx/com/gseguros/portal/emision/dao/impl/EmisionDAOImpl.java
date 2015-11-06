package mx.com.gseguros.portal.emision.dao.impl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.gseguros.portal.consultas.model.DocumentoReciboParaMostrarDTO;
import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.emision.dao.EmisionDAO;
import mx.com.gseguros.portal.emision.model.EmisionVO;
import mx.com.gseguros.utils.Utils;
import oracle.jdbc.driver.OracleTypes;

import org.apache.log4j.Logger;
import org.springframework.data.jdbc.support.oracle.SqlArrayValue;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class EmisionDAOImpl extends AbstractManagerDAO implements EmisionDAO
{
	private final static Logger logger = Logger.getLogger(EmisionDAOImpl.class);
	
	@Override
	public EmisionVO emitir(String cdusuari, String cdunieco, String cdramo, String estado, String nmpoliza, 
			String nmsituac, String nmsuplem, String cdelemento, String cdcia, String cdplan, String cdperpag, 
			String cdperson, Date fecha, String ntramite) throws Exception {
		EmisionVO emision = new EmisionVO();
		
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("pv_cdusuari" , cdusuari);
		params.put("pv_cdunieco" , cdunieco);
		params.put("pv_cdramo"   , cdramo);
		params.put("pv_estado"   , estado);
		params.put("pv_nmpoliza" , nmpoliza);
		params.put("pv_nmsituac" , nmsituac);
		params.put("pv_nmsuplem" , nmsuplem);
		params.put("pv_cdelement", cdelemento);
		params.put("pv_cdcia"    , cdcia);
		params.put("pv_cdplan"   , cdplan);
		params.put("pv_cdperpag" , cdperpag);
		params.put("pv_cdperson" , cdperson);
		params.put("pv_fecha"    , fecha);
		params.put("pv_ntramite" , ntramite);
		
		Map<String,Object> resultado = ejecutaSP(new ProcesoEmisionGeneralSP(getDataSource()), params);
		
		emision.setNmpoliza((String)resultado.get("pv_nmpoliza_o"));
		emision.setNmpoliex((String)resultado.get("pv_nmpoliex_o"));
		emision.setNmsuplem((String)resultado.get("pv_nmsuplem_o"));
		emision.setEsDxN((String)resultado.get("pv_esdxn_o"));
		emision.setMessage((String)resultado.get("pv_message"));
		emision.setCdideper((String)resultado.get("pv_cdideper_o"));
//		wrapperResultados.getItemMap().put("nmpoliza", map.get("pv_nmpoliza_o"));
//		wrapperResultados.getItemMap().put("nmpoliex", map.get("pv_nmpoliex_o"));
//		wrapperResultados.getItemMap().put("nmsuplem", map.get("pv_nmsuplem_o"));
//		wrapperResultados.getItemMap().put("esdxn", map.get("pv_esdxn_o"));
//		wrapperResultados.getItemMap().put("CDIDEPER", map.get("pv_cdideper_o"));
		return emision;
	}
	
	
	protected class ProcesoEmisionGeneralSP extends StoredProcedure {
		
		protected ProcesoEmisionGeneralSP(DataSource dataSource) {
			
			super(dataSource,"PKG_EMISION.P_PROCESO_EMISION_GENERAL");
			declareParameter(new SqlParameter("pv_cdusuari",      OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdunieco",      OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo",        OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado",        OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza",      OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsituac",      OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem",      OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdelement",     OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdcia",         OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdplan",        OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdperpag",      OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdperson",      OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_fecha",         OracleTypes.DATE));
			declareParameter(new SqlParameter("pv_ntramite",      OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_nmpoliza_o", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_nmpoliex_o", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_nmsuplem_o", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_esdxn_o",    OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_message",    OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_cdideper_o", OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o",   OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o",    OracleTypes.VARCHAR));
		}
	}
	
	
	public void actualizaNmpoliexAutos(String cdunieco, String cdramo, String estado, String nmpoliza, 
			String nmsuplem, String nmpoliex, String cduniext, String ramoGS) throws Exception {
		
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("pv_cdunieco_i", cdunieco);
		params.put("pv_cdramo_i"  , cdramo);
		params.put("pv_estado_i"  , estado);
		params.put("pv_nmpoliza_i", nmpoliza);
		params.put("pv_nmsuplem_i", nmsuplem);
		params.put("pv_nmpoliex_i", nmpoliex);
		params.put("pv_cduniext_i", cduniext);
		params.put("pv_ramo_i"    , ramoGS);
		ejecutaSP(new ActualizaPolizaExternaSP(getDataSource()), params);
	}
	
	protected class ActualizaPolizaExternaSP extends StoredProcedure {
		
		protected ActualizaPolizaExternaSP(DataSource dataSource) {
			
			super(dataSource, "PKG_SATELITES.P_ACTUALIZA_NMPOLIEX_AUTOS");
			declareParameter(new SqlParameter("pv_cdunieco_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i",   OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i",   OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliex_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cduniext_i", OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_ramo_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o",OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o" ,OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void insertarMpoliimp(
			String ntramite
			,String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String tipo
			,String nmtraope
			,String nmrecibo
			)throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("ntramite" , ntramite);
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		params.put("tipo"     , tipo);
		params.put("nmtraope" , nmtraope);
		params.put("nmrecibo" , nmrecibo);
		ejecutaSP(new InsertarMpoliimp(getDataSource()),params);
	}
	
	protected class InsertarMpoliimp extends StoredProcedure
	{
		protected InsertarMpoliimp(DataSource dataSource)
		{
			super(dataSource, "PKG_SATELITES2.P_INSERTA_MPOLIIMP");
			declareParameter(new SqlParameter("ntramite" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("tipo"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmtraope" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmrecibo" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void marcarTramiteImpreso(String ntramite, String swimpres) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("ntramite" , ntramite);
		params.put("swimpres" , swimpres);
		ejecutaSP(new MarcarTramiteImpreso(getDataSource()),params);
	}
	
	protected class MarcarTramiteImpreso extends StoredProcedure
	{
		protected MarcarTramiteImpreso(DataSource dataSource)
		{
			super(dataSource, "PKG_SATELITES2.P_IMPRIMIR_TRAMITE");
			declareParameter(new SqlParameter("ntramite" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("swimpres" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	/**
	 * SUMA A LAS REMESAS LA IMPRESION ACTUAL
	 * SI LA SUMA RESULTA ESTAR COMPLETA: ACTUALIZA LAS REMESAS, Y LOS HIJOS EMISION/ENDOSOS SI HAY
	 */
	@Override
	public boolean sumarImpresiones(String lote, String tipolote, String peso) throws Exception
	{
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("lote"     , lote);
		params.put("tipolote" , tipolote);
		params.put("peso"     , peso);
		Map<String,Object> procRes = ejecutaSP(new SumarImpresiones(getDataSource()),params);
		boolean            impreso = "S".equals((String)procRes.get("pv_impreso_o"));
		logger.debug(Utils.log("PKG_SATELITES2.P_SUMA_IMPRESIONES impreso:",impreso));
		return impreso;
	}
	
	protected class SumarImpresiones extends StoredProcedure
	{
		protected SumarImpresiones(DataSource dataSource)
		{
			super(dataSource, "PKG_SATELITES2.P_SUMA_IMPRESIONES");
			declareParameter(new SqlParameter("lote"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("tipolote" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("peso"     , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_impreso_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"  , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"   , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void mostrarRecibosImpresosListaDeListas(List<DocumentoReciboParaMostrarDTO> lista)throws Exception
	{
		String[][] array = new String[lista.size()][];
		int        i     = 0;
		for(DocumentoReciboParaMostrarDTO pMovIte : lista)
		{
			array[i++] = pMovIte.indexar();
		}
		Map<String,Object> params = new LinkedHashMap<String,Object>();
		params.put("array" , new SqlArrayValue(array));
		Utils.debugProcedure(logger, "PKG_SATELITES2.P_MUESTRA_RECIBOS_IMPRESOS", params);
		ejecutaSP(new MostrarRecibosImpresosListaDeListas(getDataSource()),params);
	}
	
	protected class MostrarRecibosImpresosListaDeListas extends StoredProcedure
	{
		protected MostrarRecibosImpresosListaDeListas(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES2.P_MUESTRA_RECIBOS_IMPRESOS");
			declareParameter(new SqlParameter("array" , OracleTypes.ARRAY , "LISTA_LISTAS_VARCHAR2"));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
}