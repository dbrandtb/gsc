package mx.com.gseguros.portal.general.util;

import java.util.HashMap;
import java.util.Map;

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
	COPAGO_MAS(11),
	COPAGO_MENOS(12),
	EXTRAPRIMA_MAS(13),
	EXTRAPRIMA_MENOS(14),
	INCREMENTO_EDAD_ASEGURADO(15),
	DECREMENTO_EDAD_ASEGURADO(16),
	DEDUCIBLE_MAS(17),
	DEDUCIBLE_MENOS(18),
	CAMBIO_AGENTE(19),
	MODIFICACION_SEXO_H_A_M(20),
	MODIFICACION_SEXO_M_A_H(21),
	CANCELACION_POR_REEXPEDICION(24),
	CAMBIO_FORMA_PAGO(26),
	BENEFICIARIO_AUTO(27),
	CAMBIO_CONTRATANTE(28),
	PLACAS_Y_MOTOR(29),
	CAMBIO_DOMICILIO_ASEGURADO_TITULAR(31),
	DESPAGO(32),
	RENOVACION(33),
	SUMA_ASEGURADA_INCREMENTO(34),
	SUMA_ASEGURADA_DECREMENTO(35),
	COASEGURO_INCREMENTO(36),
	COASEGURO_DECREMENTO(37),
	AMPLIACION_DE_VIGENCIA(40),
	ENDOSO_B_LIBRE(41),
	SERIE_AUTO(42),
	CAMBIO_TIPO_SERVICIO(43),
	CAMBIO_RFC_CLIENTE(44),
	CAMBIO_NOMBRE_CLIENTE(45),
	ADAPTACIONES_EFECTO_RC(48),
	CANCELACION_UNICA(52),
	CANCELACION_MASIVA(53),
	REHABILITACION(57),
	CORRECCION_DATOS_ASEGURADOS(60),
	ASEGURADO_ALTERNO(46),
	VIGENCIA_POLIZA(47);
	

	private final Integer cdTipSup;

	private TipoEndoso(Integer cdTipSup) {
		this.cdTipSup = cdTipSup;
	}

	public Integer getCdTipSup() {
		return cdTipSup;
	}
	
	
	private static final Map<Integer, TipoEndoso> map;
	static {
		map = new HashMap<Integer, TipoEndoso>();
		for (TipoEndoso v : TipoEndoso.values()) {
			map.put(v.cdTipSup, v);
		}
	}
	
	/**
	 * Obtiene el enum correspondiente al cdTipSup enviado
	 * 
	 * @param cdTipSup Tipo de suplemento a buscar
	 * @return Enum que coincide con el cdTipSup, nulo si no existe
	 */
	public static TipoEndoso findByKey(Integer cdTipSup) {
		return map.get(cdTipSup);
	}
	
}