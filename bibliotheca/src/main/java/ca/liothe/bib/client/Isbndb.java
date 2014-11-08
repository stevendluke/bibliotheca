package ca.liothe.bib.client;

import javax.xml.xpath.XPathExpressionException;

import ca.liothe.bib.rest.model.BookDTO;
import ca.liothe.bib.util.XmlParser;

public class Isbndb extends ClientInterface {
	
	public Isbndb() {
		super("http://isbndb.com/api/books.xml?access_key=QKI3WL33&index1=isbn&value1=%s");
	}

	@Override
	public void parse(BookDTO book, XmlParser parser) throws XPathExpressionException {
		book.setAuthor(parser.getStringValue("//AuthorsText"));
		book.setTitle(parser.getStringValue("//TitleLong"));
		
		if(book.getTitle() == null || book.getTitle().equals(""))
			book.setTitle(parser.getStringValue("//Title"));
			
		if(book.getAuthor() != null && book.getAuthor().endsWith(",")){
			book.setAuthor(book.getAuthor().substring(0, book.getAuthor().length()-2));
		}
	}
	
}
