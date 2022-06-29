package br.com.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.entidades.Lancamento;

@Named
public class IDaoLancamentoImpl implements IDaoLancamento, Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;

	@Override
	public List<Lancamento> consultar(Long codUser) {

		List<Lancamento> lista = null;

		try {
			lista = entityManager.createQuery("from Lancamento where usuario.id = :codUser").setParameter("codUser", codUser).getResultList();
		} catch (NoResultException e) {
			return null;
		}

		return lista;
	}

}
