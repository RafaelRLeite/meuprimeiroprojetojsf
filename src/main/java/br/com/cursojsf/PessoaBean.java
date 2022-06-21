package br.com.cursojsf;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import br.com.dao.DaoGeneric;
import br.com.entidades.Cidades;
import br.com.entidades.Estados;
import br.com.entidades.Pessoa;
import br.com.jpautil.JPAUtil;
import br.com.repository.IDaoPessoa;
import br.com.repository.IDaoPessoaImpl;

@ViewScoped
@ManagedBean(name = "pessoaBean")
public class PessoaBean {

	private Pessoa pessoa = new Pessoa();
	private DaoGeneric<Pessoa> daoGeneric = new DaoGeneric<Pessoa>();
	private List<Pessoa> pessoas = new ArrayList<Pessoa>();
	private IDaoPessoa iDaoPessoa = new IDaoPessoaImpl();
	private List<SelectItem> estados;
	private List<SelectItem> cidades;

	public String salvar() {
		pessoa = daoGeneric.Merge(pessoa);
		carregarPessoas();
		mostrarMsg("Cadastrado com sucesso");
		return "";
	}

	private void mostrarMsg(String msg) {
		FacesContext context = FacesContext.getCurrentInstance(); // FacesContext é o ambiente em que roda o JSF
		FacesMessage message = new FacesMessage(msg);
		context.addMessage(null, message);
	}

	public String novo() {
		pessoa = new Pessoa();
		mostrarMsg("Cadastre novo usuário");
		return "";
	}

	public String limpar() {
		pessoa = new Pessoa();
		return "";
	}

	public String remove() {
		daoGeneric.deletePorId(pessoa);
		pessoa = new Pessoa();
		carregarPessoas();
		mostrarMsg("Cadastrado removido com sucesso");
		return "";
	}

	@PostConstruct
	public void carregarPessoas() {
		pessoas = daoGeneric.getListEntity(Pessoa.class);
	}

	public String logar() {

		Pessoa pessoaUser = iDaoPessoa.consultarUsuario(pessoa.getLogin(), pessoa.getSenha());

		if (pessoaUser != null) { // Achou o usuário

			// adicionar o usuario na sessão usuarioLogado

			// Andre:
			// ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession().setAttribute("usuarioLogado",
			// pessoaUser);

			// Curso:
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext externalContext = context.getExternalContext();

			HttpServletRequest req = (HttpServletRequest) externalContext.getRequest();
			HttpSession session = req.getSession();

			session.setAttribute("usuarioLogado", pessoaUser);

			return "primeirapagina.jsf";
		}

		return "index.jsf";
	}

	public String deslogar() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		externalContext.getSessionMap().remove("usuarioLogado");

		HttpServletRequest req = (HttpServletRequest) externalContext.getRequest();
		HttpSession session = req.getSession();

		session.invalidate();

		return "index.jsf";
	}

	public boolean permiteAcesso(String acesso) {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();

		HttpServletRequest req = (HttpServletRequest) externalContext.getRequest();
		HttpSession session = req.getSession();

		Pessoa pessoaUser = (Pessoa) session.getAttribute("usuarioLogado");

		return pessoaUser.getPerfilUser().equals(acesso);

	}

	public void pesquisaCep(AjaxBehaviorEvent event) {

		try {
			URL url = new URL("https://viacep.com.br/ws/" + pessoa.getCep() + "/json/");
			URLConnection connection = url.openConnection();
			InputStream is = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

			String cep = "";
			StringBuilder jsonCep = new StringBuilder();

			while ((cep = br.readLine()) != null) {
				jsonCep.append(cep);
			}

			Pessoa gsonAux = new Gson().fromJson(jsonCep.toString(), Pessoa.class);

			pessoa.setCep(gsonAux.getCep());
			pessoa.setLogradouro(gsonAux.getLogradouro());
			pessoa.setComplemento(gsonAux.getComplemento());
			pessoa.setBairro(gsonAux.getBairro());
			pessoa.setLocalidade(gsonAux.getLocalidade());
			pessoa.setUf(gsonAux.getUf());

			// System.out.println(gsonAux);

		} catch (Exception ex) {
			ex.printStackTrace();
			mostrarMsg("erro ao consultar cep");
		}

	}

	public void carregarCidades(AjaxBehaviorEvent event) {

		String codigoEstado = (String) event.getComponent().getAttributes().get("submittedValue");

		if (codigoEstado != null) {
			Estados estado = JPAUtil.getEntityManager().find(Estados.class, Long.parseLong(codigoEstado));

			if (pessoa != null) {
				pessoa.setEstados(estado);

				List<Cidades> cidades = JPAUtil.getEntityManager().createQuery("from Cidades where estados.id = " + codigoEstado).getResultList();

				List<SelectItem> selectItemsCidades = new ArrayList<SelectItem>();

				for (Cidades cidade : cidades) {
					selectItemsCidades.add(new SelectItem(cidade.getId(), cidade.getNome()));
				}

				setCidades(selectItemsCidades);
			}
		}
	}

	public List<SelectItem> getCidades() {
		return cidades;
	}

	public void setCidades(List<SelectItem> cidades) {
		this.cidades = cidades;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public DaoGeneric<Pessoa> getDaoGeneric() {
		return daoGeneric;
	}

	public void setDaoGeneric(DaoGeneric<Pessoa> daoGeneric) {
		this.daoGeneric = daoGeneric;
	}

	public List<SelectItem> getEstados() {

		estados = iDaoPessoa.listaEstados();

		return estados;
	}
}
