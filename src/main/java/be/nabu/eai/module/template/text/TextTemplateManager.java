package be.nabu.eai.module.template.text;

import be.nabu.eai.repository.api.Repository;
import be.nabu.eai.repository.managers.base.JAXBArtifactManager;
import be.nabu.libs.resources.api.ResourceContainer;

public class TextTemplateManager extends JAXBArtifactManager<TextTemplateConfiguration, TextTemplateArtifact> {

	public TextTemplateManager() {
		super(TextTemplateArtifact.class);
	}

	@Override
	protected TextTemplateArtifact newInstance(String id, ResourceContainer<?> container, Repository repository) {
		return new TextTemplateArtifact(id, container, repository);
	}

}
