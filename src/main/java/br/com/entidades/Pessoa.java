package br.com.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;

	@javax.persistence.Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;

	/* Não é obrigatório incluir mensagem, @Size define a quantidade de caracteres */
	@NotEmpty
	@Size(min = 5, max = 50, message = "O nome deve ter entre 10 e 50 letras")
	private String nome;

	/* Anotações para validação, NotEmpty não pode ser vazio o output e NotNull não pode ser nulo mas pode ser vazio */
	@org.hibernate.validator.constraints.NotEmpty(message = "Informe o Sobrenome do usuário")
	@NotNull(message = "Informe o Sobrenome do usuário")
	private String sobrenome;

	@DecimalMin(value = "10", message = "Idade deve ser maior que 10")
	@DecimalMax(value = "100", message = "Idade maxima ultrapassada")
	private Integer idade;

	@Temporal(TemporalType.DATE)
	private Date dataNascimento = new Date();
	private String sexo;
	private String CPF;
	private String tituloEleitoral;
	private String[] frameworks;
	private Boolean ativo;
	private String senha;
	private String login;
	private String perfilUser;
	private String nivelProgramador;
	private Integer[] linguagens;
	private String cep;
	private String logradouro;
	private String complemento;
	private String bairro;
	private String localidade;
	private String uf;

	@ManyToOne /* Pode ter muitas pessoas em uma cidade */
	private Cidades cidades;

	@Transient /* Não grava na memória ou não fica persistente */
	private Estados estados;

	@Column(columnDefinition = "text") /* Tipo text grava arquivos em base64 em miniatura */
	private String fotoIconBase64;

	private String extensao; /* Grava o tipo de extensão */

	@Lob /* Gravar aquivos no banco de dados */
	@Basic(fetch = FetchType.LAZY)
	private byte[] fotoIconBase64Original;

	public Pessoa() {
	}

	public String getFotoIconBase64() {
		return fotoIconBase64;
	}

	public void setFotoIconBase64(String fotoIconBase64) {
		this.fotoIconBase64 = fotoIconBase64;
	}

	public String getExtensao() {
		return extensao;
	}

	public void setExtensao(String extensao) {
		this.extensao = extensao;
	}

	public byte[] getFotoIconBase64Original() {
		return fotoIconBase64Original;
	}

	public void setFotoIconBase64Original(byte[] fotoIconBase64Original) {
		this.fotoIconBase64Original = fotoIconBase64Original;
	}

	public Cidades getCidades() {
		return cidades;
	}

	public void setCidades(Cidades cidades) {
		this.cidades = cidades;
	}

	public Estados getEstados() {
		return estados;
	}

	public void setEstados(Estados estados) {
		this.estados = estados;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}

	public String getTituloEleitoral() {
		return tituloEleitoral;
	}

	public void setTituloEleitoral(String tituloEleitoral) {
		this.tituloEleitoral = tituloEleitoral;
	}

	public Integer[] getLinguagens() {
		return linguagens;
	}

	public void setLinguagens(Integer[] linguagens) {
		this.linguagens = linguagens;
	}

	public void setNivelProgramador(String nivelProgramador) {
		this.nivelProgramador = nivelProgramador;
	}

	public String getNivelProgramador() {
		return nivelProgramador;
	}

	public String getPerfilUser() {
		return perfilUser;
	}

	public void setPerfilUser(String perfilUser) {
		this.perfilUser = perfilUser;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public String[] getFrameworks() {
		return frameworks;
	}

	public void setFrameworks(String[] framework) {
		this.frameworks = framework;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		return Objects.equals(Id, other.Id);
	}

}
