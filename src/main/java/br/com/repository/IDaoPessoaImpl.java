package br.com.repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

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
			// entityTransaction.commit();
			entityManager.close();
		}
		return pessoa;
	}

}
