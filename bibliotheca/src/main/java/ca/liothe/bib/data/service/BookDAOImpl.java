package ca.liothe.bib.data.service;

import org.springframework.beans.factory.annotation.Autowired;

import ca.liothe.bib.exceptions.BookDetailsNotFound;
import ca.liothe.bib.model.Book;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;


public abstract class BookDAOImpl implements BookDAO  {

	@Autowired
	private DatastoreService library;
	
	public static final int pageSize = 10;
	
	@Override
	public boolean create(Book book) {
		Entity bookEntity = bookToEntity(book);
		
		library.put(bookEntity);
		
		return true;
	}

	@Override
	public Book delete(String isbn) throws BookDetailsNotFound {
		Book book = findByIsbn(isbn);
		
		if(book == null){
			throw new BookDetailsNotFound(); 
		}
		
		Key bookIsbn = KeyFactory.createKey("Book", isbn); // .stringToKey(isbn);
		library.delete(bookIsbn);
		
		return book;
	}

	@Override
	public void update(Book book) throws BookDetailsNotFound {
		if(findByIsbn(book.getIsbn()) == null){
			throw new BookDetailsNotFound();
		}

		Entity bookEntity = bookToEntity(book);
		library.put(bookEntity);
	}
	
	@Override
	public Book findByIsbn(String isbn){
		Key bookIsbn = KeyFactory.createKey("Book", isbn);
		
		Book book = null;
		
		try {
			Entity bookEntity = library.get(bookIsbn);
			
			book = entityToBook(bookEntity);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
		
		return book;
	}
	
	private Entity bookToEntity(Book book){
		Entity bookEntity = new Entity("Book", book.getIsbn());

		bookEntity.setProperty("isbn", book.getIsbn());
		bookEntity.setProperty("title", book.getTitle());
		bookEntity.setProperty("author", book.getAuthor());
		bookEntity.setProperty("bookcase", book.getBookcase());
		bookEntity.setProperty("bookshelf", book.getBookshelf());
		bookEntity.setProperty("genreType", book.getGenreType());
		
		return bookEntity;
	}
	
	private Book entityToBook(Entity bookEntity){
		Book book = new Book();

		book.setIsbn(bookEntity.getProperty("isbn").toString());
		book.setTitle(bookEntity.getProperty("title").toString());
		book.setAuthor(bookEntity.getProperty("author").toString());
		book.setBookcase(bookEntity.getProperty("bookcase").toString());
		book.setBookshelf(bookEntity.getProperty("bookshelf").toString());
		book.setGenreType(bookEntity.getProperty("genreType").toString());
		
		return book;
	}

}
