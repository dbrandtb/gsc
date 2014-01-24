package mx.com.gseguros.portal.general.util;

public enum TipoEndoso {
	
	EMISION_POLIZA(1),
	CORRECCION_NOMBRE_Y_RFC(2),
	CAMBIO_DOMICILIO(3),
	CORRECCION_ANTIGUEDAD_Y_PARENTESCO(4),
	ALTA_COBERTURAS(6),
	BAJA_COBERTURAS(7),
	CAMBIO_ENDOSOS_EXCLUSION_O_TEXTOS(8),
	ALTA_ASEGURADOS(9),
	BAJA_ASEGURADOS(10),
	INCREMENTO_EDAD_ASEGURADO(15),
	DECREMENTO_EDAD_ASEGURADO(16),
	DEDUCIBLE_MAS(17),
	DEDUCIBLE_MENOS(18),
	MODIFICACION_SEXO_H_A_M(20),
	MODIFICACION_SEXO_M_A_H(21),
	CAMBIO_DOMICILIO_ASEGURADO_TITULAR(31);
	

	private Integer cdTipSup;

	private TipoEndoso(Integer cdTipSup) {
		this.cdTipSup = cdTipSup;
	}

	public Integer getCdTipSup() {
		return cdTipSup;
	}
}