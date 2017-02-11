package be.nabu.eai.module.template.text;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import be.nabu.eai.api.InterfaceFilter;
import be.nabu.eai.api.LargeText;
import be.nabu.eai.repository.jaxb.ArtifactXMLAdapter;
import be.nabu.libs.services.api.DefinedService;
import be.nabu.libs.types.api.DefinedType;

@XmlRootElement(name = "textTemplate")
@XmlType(propOrder = { "contentType", "input", "allowNull", "translationService", "content" })
public class TextTemplateConfiguration {
	private String contentType;
	private String content;
	private DefinedType input;
	private Boolean allowNull;
	private DefinedService translationService;
	
	@XmlJavaTypeAdapter(value = ArtifactXMLAdapter.class)
	@InterfaceFilter(implement = "be.nabu.eai.repository.api.Translator.translate")
	public DefinedService getTranslationService() {
		return translationService;
	}
	public void setTranslationService(DefinedService translationService) {
		this.translationService = translationService;
	}
	
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	@LargeText
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@XmlJavaTypeAdapter(value = ArtifactXMLAdapter.class)
	public DefinedType getInput() {
		return input;
	}
	public void setInput(DefinedType input) {
		this.input = input;
	}
	
	public Boolean getAllowNull() {
		return allowNull;
	}
	public void setAllowNull(Boolean allowNull) {
		this.allowNull = allowNull;
	}
}
