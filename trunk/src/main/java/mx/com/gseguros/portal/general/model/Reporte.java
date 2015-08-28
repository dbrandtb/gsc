package mx.com.gseguros.portal.general.model;

public enum Reporte
{
	SALUD_COLECTIVO_RESUMEN_COTIZACION ("COTMSC002"),
	SALUD_COLECTIVO_COTIZACION_GRUPO   ("COTMSC001");
	
	private String cdreporte;
	
	private Reporte(String cdreporte)
	{
		this.cdreporte = cdreporte;
	}
	
	public String getCdreporte()
	{
		return this.cdreporte;
	}
}