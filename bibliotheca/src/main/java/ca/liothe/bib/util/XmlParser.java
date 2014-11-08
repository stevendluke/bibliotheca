package ca.liothe.bib.util;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XmlParser {
	private Document dom;
	private XPath xpath;
	
	public XmlParser(InputStream xml) throws SAXException, IOException, ParserConfigurationException{
		this(xml, null);
	}
	
	public XmlParser(InputStream xml, NamespaceContext namespace) throws SAXException, IOException, ParserConfigurationException{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(namespace != null);
		DocumentBuilder db = dbf.newDocumentBuilder();
		dom = db.parse(xml);
		
		xpath = XPathFactory.newInstance().newXPath();
		
		if(namespace != null)
			xpath.setNamespaceContext(namespace);
	}
	
	public String getStringValue(String query) throws XPathExpressionException{
		String result = xpath.compile(query).evaluate(dom);
		
		if(result != null)
			result = result.trim();
		
		return result;
	}
}
