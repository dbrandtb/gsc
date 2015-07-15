package mx.com.gseguros.portal.general.util;

public enum Catalogos {
	
	AGENTES(""),
	AGENTE_ESPECIFICO(""),
	AGENTES_POR_PROMOTOR(""),
	ANIOS_RENOVACION("ANIORENOVA"),
	CAUSA_SINIESTRO("TCAUSASSV"),
	//CAUSA_SINIESTROC("TCAUSACON"),
	//TIPO_CONSULTA("TTIPOCONS"),
	COBERTURAS(""),
	COBERTURASTOTALES(""),
	SUBCOBERTURASTOTALES(""),
	CLAUSULAS_POLIZA(""),
	COBERTURASXTRAMITE(""),
	COBERTURASXVALORES(""),
	CODIGOS_MEDICOS(""),
	CODIGOS_MEDICOS_TOTALES(""),
	COLONIAS(""),
	CUADROS_POR_SITUACION(""),
	DESCUENTO_POR_AGENTE(""),
	ENDOSOS(""),
	FORMAS_ASEGURAMIENTO("TFORMASEG"),
	GIROS("TGIROS"),
	ICD("2TABLICD"),
	MC_ESTATUS_TRAMITE("STATUSTRA"),
	MC_SUCURSALES_ADMIN("TUNIDECO"),
	MC_SUCURSALES_DOCUMENTO("TUNIDECO"),
	MC_SUCURSALES_SALUD("TUNIDECO"),
	MC_TIPOS_TRAMITE("TRAMITES"),
	MEDICOS(""),
	MEDICOESPECIFICO(""),
	MESES("MESES"),
	MOTIVOS_CANCELACION("TRAZCANC"),
	MOTIVOS_RECHAZO_SINIESTRO(""),
	MOTIVOS_REEXPEDICION("TRAZREEXP"),
	NACIONALIDAD("TNACIONALIDAD"),
	PARENTESCO(""),
	PENALIZACIONES("TPENALIZACIONES"),
	PLANES("TPLANES"),
	PLANES_X_PRODUCTO(""),
	PROVEEDORES(""),
	RAMOS(""),
	RAMO_4_SUMA_ASEG(""),
	RAMO_5_AUTOS(""),
	RAMO_5_CARGAS_X_NEGOCIO(""),
	RAMO_5_MARCAS("5MARAU|5MARPP|5MARCA|5MARPC"),
	RAMO_5_MARCAS_X_NEGOCIO(""),
	RAMO_5_MODELOS_X_SUBMARCA(""),
	RAMO_5_NEGOCIO_X_AGENTE(""),
	RAMO_5_NEGOCIO_X_CDTIPSIT(""),
	RAMO_5_NEGOCIO_X_CDTIPSIT_AGENTE(""),
	RAMO_5_PLAN_X_NEGOCIO_TIPSIT_TIPOVEHI(""),
	RAMO_5_SUBMARCAS("5SBMAU|5SBMPP|5SBMCA|5SBMPC"),
	RAMO_5_TIPOS_VALOR_X_ROL(""),
	RAMO_5_TIPOS_CARGA("5CATCARG"),
	RAMO_5_TIPOS_SITUACION_X_NEGOCIO(""),
	RAMO_5_TIPOS_USO("5USOAUT|5USOMOT"),
	RAMO_5_USOS_X_NEGOCIO(""),
	RAMO_5_VERSIONES("5DESCAU|5DESCPP|5DESCCA|5DESCPC"),
	RAMO_5_VERSIONES_X_MODELO(""),
	REFERENCIAS_TRAMITE_NUEVO("TREFERENCIA"),
	RELACION_CONT_ASEG("TRELCONTASEG"),
	REPARTO_PAGO_GRUPO("TREPPAG"),
	ROLES_POLIZA("TROLES"),
	ROLES_RAMO(""),
	ROLES_SISTEMA(""),
	SERVICIO_PUBLICO_AUTOS(""),
	SERVICIO_PUBLICO_NEGOCIO(""),
	SEXO("TSEXO"),
	SINO(""),
	STATUS_POLIZA("STATUSPOL"),
	SUBCOBERTURAS(""),
	SUBMOTIVOS_RECHAZO_SINIESTRO(""),
	TATRIGAR("TATRIGAR"),
	TATRIPER("TATRIPER"),
	TATRIPOL("TATRIPOL"),
	TATRISIN("TATRISIN"),
	TATRISIT("TATRISIT"),
	TIPO_CONCEPTO_SINIESTROS("TTIPCONC"),
	TIPOS_PAGO_POLIZA("TPERPAG"),
	TIPOS_PAGO_POLIZA_SIN_DXN("TPERPAG_NODXN"),
	TIPOS_PERSONA("TTIPOPERSONA"),
	TIPOS_POLIZA("TIPOPOL"),
	TIPOS_POLIZA_AUTO("TIPOPOLAUTO"),
	TIPSIT("TIPSIT"),
	TIPO_ATENCION_SINIESTROS("TTIPOATENCION"),
	TIPO_PAGO_SINIESTROS("TTIPOPAGO"),
	TIPO_RESIDENCIA("TRESIDENCIA"),
	TRATAMIENTOS("TTRATAMIENTO"),
	TIPO_MONEDA("TMONEDAS"),
	TIPO_MENU("TIPOMENU"),
	DESTINOPAGO("TCVIMPCH"),
	CATCONCEPTO("TCONCPAG"),
	TERRORWS("TERRORWS"),
	TCUMULOS("TCUMULOS"),
	TESTADOS("TESTADOS"),
	MUNICIPIOS(""),
	ZONAS_POR_PRODUCTO(""),
	TZONAS("TZONAS"),
	TFORMATOS("TFORMATOS"),
	TCANALIN("TCANALIN"),
	TEDOCIVIL("TEDOCIVIL"),
	RAMOSALUD(""),
	STATUSINIESTROS("STATUSTRA"),
	STATUS_VIGENCIA_POL("STATVIGENPOL"),
	TRAZCANAU("TRAZCANAU"),
	TIPO_SERVICIO_X_AUTO(""),
	TTIPOPAGO(""),
	CONCEPTOPAGO(""),
	ESTD_MODULOS(""),
	ESTD_TAREAS(""),
	ASEGURADOS(""),
	SECUENCIA_IVA("")
	;

	private String cdTabla;

	private Catalogos(String cdTabla) {
		this.cdTabla = cdTabla;
	}

	public String getCdTabla() {
		return cdTabla;
	}
}