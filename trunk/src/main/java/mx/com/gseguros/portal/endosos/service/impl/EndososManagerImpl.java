package mx.com.gseguros.portal.endosos.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.endosos.dao.EndososDAO;
import mx.com.gseguros.portal.endosos.service.EndososManager;

import org.apache.log4j.Logger;

public class EndososManagerImpl implements EndososManager
{
    private static Logger log = Logger.getLogger(EndososManagerImpl.class);
    
	private EndososDAO endososDAO;

	@Override
	public List<Map<String, String>> obtenerEndosos(Map<String, String> params) throws Exception
	{
		log.debug("EndososManager obtenerEndosos params: "+params);
		List<Map<String,String>> lista=endososDAO.obtenerEndosos(params);
		lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
		log.debug("EndososManager obtenerEndosos lista size: "+lista.size());
		return lista;
	}
	
	@Override
	public Map<String, String> guardarEndosoNombres(Map<String, Object> params) throws Exception
	{
		log.debug("EndososManager guardarEndosoNombres params: "+params);
		Map<String,String> mapa=endososDAO.guardarEndosoNombres(params);
		log.debug("EndososManager guardarEndosoNombres response map: "+mapa);
        return mapa;
	}
	
	@Override
	public Map<String, String> confirmarEndosoB(Map<String, String> params) throws Exception
	{
		log.debug("EndososManager confirmarEndosoB params: "+params);
		Map<String,String> mapa=endososDAO.confirmarEndosoB(params);
		log.debug("EndososManager confirmarEndosoB response map: "+mapa);
        return mapa;
	}
	
	@Override
	public Map<String, String> guardarEndosoDomicilio(Map<String, Object> params) throws Exception
	{
		log.debug("EndososManager guardarEndosoDomicilio params: "+params);
		Map<String,String> mapa=endososDAO.guardarEndosoNombres(params);
		log.debug("EndososManager guardarEndosoDomicilio response map: "+mapa);
        return mapa;
	}
	
	@Override
	public List<Map<String, String>> reimprimeDocumentos(Map<String, String> params) throws Exception
	{
		log.debug("EndososManager reimprimeDocumentos params: "+params);
		List<Map<String,String>> lista=endososDAO.reimprimeDocumentos(params);
		lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
		log.debug("EndososManager reimprimeDocumentos lista size: "+lista.size());
		return lista;
	}
	
	@Override
	public List<Map<String, String>> obtieneCoberturasDisponibles(Map<String, String> params) throws Exception
	{
		log.debug("EndososManager obtieneCoberturasDisponibles params: "+params);
		List<Map<String,String>> lista=endososDAO.obtieneCoberturasDisponibles(params);
		lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
		log.debug("EndososManager obtieneCoberturasDisponibles lista size: "+lista.size());
		return lista;
	}
	
	@Override
	public Map<String, String> guardarEndosoCoberturas(Map<String, Object> params) throws Exception
	{
		log.debug("EndososManager guardarEndosoCoberturas params: "+params);
		Map<String,String> mapa=endososDAO.guardarEndosoCoberturas(params);
		log.debug("EndososManager guardarEndosoCoberturas response map: "+mapa);
        return mapa;
	}
	
	@Override
	public List<Map<String, String>> obtenerAtributosCoberturas(Map<String, String> params) throws Exception
	{
		log.debug("EndososManager obtenerAtributosCoberturas params: "+params);
		List<Map<String,String>> lista=endososDAO.obtenerAtributosCoberturas(params);
		lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
		log.debug("EndososManager obtenerAtributosCoberturas lista size: "+lista.size());
		return lista;
	}
	
	@Override
	public Map<String,Object> sigsvalipolEnd(Map<String, String> params) throws Exception
	{
		log.debug("EndososManager sigsvalipolEnd params: "+params);
		Map<String,Object> mapa=endososDAO.sigsvalipolEnd(params);
		log.debug("EndososManager sigsvalipolEnd response map: "+mapa);
        return mapa;
	}
	
