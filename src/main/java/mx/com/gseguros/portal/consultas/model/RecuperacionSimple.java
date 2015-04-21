package mx.com.gseguros.portal.consultas.model;

public enum RecuperacionSimple
{
	
	RECUPERAR_CLAUSULAS_POLIZA                               ("RECUPERAR_CLAUSULAS_POLIZA")
	,RECUPERAR_COBERTURAS_ENDOSO_DEVOLUCION_PRIMAS           ("RECUPERAR_COBERTURAS_ENDOSO_DEVOLUCION_PRIMAS")
	,RECUPERAR_CONFIGURACION_VALOSIT_FLOTILLAS               ("RECUPERAR_CONFIGURACION_VALOSIT_FLOTILLAS")
	,RECUPERAR_DATOS_VEHICULO_RAMO_5                         ("RECUPERAR_DATOS_VEHICULO_RAMO_5")
	,RECUPERAR_DESCUENTO_RECARGO_RAMO_5                      ("RECUPERAR_DESCUENTO_RECARGO_RAMO_5")
	,RECUPERAR_DETALLES_COBERTURAS_COTIZACION_AUTOS_FLOTILLA ("RECUPERAR_DETALLES_COBERTURAS_COTIZACION_AUTOS_FLOTILLA")
	,RECUPERAR_DETALLES_COTIZACION_AUTOS_FLOTILLA            ("RECUPERAR_DETALLES_COTIZACION_AUTOS_FLOTILLA")
	,RECUPERAR_DSATRIBUS_TATRISIT                            ("RECUPERAR_DSATRIBUS_TATRISIT")
	,RECUPERAR_ENDOSOS_CANCELABLES                           ("RECUPERAR_ENDOSOS_CANCELABLES")
	,RECUPERAR_ENDOSOS_REHABILITABLES                        ("RECUPERAR_ENDOSOS_REHABILITABLES")
	,RECUPERAR_FAMILIAS_POLIZA                               ("RECUPERAR_FAMILIAS_POLIZA")
	,RECUPERAR_FECHAS_LIMITE_ENDOSO                          ("RECUPERAR_FECHAS_LIMITE_ENDOSO")
	,RECUPERAR_GRUPOS_POLIZA                                 ("RECUPERAR_GRUPOS_POLIZA")
	,RECUPERAR_HISTORICO_POLIZA                              ("RECUPERAR_HISTORICO_POLIZA")
	,RECUPERAR_INCISOS_POLIZA_GRUPO_FAMILIA                  ("RECUPERAR_INCISOS_POLIZA_GRUPO_FAMILIA")
	,RECUPERAR_MPOLIPER_OTROS_ROLES_POR_NMSITUAC             ("RECUPERAR_MPOLIPER_OTROS_ROLES_POR_NMSITUAC")
	,RECUPERAR_PERMISO_USUARIO_DEVOLUCION_PRIMAS             ("RECUPERAR_PERMISO_USUARIO_DEVOLUCION_PRIMAS")
	,RECUPERAR_POLIZAS_ENDOSABLES                            ("RECUPERAR_POLIZAS_ENDOSABLES")
	,RECUPERAR_PORCENTAJE_RECARGO_POR_PRODUCTO               ("RECUPERAR_PORCENTAJE_RECARGO_POR_PRODUCTO")
	,RECUPERAR_TEXTO_CLAUSULA_POLIZA                         ("RECUPERAR_TEXTO_CLAUSULA_POLIZA")
	,RECUPERAR_TVALOSIT                                      ("RECUPERAR_TVALOSIT")
	,RECUPERAR_ULTIMO_NMSUPLEM                               ("RECUPERAR_ULTIMO_NMSUPLEM")
	,RECUPERAR_VALOR_ATRIBUTO_UNICO                          ("RECUPERAR_VALOR_ATRIBUTO_UNICO")
	,RECUPERAR_VALORES_ATRIBUTOS_FACTORES                    ("RECUPERAR_VALORES_ATRIBUTOS_FACTORES")
	,RECUPERAR_VALORES_PANTALLA                              ("RECUPERAR_VALORES_PANTALLA")
	,VERIFICAR_CODIGO_POSTAL_FRONTERIZO                      ("VERIFICAR_CODIGO_POSTAL_FRONTERIZO");

	private String procedimiento;

	private RecuperacionSimple(String procedimiento) {
		this.procedimiento = procedimiento;
	}

	public String getProcedimiento() {
		return procedimiento;
	}
	
}