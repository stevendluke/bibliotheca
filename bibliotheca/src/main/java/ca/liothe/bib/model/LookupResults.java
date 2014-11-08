package ca.liothe.bib.model;

import java.util.ArrayList;
import java.util.List;

import ca.liothe.bib.rest.model.BookDTO;

public class LookupResults {
	private boolean exists;
	private boolean success;
	private List<BookDTO> books;
	
	public LookupResults(){
		books = new ArrayList<BookDTO>();
	}
	public boolean isExists() {
		return exists;
	}
	public void setExists(boolean exists) {
		this.exists = exists;
	}
	public List<BookDTO> getBooks() {
		return books;
	}
	public void setBooks(List<BookDTO> books) {
		this.books = books;
	}
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
