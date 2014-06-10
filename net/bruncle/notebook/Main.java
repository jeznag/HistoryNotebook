package net.bruncle.notebook;

import java.io.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
*  Control centre of note book - starts the program when the class is run, initialises
*  the GUI, provides convenience functions, etc.
*  @author  Jeremy Nagel
*/

public class Main{
	
	public static void main(String[] args){
		new Main().startApplication();
	}
	
	public void startApplication(){
                try{
                        javax.swing.SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                try{
                                        getInstance().loadingDialog = new LoadingDialog(null, true, "user settings", 6);
                                        getInstance().loadingDialog.setVisible(true);
                                        getInstance().loadingDialog.setDefaultCloseOperation(javax.swing.JDialog.DO_NOTHING_ON_CLOSE);
                                }
                                catch (Exception e){ e.printStackTrace(); }
                            }
                        });
                }catch (Exception e) { e.printStackTrace(); }    
		getInstance().loadSettings();
		getInstance().categories = new ArrayList<Category>();
		getInstance().initialiseGUI();
		if (getInstance().fileName == null || getInstance().fileName.equals("")){
			System.out.println("nullfile");
                        getInstance().loadingDialog.dispose();
                        getInstance().newFile(true);
                        getInstance().getGUI().displaySearchPanel();
			return;
		}
		getInstance().loadData();
		getInstance().getGUI().displaySearchPanel();
                getInstance().loadingDialog.dispose();
	}
	
	/**
	*  Provides other classes with access to methods in this class through an instance of it
	*/
	public static Main getInstance(){
		if (_instance == null)
			_instance = new Main();
		return _instance;
	}	

	/*miscellaneous methods*/
	/**
	*  Returns a number between low and high
	*/
	public static double random(int low, int high){
		 return Math.random() * (high - low) + low;
	}
	
	/*XML manipulation/reading methods*/
	
	/**
	*  Sets the filename data will be loaded and saved to, to the supplied string
	*/
	public void setFileName(String fileName){
		this.fileName = fileName;
	}
	
	/**
	*  Loads the Notebook data from the chosen xml file
	*/
	public void loadData(){
                File loadFile = null;
                try{
                    loadFile = new File(fileName);
                }catch(Exception e) {
                        getInstance().newFile(true);
                        getInstance().loadingDialog.dispose();
			return;
		}
		getCategories().clear();
		Document dom = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			
			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			//parse using builder to get DOM representation of the XML file
			//if (!fileName.startsWith("file:///"))
				//fileName = "file:///" + fileName;
                        dom = db.parse(loadFile);
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
                }catch(IOException ioe) {
			ioe.printStackTrace();
		}


		Element root = dom.getDocumentElement();
		
		NodeList nl = root.getElementsByTagName("category");
		if(nl != null && nl.getLength() > 0) {
			for(int i = 0 ; i < nl.getLength();i++) {
				//get the category element
				Element catEl = (Element)nl.item(i);
                                String catTitle = catEl.getAttribute("title");
                                int numData = catEl.getChildNodes().getLength();
                                System.out.println("loading category: " + catTitle + " 0\\" + numData);
                                getInstance().loadingDialog.newTask(catTitle, numData);
				getCategories().add(parseCategoryElement(catEl));
			}
		}
	}
	
	/**
	*  Returns a category object resulting from parsing the xml category element
	*/
	private Category parseCategoryElement(Element el){
		String title = el.getAttribute("title");
		List<Note> notes = parseNotes(el);
		List<TimeLine> timelines = parseTimeLines(el);
		return new Category(title, timelines, notes);
	}
	
	/**
	*  Parses the notes inside the supplied category element
	*/
	private List<Note> parseNotes(Element cat){
		List<Note> toReturn = new ArrayList<Note>();
		NodeList noteElems = cat.getElementsByTagName("note");
		for (int i = 0; i < noteElems.getLength(); i++){  
			//iterates through all the note elements the category contains
			//parses them and adds them to the list
			String title = getCDataValue((Element)noteElems.item(i), "title");
			String contents = getCDataValue((Element)noteElems.item(i), "contents");
                        if (title == null)
                            title = getTextValue((Element)noteElems.item(i), "title");
                        if (contents == null)
                            contents = getTextValue((Element)noteElems.item(i), "contents");
			int id = Integer.parseInt(getTextValue((Element)noteElems.item(i), "id"));
			toReturn.add(new Note(title, contents, id));
                        getInstance().loadingDialog.incrementProgress();
		}
		noteElems = null;
		return toReturn;
	}
	
	/**
	*  Parses the timelines inside the supplied category element
	*/
	private List<TimeLine> parseTimeLines(Element cat){
		List<TimeLine> toReturn = new ArrayList<TimeLine>();
		NodeList timelineElems = cat.getElementsByTagName("timeline");
		for (int i = 0; i < timelineElems.getLength(); i++){  
			//iterates through all the timeline elements the category contains
			//parses them and adds them to the list
			TimeLine newLine = parseTimeLine((Element)timelineElems.item(i));
			toReturn.add(newLine);
                        getInstance().loadingDialog.incrementProgress();
		}
		return toReturn;
	}

	/**
	*  Parses an individual timeline from an xml element
	*  @arg  elem  The dom element that contains the timeline's data
	*  @return  An object representation of the timeline
	*/
	private TimeLine parseTimeLine(Element elem){
		String title = elem.getAttribute("title");
		int id = Integer.parseInt(elem.getAttribute("id"));
		TimeLine toReturn = new TimeLine(title, id);
		NodeList timelineEntryElems = elem.getElementsByTagName("timelineEntry");
		for (int i = 0; i < timelineEntryElems.getLength(); i++){  
			//iterates through all the timeline entry elements the timeline contains
			//parses them and adds them to the list
			TimeLineEntry newEntry = parseTimeLineEntry((Element)timelineEntryElems.item(i));
			toReturn.getEntries().add(newEntry);
                        getInstance().loadingDialog.incrementProgress();
		}
		return toReturn;

	}
	
	/**
	*  Parses an individual timeline entry from an xml element
	*  @arg  elem  The dom element that contains the timeline entry's data
	*  @return  An object representation of the timeline entry
	*/
	private TimeLineEntry parseTimeLineEntry(Element elem){
		String title = getCDataValue(elem, "title");
                if (title == null)
                    title = getTextValue(elem, "title");
		String contents = getCDataValue(elem, "contents");
                if (contents == null)
                    contents = getTextValue(elem, "contents");
		String[] dateComponents = getTextValue(elem, "date").split("\\."); 
			//splits "12.02.1988" to new String[]{"12", "02", "1988"};
		java.util.Calendar dateOfEntry = java.util.Calendar.getInstance();
		dateOfEntry.set(java.util.Calendar.DAY_OF_MONTH, Integer.parseInt(dateComponents[0]));
		dateOfEntry.set(java.util.Calendar.MONTH, Integer.parseInt(dateComponents[1]) - 1);
		dateOfEntry.set(java.util.Calendar.YEAR, Integer.parseInt(dateComponents[2]));
		//System.out.println(dateOfEntry.get(java.util.Calendar.DAY_OF_MONTH) + "." + dateOfEntry.get(java.util.Calendar.MONTH) + "." + dateOfEntry.get(java.util.Calendar.YEAR));
		int id = Integer.parseInt(getTextValue(elem, "id"));
		return new TimeLineEntry(title, contents, dateOfEntry, id);

	}

	
	/**
	 * Parses the text value of a tag in an element 
	 * @source  http://members.fortunecity.com/seagull98/XML.html
	 * @param  ele  The element which contains the tag
	 * @param  tagName  The tag, whose text will be returned
	 * @return  The string value of the tag
	 */
	private String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}

		return textVal;
	}
        
        /**
	 * Parses the text value of a tag in an element 
	 * @source  http://members.fortunecity.com/seagull98/XML.html
	 * @param  ele  The element which contains the tag
	 * @param  tagName  The tag, whose text will be returned
	 * @return  The string value of the tag
	 */
	private String getCDataValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
                        nl = el.getChildNodes();
                        for(int i = 0 ; i < nl.getLength();i++) {
                            Node node = nl.item(i);
                            if (node.getNodeType() == Node.CDATA_SECTION_NODE)
                                textVal = node.getNodeValue();
                        }
		}
		return textVal;
	}
        
	public void saveData(){
		try{
			if (fileName.startsWith("file:///"))
				fileName = fileName.substring(8);
			System.out.println("Filename: " + fileName);
			OutputStream fout = new FileOutputStream(fileName);
			OutputStream bout = new BufferedOutputStream(fout);
			OutputStreamWriter out = new OutputStreamWriter(bout, "8859_1");
			out.write("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
			out.write("<notebook>");
			for(Category cat : getCategories()){
				out.write("<category title = \"" + cat.getTitle() + "\">");
				for(Note note : cat.getNotes()){
					out.write("<note>");
					out.write("<title><![CDATA[" + note.getTitle() + "]]></title>");
					out.write("<contents><![CDATA[" + note.getContents() + "]]></contents>");
					out.write("<id>" + note.getID() + "</id>");
					out.write("</note>");
				}
				for(TimeLine timeline : cat.getTimeLines()){
					out.write("<timeline title=\"" + timeline.getTitle() + "\" id=\"" + timeline.getID() + "\">");
					for(TimeLineEntry entry : timeline.getEntries()){
						out.write("<timelineEntry>");
						out.write("<title><![CDATA[" + entry.getTitle() + "]]></title>");
						out.write("<date>" + entry.getDateString() + "</date>");
						out.write("<contents><![CDATA[" + entry.getContents() + "]]></contents>");
						out.write("<id>" + entry.getID() + "</id>");
						out.write("</timelineEntry>");
					}
					out.write("</timeline>");
				}
				out.write("</category>");
			}
			out.write("</notebook>");
			out.close();
		}
		catch(IOException e){
			System.err.println("Something went wrong with the xml writing process:");
			e.printStackTrace();
		}
	}	
	
        /**
         * Allows the user to choose where they will save the new file
         * @var  noFile  Whether a new file must be chosen because there is no file currently being edited
         */
        public void newFile(boolean noFile){
            boolean proceed = noFile;
            if (!noFile){
                proceed = (getGUI().isConfirmed("Are you want to start a new file?"));
            }
            else
                getGUI().showMessageDialog("You haven't got an active file, so you need to start a new one. \nPlease select a location for the new file.");
            if (!proceed) //user doesn't really want to start a new file
                return;
            boolean finished = !noFile;
            do{
                    setFileName(getGUI().chooseFile("New file", "Set filename"));
                    if (categories != null)
                            categories.clear();
                    else
                            categories = new ArrayList<Category>();
                    if (getFileName().length() > 0){
                        getGUI().displaySearchPanel();
                        //System.out.println("save settings");
                        saveSettings();
                        finished = true;
                    }
            }while(!finished);	
        }
        
	public void newFile(){
		newFile(false);
	}
	
	public void openFile(){
		if (getGUI().isConfirmed("Are you sure you want to open a new file?")){
            		while(true){
            			setFileName(getGUI().chooseFile("Open file", "Choose file"));
            			if (!new File(getFileName()).exists()){
            				getGUI().showMessageDialog("The file you entered does not exist");
            				continue;
            			}
            			break;
            		}
            		loadData();
	            	getGUI().displaySearchPanel();
	            	saveSettings();
        	}	
	}
	
	public void saveFile(){
		if (getGUI().isConfirmed("Are you want to save this notebook somewhere else?")){
            		setFileName(getGUI().chooseFile("Save file", "Set filename"));
            		saveData();
	            	getGUI().displaySearchPanel();
	            	saveSettings();
        	}	
	}
	/*Accessor methods*/
	
	/**
	*  Provides access to the list of categories of the notebook
	*/
	public List<Category> getCategories(){
		return categories;
	}
	
	/**
	*  Provides access to the GUI object, for use in modal dialogs, eg. popup messages
	*/
	public GUI getGUI(){
		return gui;
	}
	
	/**
	*  @return  The file name where data is saved by the app
	*/
	public String getFileName(){
		return fileName;
	}
	
	/**
	*  Convenience method to grab all the notes in the notebook
	*/
	public List<Note> getAllNotes(){
		List<Note> toReturn = new ArrayList<Note>();
		for (Category cat : getCategories()){
			toReturn.addAll(cat.getNotes());
		}		
		return toReturn;
	}
	
	/**
	*  Convenience method to grab all the notes in the notebook of a certain category
	*/
	public List<Note> getAllNotes(String category){
		List<Note> toReturn = new ArrayList<Note>();
		for (Category cat : getCategories()){
			if (cat.getTitle().equals(category))
				toReturn.addAll(cat.getNotes());
		}		
		return toReturn;
	}
	
	/**
	*  Convenience method to grab all the timeline entries in the notebook
	*/
	public List<TimeLineEntry> getAllTimeLineEntries(){
		List<TimeLineEntry> toReturn = new ArrayList<TimeLineEntry>();
		for (Category cat : getCategories()){
			for (TimeLine timeline : cat.getTimeLines())
				toReturn.addAll(timeline.getEntries());
		}		
		return toReturn;
	}
	
	/**
	*  Convenience method to grab all the timeline entries in the notebook of a certain category
	*/
	public List<TimeLineEntry> getAllTimeLineEntries(String category){
		List<TimeLineEntry> toReturn = new ArrayList<TimeLineEntry>();
		for (Category cat : getCategories()){
			if (cat.getTitle().equals(category))
				for (TimeLine timeline : cat.getTimeLines())
					toReturn.addAll(timeline.getEntries());
		}		
		return toReturn;
	}
	
	/**
	*  Convenience method to grab all the timelines in the notebook
	*/
	public List<TimeLine> getAllTimeLines(){
		List<TimeLine> toReturn = new ArrayList<TimeLine>();
		for (Category cat : getCategories()){
			for (TimeLine timeline : cat.getTimeLines())
				toReturn.add(timeline);
		}		
		return toReturn;
	}
	
	/**
	*  Convenience method to grab all the timelines in the notebook of a certain category
	*/
	public List<TimeLine> getAllTimeLines(String category){
		List<TimeLine> toReturn = new ArrayList<TimeLine>();
		for (Category cat : getCategories()){
			if (cat.getTitle().equals(category))
				for (TimeLine timeline : cat.getTimeLines())
					toReturn.add(timeline);
		}		
		return toReturn;
	}
	
	/**
	*  Convenience method to determine category of supplied entry based on id
	*/
	public String getMatchingCategoryTitle(int id){
		for (Category cat : getCategories()){		
			for (TimeLine i : cat.getTimeLines()){
				if (i.getID() == id)
					return cat.getTitle();
				if (i.contains(id))
					return cat.getTitle();
			}
			for (Note note : cat.getNotes())
				if (note.getID() == id)
					return cat.getTitle();
		}
		return null;
	}
	
	/**
	*  Convenience method to determine category of supplied entry based on id
	*/
	public String getMatchingCategoryTitle(TimeLine testee){
		for (Category cat : getCategories())		
			for (TimeLine i : cat.getTimeLines())
				if (i.equals(testee))
					return cat.getTitle();
		return null;
	}
	
	/**
	*  Convenience method to determine category of supplied entry based on id
	*/
	public Category getMatchingCategory(TimeLine testee){
		for (Category cat : getCategories())		
			for (TimeLine i : cat.getTimeLines())
				if (i.equals(testee))
					return cat;
		return null;
	}
	
	/**
	*  Convenience method to determine category of supplied entry based on id
	*/
	public Category getMatchingCategory(int id){
		for (Category cat : getCategories()){		
			for (TimeLine i : cat.getTimeLines()){
				if (i.getID() == id)
					return cat;
				if (i.contains(id))
					return cat;
			}
			for (Note note : cat.getNotes())
				if (note.getID() == id)
					return cat;
		}
		return null;
	}
	
	public int getMatchingCategoryIndex(int id){
		for (int j = 0; j < getCategories().size(); j++){
			Category cat = getCategories().get(j);
			for (TimeLine i : cat.getTimeLines()){
				if (i.getID() == id)
					return j;
				if (i.contains(id))
					return j;
			}
			for (Note note : cat.getNotes())
				if (note.getID() == id)
					return j;
		}
		return -1;
	}
	
	public TimeLine getAssociatedTimeLine(int id){
		for (Category cat : getCategories())		
			for (TimeLine tl : cat.getTimeLines()){
				if (tl.getID() == id)
					return tl;
				for (TimeLineEntry tlEntry : tl.getEntries())
					if (tlEntry.getID() == id)
						return tl;
			}
		return null;
	}
	
	public int getTimeLineListIndex(TimeLine test){
		for (Category cat : getCategories())		
			for (int i = 0; i < cat.getTimeLines().size(); i++)
				if (cat.getTimeLines().get(i).equals(test))
					return i;
		return -1;	
	}
	
	/**
	*  @return  The timelineentry with the corresponding id if there is one, otherwise null
	*/
	public TimeLineEntry getMatchingTimeLineEntry(int id){
		for (Category cat : getCategories())
			for (TimeLine timeline : cat.getTimeLines())
				for (TimeLineEntry entry : timeline.getEntries())
					if (entry.getID() == id)
						return entry;
		return null;
	}
	
	/**
	*  @return  The note with the corresponding id if there is one, otherwise null
	*/
	public Note getMatchingNote(int id){
		for (Category cat : getCategories())
			for (Note note : cat.getNotes())
				if (note.getID() == id)
					return note;
		return null;
	}
	
	/**
	*  Generates the next id for a new note/timeline entry 
	*/
	public int getNextID(){
		int biggest = 0;
		for (Category cat : getCategories()){
			for (Note note : cat.getNotes())
				if (note.getID() > biggest)
					biggest = note.getID();
			for (TimeLine timeline : cat.getTimeLines()){
				if (timeline.getID() > biggest)
					biggest = timeline.getID();
				for (TimeLineEntry timelineEntry : timeline.getEntries())
					if (timelineEntry.getID() > biggest)
						biggest = timelineEntry.getID();
			}
		}
		return (biggest + 1);
	}
	
	/**
	*  Convenience method 
	*  @return  The category corresponding with the parameter
	*/
	public Category getCategoryByName(String name){
		for (Category cat : getCategories()){
			if (cat.getTitle().equals(name))
				return cat;
		}
		return null;
	}
	
	/**
	*  Adds a new note to the specified category
	*/
	public void addNote(Note toAdd, String category){
		Note duped = new Note(toAdd);
		if (getMatchingCategory(toAdd.getID()) != null &&
			!category.equals(getMatchingCategory(toAdd.getID()))) //note already exists in another category, delete it from there first
			getMatchingCategory(toAdd.getID()).removeNote(toAdd.getID());
		getCategoryByName(category).addNote(duped);
		saveData();
	}
	
	/**
	*  Adds a new timeline to the specified category
	*/
	public void addTimeLine(TimeLine toAdd, String category){
		TimeLine duped = toAdd.clone();
		if (getMatchingCategory(toAdd) != null &&
			!category.equals(getMatchingCategory(toAdd))){ //note already exists in another category, delete it from there first
			getMatchingCategory(toAdd).removeTimeLine(toAdd);
			//System.out.println("timeline already existed..");
		}
		//System.out.println("Adding " + duped + " to " + category);
		getCategoryByName(category).addTimeLine(duped);
		saveData();
	}
	
	/**
	*  Removes a note/timeline entry/timeline
	*/
	public void removeEntry(int id){
		for (Category cat : getCategories()){
			for (Note note : cat.getNotes()){
				if (note.getID() == id){
					cat.getNotes().remove(note);
					return;
				}
			}
			for (TimeLine timeline : cat.getTimeLines()){
				if (timeline.getID() == id){
					cat.getTimeLines().remove(timeline);
					return;
				}
				for (TimeLineEntry tlEntry : timeline.getEntries()){
					if (tlEntry.getID() == id){
						timeline.getEntries().remove(tlEntry);
						return;
					} 
				}
			}
		}	
	}
	
	/*GUI methods*/
	private void initialiseGUI(){
		if (gui != null)
			return;
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	            	try{
		        	gui = new GUI();
	        	}
	        	catch (Exception e){
	        		e.printStackTrace();
	        	}
	            }
	        });
	        while (gui == null);
	}
	
	/*Settings file methods*/
	private void loadSettings(){
		try{
			Properties p = new Properties();
			FileInputStream fis = new FileInputStream("./settings.ini");
            		p.load(fis);
            		getInstance().fileName = p.getProperty("filename");
            		//System.out.println(fileName);
	    		fis.close();		
	    	}
	    	catch (IOException e){
	    		e.printStackTrace();
	    	}
    	}
    	
    	public void saveSettings(){
		try{
			Properties p = new Properties();
			FileOutputStream fout = new FileOutputStream("./settings.ini");
			p.setProperty("filename",fileName);
			p.store(fout,"Notebook by Jeremy Nagel");
			fout.close();
		}catch (IOException e){
	    		e.printStackTrace();
	    	}
	}
	
	/*Variable declarations*/
	private static Main _instance;
	private GUI gui;

	private List<Category> categories;
	private String fileName;
        
        private LoadingDialog loadingDialog;

}