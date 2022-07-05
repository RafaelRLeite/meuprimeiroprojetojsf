package br.com.cursojsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.dao.DaoGeneric;
import br.com.entidades.Pessoa;
import br.com.repository.IDaoPessoa;

@javax.faces.view.ViewScoped
@Named(value = "RelUsuarioBean")
public class RelUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Pessoa> pessoas = new ArrayList<Pessoa>();

	@Temporal(TemporalType.DATE)
	private Date dataIni;

	@Temporal(TemporalType.DATE)
	private Date dataFim;

	private String nome;

	@Inject
	private IDaoPessoa daoPessoa;

	@Inject
	private DaoGeneric<Pessoa> daoGeneric;

	public void RelPessoa() {

		if (dataIni == null && dataFim == null && (nome == null || nome.isEmpty())) {
			pessoas = daoGeneric.getListEntity(Pessoa.class);
		} else {
			pessoas = daoPessoa.relatorioPessoa(nome, dataIni, dataFim);
		}
	}

	public Date getDataIni() {
		return dataIni;
	}

	public void setDataIni(Date dataIni) {
		this.dataIni = dataIni;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

}
