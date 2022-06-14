package br.com.entidades;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ForeignKey;

@SuppressWarnings("deprecation")
@Entity
public class Lancamento implements Serializable {

	private static final long serialVersionUID = 1L;
	@javax.persistence.Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;

	private String numeroNotaFiscal;
	private String empresaOrigem;
	private String empresaDestino;

	@ManyToOne(optional = false)
	@ForeignKey(name = "usuario_fk")
	private Pessoa usuario;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getNumeroNotaFiscal() {
		return numeroNotaFiscal;
	}

	public void setNumeroNotaFiscal(String numeroNotaFiscal) {
		this.numeroNotaFiscal = numeroNotaFiscal;
	}

	public String getEmpresaOrigem() {
		return empresaOrigem;
	}

	public void setEmpresaOrigem(String empresaOrigem) {
		this.empresaOrigem = empresaOrigem;
	}

	public String getEmpresaDestino() {
		return empresaDestino;
	}

	public void setEmpresaDestino(String empresaDestino) {
		this.empresaDestino = empresaDestino;
	}

	public Pessoa getUsuario() {
		return usuario;
	}

	public void setUsuario(Pessoa usuario) {
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lancamento other = (Lancamento) obj;
		return Objects.equals(Id, other.Id);
	}

}
