package models;

// Model class for material in db

public class Material {

	private int material_id;
	private String isbn;
	private int copies_tot;
	private int copies_avail;
	private String title;
	private String author;
	private String material_type;
	private String genre;

	public Material(Integer material_id, String title, String author, String material_type, String genre, String isbn, Integer copies_tot, Integer copies_avail) {

		this.material_id = material_id;
		this.title = title;
		this.author = author;
		this.material_type = material_type;
		this.genre = genre;
		this.isbn = isbn;
		this.copies_tot = copies_tot;
		this.copies_avail = copies_avail;

	}

	public Material(String title, String author, String material_type, String genre, String isbn, Integer copies_tot) {

		this.title = title;
		this.author = author;
		this.material_type = material_type;
		this.genre = genre;
		this.isbn = isbn;
		this.copies_tot = copies_tot;

	}

	public int getMaterial_id() {
		return material_id;
	}

	public void setMaterial_id(int material_id) {
		this.material_id = material_id;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public int getCopies_tot() {
		return copies_tot;
	}

	public void setCopies_tot(int copies_tot) {
		this.copies_tot = copies_tot;
	}

	public int getCopies_avail() {
		return copies_avail;
	}

	public void setCopies_avail(int copies_avail) {
		this.copies_avail = copies_avail;
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

	public String getMaterial_type() {
		return material_type;
	}

	public void setMaterial_type(String material_type) {
		this.material_type = material_type;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

}
