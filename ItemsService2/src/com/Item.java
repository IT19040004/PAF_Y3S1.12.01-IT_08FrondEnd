package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class Item {
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "root");
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return con;
	}

	public String readItems() {
		String output = ""; 
		 try
		 { 
			 Connection con = connect(); 
			 if (con == null) 
			 { 
			 return "Error while connecting to the database for reading."; 
			 } 
			 // Prepare the html table to be displayed
			 output = "<table border='1'><tr><th>Item Code</th> "
			 		+ "<th>Item Name</th>"
			 		+ "<th>Item Price</th>"
			 		+ "<th>Item Description</th> "
			 		+ "<th>Item Quantity</th> "
			 		+ "<th>Item Country</th> "
			 		+ "<th>Update</th><th>Remove</th></tr>"; 
			 String query = "select * from items"; 
			 Statement stmt = con.createStatement(); 
			 ResultSet rs = stmt.executeQuery(query); 
			 // iterate through the rows in the result set
			 while (rs.next()) 
			 { 
				 String itemID = Integer.toString(rs.getInt("itemID")); 
				 String itemCode = rs.getString("itemCode"); 
				 String itemName = rs.getString("itemName");
				 String itemPrice = Double.toString(rs.getDouble("itemPrice")); 
				 String itemDesc = rs.getString("itemDesc"); 
				 String itemQuantity = rs.getString("itemQuantity"); 
				 String itemCountry = rs.getString("itemCountry");
				 // Add into the html table
				 output += "<tr><td><input id='hidItemIDUpdate' "
				 		+ "name='hidItemIDUpdate' "
				 		+ "type='hidden' value='" + itemID
				 		+ "'>" + itemCode + "</td>"; 
				 output += "<td>" + itemName + "</td>"; 
				 output += "<td>" + itemPrice + "</td>"; 
				 output += "<td>" + itemDesc + "</td>";
				 output += "<td>" + itemQuantity + "</td>";
				 output += "<td>" + itemCountry + "</td>"; 
				 // buttons
				 output += "<td><input name='btnUpdate' type='button' value='Update' "
						 + "class='btnUpdate btn btn-secondary' data-itemid='" + itemID + "'></td>"
						 + "<td><input name='btnRemove' type='button' value='Remove' "
						 + "class='btnRemove btn btn-danger' data-itemid='" + itemID + "'></td></tr>";
			 } 
			 con.close(); 
			 // Complete the html table
			 output += "</table>"; 
		 } 
		 catch (Exception e) 
		 { 
			 output = "Error while reading the items."; 
			 System.err.println(e.getMessage()); 
		 } 
		 return output; 
	}

	public String insertItem(String code, String name, String price, String desc, String qua, String country) {
		String output = ""; 
		 try
		 { 
			 Connection con = connect(); 
			 if (con == null) 
			 { 
				 return "Error while connecting to the database for inserting."; 
			 } 
			 // create a prepared statement
			String query = "insert into items (itemID,itemCode,itemName,itemPrice,itemDesc,itemQuantity,itemCountry) values (?, ?, ?, ?, ?, ?, ?);"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
			// binding values
			preparedStmt.setInt(1, 0); 
			preparedStmt.setString(2, code); 
			preparedStmt.setString(3, name); 
			preparedStmt.setDouble(4, Double.parseDouble(price)); 
			preparedStmt.setString(5, desc); 
			preparedStmt.setString(6, qua); 
			preparedStmt.setString(7, country); 
			// execute the statement
			preparedStmt.execute(); 
			con.close(); 
			String newItems = readItems(); 
			output = "{\"status\":\"success\", \"data\": \""
					+ "" +newItems + "\"}"; 
			} 
			catch (Exception e) 
			{ 
				System.err.println("ERORR : "+e.getMessage());
				output = "{\"status\":\"error\", \"data\":"
					 + "\"Error while inserting the item.\"}"; 
				 
			} 
			return output;
		 }

	public String updateItem(String ID, String code, String name, String price, String desc, String qua, String country) {
		String output = ""; 
		 try
		 { 
				 Connection con = connect(); 
			 if (con == null) 
			 { 
				 return "Error while connecting to the database for updating."; 
			 } 
			 // create a prepared statement
			 String query = "UPDATE items SET "
			 		+ "itemCode=?,itemName=?,itemPrice=?,itemDesc=?,itemQuantity=?,itemCountry=? WHERE itemID=?"; 
			 PreparedStatement preparedStmt = con.prepareStatement(query); 
			 // binding values
			 preparedStmt.setString(1, code); 
			 preparedStmt.setString(2, name); 
			 preparedStmt.setDouble(3, Double.parseDouble(price)); 
			 preparedStmt.setString(4, desc); 
			 preparedStmt.setString(5, qua); 
			 preparedStmt.setString(6, country); 
			 preparedStmt.setInt(7, Integer.parseInt(ID)); 
			// execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 String newItems = readItems(); 
			 output = "{\"status\":\"success\", \"data\": \"" + 
			 newItems + "\"}"; 
		 } 
		 catch (Exception e) 
		 { 
			 output = "{\"status\":\"error\", \"data\": "
			 		+ "\"Error while updating the item.\"}"; 
			 System.err.println(e.getMessage()); 
		 } 
		 return output;
	}

	public String deleteItem(String itemID) {
		String output = ""; 
		 try
		 { 
			 Connection con = connect(); 
			 if (con == null) 
			 { 
				 return "Error while connecting to the database for deleting."; 
			 } 
			 // create a prepared statement
			 String query = "delete from items where itemID=?"; 
			 PreparedStatement preparedStmt = con.prepareStatement(query); 
			 // binding values
			 preparedStmt.setInt(1, Integer.parseInt(itemID)); 
			 // execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 String newItems = readItems(); 
			 output = "{\"status\":\"success\", \"data\": \"" + 
			 newItems + "\"}"; 
		 } 
		 catch (Exception e) 
		 { 
			 output = "{\"status\":\"error\", \"data\": "
			 		+ "\"Error while deleting the item.\"}"; 
			 System.err.println(e.getMessage()); 
		 } 
		 return output;
	}
}
