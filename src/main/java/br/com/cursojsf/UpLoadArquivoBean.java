package br.com.cursojsf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Scanner;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import br.com.dao.DaoGeneric;
import br.com.entidades.ArquivoUpLoadAula;
import br.com.entidades.Pessoa;
import br.com.repository.IDaoUpLoadArquivoAulaInterface;

@Named(value = "upLoadArquivoBean")
@javax.faces.view.ViewScoped
public class UpLoadArquivoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private ArquivoUpLoadAula arquivoUpLoadAula = new ArquivoUpLoadAula();

	@Inject
	private DaoGeneric<Pessoa> daoGeneric;

	@Inject
	private IDaoUpLoadArquivoAulaInterface daoUpLoadArquivoAula;

	private Part arquivo;

	public void UpLoad() throws IOException {

		/* LENDO ARQUIVOS E ATRIBUINDO A UM ENTITY */

		Scanner scanner = new Scanner(arquivo.getInputStream(), "UTF-8");
		scanner.useDelimiter(",");

		while (scanner.hasNext()) {
			String linha = scanner.nextLine();

			if (linha != null && !linha.trim().isEmpty()) {
				linha = linha.replaceAll("\"", "");
				String[] dados = linha.split("\\;");

				Pessoa pessoa = new Pessoa();

				pessoa.setNome(dados[0]);
				pessoa.setSobrenome(dados[1]);
				pessoa.setCPF(dados[2]);
				pessoa.setTituloEleitoral(dados[3]);
				pessoa = daoGeneric.Merge(pessoa);
			}
		}

		/* SALVANDO ARQUIVO COMPLETO NO BANCO DE DADOS */

		byte[] arquivoByte = getByte(arquivo.getInputStream());

		arquivoUpLoadAula.setArquivo(arquivoByte);

		daoUpLoadArquivoAula.salvar(arquivoUpLoadAula);

		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Upload realizado"));

	}

	private byte[] getByte(InputStream is) throws IOException {

		int len;
		int size = 1024;
		byte[] buf = null;
		if (is instanceof ByteArrayInputStream) {
			size = is.available();
			buf = new byte[size];
			len = is.read(buf, 0, size);
		} else {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			buf = new byte[size];

			while ((len = is.read(buf, 0, size)) != -1) {
				bos.write(buf, 0, len);
			}

			buf = bos.toByteArray();
		}
		return buf;
	}

	public IDaoUpLoadArquivoAulaInterface getDaoUpLoadArquivoAula() {
		return daoUpLoadArquivoAula;
	}

	public void setDaoUpLoadArquivoAula(IDaoUpLoadArquivoAulaInterface daoUpLoadArquivoAula) {
		this.daoUpLoadArquivoAula = daoUpLoadArquivoAula;
	}

	public ArquivoUpLoadAula getArquivoUpLoadAula() {
		return arquivoUpLoadAula;
	}

	public void setArquivoUpLoadAula(ArquivoUpLoadAula arquivoUpLoadAula) {
		this.arquivoUpLoadAula = arquivoUpLoadAula;
	}

	public Part getArquivo() {
		return arquivo;
	}

	public void setArquivo(Part arquivo) {
		this.arquivo = arquivo;
	}
}
