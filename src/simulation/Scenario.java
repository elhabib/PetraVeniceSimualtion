package simulation;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Scenario {
	public int day;
	public int crowd;
	public boolean bieannale;
	public String weather;

	public Scenario(String FileName) throws Exception{

		File fXmlFile = new File(FileName);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);

		//optional, but recommended
		//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		doc.getDocumentElement().normalize();

		//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

		NodeList nList = doc.getElementsByTagName("daytype");
		Node dayType = nList.item(0);
		Element eElement = (Element) dayType;

		String days[] ={"sunday","monday","tuesday","wedneday","thursday","friday","saturday"};
		int i = 0;
		while(i<7 && eElement.getElementsByTagName("day").item(0).getTextContent().compareTo(days[i]) != 0){
			i++;
		}
		if(i == 7){
			throw new Exception("In File " + FileName + " the field 'day' of daytype is incorrect format. Correct Format is: monday, tuesday ... " );
		}else{
			day = i;
		}
		if (eElement.getElementsByTagName("crowd").item(0).getTextContent().compareTo("high") == 0){
			crowd = 2;
		}else if (eElement.getElementsByTagName("crowd").item(0).getTextContent().compareTo("normal") == 0){
			crowd = 1;
		}else if (eElement.getElementsByTagName("crowd").item(0).getTextContent().compareTo("low") == 0){
			crowd = 0;
		}else{
			throw new Exception("In File " + FileName + " the field 'crowd' of daytype is incorrect. Write one of the values: 'high', '´normal', or 'low'" );
		}
		if(eElement.getElementsByTagName("bieannale").item(0).getTextContent().compareTo("yes") == 0){
			bieannale = true;
		}else if(eElement.getElementsByTagName("bieannale").item(0).getTextContent().compareTo("no") == 0){
			bieannale = false;
		}else{
			throw new Exception("In File " + FileName + " the field 'bieannale' of daytype is incorrect. Write one of the values: 'yes' or 'no'");
		}
		weather = eElement.getElementsByTagName("weather").item(0).getTextContent();
		if(weather.compareTo("normal") != 0 && weather.compareTo("bad") != 0){
			throw new Exception("In File " + FileName + " the field 'weather' of daytype is incorrect. Write one of the values: 'normal' or 'bad'");
		}
	}
}