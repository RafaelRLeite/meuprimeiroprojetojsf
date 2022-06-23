package br.com.repository;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

import br.com.entidades.Estados;
import br.com.entidades.Pessoa;
import br.com.jpautil.JPAUtil;

public class IDaoPessoaImpl implements IDaoPessoa {

	@Override
	public Pessoa consultarUsuario(String login, String senha) {

		Pessoa pessoa = null;

		EntityManager entityManager = JPAUtil.getEntityManager();

		try {
			// EntityTransaction entityTransaction = entityManager.getTransaction();
			// entityTransaction.begin();
			pessoa = (Pessoa) entityManager.createQuery("select p from Pessoa p where p.login = :login and p.senha = :senha").setParameter("login", login).setParameter("senha", senha).getSingleResult();
		} catch (NoResultException e) {
			return null;
		} finally {
			// entityTransaction.commit(); somente se alterar o banco de dados
			entityManager.close(); // sempre fechar a transação
		}
		return pessoa;
	}

	@Override
	public List<SelectItem> listaEstados() {

		List<SelectItem> selecItems = new ArrayList<SelectItem>();

		EntityManager entityManager = JPAUtil.getEntityManager();

		try {
			EntityTransaction entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();

			List<Estados> estados = entityManager.createQuery("from Estados").getResultList();

			for (Estados estado : estados) {
				selecItems.add(new SelectItem(estado, estado.getNome()));
			}
		} catch (NoResultException e) {
			return null;
		} finally {
			entityManager.close();
		}

		return selecItems;
	}

}
