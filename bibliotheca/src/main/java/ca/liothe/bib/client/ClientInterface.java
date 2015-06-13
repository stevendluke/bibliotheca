package ca.liothe.bib.client;

import java.io.IOException;

import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPathExpressionException;

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
		} catch (Exception e) {
			System.out.println("Class: " + this.getClass());
			e.printStackTrace();
		} 
		finally{
			try {
				if(response != null){
					response.disconnect();
				}
			} catch (IOException e) {
				System.out.println("Class: " + this.getClass());
				e.printStackTrace();
			}
		}

		return book;
	}
	
	public abstract void parse(BookDTO book, XmlParser parser) throws XPathExpressionException;
	
	private HttpResponse getRequest(String url) throws IOException{
		HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(url));
		request.setConnectTimeout(10000);
		request.setReadTimeout(10000);
		
		return request.execute();
	}
}
