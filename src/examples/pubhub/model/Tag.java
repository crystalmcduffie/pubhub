package examples.pubhub.model;


public class Tag {

	private String isbn13;			// International Standard Book Number, unique
	private String tag_name;

	public Tag(String isbn, String tag_name) {
		this.isbn13 = isbn;
		this.tag_name = tag_name;
	}
	
	
	// Default constructor
	public Tag() {
		this.isbn13 = null;
		this.tag_name = null;
	}
	
	public String getIsbn13() {
		return isbn13;
	}

	public void setIsbn13(String isbn13) {
		this.isbn13 = isbn13;
	}

	public String gettag_name() {
		return tag_name;
	}

	public void settag_name(String tag_name) {
		this.tag_name = tag_name;
	}

	
}
