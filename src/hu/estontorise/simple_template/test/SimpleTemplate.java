package hu.estontorise.simple_template.test;

import hu.estontorise.simple_template.client.Template;
import hu.estontorise.simple_template.client.TemplateRenderer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SimpleTemplate {

	public static void main(String[] args) throws IOException {
		File f = new File("resources/test.html");
		FileInputStream fis = new FileInputStream(f);
		byte[] buf = new byte[(int) f.length()];
		fis.read(buf);
		fis.close();
		String template_src = new String(buf);

		Template t = Template.parse(template_src);
		Template container = t.get("container");
		Template odd = container.get("odd");
		Template even = container.get("even");

		TemplateRenderer tr = t.getRender();
		for (int i = 0; i < 10; i++)
			tr.addTo("container",
					(i % 2 == 0 ? odd.getRender().set("item", "item" + i)
							: even.getRender().set("item", "item" + i)));
		System.out.println(tr.render());
	}

}
