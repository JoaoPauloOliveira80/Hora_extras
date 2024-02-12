package application.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

	static Connection conn;
	 
	public static Connection create() {

		try {
			// load the driver
			Class.forName("com.mysql.jdbc.Driver");

			// create the connection
			String user = "root";
			String password = "vertrigo";
			String url = "jdbc:mysql://localhost:3306/jornadadiaria?autoReconnect=true&useSSL=false";

			try {
				conn = DriverManager.getConnection(url, user, password);
			    //System.out.println("Connected to Database.");
				return conn;
			} catch (SQLException e) {
				throw new RuntimeException("Cannot connect to database", e);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return conn;

	}
}




