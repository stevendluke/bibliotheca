package ca.liothe.bib.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ca.liothe.bib.data.service.BookDAO;
import ca.liothe.bib.exceptions.BookDetailsNotFound;
import ca.liothe.bib.model.Book;
import ca.liothe.bib.pojo.Bookcase;
import ca.liothe.bib.pojo.GenreType;

/**
 * Handles requests for the application home page.
 */
@Controller 
public class BibliothecaController {
	 
	//private static final Logger logger = LoggerFactory.getLogger(BibliothecaController.class);
	private static List<GenreType> genres;
	private static List<Bookcase> bookcases;
	
	@Autowired
	private BookDAO bookDao;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(@RequestParam(value="page", defaultValue="0") int pageNumber, Model model) {
		model.addAttribute("page", bookDao.findAllByPage(pageNumber));
		
		return "home";
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search(@RequestParam(value="s") String search, @RequestParam(value="page", defaultValue="0") int pageNumber, Model model) {
		model.addAttribute("page", bookDao.search(search, pageNumber));
		model.addAttribute("search", search);
		
		return "search";
	}
	
	@RequestMapping(value = "/nuke", method = RequestMethod.GET)
	public @ResponseBody String search() {
		try {
			bookDao.nuke();
			return "OK";
		} catch (BookDetailsNotFound e) {
			e.printStackTrace();
			return "NOT OK";
		}
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addForm(Model model, HttpSession session) {
		Boolean validated = (Boolean) session.getAttribute("validated");
		if(validated != null && validated){
			model.addAttribute("genres", getGenres());
			model.addAttribute("bookcases", getBookcases());
			model.addAttribute("book", new Book());
			return "add";
		}
		else{
			return "redirect:/";
		}
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@Valid Book book, BindingResult result, Model model) {
		if(!result.hasErrors()){
			
			boolean success = bookDao.create(book);
			
			if(success){
				model.addAttribute("bookTitle", book.getTitle());
				model.addAttribute("statusResult", "success");
				return "redirect:/add";
			}
			else{
				model.addAttribute("bookTitle", book.getTitle());
				model.addAttribute("statusResult", "failed");
				return "add";
			}
		}
		else{
			model.addAttribute("genres", getGenres());
			model.addAttribute("bookcases", getBookcases());
			return "add";
		}
	}
	
	@RequestMapping(value = "/edit/{isbn}", method = RequestMethod.GET)
	public String editForm(@PathVariable String isbn, Model model, HttpSession session) {
		Boolean validated = (Boolean) session.getAttribute("validated");
		if(validated != null && validated){
			model.addAttribute("genres", getGenres());
			model.addAttribute("bookcases", getBookcases());
			model.addAttribute("book", bookDao.findByIsbn(isbn));
			return "edit";
		}
		else{
			return "redirect:/";
		}
	}
	
	@RequestMapping(value = "/edit/{isbn}", method = RequestMethod.POST)
	public String edit(@Valid Book book, @PathVariable String isbn, BindingResult result, Model model) {
		if(!result.hasErrors()){
			try{
				bookDao.update(book);
				
				model.addAttribute("bookTitle", book.getTitle());
				model.addAttribute("statusResult", "success");
				return "redirect:/edit/" + isbn;
			}
			catch(BookDetailsNotFound e){
				model.addAttribute("genres", getGenres());
				model.addAttribute("bookcases", getBookcases());
				model.addAttribute("bookTitle", book.getTitle());
				model.addAttribute("statusResult", "failed");
				return "edit";
			}
		}
		else{
			model.addAttribute("genres", getGenres());
			model.addAttribute("bookcases", getBookcases());
			return "edit";
		}
	}
	
	@RequestMapping(value = "/delete/{isbn}", method = RequestMethod.GET)
	public String delete(@PathVariable String isbn, Model model, HttpSession session) {
		Boolean validated = (Boolean) session.getAttribute("validated");
		if(validated != null && validated){
			Book book = bookDao.findByIsbn(isbn);
			
			try{
				bookDao.delete(isbn);
				model.addAttribute("deleteResult", "success");
			}
			catch(BookDetailsNotFound e){
				model.addAttribute("deleteResult", "failed");
			}

			model.addAttribute("bookTitle", book.getTitle());
				
			return "deleted";
		}
		else{
			return "redirect:/";
		}
	}
	
	private List<GenreType> getGenres(){
		if(genres == null){
			genres = new ArrayList<GenreType>();
			
			GenreType fiction = new GenreType();
			fiction.setName("Fiction");
			
			GenreType nonFiction = new GenreType();
			nonFiction.setName("Non-Fiction");
			
			genres.add(fiction);
			genres.add(nonFiction);
		}
		
		return genres;
	}
	
	private List<Bookcase> getBookcases(){
		if(bookcases == null){
			bookcases = new ArrayList<Bookcase>();
			
			Bookcase canada = new Bookcase();
			canada.setName("Canada");
			canada.getShelves().add("Charlottetown");
			canada.getShelves().add("Cypress Hills");
			canada.getShelves().add("Halifax");
			canada.getShelves().add("Victoria");
			canada.getShelves().add("Banff");
			bookcases.add(canada);
			
			Bookcase usa = new Bookcase();
			usa.setName("USA");
			usa.getShelves().add("Hawaii");
			usa.getShelves().add("Georgia");
			usa.getShelves().add("California");
			usa.getShelves().add("Florida");
			usa.getShelves().add("Montana");
			bookcases.add(usa);
			
			Bookcase ireland = new Bookcase();
			ireland.setName("Ireland");
			ireland.getShelves().add("Galway");
			ireland.getShelves().add("Connemara");
			ireland.getShelves().add("Limerick");
			ireland.getShelves().add("Kilkenny");
			ireland.getShelves().add("Cork");
			bookcases.add(ireland);
			
			Bookcase newZealand = new Bookcase();
			newZealand.setName("Christchurch");
			newZealand.getShelves().add("Rarotonga");
			newZealand.getShelves().add("Bay of Islands");
			newZealand.getShelves().add("Rotorua");
			bookcases.add(newZealand);
			
			Bookcase england = new Bookcase();
			england.setName("England");
			england.getShelves().add("London");
			england.getShelves().add("Windsor");
			england.getShelves().add("Bath");
			england.getShelves().add("Brighton");
			england.getShelves().add("Maidenhead");
			bookcases.add(england);
			
			Bookcase greece = new Bookcase();
			greece.setName("Greece");
			greece.getShelves().add("Athens");
			greece.getShelves().add("Delphi");
			greece.getShelves().add("Rhodes");
			greece.getShelves().add("Crete");
			greece.getShelves().add("Santorini");
			bookcases.add(greece);
			
			Bookcase turkey = new Bookcase();
			turkey.setName("Turkey");
			turkey.getShelves().add("Pamukkale");
			turkey.getShelves().add("Kusadasi");
			turkey.getShelves().add("Ephesus");
			turkey.getShelves().add("Istanbul");
			turkey.getShelves().add("Gallipoli");
			bookcases.add(turkey);
			
			Bookcase egypt = new Bookcase();
			egypt.setName("Egypt");
			egypt.getShelves().add("Alexandria");
			egypt.getShelves().add("Luxor");
			egypt.getShelves().add("Karnak");
			egypt.getShelves().add("Cairo");
			egypt.getShelves().add("Giza");
			egypt.getShelves().add("Vally of the Kings");
			bookcases.add(egypt);
			
			Bookcase jordan = new Bookcase();
			jordan.setName("Jordan");
			jordan.getShelves().add("Petra");
			jordan.getShelves().add("Jerash");
			jordan.getShelves().add("Wadi Musa");
			jordan.getShelves().add("Wadi Rum");
			jordan.getShelves().add("Aqaba");
			bookcases.add(jordan);
			
			Bookcase norway = new Bookcase();
			norway.setName("Norway");
			norway.getShelves().add("Oslo");
			norway.getShelves().add("Nordfjord");
			norway.getShelves().add("Senja");
			norway.getShelves().add("Lyngen");
			norway.getShelves().add("Sotra");
			bookcases.add(norway);
			
			Bookcase peru = new Bookcase();
			peru.setName("Peru");
			peru.getShelves().add("Lima");
			peru.getShelves().add("Machu Picchu");
			peru.getShelves().add("Cusco");
			peru.getShelves().add("Nazca Desert");
			peru.getShelves().add("Lake Titicaca");
			bookcases.add(peru);
			
			Bookcase ecuador = new Bookcase();
			ecuador.setName("Ecuador");
			ecuador.getShelves().add("Quito");
			ecuador.getShelves().add("Galapagos");
			ecuador.getShelves().add("Cotopaxi");
			ecuador.getShelves().add("Banos");
			ecuador.getShelves().add("Tena");
			bookcases.add(ecuador);
			
			Bookcase japan = new Bookcase();
			japan.setName("Japan");
			japan.getShelves().add("Osaka");
			japan.getShelves().add("Kyoto");
			japan.getShelves().add("Okinawa");
			japan.getShelves().add("Tokyo");
			bookcases.add(japan);
			
			Bookcase croatia = new Bookcase();
			croatia.setName("Croatia");
			croatia.getShelves().add("Gospic");
			croatia.getShelves().add("Split");
			croatia.getShelves().add("Dubrovnik");
			croatia.getShelves().add("Bosanska Gradiska");
			croatia.getShelves().add("Sisak");
			bookcases.add(croatia);
		}
		
		return bookcases;
	}
	
}