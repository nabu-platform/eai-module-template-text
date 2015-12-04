package be.nabu.eai.module.template.text;

import java.io.IOException;
import java.nio.charset.Charset;

import be.nabu.eai.repository.EAIResourceRepository;
import be.nabu.glue.MultipleRepository;
import be.nabu.glue.api.Parser;
import be.nabu.glue.impl.EnvironmentLabelEvaluator;
import be.nabu.glue.impl.SimpleExecutionEnvironment;
import be.nabu.glue.impl.parsers.GlueParserProvider;
import be.nabu.glue.repositories.ScannableScriptRepository;
import be.nabu.glue.services.CombinedExecutionContextImpl;
import be.nabu.glue.services.ServiceMethodProvider;
import be.nabu.libs.resources.api.ResourceContainer;
import be.nabu.libs.services.api.ExecutionContext;
import be.nabu.libs.services.api.Service;
import be.nabu.libs.services.api.ServiceException;
import be.nabu.libs.services.api.ServiceInstance;
import be.nabu.libs.types.TypeUtils;
import be.nabu.libs.types.api.ComplexContent;
import be.nabu.libs.types.api.Element;

public class TextTemplateInstance implements ServiceInstance {

	private TextTemplateArtifact template;
	private GlueParserProvider provider;
	private MultipleRepository repository;
	private Parser parser;

	public TextTemplateInstance(TextTemplateArtifact template) throws IOException {
		this.provider = new GlueParserProvider(new ServiceMethodProvider(template.getRepository(), template.getRepository(), null));
		this.template = template;
		initialize();
	}
	
	private void initialize() throws IOException {
		repository = new MultipleRepository(null);
		ResourceContainer<?> privateFolder = (ResourceContainer<?>) template.getDirectory().getChild(EAIResourceRepository.PRIVATE);
		if (privateFolder != null) {
			repository.add(new ScannableScriptRepository(repository, privateFolder, provider, Charset.defaultCharset()));
		}
		this.parser = provider.newParser(repository, "template.glue");
	}
	
	@Override
	public Service getDefinition() {
		return template;
	}

	@Override
	public ComplexContent execute(ExecutionContext executionContext, ComplexContent input) throws ServiceException {
		try {
			if (EAIResourceRepository.isDevelopment()) {
				repository.refresh();
			}
			CombinedExecutionContextImpl context = new CombinedExecutionContextImpl(executionContext, new SimpleExecutionEnvironment(template.getId()), new EnvironmentLabelEvaluator(null));
			ComplexContent variables = (ComplexContent) input.get(TextTemplateArtifact.PARAMETERS);
			if (variables != null) {
				for (Element<?> element : TypeUtils.getAllChildren(variables.getType())) {
					context.getPipeline().put(element.getName(), variables.get(element.getName()));
				}
			}
			String substitute = parser.substitute(template.getConfiguration().getContent(), context, template.getConfiguration().getAllowNull() != null && template.getConfiguration().getAllowNull());
			ComplexContent output = template.getServiceInterface().getOutputDefinition().newInstance();
			output.set(TextTemplateArtifact.RESULT, substitute);
			output.set(TextTemplateArtifact.CONTENT_TYPE, template.getConfiguration().getContentType() != null ? template.getConfiguration().getContentType() : "text/plain");
			return output;
		}
		catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}