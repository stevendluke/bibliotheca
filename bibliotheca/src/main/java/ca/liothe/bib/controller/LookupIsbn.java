package ca.liothe.bib.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ca.liothe.bib.client.ClientInterface;
import ca.liothe.bib.client.Google;
import ca.liothe.bib.client.Isbndb;
import ca.liothe.bib.client.LibraryThing;
import ca.liothe.bib.client.Xisbn;
import ca.liothe.bib.data.service.BookDAO;
import ca.liothe.bib.model.LookupResults;
import ca.liothe.bib.rest.model.BookDTO;

@RestController
public class LookupIsbn {
	
	//private static final Logger logger = LoggerFactory.getLogger(Authentication.class);

	private static ArrayList<ClientInterface> clients;
	
	@Autowired
	private BookDAO bookDao;
	
	static{
		clients = new ArrayList<ClientInterface>();

		clients.add(new Isbndb());
		clients.add(new LibraryThing());
		clients.add(new Xisbn());
		clients.add(new Google());
	}

	@RequestMapping(value = "/lookup/{isbn}", produces={"application/json"})
	public @ResponseBody LookupResults lookupBookByIsbn(@PathVariable String isbn) {
		LookupResults results = new LookupResults();
		
		if(bookDao.findByIsbn(isbn) == null){
			for(ClientInterface client : clients){
				BookDTO book = client.lookupIsbn(isbn);
				
				if(book != null)
					results.getBooks().add(book);
			}
			
			results.setSuccess(results.getBooks().size() > 0);
			results.setExists(false);
		}
		else{
			results.setExists(true);
		}
		
		return results;
	}
	
}
