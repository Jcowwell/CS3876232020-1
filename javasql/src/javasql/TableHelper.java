package javasql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TableHelper {

	public static void queryPrint(Statement stmt) throws SQLException {
		ResultSet resultSetProduct = stmt.executeQuery("SELECT * FROM Product");

		System.out.println("Product:");
		System.out.println("---------------------------------------");
		System.out.println("prod_id\t" + "  |" + "\tpname" + "\t|\tprice");
		System.out.println("---------------------------------------");
		while (resultSetProduct.next()) {
			System.out.println(resultSetProduct.getString(1) + "|\t" + resultSetProduct.getString(2) + "\t" + "|\t"
					+ resultSetProduct.getString(3));
		}
		System.out.println();
		ResultSet resultSetStock = stmt.executeQuery("SELECT * FROM Stock");
		System.out.println("Stock:");
		System.out.println("--------------------------------------------");
		System.out.println("prod_id\t  |" + "\tdepo_id" + "\t  |\tquantity");
		System.out.println("--------------------------------------------");
		while (resultSetStock.next()) {
			System.out.println(resultSetStock.getString(1) + "|\t" + resultSetStock.getString(2) + "|\t"
					+ resultSetStock.getString(3));
		}
		System.out.println();
		ResultSet resultSetDepot = stmt.executeQuery("SELECT * FROM Depot");
		System.out.println("Depot:");
		System.out.println("-----------------------------------------------");
		System.out.println("depo_id\t  |" + "\taddr" + "\t\t|\tvolume");
		System.out.println("-----------------------------------------------");
		while (resultSetDepot.next()) {
			System.out.println(resultSetDepot.getString(1) + "|\t" + resultSetDepot.getString(2) + "\t|\t"
					+ resultSetDepot.getString(3));
		}
		System.out.println();

	}

	public static void createTables(Statement stmt) throws SQLException {

		// Delete existing tables
		stmt.executeUpdate("DROP TABLE IF EXISTS Stock");
		stmt.executeUpdate("DROP TABLE IF EXISTS Product");
		stmt.executeUpdate("DROP TABLE IF EXISTS Depot");

		// Creating Product table
		stmt.executeUpdate("CREATE TABLE Product (prod_id CHAR(10), pname VARCHAR(30), price DECIMAL(10,2))");
		stmt.executeUpdate("ALTER TABLE Product ADD CONSTRAINT pk_product PRIMARY KEY (prod_id);");
		stmt.executeUpdate("ALTER TABLE Product ADD CONSTRAINT ck_price CHECK (price>0);");

		// Creating Depot table
		stmt.executeUpdate("CREATE TABLE Depot (depo_id CHAR(10), addr VARCHAR(30), volume INTEGER)");
		stmt.executeUpdate("ALTER TABLE Depot ADD CONSTRAINT pk_depot PRIMARY KEY (depo_id)");
		stmt.executeUpdate("ALTER TABLE Depot ADD CONSTRAINT ck_volume CHECK (volume>0)");

		// Creating Stock table
		stmt.executeUpdate("CREATE TABLE Stock (prod_id CHAR(10), depo_id CHAR(10), quantity INTEGER)");
		stmt.executeUpdate("ALTER TABLE Stock ADD CONSTRAINT pk_stock PRIMARY KEY (prod_id, depo_id)");
		stmt.executeUpdate(
				"ALTER TABLE Stock ADD CONSTRAINT fk_stock_prod FOREIGN KEY (prod_id) REFERENCES Product(prod_id)");
		stmt.executeUpdate(
				"ALTER TABLE Stock ADD CONSTRAINT fk_stock_depo FOREIGN KEY (depo_id) REFERENCES Depot(depo_id)");

		System.out.println("The Tables are created");

	}

	public static void insertData(Statement stmt) throws SQLException {

		// Inserting sample data to the table
		stmt.executeUpdate("INSERT INTO Product VALUES ('p1', 'tape', 2.5), ('p2', 'tv', 250), ('p3', 'vcr', 80)");
		stmt.executeUpdate(
				"INSERT INTO Depot VALUES ('d1', 'New York', 9000), ('d2', 'Syracuse', 6000), ('d4', 'New York', 2000)");
		stmt.executeUpdate(
				"INSERT INTO Stock Values ('p1', 'd1', 1000), ('p1', 'd2', -100), ('p1', 'd4', 1200), ('p3', 'd1', 3000), ('p3', 'd4', 2000), ('p2', 'd4', 1500), ('p2', 'd1', -400), ('p2', 'd2', 2000)");

		System.out.println("Data has been inserted to the table");

	}
}
