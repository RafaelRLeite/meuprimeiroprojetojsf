package br.com.repository;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

	@SuppressWarnings("unchecked")
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Lancamento> consultarLimite10(Long codUser) {

		List<Lancamento> lista = null;

		try {
			lista = entityManager.createQuery("from Lancamento where usuario.id = :codUser order by id desc").setParameter("codUser", codUser).setMaxResults(10).getResultList();
		} catch (NoResultException e) {
			return null;
		}

		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Lancamento> relatorioLancamento(String numNota, Date dataIni, Date dataFim) {

		List<Lancamento> lista = new ArrayList<Lancamento>();

		StringBuilder sql = new StringBuilder();

		sql.append(" select l from Lancamento l ");

		if (dataIni == null && dataFim == null && numNota != null && !numNota.isEmpty()) {

			sql.append(" where l.numeroNotaFiscal = '").append(numNota.trim()).append("'");

		} else if (numNota == null || (numNota != null && numNota.isEmpty()) && dataIni != null && dataFim == null) {

			String dataIniString = new SimpleDateFormat("YYYY-MM-dd").format(dataIni);

			sql.append(" where l.dataInicial >= '").append(dataIniString).append("'");

		} else if (numNota == null || (numNota != null && numNota.isEmpty()) && dataIni == null && dataFim != null) {

			String dataFimString = new SimpleDateFormat("YYYY-MM-dd").format(dataFim);

			sql.append(" where l.dataFinal <= '").append(dataFimString).append("'");
		}

		else if (numNota == null || (numNota != null && numNota.isEmpty()) && dataIni != null && dataFim != null) {

			String dataIniString = new SimpleDateFormat("YYYY-MM-dd").format(dataIni);
			String dataFimString = new SimpleDateFormat("YYYY-MM-dd").format(dataFim);

			sql.append(" where l.dataInicial >= '").append(dataIniString).append("'").append(" and l.dataFinal <= '").append(dataFimString).append("'");
		}

		else if (numNota != null && !numNota.isEmpty() && dataIni != null && dataFim != null) {

			String dataIniString = new SimpleDateFormat("YYYY-MM-dd").format(dataIni);
			String dataFimString = new SimpleDateFormat("YYYY-MM-dd").format(dataFim);

			sql.append(" where l.dataInicial >= '").append(dataIniString).append("'").append(" and l.dataFinal <= '").append(dataFimString).append("'").append(" and l.numeroNotaFiscal = '").append(numNota.trim()).append("'");
		}

		try {

			lista = entityManager.createQuery(sql.toString()).getResultList();

		} catch (NoResultException e) {
			return null;
		}
		return lista;
	}

}
