package br.com.cursojsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.dao.DaoGeneric;
import br.com.entidades.Lancamento;
import br.com.entidades.Pessoa;
import br.com.repository.IDaoLancamento;

@Named(value = "lancamentoBean")
@javax.faces.view.ViewScoped
public class LancamentoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Lancamento lancamento = new Lancamento();
	private List<Lancamento> lancamentos = new ArrayList<Lancamento>();

	@Inject
	private DaoGeneric<Lancamento> daoGeneric;

	@Inject
	private IDaoLancamento daoLancamento;

	public String salvar() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();

		HttpServletRequest req = (HttpServletRequest) externalContext.getRequest();
		HttpSession session = req.getSession();

		Pessoa pessoaUser = (Pessoa) session.getAttribute("usuarioLogado");

		lancamento.setUsuario(pessoaUser);
		daoGeneric.salvar(lancamento);

		carregarLancamento();

		mostraMsg("Lancamento salvo com sucesso");
		return "";
	}

	@PostConstruct
	private void carregarLancamento() {

		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();

		HttpServletRequest req = (HttpServletRequest) externalContext.getRequest();
		HttpSession session = req.getSession();

		Pessoa pessoaUser = (Pessoa) session.getAttribute("usuarioLogado");

		lancamentos = daoLancamento.consultar(pessoaUser.getId());

	}

	public String novo() {
		lancamento = new Lancamento();
		// FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage("Cadastre novo lançamento"));
		mostraMsg("Cadastre novo lançamento");
		return "";
	}

	public String remove() {
		daoGeneric.deletePorId(lancamento);
		lancamento = new Lancamento();
		carregarLancamento();
		mostraMsg("Lancamento removido com sucesso");
		return "";
	}

	public boolean permiteAcesso(String acesso) {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();

		HttpServletRequest req = (HttpServletRequest) externalContext.getRequest();
		HttpSession session = req.getSession();

		Pessoa pessoaUser = (Pessoa) session.getAttribute("usuarioLogado");

		return pessoaUser.getPerfilUser().equals(acesso);

	}

	private void mostraMsg(String msg) {
		FacesContext context = FacesContext.getCurrentInstance(); // FacesContext é o ambiente em que roda o JSF
		FacesMessage message = new FacesMessage(msg);
		context.addMessage(null, message);
	}

	public Lancamento getLancamento() {
		return lancamento;
	}

	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}

	public DaoGeneric<Lancamento> getDaoGeneric() {
		return daoGeneric;
	}

	public void setDaoGeneric(DaoGeneric<Lancamento> daoGeneric) {
		this.daoGeneric = daoGeneric;
	}

	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}

}
