package Calendar;


import java.util.Scanner;
import java.util.Calendar;

public class Try1 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Month & Year: ");
		int month = sc.nextInt();
		int year = sc.nextInt();
		show(year, month);
	}
	
	public static void show(int y, int m) {
		Calendar c = Calendar.getInstance();
		
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		
		c.set(Calendar.YEAR, y);
		c.set(Calendar.MONTH, m - 1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		
		int dayOfMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		
		System.out.println("Mon\tTue\tWed\tThu\tFri\tSat\tSun");
		
		dayOfWeek = dayOfWeek == 0 ? 7 : dayOfWeek;
		int space = dayOfWeek - 1;
		int count = 0;
		for (int i= 0; i<space;i++) {
			count++;
			System.out.print("\t");
		}
		for (int i = 0; i < dayOfMonth; i++) {
			count++;
			int d = i+1;
			String date = (y == year && m == month && d == day)?"*" + d + "\t" : d + "\t";
			System.out.print(date);
			if (count % 7 == 0) {
				count = 0;
				System.out.println();
			}
		}
	}
}
