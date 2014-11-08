package ca.liothe.bib.data.service;

import ca.liothe.bib.exceptions.BookDetailsNotFound;
import ca.liothe.bib.model.Book;
import ca.liothe.bib.model.Page;

public interface BookDAO {
    public boolean create(Book book);  
    public Book findByIsbn(String isbn);  
    public Book delete(String isbn) throws BookDetailsNotFound;  
    public void update(Book book) throws BookDetailsNotFound;  
    public Page findAllByPage(int pageNumber);
    public Page search(String search, int pageNumber);
}
