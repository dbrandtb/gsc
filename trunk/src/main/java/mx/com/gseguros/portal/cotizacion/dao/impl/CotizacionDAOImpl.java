package mx.com.gseguros.portal.cotizacion.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.exception.DaoException;
import mx.com.gseguros.portal.cotizacion.dao.CotizacionDAO;
import mx.com.gseguros.portal.cotizacion.model.DatosUsuario;
import mx.com.gseguros.portal.cotizacion.model.ObtieneTatrigarMapper;
import mx.com.gseguros.portal.cotizacion.model.ObtieneTatrisitMapper;
import mx.com.gseguros.portal.dao.AbstractManagerDAO;
import mx.com.gseguros.portal.dao.impl.DinamicMapper;
import mx.com.gseguros.portal.dao.impl.GenericMapper;
import mx.com.gseguros.portal.general.model.ComponenteVO;
import oracle.jdbc.driver.OracleTypes;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class CotizacionDAOImpl extends AbstractManagerDAO implements CotizacionDAO
{
	private final static Logger logger = Logger.getLogger(CotizacionDAOImpl.class);
	
	@Override
	public void movimientoTvalogarGrupo(Map<String,String>params)throws Exception
	{
		ejecutaSP(new MovimientoTvalogarGrupo(getDataSource()), params);
	}
	
	protected class MovimientoTvalogarGrupo extends StoredProcedure
	{
		protected MovimientoTvalogarGrupo(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES.P_MOV_TVALOGAR_GRUPO");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdgrupo"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdgarant" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("status"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdatribu" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("valor"    , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void movimientoMpolisitTvalositGrupo(Map<String,String>params)throws Exception
	{
		ejecutaSP(new MovimientoMpolisitTvalositGrupo(getDataSource()), params);
	}
	
	protected class MovimientoMpolisitTvalositGrupo extends StoredProcedure
	{
		protected MovimientoMpolisitTvalositGrupo(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES.P_ACTUALIZA_MPOLISIT_TVALOSIT");
			declareParameter(new SqlParameter("cdunieco"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdgrupo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor06" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor07" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor08" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor09" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor10" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor11" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor12" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor13" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor16" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void movimientoMpoligarGrupo(Map<String,String>params)throws Exception
	{
		ejecutaSP(new MovimientoMpoligarGrupo(getDataSource()), params);
	}
	
	protected class MovimientoMpoligarGrupo extends StoredProcedure
	{
		protected MovimientoMpoligarGrupo(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES.P_MOV_MPOLIGAR_GRUPO");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdgrupo"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdgarant" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("status"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdmoneda" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("accion"   , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String,String>cargarDatosCotizacionGrupo(Map<String,String>params)throws Exception
	{
		Map<String,Object>resultado=ejecutaSP(new CargarDatosCotizacionGrupo(getDataSource()), params);
		List<Map<String,String>>listaDatos=(List<Map<String,String>>)resultado.get("pv_registro_o");
		Map<String,String>datos=new HashMap<String,String>();
		if(listaDatos!=null&&listaDatos.size()>0)
		{
			datos=listaDatos.get(0);
		}
		return datos;
	}
	
	protected class CargarDatosCotizacionGrupo extends StoredProcedure
	{
		private String[] columnas=new String[]{
			"cdrfc"
			,"cdperson"
			,"nombre"
			,"codpostal"
			,"cdedo"
			,"cdmunici"
			,"dsdomici"
			,"nmnumero"
			,"nmnumint"
			,"cdgiro"
			,"cdrelconaseg"
			,"cdformaseg"
			,"ntramite"
			,"nmpoliza"
			,"cdperpag"
			,"cdagente"
			,"clasif"
			,"pcpgocte"
		};
		
		protected CargarDatosCotizacionGrupo(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_DATOS_COTIZACION_GRUPO");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("ntramite" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(columnas)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>>cargarGruposCotizacion(Map<String,String>params)throws Exception
	{
		Map<String,Object>resultado=ejecutaSP(new CargarGruposCotizacion(getDataSource()), params);
		List<Map<String,String>>listaGrupos=(List<Map<String,String>>)resultado.get("pv_registro_o");
		return listaGrupos;
	}
	
	protected class CargarGruposCotizacion extends StoredProcedure
	{
		private String[] columnas=new String[]{
			"ptsumaaseg"
			,"incrinfl"
			,"extrreno"
			,"cesicomi"
			,"pondubic"
			,"descbono"
			,"porcgast"
			,"nombre"
			,"ayudamater"
			,"letra"
			,"cdplan"
		};
		
		protected CargarGruposCotizacion(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_GRUPOS_COTIZACION");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(columnas)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String,String>cargarDatosGrupoLinea(Map<String,String>params)throws Exception
	{
		Map<String,Object>resultado=ejecutaSP(new CargarDatosGrupoLinea(getDataSource()), params);
		List<Map<String,String>>listaDatos=(List<Map<String,String>>)resultado.get("pv_registro_o");
		Map<String,String>datos=new HashMap<String,String>();
		if(listaDatos!=null&&listaDatos.size()>0)
		{
			datos=listaDatos.get(0);
		}
		return datos;
	}
	
	protected class CargarDatosGrupoLinea extends StoredProcedure
	{
		protected CargarDatosGrupoLinea(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_DATOS_GRUPO_LINEA");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdgrupo"  , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>>cargarTvalogarsGrupo(Map<String,String>params)throws Exception
	{
		Map<String,Object>resultado=ejecutaSP(new CargarTvalogarsGrupo(getDataSource()), params);
		List<Map<String,String>>listaTvalogars=(List<Map<String,String>>)resultado.get("pv_registro_o");
		return listaTvalogars;
	}
	
	protected class CargarTvalogarsGrupo extends StoredProcedure
	{
		private List<String> columnas=new ArrayList<String>();
		protected CargarTvalogarsGrupo(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_TVALOGARS_GRUPO");
			
			columnas.add("amparada");
			columnas.add("cdgarant");
			columnas.add("swobliga");
			for(int i=1;i<=640;i++)
			{
				columnas.add(
						new StringBuilder()
						    .append("parametros.pv_otvalor")
						    .append(StringUtils.leftPad(String.valueOf(i),3,"0"))
						    .toString()
						    );
			}
			
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdgrupo"  , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(columnas)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>>cargarTarifasPorEdad(Map<String,String>params)throws Exception
	{
		Map<String,Object>resultado=ejecutaSP(new CargarTarifasPorEdad(getDataSource()), params);
		List<Map<String,String>>listaTvalogars=(List<Map<String,String>>)resultado.get("pv_registro_o");
		return listaTvalogars;
	}
	
	protected class CargarTarifasPorEdad extends StoredProcedure
	{
		protected CargarTarifasPorEdad(DataSource dataSource)
		{
			super(dataSource,"PKG_COTIZA.P_OBTIENE_COTIZACION_COLECTIVO");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdplan"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdgrupo"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdperpag" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>>cargarTarifasPorCobertura(Map<String,String>params)throws Exception
	{
		Map<String,Object>resultado=ejecutaSP(new CargarTarifasPorCobertura(getDataSource()), params);
		List<Map<String,String>>listaTvalogars=(List<Map<String,String>>)resultado.get("pv_registro_o");
		return listaTvalogars;
	}
	
	protected class CargarTarifasPorCobertura extends StoredProcedure
	{
		protected CargarTarifasPorCobertura(DataSource dataSource)
		{
			super(dataSource,"PKG_COTIZA.P_OBTIENE_PRIMA_PROM_COBERTURA");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdplan"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdgrupo"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdperpag" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public String cargarNombreAgenteTramite(String ntramite)throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("ntramite",ntramite);
		Map<String,Object>resultado=ejecutaSP(new CargarNombreAgenteTramite(getDataSource()), params);
		return (String)resultado.get("pv_nombre_o");
	}
	
	protected class CargarNombreAgenteTramite extends StoredProcedure
	{
		protected CargarNombreAgenteTramite(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_NOMBRE_AGENTE_TRAMITE");
			declareParameter(new SqlParameter("ntramite" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_nombre_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String,String>cargarPermisosPantallaGrupo(String cdsisrol,String status)throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdsisrol" , cdsisrol);
		params.put("status"   , status);
		logger.debug(
				new StringBuilder()
				.append("\n********************************************************")
				.append("\n****** PKG_CONSULTA.P_GET_PERMISOS_PANTALLA_GRUPO ******")
				.append("\n****** params=").append(params)
				.append("\n********************************************************")
				.toString());
		Map<String,Object>resultado=ejecutaSP(new CargarPermisosPantallaGrupo(getDataSource()), params);
		List<Map<String,String>>listaDatos=(List<Map<String,String>>)resultado.get("pv_registro_o");
		Map<String,String>datos=new HashMap<String,String>();
		if(listaDatos!=null&&listaDatos.size()>0)
		{
			datos=listaDatos.get(0);
		}
		return datos;
	}
	
	protected class CargarPermisosPantallaGrupo extends StoredProcedure
	{
		protected CargarPermisosPantallaGrupo(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_PERMISOS_PANTALLA_GRUPO");
			declareParameter(new SqlParameter("cdsisrol" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("status"   , OracleTypes.VARCHAR));
			String[] cols=new String[]
					{
					"LINEA_EXTENDIDA"
					,"DETALLE_LINEA"
					,"COBERTURAS"
					,"FACTORES_EN_COBERTURAS"
					,"FACTORES"
					,"BLOQUEO_CONCEPTO"
					,"BLOQUEO_EDITORES"
					,"VENTANA_DOCUMENTOS"
					,"EXTRAPRIMAS"
					,"EXTRAPRIMAS_EDITAR"
					,"ASEGURADOS"
					,"ASEGURADOS_EDITAR"
					};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}

	@Override
	public Map<String,String>obtieneTipoValorAutomovil(Map<String,String>params)throws Exception
	{
		Map<String,Object>resultado=ejecutaSP(new ObtieneTipoValorAutomovil(getDataSource()), params);
		
		Map<String,String>datos=new HashMap<String,String>();
		datos.put("pv_etiqueta_o", (String) resultado.get("pv_etiqueta_o"));
		return datos;
	}
	
	protected class ObtieneTipoValorAutomovil extends StoredProcedure
	{
		protected ObtieneTipoValorAutomovil(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_VALAUT_X_CP");
			declareParameter(new SqlParameter("pv_cdpostal_i" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("pv_cdtipveh_i"   , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_etiqueta_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void guardarCensoCompleto(Map<String,String>params)throws Exception
	{
		ejecutaSP(new GuardarCensoCompleto(getDataSource()),params);
	}
	
	protected class GuardarCensoCompleto extends StoredProcedure
	{
		protected GuardarCensoCompleto(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES.P_LAYOUT_CENSO_MS_COLEC_DEF");
			declareParameter(new SqlParameter("censo"     , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdunieco"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor04" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("otvalor05" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdplan1"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdplan2"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdplan3"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdplan4"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdplan5"   , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>>cargarAseguradosExtraprimas(Map<String,String>params)throws Exception
	{
		Map<String,Object>resultado   = ejecutaSP(new CargarAseguradosExtraprimas(getDataSource()), params);
		List<Map<String,String>>lista = (List<Map<String,String>>)resultado.get("pv_registro_o");
		if(lista==null)
		{
			lista=new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class CargarAseguradosExtraprimas extends StoredProcedure
	{
		protected CargarAseguradosExtraprimas(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_TVALOSIT_X_GRUPO");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdgrupo"  , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void guardarExtraprimaAsegurado(Map<String,String>params)throws Exception
	{
		ejecutaSP(new GuardarExtraprimaAsegurado(getDataSource()),params);
	}
	
	protected class GuardarExtraprimaAsegurado extends StoredProcedure
	{
		protected GuardarExtraprimaAsegurado(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES.P_ACTUALIZA_TVALOSIT_DAT_ADIC");
			declareParameter(new SqlParameter("cdunieco"            , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"              , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"              , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza"            , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem"            , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsituac"            , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("ocupacion"           , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("extraprimaOcupacion" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("peso"                , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estatura"            , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("extraprimaSobrepeso" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o" , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"  , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>>cargarAseguradosGrupo(Map<String,String>params)throws Exception
	{
		Map<String,Object>respuesta   = ejecutaSP(new CargarAseguradosGrupo(getDataSource()),params);
		List<Map<String,String>>lista = (List<Map<String,String>>)respuesta.get("pv_registro_o");
		if(lista==null)
		{
			lista=new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class CargarAseguradosGrupo extends StoredProcedure
	{
		protected CargarAseguradosGrupo(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_ASEGURADOS_GRUPO");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdgrupo"  , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void borrarMpoliperGrupo(Map<String,String>params)throws Exception
	{
		ejecutaSP(new BorrarMpoliperGrupo(getDataSource()),params);
	}
	

	
	protected class BorrarMpoliperGrupo extends StoredProcedure
	{
		protected BorrarMpoliperGrupo(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES.P_BORRA_MPOLIPER_GRUPO");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdgrupo"  , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String,String>cargarTipoSituacion(Map<String,String>params)throws Exception
	{
		Map<String,Object>respuestaProcedure=ejecutaSP(new CargarTipoSituacion(getDataSource()),params);
		List<Map<String,String>>lista=(List<Map<String,String>>)respuestaProcedure.get("pv_registro_o");
		Map<String,String>respuesta=null;
		if(lista!=null&&lista.size()>0)
		{
			respuesta=lista.get(0);
		}
		return respuesta;
	}
	
	protected class CargarTipoSituacion extends StoredProcedure
	{
		protected CargarTipoSituacion(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_TIPO_SITUACION");
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public String cargarCduniecoAgenteAuto(Map<String,String>params)throws Exception
	{
		Map<String,Object>respuestaProcedure=ejecutaSP(new CargarCduniecoAgenteAuto(getDataSource()),params);
		return (String)respuestaProcedure.get("pv_cdunieco_o");
	}
	
	protected class CargarCduniecoAgenteAuto extends StoredProcedure
	{
		protected CargarCduniecoAgenteAuto(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_CDUNIECO_X_AGENTE_AUTO");
			declareParameter(new SqlParameter("cdagente" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_cdunieco_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String,String> obtenerDatosAgente(Map<String,String>params)throws Exception
	{
		Map<String,Object>procedureResult=ejecutaSP(new ObtenerDatosAgente(getDataSource()),params);
		List<Map<String,String>>listaAux=(List<Map<String,String>>)procedureResult.get("pv_registro_o");
		if(listaAux==null||listaAux.size()==0)
		{
			throw new Exception("No hay datos del agente");
		}
		else if(listaAux.size()>1)
		{
			throw new Exception("Datos repetidos");
		}
		return listaAux.get(0);
	}
	
	protected class ObtenerDatosAgente extends StoredProcedure
	{
		protected ObtenerDatosAgente(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_DATOS_AGENTE_X_CDAGENTE");
			declareParameter(new SqlParameter("cdagente" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	protected class CargarNumeroPasajerosPorTipoUnidad extends StoredProcedure
	{
		protected CargarNumeroPasajerosPorTipoUnidad(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_NUM_PASAJEROS");
			declareParameter(new SqlParameter("cdtipsit"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("tipoUnidad" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String,String> obtenerParametrosCotizacion(Map<String,String>params)throws Exception
	{
		Map<String,Object>procedureResult = ejecutaSP(new ObtenerParametrosCotizacion(getDataSource()),params);
		List<Map<String,String>>listaAux  = (List<Map<String,String>>)procedureResult.get("pv_registro_o");
		if(listaAux==null||listaAux.size()==0)
		{
			throw new Exception("No hay parametros");
		}
		if(listaAux.size()>1)
		{
			throw new Exception("Parametros duplicados");
		}
		return listaAux.get(0);
	}
	
	protected class ObtenerParametrosCotizacion extends StoredProcedure
	{
		protected ObtenerParametrosCotizacion(DataSource dataSource)
		{
			super(dataSource,"PKG_LISTAS.P_GET_PARAMS_COTIZACION");
			declareParameter(new SqlParameter("parametro" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("clave4"    , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("clave5"    , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String,String> cargarAutoPorClaveGS(Map<String,String>params)throws Exception
	{
		Map<String,Object>procedureResult=ejecutaSP(new CargarAutoPorClaveGS(getDataSource()),params);
		List<Map<String,String>>listaAux=(List<Map<String,String>>)procedureResult.get("pv_registro_o");
		if(listaAux==null||listaAux.size()==0)
		{
			throw new Exception("Auto no encontrado");
		}
		if(listaAux.size()>1)
		{
			logger.debug("lista: "+listaAux);
			//throw new Exception("Auto duplicado");
		}
		return listaAux.get(0);
	}
	
	protected class CargarAutoPorClaveGS extends StoredProcedure
	{
		protected CargarAutoPorClaveGS(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_VEHICULOS");
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("clavegs"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public Map<String,String> cargarClaveGSPorAuto(Map<String,String>params)throws Exception
	{
		Map<String,Object>procedureResult=ejecutaSP(new CargarClaveGSPorAuto(getDataSource()),params);
		List<Map<String,String>>listaAux=(List<Map<String,String>>)procedureResult.get("pv_registro_o");
		if(listaAux==null||listaAux.size()==0)
		{
			throw new Exception("Clave GS no encontrada");
		}
		if(listaAux.size()>1)
		{
			logger.debug("lista: "+listaAux);
			//throw new Exception("Clave GS duplicada");
		}
		return listaAux.get(0);
	}
	
	protected class CargarClaveGSPorAuto extends StoredProcedure
	{
		protected CargarClaveGSPorAuto(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_VEHICULOS_X_MODELO");
			declareParameter(new SqlParameter("cdramo" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("modelo" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
    public Map<String,String>cargarSumaAseguradaAuto(Map<String,String>params)throws Exception
    {
		Map<String,Object>procedureResult=ejecutaSP(new CargarSumaAseguradaAuto(getDataSource()),params);
		List<Map<String,String>>listaAux=(List<Map<String,String>>)procedureResult.get("pv_registro_o");
		if(listaAux==null||listaAux.size()==0)
		{
			throw new Exception("Suma asegurada no encontrada");
		}
		if(listaAux.size()>1)
		{
			throw new Exception("Suma asegurada duplicada");
		}
		return listaAux.get(0);
	}
	
	protected class CargarSumaAseguradaAuto extends StoredProcedure
	{
		protected CargarSumaAseguradaAuto(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_SUMA_ASEGURADA_AUTOS");
			declareParameter(new SqlParameter("version"  , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("modelo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdsisrol" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void movimientoMpolicotICD(Map<String,String>params)throws Exception
	{
		ejecutaSP(new MovimientoMpolicotICD(getDataSource()),params);
	}
	
	protected class MovimientoMpolicotICD extends StoredProcedure
	{
		protected MovimientoMpolicotICD(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES.P_MOV_MPOLICOTICD");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsituac" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdclausu" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("icd"      , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("accion"   , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>>cargarMpolicotICD(Map<String,String>params)throws Exception
	{
		Map<String,Object>procedureResult=ejecutaSP(new CargarMpolicotICD(getDataSource()),params);
		List<Map<String,String>>lista=(List<Map<String,String>>)procedureResult.get("pv_registro_o");
		if(lista==null)
		{
			lista=new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class CargarMpolicotICD extends StoredProcedure
	{
		protected CargarMpolicotICD(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_GET_MPOLICOTICD");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsituac" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdclausu" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>>cargarConfiguracionGrupo(Map<String,String>params)throws Exception
	{
		Map<String,Object>procedureResult=ejecutaSP(new CargarConfiguracionGrupo(getDataSource()),params);
		List<Map<String,String>>lista=(List<Map<String,String>>)procedureResult.get("pv_registro_o");
		if(lista==null)
		{
			lista=new ArrayList<Map<String,String>>();
		}
		return lista;
	}
	
	protected class CargarConfiguracionGrupo extends StoredProcedure
	{
		protected CargarConfiguracionGrupo(DataSource dataSource)
		{
			super(dataSource,"PKG_LISTAS.P_GET_CONF_GRUPO");
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new DinamicMapper()));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<ComponenteVO>cargarTatrisit(String cdtipsit,String cdusuari)throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdtipsit" , cdtipsit);
		params.put("cdusuari" , cdusuari);
		logger.debug(
				new StringBuilder()
				.append("\n*********************************************")
				.append("\n****** PKG_LISTAS.P_GET_ATRI_SITUACION ******")
				.append("\n****** params=").append(params)
				.append("\n*********************************************")
				.toString()
				);
		Map<String,Object>procResult = ejecutaSP(new CargarTatrisit(getDataSource()),params);
		List<ComponenteVO>lista      = (List<ComponenteVO>)procResult.get("pv_registro_o");
		if(lista==null||lista.size()==0)
		{
			throw new Exception("No hay tatrisit");
		}
		return lista;
	}
	
	protected class CargarTatrisit extends StoredProcedure
    {
    	protected CargarTatrisit(DataSource dataSource)
        {
            super(dataSource,"PKG_LISTAS.P_GET_ATRI_SITUACION");
            declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new ObtieneTatrisitMapper()));
            declareParameter(new SqlOutParameter("pv_messages_o" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
	
	@Override
	public ComponenteVO cargarComponenteTatrisit(Map<String,String>params)throws Exception
	{
		logger.debug(
				new StringBuilder()
				.append("\n***************************************************")
				.append("\n****** PKG_LISTAS.P_GET_ATRI_UNICO_SITUACION ******")
				.append("\n****** params=").append(params)
				.append("\n***************************************************")
				.toString()
				);
		Map<String,Object>procedureResult=ejecutaSP(new CargarComponenteTatrisit(getDataSource()),params);
		List<ComponenteVO>lista=(List<ComponenteVO>)procedureResult.get("pv_registro_o");
		if(lista==null||lista.size()==0)
		{
			throw new Exception("No existe el componente");
		}
		else if(lista.size()>1)
		{
			throw new Exception("Componente repetido");
		}
		return lista.get(0);
	}
	
	protected class CargarComponenteTatrisit extends StoredProcedure
    {
    	protected CargarComponenteTatrisit(DataSource dataSource)
        {
            super(dataSource,"PKG_LISTAS.P_GET_ATRI_UNICO_SITUACION");
            declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdatribu" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new ObtieneTatrisitMapper()));
            declareParameter(new SqlOutParameter("pv_messages_o" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
	
	@Override
	public ComponenteVO cargarComponenteTatrigar(Map<String,String>params)throws Exception
	{
		Map<String,Object>procedureResult=ejecutaSP(new CargarComponenteTatrigar(getDataSource()),params);
		List<ComponenteVO>lista=(List<ComponenteVO>)procedureResult.get("pv_registro_o");
		if(lista==null||lista.size()==0)
		{
			throw new Exception("No existe el componente");
		}
		else if(lista.size()>1)
		{
			throw new Exception("Componente repetido");
		}
		return lista.get(0);
	}

	protected class CargarComponenteTatrigar extends StoredProcedure
	{
		protected CargarComponenteTatrigar(DataSource dataSource)
		{
			super(dataSource,"PKG_LISTAS.P_GET_ATRI_UNICO_GARANTIA");
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdgarant" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdatribu" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new ObtieneTatrigarMapper()));
			declareParameter(new SqlOutParameter("pv_messages_o" , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public void validarDescuentoAgente(
			String tipoUnidad
			,String uso
			,String zona
			,String promotoria
			,String cdagente
			,String descuento)throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("tipoUnidad" , tipoUnidad);
		params.put("uso"        , uso);
		params.put("zona"       , zona);
		params.put("promotoria" , promotoria);
		params.put("cdagente"   , cdagente);
		params.put("descuento"  , descuento);
		ejecutaSP(new ValidarDescuentoAgente(getDataSource()),params);
	}
	
	protected class ValidarDescuentoAgente extends StoredProcedure
	{
		protected ValidarDescuentoAgente(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES.P_VALIDA_DESCUENTO_COMERCIAL");
			declareParameter(new SqlParameter("tipoUnidad" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("uso"        , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("zona"       , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("promotoria" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdagente"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("descuento"  , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public List<Map<String,String>>impresionDocumentosPoliza(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nmsuplem
			,String ntramite)throws DaoException,ApplicationException
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nmsuplem" , nmsuplem);
		params.put("ntramite" , ntramite);
		Map<String,Object>procedureResult=ejecutaSP(new ImpresionDocumentosPoliza(getDataSource()),params);
		List<Map<String,String>>listaDocumentos=(List<Map<String,String>>)procedureResult.get("pv_registro_o");
		if(listaDocumentos==null||listaDocumentos.size()==0)
		{
			throw new ApplicationException("No hay documentos parametrizados");
		}
		logger.debug(new StringBuilder("\n&&&&&& Lista documentos size=").append(listaDocumentos.size()).toString());
		return listaDocumentos;
	}
	
	protected class ImpresionDocumentosPoliza extends StoredProcedure
	{
		protected ImpresionDocumentosPoliza(DataSource dataSource)
		{
			super(dataSource,"PKG_CONSULTA.P_Imp_documentos");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmsuplem" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("ntramite" , OracleTypes.VARCHAR));
			String[] cols=new String[]
					{
					"nmsolici"
					,"nmsituac"
					,"descripc"
					,"descripl"
					};
			declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
		}
	}
	
	@Override
	public void movimientoTdescsup(
			String cdunieco
			,String cdramo
			,String estado
			,String nmpoliza
			,String nsuplogi
			,String cdtipsup
			,Date feemisio
			,String nmsolici
			,Date fesolici
			,Date ferefere
			,String cdseqpol
			,String cdusuari
			,String nusuasus
			,String nlogisus
			,String cdperson
			,String accion)throws Exception
	{
		Map<String,Object>params=new HashMap<String,Object>();
		params.put("cdunieco" , cdunieco);
		params.put("cdramo"   , cdramo);
		params.put("estado"   , estado);
		params.put("nmpoliza" , nmpoliza);
		params.put("nsuplogi" , nsuplogi);
		params.put("cdtipsup" , cdtipsup);
		params.put("feemisio" , feemisio);
		params.put("nmsolici" , nmsolici);
		params.put("fesolici" , fesolici);
		params.put("ferefere" , ferefere);
		params.put("cdseqpol" , cdseqpol);
		params.put("cdusuari" , cdusuari);
		params.put("nusuasus" , nusuasus);
		params.put("nlogisus" , nlogisus);
		params.put("cdperson" , cdperson);
		params.put("accion"   , accion);
		ejecutaSP(new MovimientoTdescsup(getDataSource()),params);
	}
	
	protected class MovimientoTdescsup extends StoredProcedure
	{
		protected MovimientoTdescsup(DataSource dataSource)
		{
			super(dataSource,"PKG_SATELITES.P_MOV_TDESCSUP");
			declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nsuplogi" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdtipsup" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("feemisio" , OracleTypes.DATE));
			declareParameter(new SqlParameter("nmsolici" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("fesolici" , OracleTypes.DATE));
			declareParameter(new SqlParameter("ferefere" , OracleTypes.DATE));
			declareParameter(new SqlParameter("cdseqpol" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nusuasus" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("nlogisus" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("cdperson" , OracleTypes.VARCHAR));
			declareParameter(new SqlParameter("accion"   , OracleTypes.VARCHAR));
			declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
			declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
			compile();
		}
	}
	
	@Override
	public DatosUsuario cargarInformacionUsuario(String cdusuari,String cdtipsit)throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("cdusuari" , cdusuari);
		params.put("cdtipsit" , cdtipsit);
		logger.debug(
				new StringBuilder()
				.append("\n**********************************************")
				.append("\n****** pkg_satelites.p_get_info_usuario ******")
				.append("\n****** params=").append(params)
				.append("\n**********************************************")
				.toString()
				);
		Map<String,Object>procResult = ejecutaSP(new CargarInformacionUsuario(getDataSource()),params);
		List<DatosUsuario>listaAux   = (List<DatosUsuario>)procResult.get("pv_registro_o");
		if(listaAux==null||listaAux.size()==0)
		{
			throw new Exception("No hay datos de usuario");
		}
		return listaAux.get(0);
	}
	
	protected class CargarInformacionUsuario extends StoredProcedure
    {
    	protected CargarInformacionUsuario(DataSource dataSource)
        {
            super(dataSource,"pkg_satelites.p_get_info_usuario");
            declareParameter(new SqlParameter("cdusuari" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdtipsit" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new ObtieneDatosUsuarioMapper()));
            declareParameter(new SqlOutParameter("pv_messages_o" , OracleTypes.VARCHAR));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
    
    protected class ObtieneDatosUsuarioMapper implements RowMapper
    {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException
        {
            DatosUsuario datosUsuario=new DatosUsuario();
            datosUsuario.setCdagente(rs.getString("cdagente"));
            datosUsuario.setCdperson(rs.getString("cdperson"));
            datosUsuario.setCdramo  (rs.getString("cdramo"));
            datosUsuario.setCdtipsit(rs.getString("cdtipsit"));
            datosUsuario.setCdunieco(rs.getString("cdunieco"));
            datosUsuario.setCdusuari(rs.getString("cdusuari"));
            datosUsuario.setNmcuadro(rs.getString("nmcuadro"));
            datosUsuario.setNombre  (rs.getString("nombre"));
            return datosUsuario;
        }
    }
    
    @Override
    public Map<String,String>cargarClienteCotizacion(String cdunieco,String cdramo,String estado,String nmpoliza)throws Exception
    {
    	Map<String,String>params=new HashMap<String,String>();
    	params.put("cdunieco" , cdunieco);
    	params.put("cdramo"   , cdramo);
    	params.put("estado"   , estado);
    	params.put("nmpoliza" , nmpoliza);
    	logger.debug(
    			new StringBuilder()
    			.append("\n***************************************************")
    			.append("\n****** PKG_CONSULTA.P_GET_CLIENTE_COTIZACION ******")
    			.append("\n****** params=").append(params)
    			.append("\n***************************************************")
    			.toString()
    			);
    	Map<String,Object>procResult=ejecutaSP(new CargarClienteCotizacion(getDataSource()),params);
    	List<Map<String,String>>lista=(List<Map<String,String>>)procResult.get("pv_registro_o");
    	if(lista==null||lista.size()==0)
    	{
    		throw new Exception("No se encontraron datos");
    	}
    	if(lista.size()>1)
    	{
    		throw new Exception("Datos duplicados");
    	}
    	return lista.get(0);
    }
	
	protected class CargarClienteCotizacion extends StoredProcedure
    {
    	protected CargarClienteCotizacion(DataSource dataSource)
        {
            super(dataSource,"PKG_CONSULTA.P_GET_CLIENTE_COTIZACION");
            declareParameter(new SqlParameter("cdunieco" , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("cdramo"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("estado"   , OracleTypes.VARCHAR));
            declareParameter(new SqlParameter("nmpoliza" , OracleTypes.VARCHAR));
            String[] cols=new String[]
            		{
            		"NOMBRE"
            		,"CDPERSON"
            		};
            declareParameter(new SqlOutParameter("pv_registro_o" , OracleTypes.CURSOR, new GenericMapper(cols)));
            declareParameter(new SqlOutParameter("pv_msg_id_o"   , OracleTypes.NUMERIC));
            declareParameter(new SqlOutParameter("pv_title_o"    , OracleTypes.VARCHAR));
            compile();
    	}
    }
}