package ca.liothe.bib.model;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Book implements Serializable {
	private static final long serialVersionUID = 1512119359482058481L;
	@NotEmpty
	@Min(1)
	private String isbn;
	@NotEmpty
	private String title;
	@NotEmpty
	private String author;
	@NotEmpty
	private String genreType;
	@NotEmpty
	private String bookcase;
	@NotEmpty
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
