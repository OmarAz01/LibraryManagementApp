# LibraryManagementApp

A windows application built using Java, JavaFX, and MySQL. The application is designed to do all librarian tasks such as handling user data, storing records, keeping track of catalog, and more.

## The different accounts
1- Member
2- librarian
3- Admin

Members can login, register, or change their password from the login page. Once logged in they can view the catalog, reserve an item by double clicking on it, return an item, cancel a reserve, and view their history.

Librarians can login or change their password from the login page. Once logged in they can view the catalog, view return requests, view reserved items, add/remove/update items, issue an item, return an item, view item history, and view user history. 

Admins can login or change their password from the login page. Once logged in they have access to all librarian features with the addition of being able to add/remove/update user accounts.

## The Database
The database consists of three tables: accounts, material, and records.

The material table holds the columns material_id, title, author, material_type, genre, isbn, copies_tot, copies_avail

The accounts table holds the columns user_id, account_type, f_name, l_name, username, password, email 

The records table holds the columns ref_num, user_id, acc_type, material_id, material_type, material_title, material_author, ISBN, reserved_date, issued_date, due_date, returned_date

Notes:
- The first column of each table is the PK. 
- The password is encrypted before it is stored in the database.
- There are no foreign keys

# How to run
- Download javafx-sdk-19 folder from this repo
- Download MySQL Installer here (Download the first option): https://dev.mysql.com/downloads/installer/
- Once it is installed run it and select the 'Custom' setup type
- From there you want to drop down 'MySQL servers' until you find 'MySQL server 8.0.31', select it and move it over to the right
- Do the same thing for 'MySQL Workbench' under 'Applications'
- When both of the products are moved over to the right click next
- Continue with the default options until you reach the 'Accounts and Roles' section, from there set the password to Root12345
- Execute the setup
- Launch MySQL Workbench and create a new schema called libraryappdb
- Copy and paste this into the schema:

CREATE TABLE `accounts` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `account_type` varchar(45) DEFAULT NULL,
  `f_name` varchar(45) DEFAULT NULL,
  `l_name` varchar(45) DEFAULT NULL,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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

- Once the tables are created you need to add an admin account by pasting the following ino the schema:
- INSERT INTO `libraryappdb`.`accounts` (`account_type`, `f_name`, `l_name`, `username`, `password`, `email`) VALUES ('Admin', 'admin', 'admin', 'admin', '21232f297a57a5a743894a0e4a801fc3', 'admin');
- This will create an admin account with username admin and password admin

- Finally, just download the jar file. Once downloaded open the javafx 19 folder that you downloaded earlier and copy the path to the lib folder.
- For simplicity put the jar file and javafx-sdk-19 folder in the downloads folder. Open command prompt and type 'cd downloads' then copy and paste the following
- java --module-path "Path to lib folder here" --add-modules javafx.controls,javafx.fxml,javafx.graphics -jar LMA.jar
- Replace the "Path ti lib folder here" with the path to the lib folder that is located inside the javafx-sdk-19 folder (Keep the quotation marks around the path)
- Press enter and the program should run

### Note
- I’ve included a CSV file in the repo that you can use to populate the library catalog. 
- To do this you need to download the csv file, open MySQL Workbench, click on the libraryappdb schema, right click the on Tables, select Table Data Import Wizard, 
import the CSV file click next, select the material table, fix the destination columns, and press next.


# Snapshots

