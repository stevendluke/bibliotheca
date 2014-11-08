package ca.liothe.bib.client;

import javax.xml.xpath.XPathExpressionException;

import ca.liothe.bib.rest.model.BookDTO;
import ca.liothe.bib.util.XmlParser;

public class Xisbn extends ClientInterface{

	public Xisbn() {
		super("http://xisbn.worldcat.org/webservices/xid/isbn/%s?method=getMetadata&format=xml&fl=*");
	}

	@Override
	public void parse(BookDTO book, XmlParser parser) throws XPathExpressionException {
		book.setAuthor(parser.getStringValue("//author"));
		book.setTitle(parser.getStringValue("//title"));
	}

}
