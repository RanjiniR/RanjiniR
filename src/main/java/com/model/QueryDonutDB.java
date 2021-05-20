package com.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class QueryDonutDB {

	DBConnection dbConnection = new DBConnection();
	private Statement statement = null;
	private ResultSet resultSet = null;
	private PreparedStatement preparedStmt = null;
	Connection connect = null;
	private String orderColumns = "ORDER_ID, CUSTOMER_ID , QUANTITY , START_TIME , STATUS";
	private String orderTable = "ORDERS";

	public ResultSet getAllActiveCustomers() {

		try {
			connect = dbConnection.connectTODB();

			statement = connect.createStatement();
			resultSet = statement.executeQuery("SELECT " + orderColumns + "from " + orderTable + " where STATUS = 0");

			return resultSet;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnections();
		}
		return resultSet;
	}

	public boolean insertOrder(Integer customerId, Integer quantity) {

		try {
			connect = dbConnection.connectTODB();

			int orderId = fetchMaxOrderID(connect) + 1;

			preparedStmt = connect
					.prepareStatement("INSERT into " + orderTable + "(" + orderColumns + ") values (?,?,?,?,?,?)");
			preparedStmt.setInt(1, orderId);
			preparedStmt.setInt(2, customerId);
			preparedStmt.setInt(3, quantity);
			preparedStmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
			preparedStmt.setInt(5, 0); // Set as Active

			return preparedStmt.execute();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			closeConnections();
		}
		return false;
	}

	public ResultSet getActiveOrdersForCust(Integer customerId) {
		try {
			connect = dbConnection.connectTODB();
			statement = connect.createStatement();
			resultSet = statement
					.executeQuery("SELECT * from " + orderTable + " where CUSTOMER_ID =" + customerId);

			return resultSet;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnections();
		}
		return resultSet;
	}

	public boolean cancelOrderforCust(Integer customerId) {
		try {
			connect = dbConnection.connectTODB();
			preparedStmt = connect.prepareStatement("UPDATE " + orderTable + "set STATUS = 2 where CUSTOMER_ID = ?");

			preparedStmt.setInt(1, customerId);

			preparedStmt.execute();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConnections();
		}
		return false;
	}

	public int fetchMaxOrderID(Connection connect) {

		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery("SELECT  MAX(ORDER_ID) from " + orderTable);
			return resultSet.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;

	}

	public void closeConnections() {
		if (resultSet != null)
			try {
				resultSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if (statement != null)
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if (connect != null)
			dbConnection.disconnect();
	}

}
