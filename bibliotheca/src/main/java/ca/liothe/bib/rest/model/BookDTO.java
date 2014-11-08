package ca.liothe.bib.rest.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="book")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class BookDTO implements Serializable {
	private static final long serialVersionUID = -658733976742476674L;
	
	private String isbn;
	private String title;
	private String author;
	private String genreType;
	private String genre;
	private String subGenre;
	private String bookcase;
	private String bookshelf;
	
	public String getGenreType() {
		return genreType;
	}
	public void setGenreType(String genreType) {
		this.genreType = genreType;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getSubGenre() {
		return subGenre;
	}
	public void setSubGenre(String subGenre) {
		this.subGenre = subGenre;
	}
	public String getBookcase() {
		return bookcase;
	}
	public void setBookcase(String bookcase) {
		this.bookcase = bookcase;
	}
	public String getBookshelf() {
		return bookshelf;
	}
	public void setBookshelf(String bookshelf) {
		this.bookshelf = bookshelf;
	}
}
