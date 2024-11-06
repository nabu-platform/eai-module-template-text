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
