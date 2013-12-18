package mx.com.gseguros.portal.endosos.dao;

import java.util.List;
import java.util.Map;

import mx.com.gseguros.portal.cotizacion.model.Tatri;

public interface EndososDAO
{
    public List<Map<String,String>> obtenerEndosos(Map<String,String>params) throws Exception;
    public Map<String, String>      guardarEndosoNombres(Map<String, Object> params) throws Exception;
    public Map<String, String>      confirmarEndosoB(Map<String, String> params) throws Exception;
    public Map<String, String>      guardarEndosoDomicilio(Map<String, Object> params) throws Exception;
    public List<Map<String,String>> reimprimeDocumentos(Map<String,String>params) throws Exception;
    public List<Map<String,String>> obtieneCoberturasDisponibles(Map<String,String>params) throws Exception;
    public Map<String, String>      guardarEndosoCoberturas(Map<String, Object> params) throws Exception;
	public List<Tatri>              obtPantallaAlvaro(Map<String, Object> params) throws Exception;
	public List<Map<String,String>> obtenerAtributosCoberturas(Map<String, String> params) throws Exception;
	public Map<String,Object>       sigsvalipolEnd(Map<String, String> params) throws Exception;
	public List<Map<String,String>> obtenerCamposPantalla(Map<String, Object> params) throws Exception;
	public void                     borrarCamposPantalla(Map<String, Object> params) throws Exception;
	public void                     insertarCampoPantalla(Map<String, String> params) throws Exception;
}