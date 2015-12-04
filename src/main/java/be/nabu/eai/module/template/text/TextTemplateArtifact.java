package be.nabu.eai.module.template.text;

import java.io.IOException;
import java.util.Set;

import be.nabu.eai.repository.api.Repository;
import be.nabu.eai.repository.artifacts.jaxb.JAXBArtifact;
import be.nabu.libs.resources.api.ResourceContainer;
import be.nabu.libs.services.api.DefinedService;
import be.nabu.libs.services.api.ServiceInstance;
import be.nabu.libs.services.api.ServiceInterface;
import be.nabu.libs.types.SimpleTypeWrapperFactory;
import be.nabu.libs.types.api.ComplexType;
import be.nabu.libs.types.base.ComplexElementImpl;
import be.nabu.libs.types.base.SimpleElementImpl;
import be.nabu.libs.types.structure.Structure;

public class TextTemplateArtifact extends JAXBArtifact<TextTemplateConfiguration> implements DefinedService {

	public static final String PARAMETERS = "parameters";
	public static final String RESULT = "result";
	public static final String CONTENT_TYPE = "contentType";
	
	private Repository repository;
	private Structure input, output;

	public TextTemplateArtifact(String id, ResourceContainer<?> directory, Repository repository) {
		super(id, directory, "text-template.xml", TextTemplateConfiguration.class);
		this.repository = repository;
	}

	@Override
	public ServiceInterface getServiceInterface() {
		return new ServiceInterface() {
			@Override
			public ComplexType getInputDefinition() {
				return getInput();
			}
			@Override
			public ComplexType getOutputDefinition() {
				return getOutput();
			}
			@Override
			public ServiceInterface getParent() {
				return null;
			}
		};
	}

	@Override
	public ServiceInstance newInstance() {
		try {
			return new TextTemplateInstance(this);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Set<String> getReferences() {
		return null;
	}

	public Repository getRepository() {
		return repository;
	}

	private Structure getInput() {
		if (input == null) {
			synchronized(this) {
				if (input == null) {
					try {
						Structure input = new Structure();
						input.setName("input");
						if (getConfiguration().getInput() != null) {
							input.add(new ComplexElementImpl(PARAMETERS, (ComplexType) getConfiguration().getInput(), input));
						}
						this.input = input;
					}
					catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
		return input;
	}
	
	private Structure getOutput() {
		if (output == null) {
			synchronized(this) {
				if (output == null) {
					Structure output = new Structure();
					output.setName("output");
					output.add(new SimpleElementImpl<String>(RESULT, SimpleTypeWrapperFactory.getInstance().getWrapper().wrap(String.class), output));
					output.add(new SimpleElementImpl<String>(CONTENT_TYPE, SimpleTypeWrapperFactory.getInstance().getWrapper().wrap(String.class), output));
					this.output = output;
				}
			}
		}
		return output;
	}
}