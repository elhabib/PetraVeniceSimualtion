import java.time.LocalDate;
import java.time.LocalTime;

import OnlineReading.GateOpenings;
import math.tools.Distribution;
import math.tools.MultiModalDistribution;
import math.tools.NormalDistribution;
import math.tools.UniformDistribution;

public class TestDistribution {

	public static void main(String[] args) throws Exception {


		LocalDate localDate = LocalDate.now();
		LocalTime localTime = LocalTime.now();

		int day = localDate.getDayOfWeek().getValue();
		int hour = localTime.getHour();
		int minute = localTime.getMinute();
		int second = localTime.getSecond();
		
		int totalSimulationValidation = 45000;
		
		int time = hour*3600 + minute*60 + second;
		
		GateOpenings go = new GateOpenings("/input/dumpXML.xml");
		int totalValidationAtTime = go.getTravellersAtThisTime();
		
		Distribution D;
		if (day == 6 || day == 7){ 
			totalSimulationValidation *= 1.3;
			D = new NormalDistribution(11*3600,4*4*(3600*3600));
		}else{
			D = new MultiModalDistribution(8.5*3600,(3600*3600),13*3600,4*4*(3600*3600),0.3);
		}
		double sum = 0;

		for(int d = 1; d <= time; d+=1){
			sum += D.getRealization(d);
		}
		double highCrowd = sum*totalSimulationValidation*1.3;
		double lowCrowd = sum*totalSimulationValidation*0.7;
		if (totalValidationAtTime >= highCrowd){
			if (day == 6 || day == 7){
				System.out.println("PoIsTravelTimesCrowdedWeekend.csv");
			}else{
				System.out.println("PoIsTravelTimesCrowdedweekday.csv");
			}
		}
		else if(totalValidationAtTime <= lowCrowd){
			if (day == 6 || day == 7){
				System.out.println("PoIsTravelTimesNonCrowdedWeekend.csv");
			}else{
				System.out.println("PoIsTravelTimesNonCrowdedWeekday.csv");
			}
		}else{
			if (day == 6 || day == 7){
				System.out.println("PoIsTravelTimesNormalWeekend.csv");
			}else{
				System.out.println("PoIsTravelTimesNormalWeekday.csv");
			}			
		}
	}
}