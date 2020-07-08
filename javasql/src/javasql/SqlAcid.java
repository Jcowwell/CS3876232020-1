package javasql;

import java.sql.SQLException;

public class SqlAcid {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
		final String driver = "org.postgresql.Driver";
		final String servername = " "; //Enter your server name (database name)
		final String username = " "; //Enter your username
		final String password = " "; //Enter your password
		
		Connection conn = null;
		DirectAccess dao = null;

		try {
			conn = new Connection(driver, servername, username , password); // Connect to the database

			conn.startConnection();

			dao = new DirectAccess(conn.createStatement()); // To access the postgresSQL-server and its results

			// Creating tables
			TableHelper.createTables(dao.getStatement());

			// Inserting data into the tables
			TableHelper.insertData(dao.getStatement());

			conn.setAutoCommit(false); // For atomicity

			conn.setTransactionIsolation(); // For isolation

			System.out.println("Tables before transaction: ");
			TableHelper.queryPrint(dao.getStatement()); // Query tables and print, before deletion

			// The depot d1 is deleted from Depot and Stock.
			dao.delete("DELETE FROM Stock WHERE depo_id='d1'"); //consistency
			dao.delete("DELETE FROM Depot WHERE depo_id='d1'");
			System.out.println("Depot 'd1' deleted.");
			System.out.println();

			// Query tables and print, after deletion
			System.out.println("Tables after transaction: ");
			TableHelper.queryPrint(dao.getStatement());
			conn.commit(); // for atomicity
			System.out.println("Transaction committed.");

		} catch (Exception e) {
			System.out.println("catch Exception: " + e);
			e.printStackTrace();
			conn.rollback(); // For atomicity
			System.out.println("Transaction rolled back.");
			System.out.println("Tables after rollback");
			TableHelper.queryPrint(dao.getStatement());
			return;
		} finally {
			dao.endStatement();
			conn.endConnection();
		}

	}

}