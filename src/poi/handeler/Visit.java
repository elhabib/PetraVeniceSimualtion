package poi.handeler;
import math.tools.DateHandeler;
import sun.util.calendar.BaseCalendar.Date;

public class Visit implements Comparable<Visit>{
	
	
	/* 1. photo id
	2. user id
	3. uploaded time of photo
	4. taken time of photo
	5. latitude of photo
	6. longitude of photo
	6. PoI which is in less that 100 meter distance of photo location (we clustered photos based on PoI Geo-location) 8. latitude of PoI 9. longitude of PoI */
	
	private String photoID;
	private String visitorID;
	private DateHandeler pictureUploadedTime;
	private DateHandeler pictureTakenTime;
	private double x;
	private double y;
	private VisitLoc vLoc;
	private String PoIName;
	private static int idnumber = 0;
	
	Visit(String photoID2, String visitorID2, DateHandeler pictureUploadedTime, DateHandeler pictureTakenTime, double x, double y,String PoIName, double xPoI,double yPoI){
		this.photoID = photoID2;
		this.visitorID = visitorID2;
		this.pictureUploadedTime = pictureUploadedTime;
		this.pictureTakenTime = pictureTakenTime;
		this.x = x;
		this.y = y;
		this.PoIName = PoIName;
		this.vLoc = new VisitLoc(idnumber++,xPoI,yPoI);
		
	}
	
	public String getPhotoID(){
		return photoID;
	}
	
	public String getVisitorID(){
		return visitorID;
	}
	
	public DateHandeler getPictureUploadedTime(){
		return pictureUploadedTime;
	}
	
	public DateHandeler getPictureTakenTime(){
		return pictureTakenTime;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public String getPoI(){
		return PoIName;
	}
	
	
	public VisitLoc getVisitLoc(){
		return vLoc;
		
	}


	@Override
	public int compareTo(Visit v) {
		// TODO Auto-generated method stub
		if (this.pictureTakenTime.compareTo(v.getPictureTakenTime()) == 1)
			return 1;
		else if (this.pictureTakenTime.compareTo(v.getPictureTakenTime()) == -1)
			return -1;
		else
			return 0;
	}
	
	public String toString(){
		return photoID + "," + visitorID + "," + pictureUploadedTime + "," + pictureTakenTime + "," + x + "," +
				 "," + y + "," + PoIName + "," + vLoc.x + "," + vLoc.y;
	}

	public boolean isAtSameDayAs(Visit v2) {
		if(this.pictureTakenTime.isTheSameDayAs(v2.pictureTakenTime))
			return true;
		return false;
	}
}
