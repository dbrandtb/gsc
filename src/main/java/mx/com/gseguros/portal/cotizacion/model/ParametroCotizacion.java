package mx.com.gseguros.portal.cotizacion.model;

public enum ParametroCotizacion
{
	
	ATRIBUTO_VARIABLE_TATRIGAR("ATRIVAR_TATRIGAR")//ESTE ATRIBUTO ES EL QUE RECIBE PKG_LISTAS.P_GET_ATRI_GARANTIA (DE TVALOSIT)
	,COMP_LECT_RIESGO_COT_GRUP("COTGRPRIESLECT")  //COMPONENTES DE RIESGO DE SOLO LECTURA POR ROL
	,DEPRECIACION("DEPRECIACION")                 //PARA LA DEPRECIACION DE SERVICIO PUBLICO
	,DIAS_VALIDOS_COTIZACION("DIAS_VALIDOS_COTI") //DIAS QUE ES VALIDA UNA COTIZACION
	,FLOTILLA_AGRUPACION_SITUACION("FLOTGRUPSIT") //AGRUPACION DE SITUACIONES PARA FLOTILLAS
	,IMAGEN_COTIZACION("IMAGENCOTIZACION")        //DATOS DE LA IMAGEN A MOSTRAR EN LA COTIZACION
	,MAPEO_TVALOSIT_FORMS_FLOTILLAS("MAPEOFLOT")  //MAPEO PARA PANELES 1,3,5 Y 6 DE FLOTILLAS CONTRA TVALOSIT
	,MENSAJE_TURNAR("MENSAJETURNAR")              //MENSAJE DE COLECTIVO AL TURNAR TRAMITE
	,MINIMOS_Y_MAXIMOS("MINMAXVALUES")            //MINIMOS Y MAXIMOS PARA AUTOS
	,NUMERO_FAMILIAS_COTI_COLECTIVO("NMINFAMILI") //NUMERO MINIMOS DE FAMILIAS QUE ACEPTA UNA COTIZACION
	,NUMERO_PASAJEROS_SERV_PUBL("6NUMPASAJE")     //NUMERO DE PASAJEROS PARA AUTOS
	,PROCEDURE_CENSO("PL_CENSO")                  //PROCEDIMIENTO QUE PROCESA LOS EXCEL DE CENSO PARA COLECTIVOS
	,TVALOSIT_CONSTANTE("TVALOSIT_CONST")         //VALORES ESTATICOS PARA COTIZAR TVALOSIT
	,RANGO_ANIO_MODELO("RANGOMODELO")             //PARA EL MODELO DE SERVICIO PUBLICO
	,RANGO_COBERTURAS_DEPENDIENTES("5SUMASCOBER") //RANGOS PARA COBERTURAS DEPENDIENTES DE RAMO 5
	,RANGO_VALOR("5RANGOVALOR")                   //RANGO DE EDICION DE SUMA ASEGURADA DE AUTO RAMO 5
	,RANGO_VIGENCIA("RANGOVIGENCIA")              //VIGENCIA MINIMA Y MAXIMA SOPORTADA EN PANTALLA
	,REMOLQUES_POR_TRACTOCAMION("REMOLQ_X_TRACTO")//NUMERO DE REMOLQUES POR TRACTOCAMION PERMITIDOS EN FLOTILLAS
	,TITULO_COTIZACION("TITULOCOTIZA")            //TITULO QUE SE MUESTRA EN LAS PANTALLAS DE COTIZACION
	;
	
	private String parametro;
	
	private ParametroCotizacion(String parametro)
	{
		this.parametro=parametro;
	}
	
	public String getParametro()
	{
		return this.parametro;
	}
}