package Calendar;


import java.util.Scanner;
import java.util.Arrays;
import java.util.Calendar;

public class Try1 {

	public static void main(String[] args) {
		/*Scanner sc = new Scanner(System.in);
		System.out.println("Month & Year: ");
		int month = sc.nextInt();
		int year = sc.nextInt();*/
		System.out.println("Mon\tTue\tWed\tThu\tFri\tSat\tSun");
		show(1, 2023);
		show(2, 2023);
		
	}
	
	public static void show(int m, int y) {
		Calendar c = Calendar.getInstance();
		
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		
		int[][] holiday = {{1, 1, 2023},{3,1,2023},{24,02,2023}};
		
		c.set(Calendar.YEAR, y);
		//Calendar.MONTH starts from 0
		c.set(Calendar.MONTH, m - 1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		
		int dayOfMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		//Calendar.DAY_OF_WEEK starts from Sunday
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		
		
		dayOfWeek = dayOfWeek == 0 ? 7 : dayOfWeek;
		int space = dayOfWeek - 1;
		int count = 0;
		
		for (int i= 0; i<space;i++) {
			count++;
			//to avoid blank at the beginning of the month if more than one month is shown, 
			//for the start of the year, it shows the blank
			if( m == 1) 
				System.out.print("\t");
		}
			
		for (int i = 1; i <= dayOfMonth; i++) {
			int[] date = {i, m, y};
			count++;
			//print all the calendar data
			String dateToPrint = i + "\t";
			//pick out the given dates
			for (int j = 0; j < holiday.length; j++){
				if (Arrays.equals(date, holiday[j]))
				{dateToPrint = i + "*" + "\t";}
			}

			System.out.print(dateToPrint);

			if (count % 7 == 0) {
				count = 0;
				System.out.println();
			}
			//test if date shows correctly.
			//System.out.println(Arrays.toString(date));
		}		
	}
}
