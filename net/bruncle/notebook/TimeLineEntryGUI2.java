/*
 * TimeLineEntryGUI2.java
 *
 * Created on February 27, 2006, 4:09 PM
 */

package net.bruncle.notebook;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.rtf.*;
import javax.swing.event.*;
import java.text.DecimalFormat;
/**
 *
 * @author  Jeremy
 */
public class TimeLineEntryGUI2 extends javax.swing.JPanel implements ActionListener, KeyListener{
    
    /** Creates new form TimeLineEntryGUI2 */
    public TimeLineEntryGUI2() {
        initComponents();
        setPreferredSize(new java.awt.Dimension(350, 500));
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        btnNew = new javax.swing.JButton();
        cmbCategory = new javax.swing.JComboBox();
        txtTimeLineTitle = new javax.swing.JTextField();
        txtEntryTitle = new javax.swing.JTextField();
        txtEntryDate = new javax.swing.JTextField();
        scrContents = new javax.swing.JScrollPane();
        txtEntryContents = new javax.swing.JTextPane();
        btnSubmit = new javax.swing.JButton();
        btnMoveEntry = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        lblTitle = new javax.swing.JLabel();
        lblCategory = new javax.swing.JLabel();
        lblTimeLineTitle = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        btnBack = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        lblStartYear = new javax.swing.JLabel();
        txtStartYear = new javax.swing.JTextField();
        lblEndYear = new javax.swing.JLabel();
        txtEndYear = new javax.swing.JTextField();
        btnSetBounds = new javax.swing.JButton();
        lblCurrentYear = new javax.swing.JLabel();
        lblEntryContents = new javax.swing.JLabel();
        lblEntryDate = new javax.swing.JLabel();
        lblEntryTitle = new javax.swing.JLabel();
        btnDisplayAll = new javax.swing.JButton();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        setPreferredSize(new java.awt.Dimension(332, 328));
        btnNew.setBackground(new java.awt.Color(255, 255, 255));
        btnNew.setFont(new java.awt.Font("Verdana", 0, 14));
        btnNew.setText("New");
        btnNew.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnNew.setContentAreaFilled(false);
        btnNew.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnNew.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnNew.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        add(btnNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 93, 60, 30));

