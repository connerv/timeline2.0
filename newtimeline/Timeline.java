/* @author Kurt Andres
//build to store timelines in TreeMap by key value
//key is event.id, set by year,month,day order i.e. 20130214
//that way we can sort the timeline by id
*/

package newtimeline;

import java.util.*;
import java.io.*;


public class Timeline implements Serializable {
	// Create a hash map
	//TreeMap datastructure according to ocs.oracle.com/javase/6/docs/api/java/util/TreeMap.html
	//is a sorted hashmap essentially by key value that is comparable
	// we can store the start dates as the key and the events as the value so its sorted by start date

	TreeMap<Integer, Event> timelineEvents = new TreeMap<Integer, Event>(); 
        String name;
        int eventCount;
        int max=0;
        int min=2015;
        int oldmin=0;
        int oldmax=0;
      
        
	public Timeline(String nm){
            
		name = nm;
	}

	//add timeline event
	public void addEvent(Event e){
		timelineEvents.put(e.getID(), e);
                if(e.getStartYear()<min){
                    oldmin=min;
                    min=e.getStartYear();
                }
                if(e.getEndYear()>max){
                    oldmax=max;
                    max=e.getEndYear();
                }
                eventCount++;
	}
        
        public boolean timelineNotEmpty(){
            if(eventCount >=1)
                return true;
            else{
                return false;
            }
        }

	//checks if start date and name are same, then removes event
	//NOTE, returns String to output to console depending on success or not
	public String removeEvent(Event e){
		if(timelineEvents.containsKey(e.getID()) && e.getName().equals(timelineEvents.get(e.getID()).getName())){
			timelineEvents.remove(e.getID());
                        eventCount--;
			return e.getName() + " successfuly removed."; 
		}else
			return "Event not found in timeline.";
	}

}
