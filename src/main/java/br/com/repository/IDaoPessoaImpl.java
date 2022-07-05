package br.com.repository;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.entidades.Estados;
import br.com.entidades.Pessoa;

@Named
public class IDaoPessoaImpl implements IDaoPessoa, Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;

	@Override
	public Pessoa consultarUsuario(String login, String senha) {

		Pessoa pessoa = null;

		try {
			// EntityTransaction entityTransaction = entityManager.getTransaction();
			// entityTransaction.begin();
			pessoa = (Pessoa) entityManager.createQuery("select p from Pessoa p where p.login = :login and p.senha = :senha").setParameter("login", login).setParameter("senha", senha).getSingleResult();
		} catch (NoResultException e) {
			return null;
		} finally {
			// entityTransaction.commit(); somente se alterar o banco de dados
			// entityManager.close();// sempre fechar a transação -> Tive que comentar aqui para poder logar o usuário
		}
		return pessoa;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SelectItem> listaEstados() {

		List<SelectItem> selecItems = new ArrayList<SelectItem>();

		try {
			List<Estados> estados = entityManager.createQuery("from Estados").getResultList();

			for (Estados estado : estados) {
				selecItems.add(new SelectItem(estado, estado.getNome()));
			}
		} catch (NoResultException e) {
			return null;
		}

		return selecItems;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pessoa> relatorioPessoa(String nome, Date dataIni, Date dataFim) {

		List<Pessoa> lista = new ArrayList<Pessoa>();

		StringBuilder sql = new StringBuilder();

		sql.append(" select p from Pessoa p ");

		if (dataIni == null && dataFim == null && nome != null && !nome.isEmpty()) {

			sql.append(" where upper(p.nome) like '%").append(nome.trim().toUpperCase()).append("%'");

		} else if (nome == null || (nome != null && nome.isEmpty()) && dataIni != null && dataFim == null) {

			String dataIniString = new SimpleDateFormat("YYYY-MM-dd").format(dataIni);

			sql.append(" where p.dataNascimento >= '").append(dataIniString).append("'");

		} else if (nome == null || (nome != null && nome.isEmpty()) && dataIni == null && dataFim != null) {

			String dataFimString = new SimpleDateFormat("YYYY-MM-dd").format(dataFim);

			sql.append(" where p.dataNascimento <= '").append(dataFimString).append("'");
		}

		else if (nome == null || (nome != null && nome.isEmpty()) && dataIni != null && dataFim != null) {

			String dataIniString = new SimpleDateFormat("YYYY-MM-dd").format(dataIni);
			String dataFimString = new SimpleDateFormat("YYYY-MM-dd").format(dataFim);

			sql.append(" where p.dataNascimento >= '").append(dataIniString).append("'").append(" and p.dataNascimento <= '").append(dataFimString).append("'");
		}

		else if (nome != null && !nome.isEmpty() && dataIni != null && dataFim != null) {

			String dataIniString = new SimpleDateFormat("YYYY-MM-dd").format(dataIni);
			String dataFimString = new SimpleDateFormat("YYYY-MM-dd").format(dataFim);

			sql.append(" where p.dataNascimento >= '").append(dataIniString).append("'").append(" and p.dataNascimento <= '").append(dataFimString).append("'").append(" and upper(p.nome) like = '%")
					.append(nome.trim().toUpperCase()).append("%'");
		}

		try {

			lista = entityManager.createQuery(sql.toString()).getResultList();

		} catch (NoResultException e) {
			return null;
		}
		return lista;
	}

}
