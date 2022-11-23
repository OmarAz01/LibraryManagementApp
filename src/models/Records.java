package models;

// Model class for records in db
public class Records {

	private Material material;
	private String ref_num;
	private String acc_type, material_type, reserved_date, issued_date, due_date, returned_date;
	private int user_id;
	private String material_title, material_author;
	private int material_id;
	private String isbn;

	public Records(String ref_num, Integer user_id, String acc_type, Integer material_id, String material_type, String material_title, String material_author, String isbn, String reserved_date, String issued_date, String due_date,
			String returned_date) {

		this.ref_num = ref_num;
		this.material_id = material_id;
		this.user_id = user_id;
		this.acc_type = acc_type;
		this.material_type = material_type;
		this.reserved_date = reserved_date;
		this.issued_date = issued_date;
		this.due_date = due_date;
		this.returned_date = returned_date;
		this.material_title = material_title;
		this.material_author = material_author;
		this.isbn = isbn;

	}

	public Records(Material material, String ref_num, Integer user_id) {

		this.material = material;
		this.ref_num = ref_num;
		this.user_id = user_id;

	}

	public String getIsbn() {
		return isbn;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public String getMaterial_title() {
		return material_title;
	}

	public void setMaterial_title(String material_title) {
		this.material_title = material_title;
	}

	public String getMaterial_author() {
		return material_author;
	}

	public void setMaterial_author(String material_author) {
		this.material_author = material_author;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public void setTitle(String title) {
		this.material_title = title;
	}

	public String getAuthor() {
		return material_author;
	}

	public void setAuthor(String author) {
		this.material_author = author;
	}

	public int getMaterial_id() {
		return material_id;
	}

	public void setMaterial_id(int material_id) {
		this.material_id = material_id;
	}

	public String getRef_num() {
		return ref_num;
	}

	public void setRef_num(String ref_num) {
		this.ref_num = ref_num;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getAcc_type() {
		return acc_type;
	}

	public void setAcc_type(String acc_type) {
		this.acc_type = acc_type;
	}

	public String getMaterial_type() {
		return material_type;
	}

	public void setMaterial_type(String material_type) {
		this.material_type = material_type;
	}

	public String getReserved_date() {
		return reserved_date;
	}

	public void setReserved_date(String reserved_date) {
		this.reserved_date = reserved_date;
	}

	public String getIssued_date() {
		return issued_date;
	}

	public void setIssued_date(String issued_date) {
		this.issued_date = issued_date;
	}

	public String getDue_date() {
		return due_date;
	}

	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}

	public String getReturned_date() {
		return returned_date;
	}

	public void setReturned_date(String returned_date) {
		this.returned_date = returned_date;
	}

}
