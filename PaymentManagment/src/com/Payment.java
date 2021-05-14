package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class Payment {
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/paymentdb", "root", "");
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return con;
	}

	public String readPayment() {
		String output = ""; 
		 try
		 { 
			 Connection con = connect(); 
			 if (con == null) 
			 { 
			 return "Error while connecting to the database for reading."; 
			 } 
			 // Prepare the html table to be displayed
			 output = "<table border='1'><tr><th>ItemID</th> "
			 		+ "<th>quantity</th>"
			 		+ "<th>payer</th>"
			 		+ "<th>paymentMethod</th> "
			 		+ "<th>date</th> "
			 		+ "<th>amount</th> "
			 		+ "<th>Update</th><th>Remove</th></tr>"; 
			 String query = "select * from payment"; 
			 Statement stmt = con.createStatement(); 
			 ResultSet rs = stmt.executeQuery(query); 
			 // iterate through the rows in the result set
			 while (rs.next()) 
			 { 
				 String paymentID = Integer.toString(rs.getInt("paymentID")); 
				 String ItemID = rs.getString("ItemID"); 
				 String quantity = Integer.toString(rs.getInt("quantity")); 
				 String payer = rs.getString("payer"); 
				 String paymentMethod = rs.getString("paymentMethod"); 
				 String date = rs.getString("date");
				 String amount = Double.toString(rs.getDouble("amount")); 
				 // Add into the html table
				 output += "<tr><td><input id='hidPaymentIDUpdate' "
				 		+ "name='hidPaymentIDUpdate' "
				 		+ "type='hidden' value='" + paymentID
				 		+ "'>" + ItemID + "</td>"; 
				 output += "<td>" + quantity + "</td>"; 
				 output += "<td>" + payer + "</td>"; 
				 output += "<td>" + paymentMethod + "</td>";
				 output += "<td>" + date + "</td>";
				 output += "<td>" + amount + "</td>"; 
				 // buttons
				 output += "<td><input name='btnUpdate' type='button' value='Update' "
						 + "class='btnUpdate btn btn-secondary' data-paymentid='" + paymentID + "'></td>"
						 + "<td><input name='btnRemove' type='button' value='Remove' "
						 + "class='btnRemove btn btn-danger' data-paymentid='" + paymentID + "'></td></tr>";
			 } 
			 con.close(); 
			 // Complete the html table
			 output += "</table>"; 
		 } 
		 catch (Exception e) 
		 { 
			 output = "Error while reading the Payment."; 
			 System.err.println(e.getMessage()); 
		 } 
		 return output; 
	}

	public String insertPayment(String ItemID, String quantity, String payer, String paymentMethod, String date, String amount) {
		String output = ""; 
		 try
		 { 
			 Connection con = connect(); 
			 if (con == null) 
			 { 
				 return "Error while connecting to the database for inserting."; 
			 } 
			 // create a prepared statement
			String query = "insert into payment(paymentID,ItemID,quantity,payer,paymentMethod,date,amount) values (?, ?, ?, ?, ?, ?, ?);"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
			// binding values
			preparedStmt.setInt(1, 0); 
			preparedStmt.setString(2, ItemID); 
			preparedStmt.setInt(3, Integer.parseInt(quantity)); 
			preparedStmt.setString(4, payer); 
			preparedStmt.setString(5, paymentMethod); 
			preparedStmt.setString(6, date);
			preparedStmt.setDouble(7, Double.parseDouble(amount));  
			// execute the statement
			preparedStmt.execute(); 
			con.close(); 
			String newItems = readPayment(); 
			output = "{\"status\":\"success\", \"data\": \""
					+ "" +newItems + "\"}"; 
			} 
			catch (Exception e) 
			{ 
				System.err.println("ERORR : "+e.getMessage());
				output = "{\"status\":\"error\", \"data\":"
					 + "\"Error while inserting the Payment.\"}"; 
				 
			} 
			return output;
		 }

	public String updatePayment(String paymentID, String ItemID, String quantity, String payer, String paymentMethod, String date, String amount) {
		String output = ""; 
		 try
		 { 
				 Connection con = connect(); 
			 if (con == null) 
			 { 
				 return "Error while connecting to the database for updating."; 
			 } 
			 // create a prepared statement
			 String query = "UPDATE payment SET "
			 		+ "ItemID=?,quantity=?,payer=?,paymentMethod=?,date=?,amount=? WHERE paymentID=?"; 
			 PreparedStatement preparedStmt = con.prepareStatement(query); 
			 // binding values
			 preparedStmt.setString(1, ItemID); 
			 preparedStmt.setInt(2, Integer.parseInt(quantity));
			 preparedStmt.setString(3, payer); 
			 preparedStmt.setString(4, paymentMethod); 
			 preparedStmt.setString(5, date); 
			 preparedStmt.setDouble(6, Double.parseDouble(amount)); 
			 preparedStmt.setInt(7, Integer.parseInt(paymentID)); 
			// execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 String newItems = readPayment(); 
			 output = "{\"status\":\"success\", \"data\": \"" + 
			 newItems + "\"}"; 
		 } 
		 catch (Exception e) 
		 { 
			 output = "{\"status\":\"error\", \"data\": "
			 		+ "\"Error while updating the Payment.\"}"; 
			 System.err.println(e.getMessage()); 
		 } 
		 return output;
	}

	public String deletePayment(String paymentID) {
		String output = ""; 
		 try
		 { 
			 Connection con = connect(); 
			 if (con == null) 
			 { 
				 return "Error while connecting to the database for deleting."; 
			 } 
			 // create a prepared statement
			 String query = "delete from payment where paymentID=?"; 
			 PreparedStatement preparedStmt = con.prepareStatement(query); 
			 // binding values
			 preparedStmt.setInt(1, Integer.parseInt(paymentID)); 
			 // execute the statement
			 preparedStmt.execute(); 
			 con.close(); 
			 String newItems = readPayment(); 
			 output = "{\"status\":\"success\", \"data\": \"" + 
			 newItems + "\"}"; 
		 } 
		 catch (Exception e) 
		 { 
			 output = "{\"status\":\"error\", \"data\": "
			 		+ "\"Error while deleting the Payment.\"}"; 
			 System.err.println(e.getMessage()); 
		 } 
		 return output;
	}
}
