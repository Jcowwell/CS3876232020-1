package javasql;

import java.sql.*;

public class DirectAccess {
	
	private Statement stmt;
	
	public DirectAccess(Statement stmt) {
		this.stmt = stmt;
	}
	
	public void delete(String str) throws SQLException {
		
		stmt.executeUpdate(str);
	}
	public void endStatement() throws SQLException {
		
		stmt.close();
	}

	public Statement getStatement() {
		// TODO Auto-generated method stub
		return this.stmt;
	}


	
}
