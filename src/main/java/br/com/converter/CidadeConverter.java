package br.com.converter;

import java.io.Serializable;

import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

import br.com.entidades.Cidades;

@FacesConverter(forClass = Cidades.class, value = "cidadeConverte")
public class CidadeConverter implements Converter, Serializable {

	private static final long serialVersionUID = 1L;

	@Override /* Retorna o objeto inteiro */
	public Object getAsObject(FacesContext context, UIComponent component, String codigoCidade) {

		EntityManager entityManager = CDI.current().select(EntityManager.class).get();

		Cidades cidade = entityManager.find(Cidades.class, Long.parseLong(codigoCidade));

		// entityManager.close();

		return cidade;

	}

	@Override /* Retorna apenas o código em String */
	public String getAsString(FacesContext context, UIComponent component, Object cidade) {

		if (cidade == null) {
			return null;
		}

		if (cidade instanceof Cidades) {

			return ((Cidades) cidade).getId().toString();
		}

		else {
			return cidade.toString();
		}

	}

}
