package math.tools;

public class DateHandeler {
	public int year = 2000;
	public int month = 1;
	public int dayDate = 1;
	public int day = 0; // 0:sunday, 6: Saturday 
	public int hour = 0;
	public int minute = 0;
	public int second = 0;
	
	public DateHandeler(){
	}
	
	public DateHandeler(int y, int m, int d, int dd, int h, int min, int s){
		year = y;
		month = m;
		dayDate = dd;
		day = d;
		hour = h;
		minute = min;
		second = s;
	}
	
	public int getYear(){
		return year;
	}
	
	public int getMonth(){
		return month;
	}
	
	public int getDay(){
		return day;
	}
	
	public int getDayDate(){
		return dayDate;
	}
	
	public int getHour(){
		return hour;
	}
	
	public int getMinute(){
		return minute;
	}
	
	public int getSecond(){
		return second;
	}
	
	public String toString(){
		return day +" " + year + "-" + month + "-" + dayDate + " " + " " + hour+ ":"+ minute +":"+ second;
	}
	
	public int compareTo(DateHandeler d){
		
		if (d.getYear() == year & d.getMonth() == month & d.getDayDate() == dayDate & d.getHour() == hour && d.getMinute() == minute && d.getSecond() == second)
			return 0;
		
		if (d.getYear() > year)
			return -1;
		else if (d.getYear() < year)
			return 1;
		if (d.getMonth() > month)
			return -1;
		else if (d.getMonth() < month)
			return 1;
		if (d.getDayDate() > dayDate)
			return -1;
		else if (d.getDayDate() < dayDate)
			return 1;
		if (d.getHour() > hour)
			return -1;
		else if (d.getHour() < hour)
			return 1;
		if (d.getMinute() > minute)
			return -1;
		else if (d.getMinute() < minute)
			return 1;
		if (d.getSecond() > second)
			return -1;
		else 
			return 1;
	}
	
	public static int dayIndex(String s){
		String[] days = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
		for(int i = 0; i<days.length; i++){
			if(days[i].compareTo(s) == 0)
				return i;
		}
		System.out.println("Day Unknown");
		return -1;
	}
	
	public static int monthIndex(String s){
		String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
		for(int i = 1; i <= months.length; i++){
			if(months[i-1].compareTo(s) == 0)
				return i;
		}
		System.out.println("Month Unknown");
		return -1;
	}
	
	
	/**
	 * 
	 * @param d
	 * @return true if d is the same date as object
	 */
	public boolean isTheSameDayAs(DateHandeler d) {
		if(dayDate == d.dayDate && month == d.month && year == d.year)
			return true;
		return false;
	}

	
	/**
	 * 
	 * @param h1
	 * @param h2
	 * @return true if the time is between h1 and h2
	 */
	public boolean isWithin(int h1, int h2) {
		if(hour >= h1 && hour < h2)
			return true;
		return false;
	}

	public boolean isWeekEnd() {
		return (day == 0 || day == 6);
	}
}
