package net.bruncle.notebook;

import java.util.List;
import java.util.ArrayList;

/**
*  Storage class for a category - holds notes and timelines
*/


public class Category{
	
	private List<TimeLine> timeLines = new ArrayList<TimeLine>();
	private List<Note> notes = new ArrayList<Note>();
	private String title; 
	
	/**
	*  Overloaded constructor to set the title upon instantiation
	*/
	public Category(String title){
		this.title = title;
	}
	
	/**
	*  Overloaded constructor to set the title and data upon instantiation
	*/
	public Category(String title, List<TimeLine> timelines, List<Note> notes){
		this.title = title;
		this.timeLines = timelines;
		this.notes = notes;
	}
	
	/**
	*  Accessor method to access the title of this category
	*/
	public String getTitle(){
		return title;
	}
	
	/**
	*  Accessor method to change the title of this category
	*/
	public void setTitle(String title){
		this.title = title;
	}
	
	/**
	*  Accessor method to access the timelines of this category
	*/
	public List<TimeLine> getTimeLines(){
		return timeLines;
	}
	
	/**
	*  Accessor method to access the notes of this category
	*/
	public List<Note> getNotes(){
		return notes;
	}
	
	/**
	*  @return  Whether this category contains an entry with the supplied id
	*/
	public boolean contains(int id){
		for (Note note : getNotes())
			if (note.getID() == id)
				return true;
		for (TimeLine timeline : getTimeLines()){
			if (timeline.getID() == id)
				return true;
			for (TimeLineEntry entry : timeline.getEntries())
				if (entry.getID() == id)
					return true;
		}
		return false;
	}
	
	/**
	*  Adds the supplied note to this category
	*/
	public void addNote(Note toAdd){
		getNotes().add(toAdd);
	}
	
	/**
	*  Removes the note in this category with an id matching the parameter
	*/
	public void removeNote(int id){
		for (int i = 0; i < getNotes().size(); i++)
			if (getNotes().get(i).getID() == id)
				getNotes().remove(i);
	}
	
	public void addTimeLine(TimeLine toAdd){
		getTimeLines().add(toAdd);
	}
	
	public void removeTimeLine(TimeLine toRemove){
		for (int i = 0; i < getTimeLines().size(); i++)
			if (getTimeLines().get(i).getID() == toRemove.getID()){
				//System.out.println("Removing timeline at index " + i);
				getTimeLines().remove(i);
			}
	}
	
	public boolean equals(Category cat){
		return cat.getTitle().equals(getTitle());
	}
}