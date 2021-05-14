package com;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Pattern;
import java.sql.PreparedStatement;

public class Register {
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/registerdb", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String readRegister() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Name</th><th>Email</th>" + "<th>Password</th>"
					+ "<th>Renter Password</th>" + "<th>Update</th><th>Remove</th></tr>";

			String query = "select * from register";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String registerid = Integer.toString(rs.getInt("registerid"));
				String name = rs.getString("name");
				String email = rs.getString("email");
				String password = rs.getString("password");
				String repassword = rs.getString("repassword");
				// Add into the html table
				output += "<tr><td><input id='hidRegisterIDUpdate' " + "name='hidRegisterIDUpdate' "
						+ "type='hidden' value='" + registerid + "'>" + name + "</td>";
				output += "<td>" + email + "</td>";
				output += "<td>" + password + "</td>";
				output += "<td>" + repassword + "</td>";
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' "
						+ "class='btnUpdate btn btn-secondary' data-registerid='" + registerid + "'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' "
						+ "class='btnRemove btn btn-danger' data-registerid='" + registerid + "'></td></tr>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the user.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String insertRegister(String name, String email, String password, String repassword) {
		String output = "";
		try {

			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into register(registerid,name,email,password,repassword)"
					+ " values (?,?, ?, ?, ?);";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, name);
			preparedStmt.setString(3, email);
			preparedStmt.setString(4, password);
			preparedStmt.setString(5, repassword);
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newItems = readRegister();
			output = "{\"status\":\"success\", \"data\": \"" + "" + newItems + "\"}";

		} catch (Exception e) {
			System.out.println("ERORR : " + e.getMessage());
			output = "{\"status\":\"error\", \"data\":" + "\"Error while inserting the user.\"}";

		}
		return output;
	}

	public String updateRegister(String registerid, String name, String email, String password, String repassword) {
		String output = "";
		try {

			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE register SET name=?,email=?,password=?,repassword=? WHERE registerid=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, name);
			preparedStmt.setString(2, email);
			preparedStmt.setString(3, password);
			preparedStmt.setString(4, repassword);
			preparedStmt.setInt(5, Integer.parseInt(registerid));
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newItems = readRegister();
			output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";

		}

		catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": " + "\"Error while updating the .user\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public static boolean isValid(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";

		Pattern pat = Pattern.compile(emailRegex);
		if (email == null)
			return false;
		return pat.matcher(email).matches();
	}

	public String deleteRegister(String registerid) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from register where registerid=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(registerid));
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newItems = readRegister();
			output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": " + "\"Error while deleting the user.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
}
