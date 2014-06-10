package net.bruncle.notebook;

import java.util.*;

/**
* Storage class for timelines, stores TimeLineEntries
* @author  Jeremy Nagel
*/

public class TimeLine{
	
	private String title;
	private int id;
	private List<TimeLineEntry> entries = new ArrayList<TimeLineEntry>();
	
	/**
	*  Constructor for new timeline:
	*  @param  title  The title of the new timeline
	*/
	public TimeLine(String title, int id){
		this.title = title;
		this.id = id;
	}

	/**
	*  @return  The title of the timeline
	*/
	public String getTitle(){
		return title;
	}
		
	/**
	*  Accessor method to set the title of the timeline
	*/
	public void setTitle(String title){
		this.title = title;
	}

	/**
	*  @return  The title of the timeline
	*/
	public int getID(){
		return id;
	}
		
	/**
	*  Accessor method to set the title of the timeline
	*/
	public void setID(int id){
		this.id = id;
	}

	
	/**
	*  @return  The entries of the timeline sorted chronologically
	*/
	public List<TimeLineEntry> getEntries(){
		Collections.sort(entries);
		return entries;
	}
	
	/**
	*  @return  The last entry (chronologically) in the timeline
	*/
	public TimeLineEntry getLastEntry(){
		Collections.sort(entries);
		TimeLineEntry toReturn = null;
		for (TimeLineEntry check : getEntries())
				toReturn = check;
		return toReturn;
	}

	/**
	*  @return  The first entry (chronologically) in the timeline
	*/
	public TimeLineEntry getFirstEntry(){
		Collections.sort(entries);
		TimeLineEntry toReturn = getEntries().get(0);
		return toReturn;
	}
	
	/**
	*  Finds the closest entry in the timeline to the supplied year
	*/
	public TimeLineEntry getClosestEntry(double year){
		Collections.sort(entries);
		TimeLineEntry toReturn = null;
		for (TimeLineEntry testee : entries){
			if (toReturn == null || (Math.abs(year - testee.getDoubleYear()) < Math.abs(year - toReturn.getDoubleYear())))
				toReturn = testee;
		}
		return toReturn;
	}
	
	/**
	*  Tests whether this timeline contains the supplied entry
	*/
	public boolean contains(TimeLineEntry toTest){
		Collections.sort(entries);
		for (TimeLineEntry entry : entries)
			if (entry.equals(toTest))
				return true;
		return false;
	}
	
	/**
	*  Tests whether this timeline contains the supplied entry
	*/
	public boolean contains(int toTest){
		Collections.sort(entries);
		for (TimeLineEntry entry : entries)
			if (entry.getID() == toTest)
				return true;
		return false;
	}
	
	/**
	*  Returns the index in the arraylist that a particular entry occupies
	*/
	public int getIndex(TimeLineEntry toTest){
		Collections.sort(entries);
		for (int i = 0; i < entries.size(); i++)
			if (entries.get(i).equals(toTest))
				return i;
		return -1;
	}
	
	/**
	*  Clones the timeline
	*/
	public TimeLine clone(){
		TimeLine clone = new TimeLine(getTitle(), getID());
		clone.getEntries().addAll(getEntries());
		return clone;
	}
	
	public boolean equals(TimeLine toCompare){
		return (toCompare.getID() == getID());
	}
	
	public boolean matches(String query){
		return (getTitle().toLowerCase().contains(query.toLowerCase()));
	}
	
	public String toString(){
		return getTitle() + " " + getID();
	}
}