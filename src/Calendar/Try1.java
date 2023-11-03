package Calendar;


import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

//import org.json.simple.JSONObject;
//import org.json.simple.JSONArray;
//import org.json.simple.parser.*;

public class Try1 {
	
	static int[][] holidayTxt = readTxtFile("C:/Users/User/Downloads/feiertage.txt");
	
	static String[][][] calendarData; 
	//the size of this array String[year][month][day], the *year should be max-min of the given years? the *month should be 12? 
	//and the *day should be 31?
	
	public static void main(String[] args) {
		/*Scanner sc = new Scanner(System.in);
		System.out.println("Month & Year: ");
		int month = sc.nextInt();
		int year = sc.nextInt();*/
		
		/*try Json Data
		JSONParser parser = new JSONParser();
		try {
			JSONArray holidayInput = (JSONArray) parser.parse(new FileReader("C:\\Users\\User\\Downloads\\file.json"));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/

		//System.out.println(Arrays.deepToString(holidayTxt));
		
		System.out.println("Mon\tTue\tWed\tThu\tFri\tSat\tSun");
		show(12, 2023);
	}
	
	public static int[][] readTxtFile(String filePath) {
		try {
			String encoding = "UTF-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) {
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				List<String> list = new LinkedList<>();
				while ((lineTxt = bufferedReader.readLine()) != null) {
					if (!lineTxt.trim().equals("")) {
						list.add(lineTxt);
					}
				}
				read.close();
				String[] txtFile = list.toArray(new String[list.size()]);
				int[][] intDoubleArray = new int[list.size()][3];
				for (int i = 0; i< list.size(); i ++)
				{
					intDoubleArray[i] = dateTxtToInts(txtFile[i]);
				}
				return intDoubleArray;
			}
			else {
				System.out.println("Can not find the file.");
			}
		} catch (Exception e) {
			System.out.println("Error.");
			e.printStackTrace();
		}
		return new int[][] {  };
	}
	
	public static int[] dateTxtToInts(String s){
		 int[] n = new int[3]; 
		 n[0] = Integer.parseInt(s.substring(0, 2));
		 n[1] = Integer.parseInt(s.substring(3, 5));
		 n[2] = Integer.parseInt(s.substring(6, 10));
		 return n;
	}
	
	public static void show(int m, int y) {
		Calendar c = Calendar.getInstance();	
		//int year = c.get(Calendar.YEAR);
		//int month = c.get(Calendar.MONTH) + 1;
		//int day = c.get(Calendar.DAY_OF_MONTH);
		
		c.set(Calendar.YEAR, y);
		//Calendar.MONTH starts from 0
		c.set(Calendar.MONTH, m - 1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		
		int dayOfMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		//Calendar.DAY_OF_WEEK starts from Sunday
		
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		dayOfWeek = dayOfWeek == 0 ? 7 : dayOfWeek;
		int space = dayOfWeek - 1;
		int count = 0;  //to count the position until 7, to change the line
		for (int i= 0; i<space;i++) {
			count++;
			//to avoid blank at the beginning of the month if more than one month is shown, default set as 1 (Jar)
			//if( m == 1) 
				System.out.print("\t");
		}
			
		for (int i = 1; i <= dayOfMonth; i++) {
			int[] date = {i, m, y};
			count++;
			//print all the calendar data
			String dateToPrint = i + "\t";
			
			//calendarData[y][m][i] = String.valueOf(i);
			
			//pick out the given dates (holidays)
			for (int[] j:holidayTxt){
				if (Arrays.equals(date, j))
				dateToPrint = i + "*" + "\t";
				
				//calendarData[y][m][i] = i + "*";
				
			}

			System.out.print(dateToPrint);

			if (count % 7 == 0) {
				count = 0;
				System.out.println();}
		}		
	}
	
	
}
