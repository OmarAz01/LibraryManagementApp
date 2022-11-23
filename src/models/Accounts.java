package models;

// Model class for accounts in db

public class Accounts {

	private int user_id;
	private String account_type, f_name, l_name, username, password, email;

	public Accounts(Integer user_id, String account_type, String f_name, String l_name, String username, String password, String email) {

		this.user_id = user_id;
		this.account_type = account_type;
		this.f_name = f_name;
		this.l_name = l_name;
		this.username = username;
		this.password = password;
		this.email = email;

	}

	public Accounts(Integer user_id, String account_type, String f_name, String l_name, String username, String email) {

		this.user_id = user_id;
		this.f_name = f_name;
		this.l_name = l_name;
		this.username = username;
		this.email = email;
		this.account_type = account_type;

	}

	public Accounts(String account_type, String f_name, String l_name, String username, String password, String email) {

		this.f_name = f_name;
		this.l_name = l_name;
		this.username = username;
		this.email = email;
		this.account_type = account_type;

	}

	public Accounts(Integer user_id, String username, String f_name, String l_name, String email) {

		this.f_name = f_name;
		this.l_name = l_name;
		this.username = username;
		this.email = email;
		this.user_id = user_id;
		this.email = email;

	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getAccount_type() {
		return account_type;
	}

	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}

	public String getF_name() {
		return f_name;
	}

	public void setF_name(String f_name) {
		this.f_name = f_name;
	}

	public String getL_name() {
		return l_name;
	}

	public void setL_name(String l_name) {
		this.l_name = l_name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
