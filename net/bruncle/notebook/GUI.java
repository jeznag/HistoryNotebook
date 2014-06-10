package net.bruncle.notebook;

import javax.swing.*;
import java.awt.event.*;

/**
*  Class, which controls the GUI of the application
*  @author  Jeremy Nagel
*/

public class GUI extends JFrame{
	
	public GUI(){
		super("Notebook - by Jeremy Nagel");
		initGUI();
	}
	
	private void initGUI(){
		addMenuBar();
		JFrame.setDefaultLookAndFeelDecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);           		              		
		setVisible(true);
	}
	
	/**
	*  Sets up the menu bar of the application
	*/
	private void addMenuBar(){
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(createFileMenu());
		menuBar.add(createViewMenu());
		setJMenuBar(menuBar);
	}
	
	/**
	*  Sets up the file menu of the app
	*/
	private JMenu createFileMenu(){
		JMenu menu = new JMenu("File");
		JMenuItem newFile = new JMenuItem("New");
		newFile.setAccelerator(KeyStroke.getKeyStroke(
        		KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        	menu.add(newFile);
        	newFile.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent event) {
		        	Main.getInstance().newFile();
		        }
	        });
		JMenuItem open = new JMenuItem("Open");
		open.setAccelerator(KeyStroke.getKeyStroke(
        		KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        	menu.add(open);
        	open.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent event) {
		        	Main.getInstance().openFile();
		        }
	        });
        	JMenuItem save = new JMenuItem("Save");
		save.setAccelerator(KeyStroke.getKeyStroke(
        		KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        	menu.add(save);
        	save.setActionCommand("save");
        	save.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent event) {
		        	Main.getInstance().saveFile();	
		        }
	        });
        	JMenuItem quit = new JMenuItem("Exit");
		quit.setAccelerator(KeyStroke.getKeyStroke(
        		KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        	menu.add(quit);
        	quit.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent event) {
		        	System.exit(0);	
		        }
	        });
	        return menu;
	}
	
	/**
	*  Sets up the view menu of the app
	*/
	private JMenu createViewMenu(){
		JMenu menu = new JMenu("View");
		JMenuItem searchItem = new JMenuItem("Search");
		searchItem.setAccelerator(KeyStroke.getKeyStroke(
        		KeyEvent.VK_F, ActionEvent.CTRL_MASK));
        	menu.add(searchItem);
        	searchItem.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent event) {
		        	displaySearchPanel();
		        }
	        });
	        JMenuItem timeLineItem = new JMenuItem("Add TimeLine");
		timeLineItem.setAccelerator(KeyStroke.getKeyStroke(
        		KeyEvent.VK_T, ActionEvent.CTRL_MASK));
        	menu.add(timeLineItem);
        	timeLineItem.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent event) {
		        	displayTimeLineGUI(null);
		        }
	        });
	        JMenuItem newNoteItem = new JMenuItem("Add Note");
		newNoteItem.setAccelerator(KeyStroke.getKeyStroke(
        		KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        	menu.add(newNoteItem);
        	newNoteItem.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent event) {
		        	displayAddNoteGUI();
		        }
	        });
		JMenuItem editCategoriesItem = new JMenuItem("Edit categories");
		editCategoriesItem.setAccelerator(KeyStroke.getKeyStroke(
        		KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        	menu.add(editCategoriesItem);
        	editCategoriesItem.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent event) {
		        	displayEditCategoryGUI();
		        }
	        });
		return menu;
	}
	
	/**
	*  Displays the search panel, which acts as a home page
	*/
	public void displaySearchPanel(){
		if (searchPanel == null)
			searchPanel = new SearchGUI();
		changeScreen(searchPanel);
	}
	
	/**
	*  Displays a gui representation of the supplied timeline
	*/
	public void displayTimeLineGUI(TimeLine data){
		if (Main.getInstance().getCategories().size() == 0){
    			Main.getInstance().getGUI().showMessageDialog("You must create a category before you can create a new timeline");
    			Main.getInstance().getGUI().displayEditCategoryGUI();
    			return;
    		}
		if (timeLineEntryGUI == null)
			timeLineEntryGUI = new TimeLineEntryGUI2();
		timeLineEntryGUI.resetData();
		timeLineGUI = new TimeLineGUI(data, timeLineEntryGUI);
		timeLineEntryGUI.addProperties();
		changeScreen(timeLineGUI);
	}
	
	/**
	*  Displays a gui representation of the supplied timeline, with the supplied entry selected
	*/
	public void displayTimeLineGUI(TimeLine data, TimeLineEntry selected){
		if (data == null)
			return;
		if (timeLineEntryGUI == null)
			timeLineEntryGUI = new TimeLineEntryGUI2();
		timeLineEntryGUI.resetData();
		timeLineGUI = new TimeLineGUI(data, timeLineEntryGUI);
		timeLineEntryGUI.addProperties();
		if (data.contains(selected))
			timeLineGUI.displayEntry(selected);
		changeScreen(timeLineGUI);
	}
	
	/**
	*  Displays the add note gui screen
	*/
	public void displayAddNoteGUI(){
		if (Main.getInstance().getCategories().size() == 0){
			Main.getInstance().getGUI().showMessageDialog("You must create a category before you can add a new note");
			Main.getInstance().getGUI().displayEditCategoryGUI();
			return;
		}
		if (addNoteGUI == null)
			addNoteGUI = new AddNoteGUI();
		addNoteGUI.reset();
		changeScreen(addNoteGUI);
	}
	
	/**
	*  Displays the add note gui screen, on edit mode
	*/
	public void displayEditNoteGUI(Note toEdit){
		if (addNoteGUI == null)
			addNoteGUI = new AddNoteGUI();
		changeScreen(addNoteGUI);
		addNoteGUI.setEditMode(toEdit);
	}
	
	/**
	*  Displays the edit category screen
	*/
	public void displayEditCategoryGUI(){
		if (categoryForm == null)
			categoryForm = new EditCategoryForm();
		changeScreen(categoryForm);
	}
	
	private void changeScreen(JPanel panel){
		try{
			setContentPane(panel);
			setSize(getContentPane().getPreferredSize());	
			validate();
		}
		catch (Exception e){ 
			e.printStackTrace(); 
		}
	}
	
	public TimeLineGUI getTimeLineGUI(){
		return timeLineGUI;
	}
	
	/**
	*  Convenience method to display a file chooser dialog
	*  @arg  title  The title of the dialog
	*  @arg  buttonText  The text on the button that the user clicks, eg. open, save, set
	*/
	public String chooseFile(String title, String buttonText){
		JFileChooser fc = new JFileChooser("./");
        	fc.addChoosableFileFilter(new OnlyExt("xml", "Note book files"));
        	fc.setDialogTitle(title);
		int returnVal = fc.showDialog(this, buttonText);
            	if (returnVal == JFileChooser.APPROVE_OPTION) {
            		String filename = fc.getSelectedFile().toString();
            		if (!filename.endsWith(".xml"))
            			filename += ".xml";
            		return filename;
            	}
            	return null;	
	}
	
	/**
	*  Convenience method to display a confirm dialog
	*/ 
	public boolean isConfirmed(String text){
		int response = JOptionPane.showConfirmDialog(this, text);
            	return (response == javax.swing.JOptionPane.YES_OPTION);
	}
	
	/**
	*  Convenience method to display a message dialog
	*/
	public void showMessageDialog(String text){
		JOptionPane.showMessageDialog(this, text);
	}
	
	private AddNoteGUI addNoteGUI;
	private TimeLineGUI timeLineGUI;
	private TimeLineEntryGUI2 timeLineEntryGUI;
	private SearchGUI searchPanel;
	private EditCategoryForm categoryForm;
}