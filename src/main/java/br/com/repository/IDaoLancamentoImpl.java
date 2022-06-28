package br.com.repository;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

import br.com.entidades.Lancamento;
import br.com.jpautil.JPAUtil;

public class IDaoLancamentoImpl implements IDaoLancamento {

	@Inject
	private JPAUtil jpaUtil;

	@Override
	public List<Lancamento> consultar(Long codUser) {
		List<Lancamento> lista = null;

		EntityManager entityManager = jpaUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		try {
			lista = entityManager.createQuery("from Lancamento where usuario.id = :codUser").setParameter("codUser", codUser).getResultList();
		} catch (NoResultException e) {
			return null;
		} finally {
			entityManager.close();
		}

		return lista;
	}

}
