package Calendar;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class IDReader {

	static String fileName = "NameYearIDs";
	static String idFilePath = String.format("./%s", fileName);
	static int vacationPerYear = 30;
	static int[] vacationIDs = new int[vacationPerYear];
	
	public static void main(String args[]) {
		System.out.println(Arrays.toString(readIDs()));
	}
	
	public static void writeIDs(int[] idsUpdate) {
		
		ByteBuffer byteBuffer = ByteBuffer.allocate(idsUpdate.length * 4).order(ByteOrder.BIG_ENDIAN);        
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.put(idsUpdate);

        byte[] vacationIDsFile = byteBuffer.array();

        try {
    		Files.write(Path.of(idFilePath), vacationIDsFile);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
	}
	
	public static int[] readIDs() {

		File f = new File(idFilePath);
	    if(f.exists() && !f.isDirectory()) { 
			try {
				byte[] idsByte = Files.readAllBytes(Path.of(idFilePath));
				
				IntBuffer intBuf = ByteBuffer.wrap(idsByte).order(ByteOrder.BIG_ENDIAN).asIntBuffer();
				int[] ids = new int[intBuf.remaining()];
				intBuf.get(ids);

				return ids;
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
		return vacationIDs;

	}
}
