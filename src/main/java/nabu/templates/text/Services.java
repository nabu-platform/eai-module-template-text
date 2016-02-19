package nabu.templates.text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.validation.constraints.NotNull;

import nabu.templates.text.types.PropertyImpl;
import be.nabu.eai.module.template.text.TextTemplateArtifact;
import be.nabu.glue.impl.ImperativeSubstitutor;
import be.nabu.libs.services.api.ExecutionContext;
import be.nabu.libs.types.api.KeyValuePair;

@WebService
public class Services {

	private ExecutionContext executionContext;
	
	@WebResult(name = "translationKeys")
	public List<KeyValuePair> translationKeys(@NotNull @WebParam(name = "artifactId") String id) throws IOException {
		List<KeyValuePair> properties = new ArrayList<KeyValuePair>();
		if (id != null) {
			TextTemplateArtifact resolved = executionContext.getServiceContext().getResolver(TextTemplateArtifact.class).resolve(id);
			if (resolved != null) {
				for (String key : ImperativeSubstitutor.getValues("%", resolved.getConfiguration().getContent())) {
					properties.add(new PropertyImpl("template:" + id, key));
				}
			}
			else {
				throw new IllegalArgumentException("Can not find text template: " + id);
			}
		}
		return properties;
	}
}
