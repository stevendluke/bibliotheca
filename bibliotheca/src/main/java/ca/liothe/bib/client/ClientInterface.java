package ca.liothe.bib.client;

import java.io.IOException;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import ca.liothe.bib.rest.model.BookDTO;
import ca.liothe.bib.util.XmlParser;

import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;

public abstract class ClientInterface {
	private static HttpRequestFactory requestFactory = UrlFetchTransport.getDefaultInstance().createRequestFactory();
	private String urlTemplate; 
	private NamespaceContext namespace;
	
	public ClientInterface(String urlTemplate){
		this.urlTemplate = urlTemplate;
	}
	
	public ClientInterface(String urlTemplate, NamespaceContext namespace){
		this.urlTemplate = urlTemplate;
		this.namespace = namespace;
	}
	
	public BookDTO lookupIsbn(String isbn){
		BookDTO book = new BookDTO();
		
		HttpResponse response = null;
		try {
			response = getRequest(String.format(urlTemplate, isbn));
			
			if(response.isSuccessStatusCode()){
				XmlParser parser = new XmlParser(response.getContent(), namespace);
				
				book.setIsbn(isbn);
				
				parse(book, parser);
				
				if(book.getAuthor() == null || book.getTitle() == null || book.getAuthor().equals("") || book.getTitle().equals(""))
					book = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		finally{
			try {
				response.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return book;
	}
	
	public abstract void parse(BookDTO book, XmlParser parser) throws XPathExpressionException;
	
	private HttpResponse getRequest(String url) throws IOException{
		HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(url));
		
		return request.execute();
	}
}
