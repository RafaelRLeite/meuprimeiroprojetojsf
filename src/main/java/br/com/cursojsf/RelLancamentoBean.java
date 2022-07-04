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
import br.com.entidades.Lancamento;
import br.com.repository.IDaoLancamento;

@javax.faces.view.ViewScoped
@Named(value = "RelLancamentoBean")
public class RelLancamentoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Lancamento> lancamentos = new ArrayList<Lancamento>();

	@Temporal(TemporalType.DATE)
	private Date dataIni;

	@Temporal(TemporalType.DATE)
	private Date dataFim;

	private String numNota;

	@Inject
	private IDaoLancamento daoLancamento;

	@Inject
	private DaoGeneric<Lancamento> daoGeneric;

	public void buscarLancamento() {

		if (dataIni == null && dataFim == null && numNota == null) {
			lancamentos = daoGeneric.getListEntity(Lancamento.class);
		} else {
			lancamentos = daoLancamento.relatorioLancamento(numNota, dataIni, dataFim);
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

	public String getNumNota() {
		return numNota;
	}

	public void setNumNota(String numNota) {
		this.numNota = numNota;
	}

	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}

}