	@Override
	public Map<String, String> guardarEndosoClausulas(Map<String, Object> params) throws Exception
	{
		log.debug("EndososManager guardarEndosoClausulas params: "+params);
		Map<String,String> mapa=endososDAO.guardarEndosoClausulas(params);
		log.debug("EndososManager guardarEndosoClausulas response map: "+mapa);
        return mapa;
	}
	
	@Override
	public Map<String,String> calcularValorEndoso(Map<String,Object>params) throws Exception
	{
		log.debug("EndososManager calcularValorEndoso params: "+params);
		Map<String,String> mapa=endososDAO.calcularValorEndoso(params);
		log.debug("EndososManager calcularValorEndoso response map: "+mapa);
        return mapa;
	}
	
	@Override
	public Map<String,String> iniciarEndoso(Map<String, String> params) throws Exception
	{
		log.debug("EndososManager iniciarEndoso params: "+params);
		Map<String,String> mapa=endososDAO.iniciarEndoso(params);
		log.debug("EndososManager iniciarEndoso response map: "+mapa);
        return mapa;
	}
	
	@Override
	public void insertarTworksupEnd(Map<String, String> params) throws Exception
	{
		log.debug("EndososManager insertarTworksupEnd params: "+params);
		endososDAO.insertarTworksupEnd(params);
		log.debug("EndososManager insertarTworksupEnd end");
	}
	
	@Override
	public Map<String, String> obtieneDatosMpolisit(Map<String, String> params) throws Exception
	{
		log.debug("EndososManager obtieneDatosMpolisit params: "+params);
		Map<String,String> mapa=endososDAO.obtieneDatosMpolisit(params);
		log.debug("EndososManager obtieneDatosMpolisit response map: "+mapa);
        return mapa;
	}
	
	@Override
	public List<Map<String, String>> obtenerNombreEndosos() throws Exception
	{
		log.debug("EndososManager obtenerNombreEndosos");
		List<Map<String,String>> lista=endososDAO.obtenerNombreEndosos();
		lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
		log.debug("EndososManager obtenerNombreEndosos lista size: "+lista.size());
		return lista;
	}
	
	@Override
	public void actualizarFenacimi(Map<String, String> params) throws Exception
	{
		log.debug("EndososManager actualizarFenacimi params: "+params);
		endososDAO.actualizarFenacimi(params);
		log.debug("EndososManager actualizarFenacimi end");
	}
	
	@Override
	public void actualizarSexo(Map<String, String> params) throws Exception
	{
		log.debug("EndososManager actualizarSexo params: "+params);
		endososDAO.actualizarSexo(params);
		log.debug("EndososManager actualizarSexo end");
	}
	
	@Override
	public List<Map<String, String>> obtenerCdpersonMpoliper(Map<String, String> params) throws Exception
	{
		log.debug("EndososManager obtenerCdpersonMpoliper params: "+params);
		List<Map<String,String>> lista=endososDAO.obtenerCdpersonMpoliper(params);
		lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
		log.debug("EndososManager obtenerCdpersonMpoliper lista size: "+lista.size());
		return lista;
	}
	
	@Override
	public String obtenerNtramiteEmision(String cdunieco,String cdramo,String estado,String nmpoliza) throws Exception
	{
		Map<String,String>params=new HashMap<String,String>();
		params.put("pv_cdunieco_i" , cdunieco);
		params.put("pv_cdramo_i"   , cdramo);
		params.put("pv_estado_i"   , estado);
		params.put("pv_nmpoliza_i" , nmpoliza);
		log.debug("EndososManager obtenerNtramiteEmision params: "+params);
		List<Map<String,String>> lista=endososDAO.obtenerNtramiteEmision(params);
		lista=lista!=null?lista:new ArrayList<Map<String,String>>(0);
		log.debug("EndososManager obtenerNtramiteEmision lista: "+lista);
		String ntramite=lista.size()>0?lista.get(0).get("NTRAMITE"):"";
		log.debug("EndososManager obtenerNtramiteEmision ntramite: "+ntramite);
		return ntramite;
	}
	
	/////////////////////////////////
	////// getters and setters //////
	/*/////////////////////////////*/
	public void setEndososDAO(EndososDAO endososDAO) {
		this.endososDAO = endososDAO;
	}
	
}