package ca.liothe.bib.client;

import javax.xml.xpath.XPathExpressionException;

import ca.liothe.bib.rest.model.BookDTO;
import ca.liothe.bib.util.XmlParser;

public class LibraryThing extends ClientInterface {

	public LibraryThing() {
		super("http://www.librarything.com/services/rest/1.0/?method=librarything.ck.getwork&apikey=bf43e5212866b2d788ff35aa3a4ac576&isbn=%s");
	}

	@Override
	public void parse(BookDTO book, XmlParser parser) throws XPathExpressionException {
		book.setAuthor(parser.getStringValue("//author"));
		book.setTitle(parser.getStringValue("//field[@name='canonicaltitle']//fact"));
	}
	
}
