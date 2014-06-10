package net.bruncle.notebook;

/**
* Storage class for notes, holds information about title and contents
* @author  Jeremy Nagel
*/

public class Note{
	
	private String title;
	private String contents;
	private int id;
	
	/**
	*  Constructor for new note:
	*  @param  title  The title of the new note
	*  @param  contents  The contents of the new note
	*  @param  id  The unique id of the new note
	*/
	public Note(String title, String contents, int id){
		this.title = title;
		this.contents = contents;
		this.id = id;
	}
	
	public Note(Note contents){
		this.title = contents.getTitle();
		this.contents = contents.getContents();
		this.id = contents.getID();
	}
	
	/**
	*  @return  The title of the note
	*/
	public String getTitle(){
		return title;
	}
	
	/**
	*  @return  The contents of the note
	*/
	public String getContents(){
		return contents;
	}
	
	/**
	*  @return  The id of the note
	*/
	public int getID(){
		return id;
	}
	
	/**
	*  Accessor method to set the title of the note
	*/
	public void setTitle(String title){
		this.title = title;
	}
	
	/**
	*  Accessor method to set the contents of the note
	*/
	public void setContents(String contents){
		this.contents = contents;
	}
	
	/**
	*  Convenience method - determines whether this note matches the search query
	*/
	public boolean matches(String query){
		return (getContents().toLowerCase().contains(query.toLowerCase()) || getTitle().toLowerCase().contains(query.toLowerCase()));
	}
}