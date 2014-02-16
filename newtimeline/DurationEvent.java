package newtimeline;
import java.io.*;

/**
 * @author Kurt Andres
 *
 */
//may want to use the following for simple date output for getDate()
//import java.text.SimpleDateFormat;

public class DurationEvent implements Event, Serializable{

     // atomic or not
        public boolean isDuration;
	public String name; //event name
        
        public int endYear;
        public int endMonth;
        public int endDay;
        
        public int startYear;
        public int startMonth;
        public int startDay;
        
	public int timelineID; //event start date as continuous string key
        


        public void setEvent(String n, int sY, int sM, int sD, int eY, int eM, int eD){
		name = n;
                startYear = sY;
                startMonth = sM;
                startDay = sD;
                endYear = eY;
                endMonth = eM;
                endDay = eD;
                isDuration = true;
                
                String tempIDstring;
                String tempSM, tempSD;
                if(sM < 10) tempSM ="0"+Integer.toString(sM);
                else tempSM = Integer.toString(sM);
                if(sD < 10) tempSD ="0"+Integer.toString(sD);
                else tempSD = Integer.toString(sD);
                tempIDstring = Integer.toString(sY)+tempSM+tempSD;
                timelineID = Integer.parseInt(tempIDstring);
	}

	public void setName(String n){
		name = n;

	}

	public void setStartYear(int sD){
		startYear= sD;

	}
        
        public void setStartMonth(int sD){
		startMonth= sD;

	}
        
        public void setStartDay(int sD){
		startDay= sD;
	}
        
        public void setEndYear(int sD){
		endYear= 0;
	}
        
        public void setEndMonth(int sD){
		endMonth= 0;
	}
        
        public void setEndDay(int sD){
		endDay= 0;
	}
        
        //note this doesn't work for B.C. dates
        
        public void setID(int sY, int sM, int sD){
           String tempIDstring;
           tempIDstring = Integer.toString(sY)+Integer.toString(sM)+Integer.toString(sD);
           timelineID = Integer.parseInt(tempIDstring);
        }
        
	public String getName(){
		return name;
	}
        public int getStartYear(){
		return startYear;
	}
        
        public int getStartMonth(){
            return startMonth;
	}
        
        public int getStartDay(){
            return startDay;
	}
        
        public int getEndYear(){
            return endYear;
	}
        
        public int getEndMonth(){
            return endMonth;
	}
        
        public int getEndDay(){
            return endDay;
	}
        
        public int getID(){
            return timelineID;
        }

        public boolean getDuration(){
            return isDuration;
        }
}
