package br.com.repository;

import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import br.com.entidades.ArquivoUpLoadAula;

@Named
public class IDaoUpLoadArquivoAulaImpl implements IDaoUpLoadArquivoAulaInterface, Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;

	@Override
	public void salvar(ArquivoUpLoadAula arquivoUpLoadAula) {
		entityManager.persist(arquivoUpLoadAula);

	}

}
