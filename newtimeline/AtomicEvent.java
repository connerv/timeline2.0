package newtimeline;
import java.io.*;
/**
 * @author Kurt Andres
 *
 */
public class AtomicEvent implements Event, Serializable {

        // atomic event or not
        public boolean isDuration;
        
	public String name; //event name
        
        public int startYear;
        public int startMonth;
        public int startDay;
        
        public int endYear;
        public int endMonth;
        public int endDay;
        
	public int timelineID; //event start date as continuous string for treeMap key

	public void setEvent(String n, int sY, int sM, int sD, int eY, int eM, int eD){
		name = n;
                startYear = sY;
                startMonth = sM;
                startDay = sD;
                endYear = 0;
                endMonth = 0;
                endDay = 0;
                isDuration = false;
                
                //set timelineID
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
        
        //note this doesn't work for B.C. dates
        public void setID(int sY, int sM, int sD){
           String tempIDstring;
           tempIDstring = Integer.toString(sY)+Integer.toString(sM)+Integer.toString(sD);
           timelineID = Integer.parseInt(tempIDstring);
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
                endYear = 0;
		return endYear;
	}
        
        public int getEndMonth(){
            endMonth = 0;
            return endMonth;
	}
        
        public int getEndDay(){
            endDay = 0; 
            return endDay;
	}
        
        public int getID(){
            return timelineID;
        }
        
        public boolean getDuration(){
            return isDuration;
        }
}
