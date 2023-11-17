package Calendar;

import java.time.LocalDate;
import java.util.Arrays;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Calendar.IDReader;

public class OperationTry2 {
	public static void main(String args[]) {
		
		String user = "Lina Reich";
		int[] vacationIDs = IDReader.readIDs();

		
		String chosenDateStart = "2023-12-28";
		String chosenDateEnd = "2023-12-28";
		
		Date dateStart =Date.valueOf(chosenDateStart);
		Date dateEnd =Date.valueOf(chosenDateEnd);
		
		int chosenYear = Integer.parseInt(chosenDateStart.substring(0, 4));
		//boolean isTheSameYear = chosenYear == Integer.parseInt(chosenDateEnd.substring(0, 4));
		
		boolean insertTimePossible = LocalDate.now().isBefore(LocalDate.parse(chosenDateStart));
		System.out.println(insertTimePossible);
		
		Connection c = null;
		
		String JDBC_URL = "jdbc:postgresql://localhost:5432/vacation";
		String JDBC_USER = "postgres";
		String JDBC_PASSWORD = "123";
		
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
			c.setAutoCommit(false);
			//System.out.println("Opened database successfully");
			

			int tmpID = VacationMethod.insertVacation(c, user, chosenYear, dateStart, dateEnd);
			
			//java IO
			if (tmpID != 0) {
				for (int i = 0; i < vacationIDs.length; i++) {
					if (vacationIDs[i] == 0) {
						vacationIDs[i] = tmpID;
						break;}
					else {
						//index out of range
					}
				}
			}
			//VacationMethod.updateVacation(c, user, vacationIDs[1], dateStart, dateEnd);
			//VacationMethod.deleteVacation(c, user, 2);
			VacationMethod.showDaysAmount(c, user, chosenYear);

			c.commit();
			c.close();
			
			System.out.println("Operate database successfully");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+ ": "+e.getMessage());
			System.exit(0);
		}
		
		IDReader.writeIDs(vacationIDs);
		System.out.println(Arrays.toString(IDReader.readIDs()));
	}
	
	
	class VacationMethod{
		
		static String TableName = new String("VACATION_LIST");
		static String TableColumn1 = new String("NAME");
		static String TableColumn2 = new String("YEAR");
		static String TableColumn3 = new String("DATE_START");
		static String TableColumn4 = new String("DATE_END");
		static String TableColumn5 = new String("DAYS");
		
		public static int insertVacation(Connection c, String user, int chosenYear, Date chosenDateStart, Date chosenDateEnd) {
			String sql = String.format("INSERT INTO %s (%s, %s, %s, %s) "
					+ "SELECT INITCAP(?), ?, ?, ? WHERE "
					+ "NOT EXISTS (SELECT * FROM %s WHERE %s = ? AND %s = ? AND %s = ?);",
					TableName, TableColumn1, TableColumn2, TableColumn3, TableColumn4,
					TableName, TableColumn1, TableColumn3, TableColumn4);
			try (PreparedStatement ps = c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
				ps.setObject(1, user);
				ps.setObject(2, chosenYear);
				ps.setDate(3, chosenDateStart);
				ps.setDate(4, chosenDateEnd);
				ps.setObject(5, user);
				ps.setDate(6, chosenDateStart);
				ps.setDate(7, chosenDateEnd);
				int i = ps.executeUpdate();
				System.out.println("If data is written: "+ i);
				try (ResultSet rs = ps.getGeneratedKeys()){
					if (rs.next()) {
						int id = rs.getInt(1);
						return id;
					}
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 0;
		}
		
		public static void updateVacation(Connection c, String user, int id, Date chosenDateStart, Date chosenDateEnd) {
			String sql = String.format("UPDATE %s SET %s = ?, %s = ? WHERE id = ?;",
					TableName, TableColumn3, TableColumn4);
			try (PreparedStatement ps = c.prepareStatement(sql)){
				ps.setDate(1, chosenDateStart);
				ps.setDate(2, chosenDateEnd);
				ps.setObject(3, id);
				int i = ps.executeUpdate();
				
				System.out.println("If data is written: "+ i);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public static void deleteVacation(Connection c, String user, int id) {
			String sql = String.format("DELETE FROM %s WHERE id = ?;",
					TableName);
			try (PreparedStatement ps = c.prepareStatement(sql)){
				ps.setObject(1, id);
				int i = ps.executeUpdate();
				
				System.out.println("If data is written: "+ i);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		public static void showDaysAmount(Connection c, String user, int chosenYear) {
			String sql = String.format("SELECT id, %s, %s, %s, %s, %s "
					+ "FROM %s WHERE %s = ? AND %s = ?;",
					TableColumn1, TableColumn2, TableColumn3, TableColumn4, TableColumn5,
					TableName, TableColumn1, TableColumn2);
			try (PreparedStatement ps = c.prepareStatement(sql)){
				ps.setObject(1, user);
				ps.setObject(2, chosenYear);
				try (ResultSet rs = ps.executeQuery()){
					while (rs.next()) {
						int id = rs.getInt("id");
						String name = rs.getString("name");
						int year = rs.getInt("year");
						String dateStart = rs.getString("date_start");
						String dateEnd = rs.getString("date_end");
						int days = rs.getInt("days");
						
						System.out.println("ID = " + id);
						System.out.println("NAME = " + name);
						System.out.println("YEAR = " + year);
						System.out.println("DATE_START = " + dateStart);
						System.out.println("DATE_END = " + dateEnd);
						System.out.println("DAYS = " + days);
						System.out.println();
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}


