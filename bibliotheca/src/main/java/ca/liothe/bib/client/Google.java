package ca.liothe.bib.client;

import java.util.Iterator;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPathExpressionException;

import ca.liothe.bib.rest.model.BookDTO;
import ca.liothe.bib.util.XmlParser;

public class Google extends ClientInterface {
	
	public Google() {
		super("http://www.google.com/books/feeds/volumes?q=%s", new NamespaceContext(){
			@Override
			public String getNamespaceURI(String prefix) {
				if (prefix == null) throw new NullPointerException("Null prefix");
		        else if ("dc".equals(prefix)) return "http://purl.org/dc/terms";
		        return XMLConstants.NULL_NS_URI;
			}

			@Override
			public String getPrefix(String namespaceURI) {
		        throw new UnsupportedOperationException();
			}

			@Override
			public Iterator<?> getPrefixes(String namespaceURI) {
		        throw new UnsupportedOperationException();
			}
			
		});
	}

	@Override
	public void parse(BookDTO book, XmlParser parser) throws XPathExpressionException {
		book.setAuthor(parser.getStringValue("//dc:creator"));
		book.setTitle(parser.getStringValue("//dc:title"));
	}
	
}
