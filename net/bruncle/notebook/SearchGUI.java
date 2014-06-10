/*
 * SearchPanel.java
 *
 * Created on February 26, 2006, 5:25 PM
 */

package net.bruncle.notebook;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

/**
 *
 * @author  Jeremy
 */
public class SearchGUI extends javax.swing.JPanel implements ActionListener{
    
    /** Creates new form SearchPanel */
    public SearchGUI() {
        initComponents();
        setPreferredSize(new java.awt.Dimension(800, 600));
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     */
    private void initComponents() {
        lblTitle = new javax.swing.JLabel();
        txtQuery = new javax.swing.JTextField();
        lblQuery = new javax.swing.JLabel();
        scrollResults = new javax.swing.JScrollPane();
        tblResults = new javax.swing.JTable();
        btnEdit = new javax.swing.JButton();
        btnDel = new javax.swing.JButton();
        cmbCategory = new javax.swing.JComboBox(getCategoryData());
        cmbType = new javax.swing.JComboBox(new String[] {"All types", "Notes only", "Timelines only"});

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitle.setFont(new java.awt.Font("Verdana", 0, 24));
        lblTitle.setText("Search:");
        add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        txtQuery.setFont(new java.awt.Font("Verdana", 0, 11));
        add(txtQuery, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 110, -1));

        lblQuery.setFont(new java.awt.Font("Verdana", 0, 12));
        lblQuery.setText("Term:");
        add(lblQuery, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        tblResults.setModel(new javax.swing.table.DefaultTableModel(
            getData(""),
            new String [] {
                "Date", "Category", "Contents", "Type"
            }
        ));
        scrollResults.setViewportView(tblResults);

        add(scrollResults, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 700, 420));

        btnEdit.setFont(new java.awt.Font("Verdana", 0, 11));
        btnEdit.setText("Edit");
        btnEdit.setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnEdit.setContentAreaFilled(false);
        btnEdit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEdit.setIconTextGap(0);
        btnEdit.setMargin(new java.awt.Insets(0, 14, 0, 14));
        btnEdit.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnEdit.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        add(btnEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 500, 30, -1));
	btnEdit.addActionListener(this);
	
