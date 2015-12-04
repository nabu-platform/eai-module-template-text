package be.nabu.eai.module.template.text;

import java.io.IOException;
import java.util.List;

import be.nabu.eai.developer.MainController;
import be.nabu.eai.developer.managers.base.BaseJAXBGUIManager;
import be.nabu.eai.repository.resources.RepositoryEntry;
import be.nabu.libs.property.api.Property;
import be.nabu.libs.property.api.Value;

public class TextTemplateGUIManager extends BaseJAXBGUIManager<TextTemplateConfiguration, TextTemplateArtifact> {

	public TextTemplateGUIManager() {
		super("Text Template", TextTemplateArtifact.class, new TextTemplateManager(), TextTemplateConfiguration.class);
	}

	@Override
	protected List<Property<?>> getCreateProperties() {
		return null;
	}

	@Override
	protected TextTemplateArtifact newInstance(MainController controller, RepositoryEntry entry, Value<?>... values) throws IOException {
		return new TextTemplateArtifact(entry.getId(), entry.getContainer(), entry.getRepository());
	}

	@Override
	public String getCategory() {
		return "Templates";
	}
}
