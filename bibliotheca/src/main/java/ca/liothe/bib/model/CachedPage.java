package ca.liothe.bib.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CachedPage implements Serializable {
	private static final long serialVersionUID = 7313193519056055306L;
	private int begin;
	private int end;
	private int current;
	private int total;
	private List<String> isbns;
	
	public CachedPage(){
		isbns = new ArrayList<String>();
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

	public List<String> getIsbns() {
		return isbns;
	}

	public void setIsbns(List<String> isbns) {
		this.isbns = isbns;
	} 
}
