package examples.pubhub.model;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import examples.pubhub.dao.BookDAO;
//import examples.pubhub.dao.BookDAOImpl;
import examples.pubhub.dao.TagDAO;
//import examples.pubhub.dao.TagDAOImpl;
import examples.pubhub.utilities.DAOUtilities;

public class TestDAO {
	
	public static void main (String[] args) {
		
	BookDAO dao1 = DAOUtilities.getBookDAO();
	//BookDAO dao1 = new BookDAOImpl();
	List<Book> list1 = dao1.getAllBooks();

	for (int i = 0; i < list1.size(); i++){
	    Book t = list1.get(i);
	    System.out.println(t.getIsbn13() + ", " + t.getTitle() + ", " + t.getAuthor());
	}
	  
	TagDAO dao2 = DAOUtilities.getTagDAO();
	//TagDAO dao2 = new TagDAOImpl();
    List<Tag> list2 = dao2.getAllTagsForISBN("1111111111111");

    for (int i = 0; i < list2.size(); i++){
      Tag t = list2.get(i);
      System.out.println(t.getIsbn13() + ", " + t.gettag_name());
    }
    
    List<Tag> list3 = dao2.getTagsByName("adventure");
    
    for(int i = 0; i < list3.size(); i++) {
    	Tag t = list3.get(i);
    	System.out.println(t.getIsbn13() + ", " + t.gettag_name());
    }
    
	List<Tag> taglist = dao2.getTagsByName("adventure");
	List<Book> booklist = new ArrayList<Book>();
	
	for(int i=0; i< taglist.size(); i++) {
		//Make a list of books all associated with tag_name
		//booklist.add(dao1.getBookByISBN(taglist.get(i).getIsbn13()));
		String tagisbn = taglist.get(i).getIsbn13();
		Book b = dao1.getBookByISBN(tagisbn);
		booklist.add(b);
		System.out.println(booklist.get(i).getIsbn13());
	}
	
	String newisbn = "1234123412341";
	boolean condition = false;
	
	Book newbook = new Book();
	newbook.setIsbn13(newisbn);
	newbook.setAuthor("Tom Featherland");
	newbook.setPrice(125);
	newbook.setTitle("Silly Time Adventures");
	
	condition = dao1.addBook(newbook);
	System.out.println("Add book = " +condition);
	
	Tag newtag = new Tag();
	newtag.setIsbn13("1234123412341");
	newtag.settag_name("Humor");
	
	condition= dao2.addTag(newtag);
	System.out.println("Add tag = " + condition);
	
	newtag.setIsbn13("1111111111111");
	newtag.settag_name("Adventure");
	condition = dao2.updateTag(newtag);
	System.out.println("Update tag = " + condition);
	
	
	
	//condition = dao2.deleteTag(newtag);
	//System.out.println("Delete tag = " + condition);
	
	condition = dao1.deleteBookByISBN(newisbn);
	System.out.println("Delete book = " +condition);
	
//	Book b = dao1.getBookByISBN(newisbn);
//	b.setPrice(125);
//	condition = dao1.updateBook(b);
	
	
	
	
  }
}
