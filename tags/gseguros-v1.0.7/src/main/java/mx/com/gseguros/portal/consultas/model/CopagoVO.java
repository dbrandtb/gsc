package mx.com.gseguros.portal.consultas.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class CopagoVO implements Serializable {
	
	private static final long serialVersionUID = -57318621458532287L;

	public CopagoVO() {
		super();
	}
	
	public CopagoVO(int orden, String descripcion, String valor, String agrupador) {
		super();
		this.orden = orden;
		this.descripcion = descripcion;
		this.valor = valor;
		this.agrupador = agrupador;
	}

	private int orden;
	
	private String descripcion;
	
	private String valor;
	
	private String agrupador;
	
	/**
	 * Indica el nivel del elemento
	 */
	private int nivel;
	
	/**
	 * Indica si el elemento debe visualizarse
	 */
	private boolean visible;

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getAgrupador() {
		return agrupador;
	}

	public void setAgrupador(String agrupador) {
		this.agrupador = agrupador;
	}
	
	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
