package com.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

	private Connection connection = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public Connection connectTODB() throws Exception {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			connection = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/DonutQ?" + "user=sqluser&password=sqluserpw");

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return connection;
	}

	public void disconnect() {
		if (connection != null) {
			try {
				connection.close();
				connection = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
