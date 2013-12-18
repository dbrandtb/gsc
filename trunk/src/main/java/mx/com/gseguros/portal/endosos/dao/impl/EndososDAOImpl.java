package mx.com.gseguros.portal.endosos.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import mx.com.aon.portal.dao.WrapperResultadosGeneric;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.portal.cotizacion.model.Tatri;
import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.dao.impl.DinamicMapper;
import mx.com.gseguros.portal.dao.impl.GenericMapper;
import mx.com.gseguros.portal.endosos.dao.EndososDAO;
import mx.com.gseguros.utils.Utilerias;
import oracle.jdbc.driver.OracleTypes;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class EndososDAOImpl extends AbstractManagerDAO implements EndososDAO
{

	protected class ObtenerEndosos extends StoredProcedure
	{
		String[] columnas=new String[]{
				"CDUNIECO" 
	            ,"CDRAMO" 
	            ,"ESTADO" 
	            ,"NMPOLIZA" 
	            ,"NMSUPLEM" 
	            ,"NMPOLIEX" 
	            ,"NSUPLOGI" 
	            ,"FEEMISIO" 
	            ,"FEINIVAL" 
	            ,"DSCOMENT" 
	            ,"CDTIPSIT" 
	            ,"DSTIPSIT" 
                ,"PRIMA_TOTAL"
                ,"NTRAMITE"
		};

		protected ObtenerEndosos(DataSource dataSource)
		{
			super(dataSource, "PKG_CONSULTA.P_GET_ENDOSOS_G");
			declareParameter(new SqlParameter("pv_nmpoliex_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdrfc_i"       , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdperson_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nombre_i"    , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR , new GenericMapper(columnas)));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
	        declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String, String>> obtenerEndosos(Map<String, String> params) throws Exception
	{
		Map<String,Object> resultadoMap=this.ejecutaSP(new ObtenerEndosos(this.getDataSource()), params);
		return (List<Map<String, String>>) resultadoMap.get("pv_registro_o");
	}
	
	protected class GuardarEndosoNombres extends StoredProcedure
	{
		protected GuardarEndosoNombres(DataSource dataSource)
		{
			super(dataSource, "PKG_ENDOSOS.P_ENDOSO_INICIA");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_fecha_i"    , OracleTypes.DATE));
			declareParameter(new SqlParameter("pv_cdelemen_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdusuari_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_proceso_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipsup_i" , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_nmsuplem_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_nsuplogi_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_fesolici_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_feinival_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String, String> guardarEndosoNombres(Map<String, Object> params) throws Exception
	{
		Map<String,Object> resultadoMap=this.ejecutaSP(new GuardarEndosoNombres(this.getDataSource()), params);
		Map<String,String>map=new LinkedHashMap<String,String>(0);
		for(Entry en:resultadoMap.entrySet())
		{
			String col=(String) en.getKey();
			if(col!=null&&col.substring(0,5).equalsIgnoreCase("pv_fe"))
			{
				map.put(col,Utilerias.formateaFecha(en.getValue()+""));
			}
			else
			{
				map.put(col,en.getValue()+"");
			}
		}
		return map;
	}
	
	protected class GuardarEndosoDomicilio extends StoredProcedure
	{
		protected GuardarEndosoDomicilio(DataSource dataSource)
		{
			super(dataSource, "PKG_ENDOSOS.P_INICIA_ENDOSO");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_fecha_i"    , OracleTypes.DATE));
			declareParameter(new SqlParameter("pv_cdelemen_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdusuari_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_proceso_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipsup_i" , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_nmsuplem_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_nsuplogi_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_fesolici_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_feinival_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String, String> guardarEndosoDomicilio(Map<String, Object> params) throws Exception
	{
		Map<String,Object> resultadoMap=this.ejecutaSP(new GuardarEndosoNombres(this.getDataSource()), params);
		Map<String,String>map=new LinkedHashMap<String,String>(0);
		for(Entry en:resultadoMap.entrySet())
		{
			String col=(String) en.getKey();
			if(col!=null&&col.substring(0,5).equalsIgnoreCase("pv_fe"))
			{
				map.put(col,Utilerias.formateaFecha(en.getValue()+""));
			}
			else
			{
				map.put(col,en.getValue()+"");
			}
		}
		return map;
	}
	
	protected class ConfirmarEndosoB extends StoredProcedure
	{
		protected ConfirmarEndosoB(DataSource dataSource)
		{
			/*
		    pv_cdunieco_i
		    pv_cdramo_i
		    pv_estado_i
		    pv_nmpoliza_i
		    pv_nmsuplem_i
		    pv_nsuplogi_i
		    pv_cdtipsup_i
		    pv_dscoment_i
		    pv_msg_id_o
		    pv_title_o
			*/
			super(dataSource, "PKG_ENDOSOS.P_CONFIRMAR_ENDOSOB");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nsuplogi_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipsup_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_dscoment_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String, String> confirmarEndosoB(Map<String, String> params) throws Exception
	{
		Map<String,Object> resultadoMap=this.ejecutaSP(new ConfirmarEndosoB(this.getDataSource()), params);
		Map<String,String>map=new LinkedHashMap<String,String>(0);
		for(Entry en:resultadoMap.entrySet())
		{
			String col=(String) en.getKey();
			if(col!=null&&col.substring(0,5).equalsIgnoreCase("pv_fe"))
			{
				map.put(col,Utilerias.formateaFecha(en.getValue()+""));
			}
			else
			{
				map.put(col,en.getValue()+"");
			}
		}
		return map;
	}
	
	protected class ReimprimeDocumentos extends StoredProcedure
	{
		String columnas[]=new String[]{
				"nmsolici"
				,"nmsituac"
				,"descripc"
				,"descripl"
				,"ntramite"
		};
		protected ReimprimeDocumentos(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_reImp_documentos");
			declareParameter(new SqlParameter("pv_cdunieco_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_tipmov_i"      , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(columnas)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
		}
	}
	
	@Override
	public List<Map<String,String>> reimprimeDocumentos(Map<String,String>params) throws Exception
	{
		Map<String,Object> resultadoMap=this.ejecutaSP(new ReimprimeDocumentos(this.getDataSource()), params);
		return (List<Map<String, String>>) resultadoMap.get("pv_registro_o");
	}
	
	protected class ObtieneCoberturasDisponibles extends StoredProcedure
	{
		
		String columnas[]=new String[]{"GARANTIA","NOMBRE_GARANTIA","SWOBLIGA","SUMA_ASEGURADA","CDCAPITA",
				"status","cdtipbca","ptvalbas","swmanual","swreas","cdagrupa",
				"ptreduci","fereduci","swrevalo"};
		
		protected ObtieneCoberturasDisponibles(DataSource dataSource)
		{
			super(dataSource,"PKG_COTIZA.P_GET_COBERTURAS_DISP");
			declareParameter(new SqlParameter("pv_cdunieco_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i",      OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i",      OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsituac_i",    OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o",   OracleTypes.CURSOR, new GenericMapper(columnas)));
			declareParameter(new SqlOutParameter("pv_msg_id_o",     OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o",      OracleTypes.VARCHAR));
		}
	}
	
	@Override
	public List<Map<String,String>> obtieneCoberturasDisponibles(Map<String,String>params) throws Exception
	{
		Map<String,Object> resultadoMap=this.ejecutaSP(new ObtieneCoberturasDisponibles(this.getDataSource()), params);
		return (List<Map<String, String>>) resultadoMap.get("pv_registro_o");
	}
	
	protected class GuardarEndosoCoberturas extends StoredProcedure
	{
		protected GuardarEndosoCoberturas(DataSource dataSource)
		{
			super(dataSource, "PKG_ENDOSOS.P_ENDOSO_INICIA");
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_fecha_i"    , OracleTypes.DATE));
			declareParameter(new SqlParameter("pv_cdelemen_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdusuari_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_proceso_i"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipsup_i" , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("pv_nmsuplem_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_nsuplogi_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_fesolici_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_feinival_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String, String> guardarEndosoCoberturas(Map<String, Object> params) throws Exception
	{
		Map<String,Object> resultadoMap=this.ejecutaSP(new GuardarEndosoNombres(this.getDataSource()), params);
		Map<String,String>map=new LinkedHashMap<String,String>(0);
		for(Entry en:resultadoMap.entrySet())
		{
			String col=(String) en.getKey();
			if(col!=null&&col.substring(0,5).equalsIgnoreCase("pv_fe"))
			{
				map.put(col,Utilerias.formateaFecha(en.getValue()+""));
			}
			else
			{
				map.put(col,en.getValue()+"");
			}
		}
		return map;
	}

	protected class ObtPantallaAlvaro extends StoredProcedure
	{
		
		protected ObtPantallaAlvaro(DataSource dataSource)
		{
			super(dataSource,"ALVARO_PKG.P_GET_ALVARO");
			
			declareParameter(new SqlParameter("PV_CDUNO_I"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDDOS_I"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDTRES_I"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDCUATRO_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDCINCO_I"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDSEIS_I"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDSIETE_I"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDOCHO_I"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDNUEVE_I"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDDIEZ_I"   , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("PV_REGISTRO_O" , OracleTypes.CURSOR, new TatriAlvaroMapper()));
			declareParameter(new SqlOutParameter("PV_MSG_ID_O"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("PV_TITLE_O"    , OracleTypes.VARCHAR));
		}
	}
	
	protected class TatriAlvaroMapper implements RowMapper
	{
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			Tatri result=new Tatri();
			result.setType(Tatri.TATRIGEN);
			result.setCdatribu(rs.getString("CDATRIBU"));
			result.setSwformat(rs.getString("SWFORMAT"));
			result.setNmlmin(rs.getString("NMLMIN"));
			result.setNmlmax(rs.getString("NMLMAX"));
			result.setSwobliga(rs.getString("SWOBLIGA"));
			result.setDsatribu(rs.getString("DSATRIBU"));
			result.setOttabval(rs.getString("OTTABVAL"));
			result.setCdtablj1(rs.getString("CDTABLJ1"));
			result.setReadOnly(rs.getString("OTVALOR11")!=null&&rs.getString("OTVALOR11").equalsIgnoreCase("S"));
			Map<String,String>mapa=new LinkedHashMap<String,String>(0);
			String cols[]=new String[]{
					"OTVALOR01","OTVALOR02","OTVALOR03","OTVALOR04","OTVALOR05","OTVALOR06","OTVALOR07","OTVALOR08","OTVALOR09","OTVALOR10"
					,"OTVALOR11","OTVALOR12","OTVALOR13","OTVALOR14","OTVALOR15","OTVALOR16","OTVALOR17","OTVALOR18","OTVALOR19","OTVALOR20"
					,"OTVALOR21","OTVALOR22","OTVALOR23","OTVALOR24","OTVALOR25","OTVALOR26","OTVALOR27","OTVALOR28","OTVALOR29","OTVALOR30"
					,"OTVALOR31","OTVALOR32","OTVALOR33","OTVALOR34","OTVALOR35","OTVALOR36","OTVALOR37","OTVALOR38","OTVALOR39","OTVALOR40"
					,"OTVALOR41","OTVALOR42","OTVALOR43","OTVALOR44","OTVALOR45","OTVALOR46","OTVALOR47","OTVALOR48","OTVALOR49","OTVALOR50"
			};
			for(String col:cols)
			{
				mapa.put(col,rs.getString(col));
			}
			result.setMapa(mapa);
			return result;
		}
	}
	
	@Override
	public List<Tatri> obtPantallaAlvaro(Map<String, Object> params) throws Exception
	{
		Map<String,Object> resultadoMap=this.ejecutaSP(new ObtPantallaAlvaro(this.getDataSource()), params);
		return (List<Tatri>) resultadoMap.get("PV_REGISTRO_O");
	}
	
	protected class ObtenerCamposPantalla extends StoredProcedure
	{
		
		protected ObtenerCamposPantalla(DataSource dataSource)
		{
			super(dataSource,"ALVARO_PKG.P_GET_ALVARO");
			
			declareParameter(new SqlParameter("PV_CDUNO_I"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDDOS_I"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDTRES_I"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDCUATRO_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDCINCO_I"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDSEIS_I"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDSIETE_I"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDOCHO_I"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDNUEVE_I"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDDIEZ_I"   , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("PV_REGISTRO_O" , OracleTypes.CURSOR, new DinamicMapper()));
			declareParameter(new SqlOutParameter("PV_MSG_ID_O"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("PV_TITLE_O"    , OracleTypes.VARCHAR));
		}
	}
	
	@Override
	public List<Map<String,String>> obtenerCamposPantalla(Map<String, Object> params) throws Exception
	{
		Map<String,Object> resultadoMap=this.ejecutaSP(new ObtenerCamposPantalla(this.getDataSource()), params);
		return (List<Map<String,String>>) resultadoMap.get("PV_REGISTRO_O");
	}
	
	protected class BorrarCamposPantalla extends StoredProcedure
	{
		
		protected BorrarCamposPantalla(DataSource dataSource)
		{
			super(dataSource,"ALVARO_PKG.P_BORRAR_ALVARO");
			
			declareParameter(new SqlParameter("PV_CDUNO_I"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDDOS_I"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDTRES_I"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDCUATRO_I" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDCINCO_I"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDSEIS_I"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDSIETE_I"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDOCHO_I"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDNUEVE_I"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("PV_CDDIEZ_I"   , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("PV_MSG_ID_O"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("PV_TITLE_O"    , OracleTypes.VARCHAR));
		}
	}
	
	@Override
	public void borrarCamposPantalla(Map<String, Object> params) throws Exception
	{
		Map<String,Object> resultadoMap=this.ejecutaSP(new BorrarCamposPantalla(this.getDataSource()), params);
	}
	
	protected class InsertarCampoPantalla extends StoredProcedure
	{
		
		protected InsertarCampoPantalla(DataSource dataSource)
		{
			super(dataSource,"ALVARO_PKG.P_INSERTA_ALVARO");
			
			declareParameter(new SqlParameter("CDUNO"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("CDDOS"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("CDTRES"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("CDCUATRO" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("CDCINCO"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("CDSEIS"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("CDSIETE"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("CDOCHO"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("CDNUEVE"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("CDDIEZ"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("OTVALOR01"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR02"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR03"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR04"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR05"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR06"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR07"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR08"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR09"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR10"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR11"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR12"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR13"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR14"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR15"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR16"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR17"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR18"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR19"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR20"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR21"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR22"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR23"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR24"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR25"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR26"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR27"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR28"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR29"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR30"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR31"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR32"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR33"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR34"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR35"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR36"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR37"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR38"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR39"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR40"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR41"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR42"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR43"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR44"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR45"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR46"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR47"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR48"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR49"  , OracleTypes.VARCHAR));
    		declareParameter(new SqlParameter("OTVALOR50"  , OracleTypes.VARCHAR));
			
			declareParameter(new SqlOutParameter("PV_MSG_ID_O"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("PV_TITLE_O"    , OracleTypes.VARCHAR));
		}
	}
	
	@Override
	public void insertarCampoPantalla(Map<String, String> params) throws Exception
	{
		Map<String,Object> resultadoMap=this.ejecutaSP(new InsertarCampoPantalla(this.getDataSource()), params);
	}
	
	protected class ObtenerAtributosCoberturas extends StoredProcedure
	{
		
		String columnas[]=new String[]{
				 "OTVALOR09"
				,"OTVALOR10"      
				,"OTVALOR14"      
				,"OTVALOR15"};
		
		/*
		pv_cdunieco_i
		pv_cdramo_i
        pv_estado_i
        pv_nmpoliza_i
        pv_nmsuplem_i
		 */
		protected ObtenerAtributosCoberturas(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES.P_GET_ATRI_COBER");
			declareParameter(new SqlParameter("pv_cdunieco_i"    , OracleTypes.VARCHAR));			
			declareParameter(new SqlParameter("pv_cdramo_i"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_estado_i"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsituac_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmsuplem_i"    , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(columnas)));
			declareParameter(new SqlOutParameter("pv_messages_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			
		}
	}
	
	@Override
	public List<Map<String,String>> obtenerAtributosCoberturas(Map<String, String> params) throws Exception
	{
		Map<String,Object> resultadoMap=this.ejecutaSP(new ObtenerAtributosCoberturas(this.getDataSource()), params);
		return (List<Map<String,String>>) resultadoMap.get("pv_registro_o");
	}
	
	protected class EjecutarSIGSVALIPOL_END extends StoredProcedure
	{

		protected EjecutarSIGSVALIPOL_END(DataSource dataSource)
		{
			super(dataSource, "PKG_COTIZA.P_EJECUTA_SIGSVALIPOL_END");

			declareParameter(new SqlParameter("pv_cdusuari_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdelemen_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdunieco_i" , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_cdramo_i"   , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_estado_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_nmpoliza_i" , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_nmsituac_i" , OracleTypes.NUMERIC));
			declareParameter(new SqlParameter("pv_nmsuplem_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipsit_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipsup_i" , OracleTypes.VARCHAR));

	        declareParameter(new SqlOutParameter("pv_msg_id_o", OracleTypes.NUMERIC));
	        declareParameter(new SqlOutParameter("pv_title_o", OracleTypes.VARCHAR));
			
			compile();
		}

		public WrapperResultados mapWrapperResultados(Map map) throws Exception
		{
            WrapperResultadosGeneric mapper = new WrapperResultadosGeneric();
            return mapper.build(map);
        }
	}
	
	@Override
	public Map<String,Object> sigsvalipolEnd(Map<String, String> params) throws Exception
	{
		Map<String,Object> resultadoMap=this.ejecutaSP(new EjecutarSIGSVALIPOL_END(this.getDataSource()), params);
		return resultadoMap;
	}

}