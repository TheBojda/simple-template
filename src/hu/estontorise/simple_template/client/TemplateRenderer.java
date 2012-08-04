package hu.estontorise.simple_template.client;

import hu.estontorise.simple_template.client.blocks.Block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemplateRenderer {

	private Template template;
	private Map<String, List<String>> data = new HashMap<String, List<String>>();
	
	TemplateRenderer(Template template) {
		this.template = template;
	}
	
	public TemplateRenderer addTo(String name, String value) {
		if(!data.containsKey(name))
			data.put(name, new ArrayList<String>());
		data.get(name).add(value);
		return this;
	}

	public TemplateRenderer addTo(String name, TemplateRenderer templateRenderer) {
		return addTo(name, templateRenderer.render());
	}
	
	public TemplateRenderer set(String name, String value) {
		return addTo(name, value);
	}

	public TemplateRenderer set(String name, TemplateRenderer templateRenderer) {
		return addTo(name, templateRenderer);
	}

	public String render() {
		StringBuffer sb = new StringBuffer();
		for(Block b : template.blocks) {
			if(b.text != null) {
				sb.append(b.text);
			} else if (data.containsKey(b.name)) {
				for(String v : data.get(b.name))
					sb.append(v);
			}
		}
		return sb.toString();
	}

}
