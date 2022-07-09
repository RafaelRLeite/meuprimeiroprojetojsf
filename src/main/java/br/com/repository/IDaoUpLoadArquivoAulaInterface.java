package br.com.repository;

import java.util.List;

import br.com.entidades.ArquivoUpLoadAula;

public interface IDaoUpLoadArquivoAulaInterface {

	void salvar(ArquivoUpLoadAula arquivoUpLoadAula);

	List<ArquivoUpLoadAula> lista();

	ArquivoUpLoadAula buscar(String fileDownloadId);

}
