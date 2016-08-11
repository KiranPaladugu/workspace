/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tools.plugin.xmltreeviewer.tech;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.tools.plugin.xmltreeviewer.UnlimitedStack;

public class SimpleSaxXmlHandler extends DefaultHandler {
	private final UnlimitedStack<XmlNode> stack = new UnlimitedStack<XmlNode>();
	private final TagHandlerHelper helper = new TagHandlerHelper();
	private XmlNode rootNode;

	@Override
	public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {
		// log("StartElemnt "+qName);
		String q_name = qName;
		if (qName.contains(":")) {
			q_name = q_name.replace(':', '_');
		}
		final AbstractSimpleTagHandler simpleHanlder = helper.getTagHanlder(q_name);
		if (simpleHanlder != null) {
			simpleHanlder.startElement(uri, localName, qName, attributes);
		}
		final XmlNode node = new XmlNode(qName, uri);
		final XmlNode parent = stack.peek();
		if (parent != null) {
			parent.addChild(node);
		}

		for (int i = 0; i < attributes.getLength(); i++) {
			node.addAttribute(attributes.getQName(i), attributes.getValue(i));
		}
		stack.push(node);
	}

	@Override
	public void characters(final char[] ch, final int start, final int length) throws SAXException {
		final XmlNode node = stack.peek();
		node.append(ch, start, length);
	}

	@Override
	public void ignorableWhitespace(final char[] ch, final int start, final int length) throws SAXException {
		super.ignorableWhitespace(ch, start, length);
		System.out.println(new String(ch, start, length));
	}

	@Override
	public void endElement(final String uri, final String localName, final String qName) throws SAXException {
		// log("EndElement :"+qName);
		// final XmlNode currentNode = stack.peek();
		/*
		 * if(currentNode.isAttribute()){ XmlNode parentNode = currentNode.getParent(); parentNode.removeChild(currentNode);
		 * parentNode.addAttribute(currentNode.getTagName(), currentNode.getValue()); }
		 */
		rootNode = stack.pop();
	}

	public XmlNode getRootNode() {
		return this.rootNode;
	}

}
