/*
* Copyright (C) 2015 Alexander Verbruggen
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public License
* along with this program. If not, see <https://www.gnu.org/licenses/>.
*/

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
import be.nabu.libs.types.base.ValueImpl;
import be.nabu.libs.types.properties.MinOccursProperty;
import be.nabu.libs.types.structure.Structure;

public class TextTemplateArtifact extends JAXBArtifact<TextTemplateConfiguration> implements DefinedService {

	public static final String PARAMETERS = "parameters";
	public static final String LANGUAGE = "language";
	public static final String TRANSLATION_SERVICE = "translationService";
	public static final String RESULT = "result";
	public static final String CONTENT_TYPE = "contentType";
	
	private Structure input, output;

	public TextTemplateArtifact(String id, ResourceContainer<?> directory, Repository repository) {
		super(id, directory, repository, "text-template.xml", TextTemplateConfiguration.class);
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

	private Structure getInput() {
		if (input == null) {
			synchronized(this) {
				if (input == null) {
					try {
						Structure input = new Structure();
						input.setName("input");
						input.add(new SimpleElementImpl<String>(LANGUAGE, SimpleTypeWrapperFactory.getInstance().getWrapper().wrap(String.class), input, new ValueImpl<Integer>(MinOccursProperty.getInstance(), 0)));
						input.add(new SimpleElementImpl<String>(TRANSLATION_SERVICE, SimpleTypeWrapperFactory.getInstance().getWrapper().wrap(String.class), input, new ValueImpl<Integer>(MinOccursProperty.getInstance(), 0)));
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
