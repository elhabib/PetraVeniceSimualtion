package poi.handeler;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;

public class ReadPatterns {


	public static void main(String[] args) throws ParseException {

		try {
			FileInputStream fstream = new FileInputStream("../../DataAndReports/Cartography/Patterns"
					+ ".csv");
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));


			String strLine;

			//Read File Line By Line
			strLine = br.readLine();
			int trees = 0;
			int fours = 0;
			
			while ((strLine = br.readLine()) != null)   {
				String[] data = strLine.split(";");
				if (data.length == 3)
					trees++;
				else
					fours++;
			}
			br.close();
			System.out.println("trees: " + trees);
			System.out.println("fours: " + fours);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
