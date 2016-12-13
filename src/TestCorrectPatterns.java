import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;


public class TestCorrectPatterns {
	
	
	public static void main(String[] args) throws ParseException {
		
		try {
			FileInputStream fstream = new FileInputStream("../../DataAndReports/Cartography/Patterns.csv");
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

			PrintWriter writer = new PrintWriter("../../DataAndReports/Cartography/Patterns_Final.csv", "UTF-8");
			
			
			String strLine;

			//Read File Line By Line
			strLine = br.readLine();
			writer.write(strLine+"\n");

			while ((strLine = br.readLine()) != null)   {
				String[] data = strLine.split(";");
				if(data.length == 3){
					writer.write(strLine + "0\n");
				}
				else{
					writer.write(strLine +"\n");
				}
			}
			br.close();
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
