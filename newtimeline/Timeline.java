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
	public Timeline(String nm){
            
		name = nm;
	}

	//add timeline event
	public void addEvent(Event e){
		timelineEvents.put(e.getID(), e);
	}

	//checks if start date and name are same, then removes event
	//NOTE, returns String to output to console depending on success or not
	public String removeEvent(Event e){
		if(timelineEvents.containsKey(e.getID()) && e.getName().equals(timelineEvents.get(e.getID()).getName())){
			timelineEvents.remove(e.getID());
			return e.getName() + " successfuly removed."; 
		}else
			return "Event not found in timeline.";
	}

	public void display(){
		//need to implement this still
	}

	public void loadTimeline(){
		//need to implement this to take all the file info and load it into the timeline TreeMap 
	}

	public void saveTimeline(){
		//need to implement to save timeline as TreeMap to file.
	}

}
