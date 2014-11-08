package ca.liothe.bib.pojo;

import java.util.ArrayList;
import java.util.List;

public class Genre {
	private String name;
	private List<String> subGenres;
	
	public Genre(){
		subGenres = new ArrayList<String>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getSubGenres() {
		return subGenres;
	}
	public void setSubGenres(List<String> shelves) {
		this.subGenres = shelves;
	}
}
