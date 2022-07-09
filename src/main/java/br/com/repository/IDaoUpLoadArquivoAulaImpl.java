package br.com.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.dao.DaoGeneric;
import br.com.entidades.ArquivoUpLoadAula;

@Named
public class IDaoUpLoadArquivoAulaImpl implements IDaoUpLoadArquivoAulaInterface, Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private DaoGeneric<ArquivoUpLoadAula> daoGeneric;

	@Override
	public void salvar(ArquivoUpLoadAula arquivoUpLoadAula) {
		daoGeneric.salvar(arquivoUpLoadAula);
	}

	@Override
	public List<ArquivoUpLoadAula> lista() {
		return daoGeneric.getListEntity(ArquivoUpLoadAula.class);
	}

	@Override
	public ArquivoUpLoadAula buscar(String fileDownloadId) {
		return daoGeneric.consultar(ArquivoUpLoadAula.class, fileDownloadId);
	}

}
