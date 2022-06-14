package br.com.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

import br.com.entidades.Lancamento;
import br.com.jpautil.JPAUtil;

public class IDaoLancamentoImpl implements IDaoLancamento {

	@SuppressWarnings("unchecked")
	@Override
	public List<Lancamento> consultarCodUser(Long codUser) {
		List<Lancamento> lista = null;

		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		try {
			lista = entityManager.createQuery("from Lancamento where usuario.id = :codUser").setParameter("codUser", codUser).getResultList();
		} catch (NoResultException e) {
			return null;
		} finally {
			transaction.commit();
			entityManager.close();
		}

		return lista;
	}

}
