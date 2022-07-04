package br.com.repository;

import java.io.Serializable;
import java.util.ArrayList;
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

}
