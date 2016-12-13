import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import javafx.collections.transformation.SortedList;
import poi.handeler.Visits;

import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;


public class TestUploadFlickrData {

	public static void main(String[] args) throws ParseException {
		// -define .csv file in app

		Visits v = new Visits("flickrData.csv");
		v.filterWeekEnds();
		System.out.println(v.toString());
		//DateHandeler dh1 = new DateHandeler(2012,1,1,1,2,10,10);
		//DateHandeler dh2 = new DateHandeler(2012,2,1,1,2,10,10);
		//DateHandeler dh3 = new DateHandeler(2011,12,5,5,11,11,55);
		//DateHandeler dh4 = new DateHandeler(2012,11,6,4,11,10,23);
		//DateHandeler dh5 = new DateHandeler(2012,11,2,1,10,10,10);
		//DateHandeler dh6 = new DateHandeler(2013,12,1,1,10,10,10);
		//DateHandeler dh7 = new DateHandeler(2013,12,1,1,10,10,10);
		//DateHandeler dh8 = new DateHandeler(2013,12,1,1,10,10,10);
		//DateHandeler dh9 = new DateHandeler(2013,12,1,1,10,10,10);
		//DateHandeler dh10 = new DateHandeler(2013,12,1,1,10,10,10);

		//System.out.println(dh1.compareTo(dh2));
		//System.out.println(dh2.compareTo(dh3));
		//System.out.println(dh3.compareTo(dh4));
		//System.out.println(dh3.compareTo(dh4));
		//System.out.println(dh4.compareTo(dh3));
		//System.out.println(dh5.compareTo(dh6));
		//System.out.println(dh6.compareTo(dh7));
		//System.out.println(dh7.compareTo(dh8));
		//System.out.println(dh8.compareTo(dh9));
		//System.out.println(dh9.compareTo(dh10));
		//System.out.println(dh10.compareTo(dh1));
	}
}