## Main Page
![Main](https://user-images.githubusercontent.com/118571302/206301719-a6835bc5-75e1-4391-8420-81b5e8cba7f4.PNG)

## Login Page
![Login](https://user-images.githubusercontent.com/118571302/206301771-ff8cbaa4-4599-4809-97fd-7cf97f201d98.PNG)

## Members Register Page - Only members can register for an account
![Register](https://user-images.githubusercontent.com/118571302/206302111-40caa0d3-2896-43e7-9fc6-6026b5cbe662.PNG)

## Change Password Page
![ChangePass](https://user-images.githubusercontent.com/118571302/206302292-48f967a4-018a-4146-a69a-750358cf6de4.PNG)

## Members Home Page - Browse the catalog
![membersHome](https://user-images.githubusercontent.com/118571302/206302363-2d7c0371-f785-4426-bdaa-e8dde582aca3.PNG)

## Members Confirm Reservation Page - Reserve an item by double clicking on it
![membersConfirmReserve](https://user-images.githubusercontent.com/118571302/206302483-4776108d-cfb9-4277-9f11-47d510595825.PNG)

## Members Return Page - View all items eligible for a return
![membersReturnPG](https://user-images.githubusercontent.com/118571302/206302577-0ada2339-c769-48ed-8770-338659d5266f.PNG)

## Members Confirm Return Page - Return an item by double clicking on it
![membersReturn](https://user-images.githubusercontent.com/118571302/206302665-3ba74873-60b7-4efe-8bb1-c0e58690de7d.PNG)

## Members History Page - View all your history
![memberHistory](https://user-images.githubusercontent.com/118571302/206302752-e6fde745-fc26-4f04-b110-9be6bdd8c7a5.PNG)

## Members Cancel Reserve Page - Cancel an item from history page if it has not been issued out yet
![cancelReserve](https://user-images.githubusercontent.com/118571302/206302853-7893a93e-b985-497b-9ee3-89d971eabad4.PNG)

## Librarians/Admins Home Page - Browse the catalog
![librarians-adminHome](https://user-images.githubusercontent.com/118571302/206302979-21a96e65-8d35-48c6-bd4e-325b39f7b48e.PNG)

## Librarians/Admin History Page – Double-click on an item to view that items history
![libradminMatHistory](https://user-images.githubusercontent.com/118571302/206303166-31e27272-c337-4747-94b4-a7ee7c3b825a.PNG)

## Librarians/Admin Check Returns Page - View all items that have been requested to return
![librarian-adminReturns](https://user-images.githubusercontent.com/118571302/206303312-f689f2d1-3552-4070-9975-89395fab3722.PNG)

## Librarians/Admin Confirm Return Page – Double-click on an item to confirm the return
![librarian-adminConfirmReturn](https://user-images.githubusercontent.com/118571302/206303402-c17ebd65-de03-42a5-9e64-eb8b4beb6222.PNG)

## Librarians/Admin Current Reserved Page - View all items currently reserved by members
![librarian-adminReserved](https://user-images.githubusercontent.com/118571302/206303505-8875f50e-e384-4a5b-a730-0385f5a815c5.PNG)

## Librarians/Admin Issue/Remove Reserve Page – Double-click on an item to issue or remove the reserve
![librarian-adminIssue](https://user-images.githubusercontent.com/118571302/206303609-4e3bcd4b-11a0-4187-b7c5-3362c172d3cb.PNG)

## Librarians/Admin Add/Remove Page - View the catalog and add a new item, remove an item, or update an item
![add-remove](https://user-images.githubusercontent.com/118571302/206307429-3b5d4384-72b6-40b6-ac13-d9f5ad40f015.PNG)

## Librarians/Admin Update or Remove Choice Page – Double-click on an item and choose whether to update or remove
![update-remove](https://user-images.githubusercontent.com/118571302/206307560-3045f1f1-a59b-43b0-be90-a3e6c0a1a689.PNG)
![removeMat](https://user-images.githubusercontent.com/118571302/206307606-55d1845e-92d4-4612-a066-6950c74e26e4.PNG)
![updateMat](https://user-images.githubusercontent.com/118571302/206307620-828b64b7-90ea-4c46-b617-da20d7ef47b6.PNG)

## Librarians/Admin Add Material Page - Click on the 'Add' button in the top right to add a new item
![addMat](https://user-images.githubusercontent.com/118571302/206307708-cd8124c7-23d8-4788-8aec-172de283b4c7.PNG)

## Librarians/Admin Generate a Report Page - View all members and generate a history report on that member
![userReport](https://user-images.githubusercontent.com/118571302/206307899-9a5a2879-f53c-4ef6-8d33-ad39a5f57b5b.PNG)

## Librarians/Admin User Report Page – Double-click a member to view their history
![userHistory](https://user-images.githubusercontent.com/118571302/206307989-510a8d91-b037-4c1d-b8ee-e7528a298a06.PNG)

## Admin Accounts Page - View all accounts in the system and update, remove, or add a new account
![adminAccs](https://user-images.githubusercontent.com/118571302/206308111-e4474761-8e37-4a21-9a21-1a27fe3373d7.PNG)
![update-removeAccs](https://user-images.githubusercontent.com/118571302/206308125-be874357-ee41-4579-a27b-69623044cbc4.PNG)
![removeAcc](https://user-images.githubusercontent.com/118571302/206308149-1d91759c-1109-46a4-ab22-d152c49facde.PNG)
![updateAcc](https://user-images.githubusercontent.com/118571302/206308167-1b4cd75c-b6f7-4f2b-9722-0e8202f78d25.PNG)

## Admin Add Account Page - Add an account by clicking the 'Add' button in the top right
![addAcc](https://user-images.githubusercontent.com/118571302/206308232-cffb451b-3705-4b51-a4eb-54c64c164b41.PNG)
