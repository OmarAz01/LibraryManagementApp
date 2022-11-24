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

# How to run
- Download MySQL Workbench 8.0
- Create a database named libraryappdb with root as the username and Root12345 as the password
- Copy paste this into the database:

CREATE TABLE `accounts` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `account_type` varchar(45) DEFAULT NULL,
  `f_name` varchar(45) DEFAULT NULL,
  `l_name` varchar(45) DEFAULT NULL,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `material` (
  `material_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(250) DEFAULT NULL,
  `author` varchar(250) DEFAULT NULL,
  `material_type` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `genre` varchar(250) DEFAULT NULL,
  `isbn` varchar(13) DEFAULT NULL,
  `copies_tot` int DEFAULT NULL,
  `copies_avail` int DEFAULT NULL,
  PRIMARY KEY (`material_id`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `records` (
  `ref_num` varchar(125) NOT NULL,
  `user_id` int DEFAULT NULL,
  `acc_type` varchar(10) DEFAULT NULL,
  `material_id` int DEFAULT NULL,
  `material_type` varchar(25) DEFAULT NULL,
  `material_title` varchar(125) DEFAULT NULL,
  `material_author` varchar(125) DEFAULT NULL,
  `ISBN` varchar(25) DEFAULT NULL,
  `reserved_date` varchar(10) DEFAULT NULL,
  `issued_date` varchar(10) DEFAULT NULL,
  `due_date` varchar(10) DEFAULT NULL,
  `returned_date` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`ref_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

- Once the tables are created you would need to add a admin account in the database using the username admin and password 21232f297a57a5a743894a0e4a801fc3 which is the word "admin" encrypted. The rest of the columns can be anything.

- Finally just download the jar file and the LMA_Lib folder. Once downloaded extract the folder and copy the path to the folder.
- Open command prompt and type --module-path "Path to LMA_lib file here" --add-modules javafx.controls,javafx.fxml,mysql-connector-j-8.0.31
- Press enter and the program should run

# Snapshots

## Main Screen
![mainScreen](https://user-images.githubusercontent.com/118571302/203827055-c699c462-d18b-4c59-ae14-62efe94433e3.PNG)

## Login Screen
![loginScreen](https://user-images.githubusercontent.com/118571302/203827099-3ee7e0fb-7384-4db9-93c7-c432cc04d32f.PNG)

## Members Home Page - Double click on item to reserve
![membersReserve](https://user-images.githubusercontent.com/118571302/203827241-231d6532-6ecd-4037-a62b-bb085aff9f0b.PNG)

## Members Return Page - Once an item is issued you can return by double clicking on it 
![membersReturn](https://user-images.githubusercontent.com/118571302/203827331-6889792a-b5e8-449e-8e90-d2d26af19d17.PNG)

## Members History Page - View your history and cancel a reserve if it has not been issued yet
![membersHistory](https://user-images.githubusercontent.com/118571302/203827434-7de81158-f6db-4aba-9b1f-845e3cbd7f79.PNG)

## Librarian/Admin Home Page - View item history by double clicking on it
![librarian_adminHP](https://user-images.githubusercontent.com/118571302/203827550-995e2f34-f049-4a02-935f-e3940fd0ffe4.PNG)

## Librarian/Admin Returns Page - View requested returns and return them by double clicking
![adminReturns](https://user-images.githubusercontent.com/118571302/203827638-b4261a60-b939-4dc5-8e69-fe63a783937a.PNG)

## Librarian/Admin Report Page - View history of any member account by double clicking
![report](https://user-images.githubusercontent.com/118571302/203827750-42951642-c57b-4315-953a-306c91db858f.PNG)

## Admin/Librarian Add Page - Add/Remove/Update any material
![addremove](https://user-images.githubusercontent.com/118571302/203828345-fe88c7af-6431-4bf9-8479-4b64fd431993.PNG)


## Admin Accounts Page - View/Update/Remove/Add any account (member, admnin, librarian)
![adminacc](https://user-images.githubusercontent.com/118571302/203827876-fd5821d0-b407-49be-b5e4-74c1b7cc7778.PNG)











