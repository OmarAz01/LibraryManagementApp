# LibraryManagementApp

A windows application built using Java, MySQL, and SceneBuilder. The applicaiton is designed to do all librarian tasks such as handling user data, storing records, keeping track of catalog, and more.

## The different accounts
1- Member
2- librarian
3- Admin

Members can login, register, or change their password from the login page. Once logged in they can view the catalog, reserve an item by double clicking on it, return an item, cancel a reserve, and view their history.

Librarians can login or change their password from the login page. Once logged in they can view the catalog, view return requests, view reserved items, add/remove/update items, issue an item, return an item, view item history, and view user history. 

Admins can login or change their password from the login page. Once logged in they have access to all librarian features with the addition of being able to add/remove/update user accounts.

## The Database
The database consists of three tables; accounts, material, and records.

The material table holds the columns material_id, title, author, material_type, genre, isbn, copies_tot, copies_avail

The accounts table holds the columns user_id, account_type, f_name, l_name, username, password, email 

The records table holds the columns ref_num, user_id, acc_type, material_id, material_type, material_title, material_author, ISBN, reserved_date, issued_date, due_date, returned_date

Notes:
- The first column of each table is the PK. 
- The password is encrypted before it is stored in the database.
- There are no foreign keys

#Snapshots


