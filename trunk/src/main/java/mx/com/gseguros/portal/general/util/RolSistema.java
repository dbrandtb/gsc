package mx.com.gseguros.portal.general.util;

public enum RolSistema {
	
	AGENTE("EJECUTIVOCUENTA"),
	COORDINADOR_SINIESTROS("COORDINASINI"),
	COORDINADOR_MEDICO("COORDMED"),
	COORDINADOR_MEDICO_MULTIREGIONAL("COORDMEDMULTI"),
	GERENTE_OPERACION_SINIESTROS("GERENTEOPSINI"),
	GERENTE_MEDICO_MULTIREGIONAL("GERMEDMULTI"),
	MEDICO("MEDICO"),
	MEDICO_COORDINADOR("MEDICOCOORDINADOR"),
	MESA_DE_CONTROL("MESADECONTROL"),
	MESA_DE_CONTROL_SINIESTROS("MCSINIESTROS"),
	OPERADOR_SINIESTROS("OPERADORSINI"),
	PARAMETRIZADOR("PARAMETRIZADOR"),
	SUPERVISOR ("SUPERVISOR"),
	SUSCRIPTOR("SUSCRIPTOR");

	private String cdsisrol;

	private RolSistema(String cdsisrol) {
		this.cdsisrol = cdsisrol;
	}

	public String getCdsisrol() {
		return cdsisrol;
	}
	
}