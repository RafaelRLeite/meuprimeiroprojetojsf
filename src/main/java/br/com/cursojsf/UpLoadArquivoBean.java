package br.com.cursojsf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Scanner;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.Part;

import br.com.entidades.ArquivoUpLoadAula;
import br.com.repository.IDaoUpLoadArquivoAulaInterface;

@Named(value = "UpLoadArquivoBean")
@ViewScoped
public class UpLoadArquivoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;

	@Inject
	private IDaoUpLoadArquivoAulaInterface daoUpLoadArquivoAula;

	@Inject
	private ArquivoUpLoadAula arquivoUpLoadAula;

	private Part arquivo;

	public void UpLoad() throws IOException {

		System.err.println(arquivo.getContentType());

		byte[] arquivoByte = getByte(arquivo.getInputStream());

		arquivoUpLoadAula.setArquivo(arquivoByte);

		daoUpLoadArquivoAula.salvar(arquivoUpLoadAula);

		Scanner conteudo = new Scanner(arquivo.getInputStream());

		while (conteudo.hasNext()) {
			System.out.println(conteudo.next());
		}
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
