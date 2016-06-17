package test;

import com.lyte.parser.PluginXmlParserPlugin;

public class Test1 {

	public static void main(String[] args) {
		PluginXmlParserPlugin plugin = new PluginXmlParserPlugin();
		System.out.println(Thread.currentThread().getName());
		plugin.addPluginListener(new TestListener());
		plugin.startPlugin();
		plugin.startPlugin();
		plugin.startPlugin();
		plugin.startPlugin();
		plugin.startPlugin();
		plugin.startPlugin();
		plugin.startPlugin();
		plugin.startPlugin();
		plugin.startPlugin();
	}

}
