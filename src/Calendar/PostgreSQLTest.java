package Calendar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class PostgreSQLTest {

	protected static int id = 1;
	
	public static void main(String args[]) {
		String user = "Lina";
		int chosenYear = 2023;
		String chosenDate = "2023-12-24";
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager
					.getConnection("jdbc:postgresql://localhost:5432/vacation", "postgres", "123");
			c.setAutoCommit(false);//set false, then should be committed later
			System.out.println("Opened database successfully");
			
			stmt = c.createStatement();
			/*create table
			String sql = "CREATE TABLE VACATIONLIST" +
					"(ID INT PRIMARY KEY     NOT NULL," +
                    " NAME           TEXT    NOT NULL," +
                    " YEAR           INT     NOT NULL," +
                    " DATE			 DATE );";
			stmt.executeUpdate(sql);
			*/
			
			//Insert data
			String sql = String.format("INSERT INTO VACATIONLIST (ID,NAME,YEAR,DATE) " +
					"VALUES (%d, '%S', %d, '%s');", id, user, chosenYear, chosenDate);

			stmt.executeUpdate(sql);
			c.commit();
			id ++;
			
			
			/*Update data, ***the newest update data will be printed as the last
			String sql = "UPDATE CALENDARBASE set DATE = 20240101 where ID=1;";
			stmt.executeUpdate(sql);
			*/
			
			/*Delete data
	        String sql = "DELETE from CALENDARBASE where ID<5;";
	        stmt.executeUpdate(sql);
			
			c.commit();*///for any change of the database (e.g. insert/update/delete data)
			
			//Select and read data from database
			ResultSet rs = stmt.executeQuery("SELECT * FROM VACATIONLIST;");
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int year = rs.getInt("year");
				String date = rs.getString("date");
				
				System.out.println("ID = " + id);
				System.out.println("NAME = " + name);
				System.out.println("YEAR = " + year);
				System.out.println("DATE = " + date);
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
