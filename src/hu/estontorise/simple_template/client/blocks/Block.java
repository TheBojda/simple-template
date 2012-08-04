package hu.estontorise.simple_template.client.blocks;

import hu.estontorise.simple_template.client.Template;

public class Block {

	public String name;
	public Template template;
	public String text;

	public Block(String text) {
		this.text = text;
	}

	public Block(String name, Template template) {
		this.name = name;
		this.template = template;
	}

}
