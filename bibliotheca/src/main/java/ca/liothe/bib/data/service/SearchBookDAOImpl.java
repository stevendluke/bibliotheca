package ca.liothe.bib.data.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.liothe.bib.exceptions.BookDetailsNotFound;
import ca.liothe.bib.model.Book;
import ca.liothe.bib.model.CachedPage;
import ca.liothe.bib.model.Page;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.Query;
import com.google.appengine.api.search.QueryOptions;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.appengine.api.search.SortExpression;
import com.google.appengine.api.search.SortOptions;


@Service
public class SearchBookDAOImpl extends BookDAOImpl {

	@Autowired
	private Index library;
	
	@Autowired
	private MemcacheService memcache;
	
	public static final int pageSize = 20;
	
	@Override
	public boolean create(Book book) {
		super.create(book);
		
		Document doc = bookToDocument(book);
		
		library.put(doc);
		
		memcache.clearAll();
		
		return true;
	}

	@Override
	public Book delete(String isbn) throws BookDetailsNotFound {
		Book book = super.delete(isbn);
		
		if(book == null){
			throw new BookDetailsNotFound(); 
		}
		
		library.delete(isbn);
		
		memcache.clearAll();
		
		return book;
	}

	@Override
	public void update(Book book) throws BookDetailsNotFound {
		super.update(book);
		
		if(findByIsbn(book.getIsbn()) == null){
			throw new BookDetailsNotFound();
		}

		Document doc = bookToDocument(book);
		library.put(doc);
		
		memcache.clearAll();
	}

	@Override
	public Page findAllByPage(int pageNumber) {
	    return search("", pageNumber);
	}

	@Override
	public Page search(String search, int pageNumber) {
	    String key = "search=" + search + "&page=" + pageNumber;
	    Page page = cachedPageToPage(key);
	    
	    if(page == null){
	    	page = new Page();
	    	
			SortOptions sort = SortOptions
					.newBuilder()
					.addSortExpression(
							SortExpression.newBuilder()
							.setExpression("title")
							.setDirection(SortExpression.SortDirection.ASCENDING)
					)
					.build();
			
			QueryOptions options = QueryOptions.newBuilder().setSortOptions(sort).setOffset(pageNumber * pageSize).setLimit(pageSize).build();
			
		    Query query = Query.newBuilder().setOptions(options).build(search);
	
		    Results<ScoredDocument> result = library.search(query);
	
		    Collection<ScoredDocument> books = result.getResults();
		    
			for (Document doc : books) {
		    	Book book = documentToBook(doc);
		    	
		    	page.getBooks().add(book);
		    } 
	
			int current = pageNumber;
			int begin = Math.max(0, current - 5);
			int total = ((int) Math.ceil((double) result.getNumberFound() / (double) pageSize)) - 1;
			
			if(total < 0) total = 0;
			
			int end = Math.min(begin + 5, total);
	
			page.setCurrent(current);
			page.setBegin(begin);
			page.setEnd(end);
			page.setTotal(total);
			
			pageToCachedPage(key, page);
	    }
	    
	    return page;
	}
	
	@Override
	public Book findByIsbn(String isbn){
		return super.findByIsbn(isbn);
	}
	
	private void pageToCachedPage(String key, Page page){
		CachedPage cachedPage = new CachedPage();

		cachedPage.setCurrent(page.getCurrent());
		cachedPage.setBegin(page.getBegin());
		cachedPage.setEnd(page.getEnd());
		cachedPage.setTotal(page.getTotal());
		
		for(Book book : page.getBooks()){
			cachedPage.getIsbns().add(book.getIsbn());
			memcache.put(book.getIsbn(), book);
		}
		
		memcache.put(key, cachedPage);
	}
	
	private Page cachedPageToPage(String key){
		CachedPage cachedPage = (CachedPage) memcache.get(key);
		
		Page page = null;
		
		if(cachedPage != null){
			page = new Page();
	
			page.setCurrent(cachedPage.getCurrent());
			page.setBegin(cachedPage.getBegin());
			page.setEnd(cachedPage.getEnd());
			page.setTotal(cachedPage.getTotal());
			
			for(String isbn : cachedPage.getIsbns()){
				Book book = (Book) memcache.get(isbn);
				
				if(book == null){
					book = findByIsbn(isbn);
					memcache.put(isbn, book);
				}
				
				page.getBooks().add(book);
			}
		}
		
		return page;
	}
	
	private Document bookToDocument(Book book){
		if(book == null){
			return null;
		}
		
		Document doc = Document.newBuilder()
			    .setId(book.getIsbn()) 
			    .addField(Field.newBuilder().setName("isbn").setText(book.getIsbn()))
			    .addField(Field.newBuilder().setName("title").setText(book.getTitle()))
			    .addField(Field.newBuilder().setName("author").setText(book.getAuthor()))
			    .addField(Field.newBuilder().setName("bookcase").setText(book.getBookcase()))
			    .addField(Field.newBuilder().setName("bookshelf").setText(book.getBookshelf()))
			    .addField(Field.newBuilder().setName("genreType").setText(book.getGenreType()))
			    .build();
		
		return doc;
	}
	
	private Book documentToBook(Document doc){
		if(doc == null){
			return null;
		}
		
		Book book = new Book();

		book.setIsbn(doc.getOnlyField("isbn").getText());
		book.setTitle(doc.getOnlyField("title").getText());
		book.setAuthor(doc.getOnlyField("author").getText());
		book.setBookcase(doc.getOnlyField("bookcase").getText());
		book.setBookshelf(doc.getOnlyField("bookshelf").getText());
		book.setGenreType(doc.getOnlyField("genreType").getText());
		
		return book;
	}
	
	private void nukeAssist(String isbn) throws BookDetailsNotFound{
		super.delete(isbn);
	}

	@Override
	public void nuke() throws BookDetailsNotFound {
	    Page page =  search("", 0);
		
		for (Book book : page.getBooks()) {
			try{
				library.delete(book.getIsbn());
			}
			catch(Exception e){}
			
			try{
				this.delete(book.getIsbn());
			}
			catch(Exception e){}
			
			try{
				nukeAssist(book.getIsbn());
			}
			catch(Exception e){}
		}

		memcache.clearAll();
	}

}
