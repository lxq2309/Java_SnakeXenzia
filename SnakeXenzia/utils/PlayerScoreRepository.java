package snakexenzia.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import snakexenzia.models.PlayerScore;

public class PlayerScoreRepository
{	
	static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/snakexenziadb";
	static final String USER = "root";
	static final String PASS = "";
	
	/*
	 
	 CREATE TABLE SCORE
	 (
		ID INT AUTO_INCREMENT,
	    NAME VARCHAR(50),
	    SCORE INT,
	    CONSTRAINT PK_SCORE PRIMARY KEY(ID)
	 );
	  	
	  	
	 */

	public static List<PlayerScore> readTop5HighestScore() throws SQLException, ClassNotFoundException
	{
		Connection conn = null;
		Statement stmt = null;

		List<PlayerScore> playerScores = new ArrayList<PlayerScore>();
		try
		{
			// Register JDBC driver
			Class.forName(DRIVER_CLASS);
			// Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			// Execute a query
			stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM Score ORDER BY score DESC LIMIT 5");
			// Extract data from result set
			while (rs.next())
			{
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int score = rs.getInt("score");
				playerScores.add(new PlayerScore(id, name, score));
			}
			rs.close();
		}
		catch (SQLException e)
		{
			throw e;
		}
		finally
		{
			// Close connection
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
		return playerScores;
	}

	public static void add(PlayerScore playerScore) throws ClassNotFoundException, SQLException
	{
		Connection conn = null;
		Statement stmt = null;

		try
		{
			// Register JDBC driver
			Class.forName(DRIVER_CLASS);
			// Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			// Execute a query
			stmt = conn.createStatement();

			stmt.executeUpdate("INSERT INTO Score(name, score) VALUES ('" + playerScore.getName() + "', "
					+ playerScore.getScore() + ")");
		}
		catch (SQLException e)
		{
			throw e;
		}
		finally
		{
			// Close connection
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}

	public static void delete(int playerScoreID) throws ClassNotFoundException, SQLException
	{
		Connection conn = null;
		Statement stmt = null;

		try
		{
			// Register JDBC driver
			Class.forName(DRIVER_CLASS);
			// Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			// Execute a query
			stmt = conn.createStatement();

			stmt.executeUpdate("DELETE FROM Score WHERE ID = " + playerScoreID);
		}
		catch (SQLException e)
		{
			throw e;
		}
		finally
		{
			// Close connection
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
	}

}
