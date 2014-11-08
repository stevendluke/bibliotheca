package ca.liothe.bib.pojo;

import java.util.List;

public class GenreType {
	private String name;
	private List<Genre> genres;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Genre> getGenres() {
		return genres;
	}
	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}
}
