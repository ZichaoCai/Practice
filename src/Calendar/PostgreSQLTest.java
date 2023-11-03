package Calendar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class PostgreSQLTest {

	public static void main(String args[]) {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager
					.getConnection("jdbc:postgresql://localhost:5432/test", "postgres", "123");
			c.setAutoCommit(false);//set false, then should be committed later
			System.out.println("Opened database successfully");
			
			stmt = c.createStatement();
			/*create table
			String sql = "CREATE TABLE CALENDARBASE" +
					"(ID INT PRIMARY KEY     NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " DATE           INT     NOT NULL, " +
                    " ADDRESS        CHAR(50) )";
			stmt.executeUpdate(sql);
			*/
			
			/*Insert data
			String sql = "INSERT INTO CALENDARBASE (ID,NAME,DATE,ADDRESS)" +
					"VALUES (1, 'Alice', 20231103, 'Berlin');";
			stmt.executeUpdate(sql);
			
			sql = "INSERT INTO CALENDARBASE (ID,NAME,DATE,ADDRESS)" +
					"VALUES (2, 'Bob', 20231130, 'Leipzig');";
			stmt.executeUpdate(sql);
			
			sql = "INSERT INTO CALENDARBASE (ID,NAME,DATE,ADDRESS)" +
					"VALUES (3, 'Colin', 20231205, 'London');";
			stmt.executeUpdate(sql);
			
			sql = "INSERT INTO CALENDARBASE (ID,NAME,DATE,ADDRESS)" +
					"VALUES (4, 'David', 20231228, 'Berlin');";
			stmt.executeUpdate(sql);
			*/
			
			/*Update data, ***the newest update data will be printed as the last
			String sql = "UPDATE CALENDARBASE set DATE = 20240101 where ID=1;";
			stmt.executeUpdate(sql);
			*/
			
			//Delete data
	        String sql = "DELETE from CALENDARBASE where ID=2;";
	        stmt.executeUpdate(sql);
			
			c.commit();//for any change of the database (e.g. insert/update/delete data)
			
			//Select and read data from database
			ResultSet rs = stmt.executeQuery("SELECT * FROM CALENDARBASE;");
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int date = rs.getInt("date");
				String address = rs.getString("address");
				
				System.out.println("ID = " + id);
				System.out.println("NAME = " + name);
				System.out.println("DATE = " + date);
				System.out.println("ADDRESS = " + address);
				System.out.println();
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+ ": "+e.getMessage());
			System.exit(0);
		}
		System.out.println("Operate successfully");
	}
}