        btnDel.setFont(new java.awt.Font("Verdana", 0, 11));
        btnDel.setText("Delete");
        btnDel.setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnDel.setContentAreaFilled(false);
        btnDel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnDel.setIconTextGap(0);
        btnDel.setMargin(new java.awt.Insets(0, 14, 0, 14));
        btnDel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnDel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        add(btnDel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 500, 50, -1));
	btnDel.addActionListener(this);
	
	add(cmbCategory, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, -1, -1));
	cmbCategory.addActionListener(this);
	
	add(cmbType, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 50, -1, -1));
	cmbType.addActionListener(this);
	
	final String enterPressed = "ENTER"; 
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(enterPressed), enterPressed);
        this.getActionMap().put(enterPressed, new AbstractAction() {
            public void actionPerformed(ActionEvent ignored) {
            	if (!txtQuery.hasFocus())
    			return;
    		lastQuery = txtQuery.getText();
                tblResults.setModel(new javax.swing.table.DefaultTableModel(
	            getData(txtQuery.getText()),
	            new String [] {
	                "Date", "Category", "Contents", "Type"
	            }
	        ));
                scrollResults.getVerticalScrollBar().setValue(0);
            }
        });
    }
    
    public void actionPerformed(ActionEvent e){
    	if (e.getSource() == btnDel){
    		Main.getInstance().removeEntry(results.get(tblResults.getSelectedRow()));
    		Main.getInstance().saveData();
    		tblResults.setModel(new javax.swing.table.DefaultTableModel(
	            getData(lastQuery),
	            new String [] {
	                "Date", "Category", "Contents", "Type"
	            }
	        ));
    	}
    	if (e.getSource() == btnEdit){
    		int selectedRow = tblResults.getSelectedRow();
    		if (selectedRow == -1)
    			return;
    		if (((String)tblResults.getValueAt(selectedRow, 3)).equals("Note"))
    			Main.getInstance().getGUI().displayEditNoteGUI(Main.getInstance().getMatchingNote(results.get(tblResults.getSelectedRow())));
    		else if (((String)tblResults.getValueAt(selectedRow, 3)).equals("TimeLineEntry")){
    			TimeLine timeline = Main.getInstance().getAssociatedTimeLine(results.get(tblResults.getSelectedRow()));
    			TimeLineEntry selectedEntry = Main.getInstance().getMatchingTimeLineEntry(results.get(tblResults.getSelectedRow()));
    			Main.getInstance().getGUI().displayTimeLineGUI(timeline, selectedEntry);
    		}
    		else if (((String)tblResults.getValueAt(selectedRow, 3)).equals("TimeLine")){
    			int id = results.get(tblResults.getSelectedRow());
    			TimeLine timeline = Main.getInstance().getAssociatedTimeLine(results.get(tblResults.getSelectedRow()));
    			//System.out.println("..." + id + ":" + timeline.getTitle());
    			Main.getInstance().getGUI().displayTimeLineGUI(timeline);
    		}
    	}
    	else if (e.getSource() == cmbCategory){
    		tblResults.setModel(new javax.swing.table.DefaultTableModel(
	            getData(lastQuery),
	            new String [] {
	                "Date", "Category", "Contents", "Type"
	            }
	        ));
    	}
    	else if (e.getSource() == cmbType){
    		tblResults.setModel(new javax.swing.table.DefaultTableModel(
	            getData(lastQuery),
	            new String [] {
	                "Date", "Category", "Contents", "Type"
	            }
	        ));
    	}
    }

    /**
    *  A list of the titles of all of the categories in this notebook
    */
    public String[] getCategoryData(){
    	String[] catTitles = new String[Main.getInstance().getCategories().size() + 1];
    	catTitles[0] = "Any category";
    	int i = 1;
    	for (Category cat : Main.getInstance().getCategories()){
    		catTitles[i++] = cat.getTitle();
    	}
    	return catTitles;
    }

    /**
    *  Iterates through all of the data in the notebook, and returns matching notes/timeline entries
    */
    private Object[][] getData(String query){
    	results = new ArrayList<Integer>();
    	query = query.toLowerCase();
    	List<Note> notes = Main.getInstance().getAllNotes();
    	List<TimeLineEntry> timeLineEntries = Main.getInstance().getAllTimeLineEntries();
    	List<TimeLine> timeLines = Main.getInstance().getAllTimeLines();
    	//System.out.println(cmbType.getSelectedItem());
    	if (!cmbCategory.getSelectedItem().equals("Any category")){
    		notes = Main.getInstance().getAllNotes((String)cmbCategory.getSelectedItem());
    		timeLineEntries = Main.getInstance().getAllTimeLineEntries((String)cmbCategory.getSelectedItem());
    		timeLines = Main.getInstance().getAllTimeLines((String)cmbCategory.getSelectedItem());
    	}
    	Object[][] toReturn = new Object[notes.size() + timeLineEntries.size() + timeLines.size()][4];
    	int idx = 0;
    	for (int i = 0; i < notes.size(); i++){
    		Note note = notes.get(i);
    		if ((note.matches(query) || query.equals(""))&& (cmbType.getSelectedItem().equals("All types") || cmbType.getSelectedItem().equals("Notes only"))){
    			results.add(note.getID());
    			toReturn[idx][0] = "-";
    			toReturn[idx][1] = Main.getInstance().getMatchingCategory(note.getID()).getTitle();
    			String title = new String(note.getTitle());
    			title = (title.replaceAll("&amp;", "&"));
			title = (title.replaceAll("&lt;", "<"));
			title = (title.replaceAll("&gt;", ">"));
    			toReturn[idx][2] = title;
    			toReturn[idx][3] = "Note";
    			idx++;
    		}
    	}
    	for (int i = 0; i < timeLineEntries.size(); i++){
    		TimeLineEntry timeLineEntry = timeLineEntries.get(i);
    		//System.out.println("timeline " + timeLineEntry.getTitle() + " matches query : " + timeLineEntry.matches(query));
    		if ((timeLineEntry.matches(query) || query.equals("")) && (cmbType.getSelectedItem().equals("All types") || cmbType.getSelectedItem().equals("Timelines only"))){
    			results.add(timeLineEntry.getID());
    			toReturn[idx][0] = timeLineEntry.getDateString();
    			toReturn[idx][1] = Main.getInstance().getMatchingCategory(timeLineEntry.getID()).getTitle();
    			String title = new String(timeLineEntry.getTitle());
    			title = (title.replaceAll("&amp;", "&"));
			title = (title.replaceAll("&lt;", "<"));
			title = (title.replaceAll("&gt;", ">"));
    			toReturn[idx][2] = title;
    			toReturn[idx][3] = "TimeLineEntry";
    			idx++;
    		}
    	}
    	for (int i = 0; i < timeLines.size(); i++){
    		TimeLine timeLine = timeLines.get(i);
    		if ((timeLine.matches(query) || query.equals("")) && (cmbType.getSelectedItem().equals("All types") || cmbType.getSelectedItem().equals("Timelines only"))){
    			results.add(timeLine.getID());
    			toReturn[idx][0] = "-";
    			toReturn[idx][1] = Main.getInstance().getMatchingCategory(timeLine.getID()).getTitle();
    			toReturn[idx][2] = timeLine.getTitle();
    			toReturn[idx][3] = "TimeLine";
    			idx++;
    		}
    	}
    	return toReturn;
    }
    
    /**
    *  Is called when this gui panel is displayed
    *  method used to add information that changes each time panel is loaded, eg. category data
    */
    public void addNotify(){
    	super.addNotify();
    	cmbCategory.setModel(new javax.swing.DefaultComboBoxModel((getCategoryData())));
    	tblResults.setModel(new javax.swing.table.DefaultTableModel(
            getData(""),
            new String [] {
                "Date", "Category", "Contents", "Type"
            }
        ));
        txtQuery.requestFocusInWindow();
        txtQuery.setText("");
        scrollResults.getVerticalScrollBar().setValue(0);
    }
    
    private javax.swing.JButton btnDel;
    private javax.swing.JButton btnEdit;
    private javax.swing.JLabel lblQuery;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTable tblResults;
    private javax.swing.JScrollPane scrollResults;
    private javax.swing.JTextField txtQuery;
    private javax.swing.JComboBox cmbCategory;
    private javax.swing.JComboBox cmbType;
    
    private List<Integer> results = new ArrayList<Integer>(); //stores the ids of the matching timeline entries/notes
    private String lastQuery = "";
    
}