        add(cmbCategory, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, 170, -1));

        txtTimeLineTitle.setFont(new java.awt.Font("Verdana", 0, 12));
        add(txtTimeLineTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, 160, -1));

        txtEntryTitle.setFont(new java.awt.Font("Verdana", 0, 12));
        add(txtEntryTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, 160, -1));

        txtEntryDate.setFont(new java.awt.Font("Verdana", 0, 12));
        add(txtEntryDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 160, -1));

        scrContents.setViewportView(txtEntryContents);

        add(scrContents, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 290, 160));

        btnSubmit.setBackground(new java.awt.Color(255, 255, 255));
        btnSubmit.setFont(new java.awt.Font("Verdana", 0, 14));
        btnSubmit.setText("Submit");
        btnSubmit.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSubmit.setContentAreaFilled(false);
        btnSubmit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSubmit.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnSubmit.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        add(btnSubmit, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 120, 60, 30));

        btnMoveEntry.setBackground(new java.awt.Color(255, 255, 255));
        btnMoveEntry.setFont(new java.awt.Font("Verdana", 0, 14));
        btnMoveEntry.setText("Move");
        btnMoveEntry.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnMoveEntry.setContentAreaFilled(false);
        btnMoveEntry.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnMoveEntry.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnMoveEntry.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnMoveEntry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoveEntryActionPerformed(evt);
            }
        });

        add(btnMoveEntry, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 180, 60, 30));

        btnDelete.setBackground(new java.awt.Color(255, 255, 255));
        btnDelete.setFont(new java.awt.Font("Verdana", 0, 14));
        btnDelete.setText("Delete");
        btnDelete.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnDelete.setContentAreaFilled(false);
        btnDelete.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnDelete.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnDelete.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 150, 60, 30));

        lblTitle.setFont(new java.awt.Font("Verdana", 0, 24));
        lblTitle.setText("Add time line entry:");
        add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        lblCategory.setFont(new java.awt.Font("Verdana", 0, 14));
        lblCategory.setText("Timeline's category:");
        add(lblCategory, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 150, -1));

        lblTimeLineTitle.setFont(new java.awt.Font("Verdana", 0, 14));
        lblTimeLineTitle.setText("Timeline's title:");
        add(lblTimeLineTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 150, -1));

        add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 310, 10));

        add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, 310, 10));

        btnBack.setBackground(new java.awt.Color(255, 255, 255));
        btnBack.setFont(new java.awt.Font("Verdana", 0, 14));
        btnBack.setText("Back");
        btnBack.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnBack.setContentAreaFilled(false);
        btnBack.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnBack.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnBack.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        add(btnBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, 40, -1));

        btnNext.setBackground(new java.awt.Color(255, 255, 255));
        btnNext.setFont(new java.awt.Font("Verdana", 0, 14));
        btnNext.setText("Next");
        btnNext.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnNext.setContentAreaFilled(false);
        btnNext.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnNext.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnNext.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        add(btnNext, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, 40, -1));

        lblStartYear.setFont(new java.awt.Font("Verdana", 0, 12));
        lblStartYear.setText("Start year:");
        add(lblStartYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 390, -1, 20));

        txtStartYear.setFont(new java.awt.Font("Verdana", 0, 12));
        add(txtStartYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 390, 50, 20));

        lblEndYear.setFont(new java.awt.Font("Verdana", 0, 12));
        lblEndYear.setText("End year:");
        add(lblEndYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 420, -1, 20));

        txtEndYear.setFont(new java.awt.Font("Verdana", 0, 12));
        add(txtEndYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 420, 50, 20));

        btnSetBounds.setBackground(new java.awt.Color(255, 255, 255));
        btnSetBounds.setFont(new java.awt.Font("Verdana", 0, 14));
        btnSetBounds.setText("Set");
        btnSetBounds.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSetBounds.setContentAreaFilled(false);
        btnSetBounds.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSetBounds.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnSetBounds.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        add(btnSetBounds, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 400, 40, 30));

        lblCurrentYear.setFont(new java.awt.Font("Verdana", 0, 12));
        lblCurrentYear.setText("Year:");
        add(lblCurrentYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, -1, 20));

        lblEntryContents.setFont(new java.awt.Font("Verdana", 0, 12));
        lblEntryContents.setText("Contents:");
        add(lblEntryContents, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, -1));

        lblEntryDate.setFont(new java.awt.Font("Verdana", 0, 12));
        lblEntryDate.setText("Date (dd.mm.yyyy)");
        add(lblEntryDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, -1, -1));

        lblEntryTitle.setFont(new java.awt.Font("Verdana", 0, 12));
        lblEntryTitle.setText("Title:");
        add(lblEntryTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));

        btnDisplayAll.setBackground(new java.awt.Color(255, 255, 255));
        btnDisplayAll.setFont(new java.awt.Font("Verdana", 0, 14));
        btnDisplayAll.setText("Display all");
        btnDisplayAll.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnDisplayAll.setContentAreaFilled(false);
        btnDisplayAll.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnDisplayAll.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnDisplayAll.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        add(btnDisplayAll, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 450, 90, 30));

    }// </editor-fold>//GEN-END:initComponents

    private void btnMoveEntryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoveEntryActionPerformed
	java.util.List<String> availableTimeLines = new java.util.ArrayList<String>();
	for (Category cat : Main.getInstance().getCategories())
		for (TimeLine tl : cat.getTimeLines())
			availableTimeLines.add(tl.getTitle());
	Object[] choices = availableTimeLines.toArray();
	String toMoveTo = (String)javax.swing.JOptionPane.showInputDialog(Main.getInstance().getGUI(), "Choose the timeline you want to move this entry to",
		"Move this entry to another timeline", javax.swing.JOptionPane.QUESTION_MESSAGE,
		null, choices, choices[0]);
		
	TimeLineEntry entry = new TimeLineEntry(Main.getInstance().getGUI().getTimeLineGUI().getSelectedEntry());
	Main.getInstance().getGUI().getTimeLineGUI().deleteEntry();
	
	for (Category cat : Main.getInstance().getCategories())
		for (TimeLine tl : cat.getTimeLines())
			if (tl.getTitle().equals(toMoveTo))
				tl.getEntries().add(entry);
	
    }//GEN-LAST:event_btnMoveEntryActionPerformed
    
    //Allows user to make style changes
    protected void addBindings() {
        InputMap inputMap = txtEntryContents.getInputMap();
        //Ctrl-b to create bold text
        KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_B, Event.CTRL_MASK);
        inputMap.put(key, new RTFEditorKit.BoldAction());

        //Ctrl-i to create italicised text
        key = KeyStroke.getKeyStroke(KeyEvent.VK_I, Event.CTRL_MASK);
        inputMap.put(key, new RTFEditorKit.ItalicAction());

        //Ctrl-u to create underlined text
        key = KeyStroke.getKeyStroke(KeyEvent.VK_U, Event.CTRL_MASK);
        inputMap.put(key, new RTFEditorKit.UnderlineAction());
        
        //Ctrl-shift-greater than to increase font
        key = KeyStroke.getKeyStroke(46, 3);
        class IncreaseFontAction extends AbstractAction {
            public IncreaseFontAction(){
            }
	    public void actionPerformed(ActionEvent e) {
	    	fontSize = StyleConstants.getFontSize(editor.getInputAttributes());
	        fontSize += 2;	
        	new RTFEditorKit.FontSizeAction("increase font", fontSize).actionPerformed(null);
	    }
	}
        inputMap.put(key, new IncreaseFontAction());
        
        //Ctrl-shift-less than to decrease font
        key = KeyStroke.getKeyStroke(44, 3);
        class DecreaseFontAction extends AbstractAction {
            public DecreaseFontAction(){
            }
	    public void actionPerformed(ActionEvent e) {
	    	fontSize = StyleConstants.getFontSize(editor.getInputAttributes());
	        fontSize -= 2;	
        	new RTFEditorKit.FontSizeAction("decrease font", fontSize).actionPerformed(null);
	    }
	}
        inputMap.put(key, new DecreaseFontAction());
        
        //alt-r to make red text
        key = KeyStroke.getKeyStroke(KeyEvent.VK_R, Event.ALT_MASK);
        inputMap.put(key, new RTFEditorKit.ForegroundAction("Red", Color.red));
        
        //alt-g to make green text
        key = KeyStroke.getKeyStroke(KeyEvent.VK_G, Event.ALT_MASK);
        inputMap.put(key, new RTFEditorKit.ForegroundAction("Green", Color.GREEN));
        
        //alt-b to make black text
        key = KeyStroke.getKeyStroke(KeyEvent.VK_B, Event.ALT_MASK);
        inputMap.put(key, new RTFEditorKit.ForegroundAction("Black", Color.BLACK));
        
        //alt-c to make cyan text
        key = KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.ALT_MASK);
        inputMap.put(key, new RTFEditorKit.ForegroundAction("Cyan", Color.CYAN));
        
        //alt-n to make blue
        key = KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.ALT_MASK);
        inputMap.put(key, new RTFEditorKit.ForegroundAction("Blue", Color.BLUE));
        
        new RTFEditorKit.ForegroundAction("Black", Color.BLACK).actionPerformed(null);
    }
    
    /**
    *  Adds properties to gui components, which cannot be set in netbeans
    */
    public void addProperties(){
    	txtTimeLineTitle.setText(Main.getInstance().getGUI().getTimeLineGUI().getData().getTitle());
    	setBoundsText((Main.getInstance().getGUI().getTimeLineGUI().getStartYear()), (Main.getInstance().getGUI().getTimeLineGUI().getEndYear()));
    	btnSubmit.addActionListener(this);
    	cmbCategory.setModel(new javax.swing.DefaultComboBoxModel(getCategoryData()));
    	if (Main.getInstance().getMatchingCategory(Main.getInstance().getGUI().getTimeLineGUI().getData()) != null)
    		cmbCategory.setSelectedItem(Main.getInstance().getMatchingCategory(Main.getInstance().getGUI().getTimeLineGUI().getData()).getTitle());
    	cmbCategory.addActionListener(this);
    	txtTimeLineTitle.addKeyListener(this);
    	txtEntryContents.addKeyListener(this);
    	btnBack.addActionListener(this);
    	btnNext.addActionListener(this);
    	btnSetBounds.addActionListener(this);
    	btnNew.addActionListener(this);
    	btnDelete.addActionListener(this);
    	btnDisplayAll.addActionListener(this);
    	addBindings();
        txtEntryContents.setEditorKit(editor);
    }
    
    /**
    *  A list of the titles of all of the categories in this notebook
    */
    public String[] getCategoryData(){
    	String[] catTitles = new String[Main.getInstance().getCategories().size()];
    	int i = 0;
    	for (Category cat : Main.getInstance().getCategories()){
    		catTitles[i++] = cat.getTitle();
    	}
    	return catTitles;
    }
    
    /**
    *  @return  Whether it is safe to display a different entry - 
    */
    public boolean canSwitchEntries(){
    	    if (hasChanged) //if we change the data now, changes will be lost
    		if (!Main.getInstance().getGUI().isConfirmed("Are you sure you want to view another entry - the changes you made will be lost"))
    			return false;
    		return true;
    }
    
    /**
    *  Gives the GUI data to display
    */
    public void setData(TimeLineEntry entry){
    	if (entry == null){
    		return;
    	}
    	String contents = new String(entry.getContents());
    	String title = entry.getTitle();
    	txtEntryTitle.setText(title);
    	txtEntryDate.setText(entry.getDateString());
    	txtEntryContents.setText("");
	try {
		java.io.StringReader reader = new java.io.StringReader(contents);
		editor.read(reader, txtEntryContents.getDocument(), 0);
	}
	catch(Exception e ){
		e.printStackTrace();
	}
    	id = entry.getID();
    	editMode = true;
    	hasChanged = false;
    }
    
    public void setCurrentYear(int year){
    	lblCurrentYear.setText("Year: " + year);
    }
    
    public void resetData(){
    	txtEntryTitle.setText("");
    	txtEntryDate.setText("");
    	txtEntryContents.setText("");
    }
    
    public void actionPerformed(ActionEvent e){
    	if ((System.currentTimeMillis() - lastAction) < 1000){
            System.out.println("Slow down buddy");
            return;
        }
    	lastAction = System.currentTimeMillis();
    	if (e.getSource() == btnSubmit){
    		String title = txtEntryTitle.getText();
    		if (title.length() <= 2){
    			javax.swing.JOptionPane.showMessageDialog(Main.getInstance().getGUI(), "You did not enter a value for title");
    			return;
    		}
    		String contents = "";
    		try{
    			StringOutputStream writer = new StringOutputStream();
    			editor.write(writer, txtEntryContents.getDocument(), 0, txtEntryContents.getDocument().getLength());
    			contents = writer.getSink(); 
    		}
    		catch(Exception ex){
    			ex.printStackTrace();
    		}
    		if (contents.length() <= 2){
    			javax.swing.JOptionPane.showMessageDialog(Main.getInstance().getGUI(), "You did not enter a value for contents");
    			return;
    		}
    		java.util.Calendar date = TimeLineEntry.getCalendarFromString(txtEntryDate.getText());
    		if (date == null){ //invalid date, msg displayed in above method
    			System.out.println("invalid date: " + txtEntryDate.getText());
    			return; 
    		}
    		if (!editMode)
    			id = Main.getInstance().getNextID();
    		TimeLineEntry newEntry = new TimeLineEntry(title, contents, date, id);
       		if (!editMode)
    			Main.getInstance().getGUI().getTimeLineGUI().addEntry(newEntry);
    		else
    			Main.getInstance().getGUI().getTimeLineGUI().editEntry(newEntry);
    		hasChanged = false;
    		Main.getInstance().getGUI().getTimeLineGUI().displayEntry(null);
    		txtEntryTitle.setText("");
    		txtEntryContents.setText("");
    		txtEntryDate.setText("");
    		editMode = false;
    	}
    	else if (e.getSource() == cmbCategory){
    		Main.getInstance().getGUI().getTimeLineGUI().setCategory((String)cmbCategory.getSelectedItem());
    	}
    	else if (e.getSource() == btnSetBounds){
    		try{
    			double startYear = Double.parseDouble(txtStartYear.getText());
    			double endYear = Double.parseDouble(txtEndYear.getText());
    			if (startYear > endYear){
    				Main.getInstance().getGUI().showMessageDialog("Start year must be less than end year");
    				return;
    			}
    			Main.getInstance().getGUI().getTimeLineGUI().setBounds(startYear, endYear);
    		}
    		catch(Exception ex){
    			Main.getInstance().getGUI().showMessageDialog("Invalid year inputted");
    		}
    	}
    	else if (e.getSource() == btnBack){
    		Main.getInstance().getGUI().getTimeLineGUI().showPreviousEntry();
    	}
    	else if (e.getSource() == btnNext){
    		Main.getInstance().getGUI().getTimeLineGUI().showNextEntry();
    	}
    	else if (e.getSource() == btnNew){
    		hasChanged = false;
    		Main.getInstance().getGUI().getTimeLineGUI().displayEntry(null);
    		txtEntryTitle.setText("");
    		txtEntryContents.setText("");
    		txtEntryDate.setText("");
    		editMode = false;
    	}
    	else if (e.getSource() == btnDelete){
    		Main.getInstance().getGUI().getTimeLineGUI().deleteEntry();
    	}
    	else if (e.getSource() == btnDisplayAll){
    		Main.getInstance().getGUI().getTimeLineGUI().displayAll();
    	}
    }
    
    public void keyPressed(KeyEvent e){
    	if (e.getSource() == txtTimeLineTitle){
    		if (e.getKeyCode() == KeyEvent.VK_ENTER){
    			Main.getInstance().getGUI().getTimeLineGUI().setTitle(txtTimeLineTitle.getText());
    			Main.getInstance().saveData();
    		}
    	}
    }
    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){
    	if (e.getSource() == txtEntryContents)
    		hasChanged = true;
    }
    
    /**
    * Sets the start and end year text boxes to a particular range
    */
    public void setBoundsText(double start, double end){
    	DecimalFormat df = new DecimalFormat("#.##");
    	txtEndYear.setText(df.format(end) + "");
    	txtStartYear.setText(df.format(start) + "");
    }	
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDisplayAll;
    private javax.swing.JButton btnMoveEntry;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnSetBounds;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JComboBox cmbCategory;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblCategory;
    private javax.swing.JLabel lblCurrentYear;
    private javax.swing.JLabel lblEndYear;
    private javax.swing.JLabel lblEntryContents;
    private javax.swing.JLabel lblEntryDate;
    private javax.swing.JLabel lblEntryTitle;
    private javax.swing.JLabel lblStartYear;
    private javax.swing.JLabel lblTimeLineTitle;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JScrollPane scrContents;
    private javax.swing.JTextField txtEndYear;
    private javax.swing.JTextPane txtEntryContents;
    private javax.swing.JTextField txtEntryDate;
    private javax.swing.JTextField txtEntryTitle;
    private javax.swing.JTextField txtStartYear;
    private javax.swing.JTextField txtTimeLineTitle;
    // End of variables declaration//GEN-END:variables
	
    private boolean editMode;
    private boolean hasChanged;
    private int id;
    private RTFEditorKit editor = new RTFEditorKit();
    int fontSize = 12;
    long lastAction = System.currentTimeMillis();
}