import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;

import javax.swing.text.html.parser.Element;

import com.sun.xml.internal.txw2.Document;

public class testGenerateRequest {

	public static void main(String[] args) {


		LocalDate localDate = LocalDate.now();

		int[] monthLength ={31,28,31,30,31,30,31,31,30,31,30,31 };
		int year = localDate.getYear();
		int month = localDate.getMonthValue();
		int day = localDate.getDayOfMonth();
		
		String today = indexToString(year) + indexToString(month) + indexToString(day);
		int dayYesterday, monthYesterday, yearYesterday;
		if (day == 1){
			if (month != 1){
				dayYesterday = monthLength[month-2];
				monthYesterday = month-1;
				yearYesterday = year;
			}else{
				dayYesterday = monthLength[11];
				monthYesterday = 12;
				yearYesterday = year-1;
			}
		}else{
			dayYesterday = day - 1;
			monthYesterday = month;
			yearYesterday = year;
		}
		String yesterday = indexToString(yearYesterday) + indexToString(monthYesterday) + indexToString(dayYesterday);
		String s = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
				+ "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n"
				+ "<soap:Body soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n"
				+ "<q1:getApertureInfo xmlns:q1=\"http://localhost:16302/axis\">\n"
				+ "<i_dataDa xsi:type=\"xsd:string\">"+yesterday+"</i_dataDa>\n"
				+ "<i_dataA xsi:type=\"xsd:string\">"+today+"</i_dataA>\n"
				+ "</q1:getApertureInfo>\n"
				+ "</soap:Body>\n"
				+ "</soap:Envelope>\n";
		PrintWriter writer;
		try {
			writer = new PrintWriter("./output/newrequest.xml", "UTF-8");
			writer.print(s);
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static String indexToString(int i){
		if (i>10)
			return Integer.toString(i);
		else
			return "0" + Integer.toString(i);
	}

}
