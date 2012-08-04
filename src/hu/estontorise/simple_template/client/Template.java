package hu.estontorise.simple_template.client;

import hu.estontorise.simple_template.client.blocks.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Template {

	public static Template parse(String source) {
		source = source.replaceAll("\\{([a-zA-Z_]*)\\}", "<!--{section $1}--><!--{/section}-->");
		Template result = new Template();
		Template container = result;
		Stack<Block> blockStack = new Stack<Block>();
		blockStack.add(new Block("_root_", container));
		int pos = 0, lastPos = 0;
		String frag;
		while (pos > -1) {
			pos = source.indexOf("<!--{", lastPos);
			if (pos < 0)
				continue;			
			frag = source.substring(lastPos, pos);
			pos += 5;
			container.blocks.add(new Block(frag));
			lastPos = pos;
			pos = source.indexOf("}-->", lastPos);
			frag = source.substring(lastPos, pos);
			pos += 4;
			String blockContent = frag.trim();
			if (blockContent.startsWith("/")) {
				blockStack.pop();
				container = blockStack.peek().template;
			} else {
				String[] frags = blockContent.split(" ");
				blockContent = frags[1];
				Block block = new Block(blockContent, new Template());
				container.blocks.add(block);
				blockStack.push(block);
				container = block.template;
			}
			lastPos = pos;
		}
		frag = source.substring(lastPos, source.length());
		container.blocks.add(new Block(frag));	
		return result;
	}

	List<Block> blocks = new ArrayList<Block>();
	
	private Template() {
	}

	public Template get(String name) {
		for(Block b : blocks)
			if(name.equals(b.name))
				return b.template;
		return null;
	}

	public TemplateRenderer getRender() {
		return new TemplateRenderer(this);
	}
	
}
