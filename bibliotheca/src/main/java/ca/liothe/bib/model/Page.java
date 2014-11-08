package ca.liothe.bib.model;

import java.util.ArrayList;
import java.util.List;

public class Page {
	private int begin;
	private int end;
	private int current;
	private int total;
	private List<Book> books;
	
	public Page(){
		books = new ArrayList<Book>();
	}
	
	public int getBegin() {
		return begin;
	}
	public void setBegin(int begin) {
		this.begin = begin;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public int getCurrent() {
		return current;
	}
	public void setCurrent(int current) {
		this.current = current;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<Book> getBooks() {
		return books;
	}
	public void setBooks(List<Book> books) {
		this.books = books;
	} 
}
