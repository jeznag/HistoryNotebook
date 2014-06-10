package net.bruncle.notebook;

import java.util.*;

/**
* Storage class for timeline entries, holds information about title, date and contents
* @author  Jeremy Nagel
*/

public class TimeLineEntry implements Comparable{

	private String title;
	private String contents;
	private Calendar date;
	private int id;
	
	/**
	*  Constructor for new timeline entry:
	*  @param  title  The title of the new entry
	*  @param  contents  The contents of the new entry
	*  @param  date  The date of the new entry
	*/
	public TimeLineEntry(String title, String contents, Calendar date, int id){
		this.title = title;
		this.contents = contents;
		this.date = date;
		this.id = id;
	}
	
	/**
	*  Constructor for new timeline entry:
	*  @param  entry  An entry, which contains the data this entry will be based on
	*/
	public TimeLineEntry(TimeLineEntry entry){
		if (entry == null)
                    return;
                this.title = new String(entry.getTitle());
		this.contents = new String(entry.getContents());
		date = Calendar.getInstance();
		this.date.set(entry.getYear(), entry.getDate().get(Calendar.MONTH), entry.getDate().get(Calendar.DAY_OF_MONTH));
		this.id = entry.getID();
	}
	
	/**
	*  @return  The title of the entry
	*/
	public String getTitle(){
		return title;
	}
	
	/**
	*  @return  The contents of the entry
	*/
	public String getContents(){
		if (contents == null)
			return null;
		return contents;
	}
	
	/**
	*  @return  The date of the entry
	*/
	public Calendar getDate(){
		return date;
	}
	
	/**
	*  @return  The id of the entry
	*/
	public int getID(){
		return id;
	}
	
	/**
	*  Accessor method to set the title of the entry
	*/
	public void setTitle(String title){
		this.title = title;
	}
	
	/**
	*  Accessor method to set the contents of the entry
	*/
	public void setContents(String contents){
		this.contents = contents;
	}

	/**
	*  Accessor method to set the date of the entry
	*/
	public void setDate(Calendar date){
		this.date = date;
	}
	
	/**
	*  @return  The year that this entry is in
	*/
	public int getYear(){
		return date.get(Calendar.YEAR);
	}
	
	/**
	*  @return  The year (in decimal form) that this entry is in
	*/
	public double getDoubleYear(){
		//System.out.println(((double)(date.get(Calendar.YEAR))) + ((double)(date.get(Calendar.DAY_OF_YEAR)) / 365D));
		return ((double)(date.get(Calendar.YEAR))) + ((double)(date.get(Calendar.DAY_OF_YEAR)) / 365D);
	}
	
	/**
	*  @return  A string representation of the date of this entry
	*/
	public String getDateString(){
		return date.get(Calendar.DAY_OF_MONTH) + "." + (date.get(Calendar.MONTH) + 1) + "." + getYear();
	}
        
	/**
	*  Convenience method - Parses a date of format dd/mm/yyy
	*  @return  A calendar representation of the date
	*/
	public static Calendar getCalendarFromString(String date){
		date = date.replace('/', '.');
		if (date.indexOf(".") == -1){
			Main.getInstance().getGUI().showMessageDialog("You did not enter a valid value for date (no '.'s), it must be of the dd.mm.yyyy format");
			System.out.println("date");
                        return null;
		}
		Calendar toReturn = Calendar.getInstance();
		String[] dateComponents = date.split("\\.");
                try {
                    int day = Integer.parseInt(dateComponents[0]);
                    if (day > 31){
                        Main.getInstance().getGUI().showMessageDialog("No month has more than 31 days in it, correct the date value");
                        return null;
                    }
                    toReturn.set(Calendar.DAY_OF_MONTH, day);
                    int month = Integer.parseInt(dateComponents[1]) - 1;
                    if (month > 11 || month < 0){
                        Main.getInstance().getGUI().showMessageDialog("No year has more than 12 months in it or a negative number of years, correct the date value");
                        return null;
                    }	
                    toReturn.set(Calendar.MONTH, month);
                    toReturn.set(Calendar.YEAR, Integer.parseInt(dateComponents[2]));
                } catch (NumberFormatException ex) {
                    Main.getInstance().getGUI().showMessageDialog("Date formatted incorrectly: must be of form: dd.mm.yyyy");
                    return null;
                }
                return toReturn;
	}
	
	/**
	*  Tests whether the supplied entry is the same as this one
	*/
	public boolean equals(TimeLineEntry toTest){
		if (toTest == null)
			return false;
		return toTest.getID() == getID();
	}
	
	/**
	*  Convenience method - determines whether this entry matches the search query
	*/
	public boolean matches(String query){
		return (getContents().toLowerCase().contains(query.toLowerCase()) || getTitle().toLowerCase().contains(query.toLowerCase()));
	}
	
	/**
	*  Used to sort timelines chronologically
	*/
	public int compareTo(Object o) {
		if (!(o instanceof TimeLineEntry)){
			throw new ClassCastException("wrong type");
		}
		return getDate().compareTo(((TimeLineEntry)o).getDate());
	}
}