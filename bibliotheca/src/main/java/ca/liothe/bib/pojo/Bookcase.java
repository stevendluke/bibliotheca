package ca.liothe.bib.pojo;

import java.util.ArrayList;
import java.util.List;

public class Bookcase {
	private String name;
	private List<String> shelves;
	
	public Bookcase(){
		shelves = new ArrayList<String>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getShelves() {
		return shelves;
	}
	public void setShelves(List<String> shelves) {
		this.shelves = shelves;
	}
}
