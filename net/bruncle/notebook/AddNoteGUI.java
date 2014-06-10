/*
 * AddNoteGUI.java
 *
 * Created on February 26, 2006, 5:41 PM
 */

package net.bruncle.notebook;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.rtf.*;
import javax.swing.event.*;
/**
 *
 * @author  Jeremy
 */
public class AddNoteGUI extends javax.swing.JPanel implements ActionListener{
    
    /** Creates new form AddNoteGUI */
    public AddNoteGUI() {
        initComponents();
        setPreferredSize(new java.awt.Dimension(500, 650));
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     */
    private void initComponents() {
        lblTitle = new javax.swing.JLabel();
        lblCategory = new javax.swing.JLabel();
        cmbCategory = new javax.swing.JComboBox(getCategories());
        lblNoteTitle = new javax.swing.JLabel();
        txtNoteTitle = new javax.swing.JTextField();
        lblNoteContents = new javax.swing.JLabel();
        scrContents = new javax.swing.JScrollPane();
        txtNoteContents = new javax.swing.JEditorPane();
        btnSubmit = new javax.swing.JButton();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        setMinimumSize(new java.awt.Dimension(500, 650));
        setPreferredSize(new java.awt.Dimension(500, 650));
        lblTitle.setFont(new java.awt.Font("Verdana", 0, 24));
        lblTitle.setText("Add note:");
        add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        lblCategory.setFont(new java.awt.Font("Verdana", 0, 11));
        lblCategory.setText("Category:");
        add(lblCategory, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        add(cmbCategory, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 120, -1));

        lblNoteTitle.setFont(new java.awt.Font("Verdana", 0, 11));
        lblNoteTitle.setText("Title:");
        add(lblNoteTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        txtNoteTitle.setFont(new java.awt.Font("Verdana", 0, 11));
        add(txtNoteTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, 110, -1));

        lblNoteContents.setFont(new java.awt.Font("Verdana", 0, 11));
        lblNoteContents.setText("Contents:");
        add(lblNoteContents, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        scrContents.setViewportView(txtNoteContents);
        addBindings();
        txtNoteContents.setEditorKit(editor);
        
        add(scrContents, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 450, 430));

        btnSubmit.setFont(new java.awt.Font("Verdana", 0, 11));
        btnSubmit.setText("Submit");
        btnSubmit.setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnSubmit.setContentAreaFilled(false);
        btnSubmit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSubmit.setIconTextGap(0);
        btnSubmit.setMargin(new java.awt.Insets(0, 14, 0, 14));
        btnSubmit.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnSubmit.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        add(btnSubmit, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 50, 70, 60));
	btnSubmit.addActionListener(this);
    }
    
    public void actionPerformed(ActionEvent e){
    	if (e.getSource() == btnSubmit){
    		/*if (txtNoteContents.getText() == null){
    			System.out.println("asdsadsa");
    			return;
    		}*/
    		String contents = "";
    		try{
    			StringOutputStream writer = new StringOutputStream();
    			editor.write(writer, txtNoteContents.getDocument(), 0, txtNoteContents.getDocument().getLength());
    			contents = writer.getSink(); 
    		}
    		catch(Exception ex){
    			ex.printStackTrace();
    		}
    		if (txtNoteTitle.getText().length() < 2){
    			Main.getInstance().getGUI().showMessageDialog("You have not filled out the title field");
    			return;
    		}
    		else if (contents.length() < 2){
    			Main.getInstance().getGUI().showMessageDialog("You have not filled out the contents field");
    			return;
    		}
    		String title = txtNoteTitle.getText();
    		int id = Main.getInstance().getNextID();
    		if (toEdit != null) //ie we're editing a note
    			id = toEdit.getID();
    		String category = (String)cmbCategory.getSelectedItem();
    		Main.getInstance().addNote(new Note(title, contents, id), category);	
    		Main.getInstance().saveData();
    		txtNoteTitle.setText("");
    		txtNoteContents.setText("");
    		toEdit = null;
    	}
    }
    
    //Allows user to make style changes
    protected void addBindings() {
        InputMap inputMap = txtNoteContents.getInputMap();
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
    *  @return  An array of all of the categories' names
    */
    public String[] getCategories(){
    	String[] categories = new String[Main.getInstance().getCategories().size()];
    	for (int i = 0; i < categories.length; i++)
    		categories[i] = Main.getInstance().getCategories().get(i).getTitle();
    	return categories;
    }
    
    public void setEditMode(Note entry){
    	lblTitle.setText("Edit note:");
    	this.toEdit = entry;
    	String contents = new String(entry.getContents());
    	String title = new String(entry.getTitle());
    	txtNoteTitle.setText(title);
    	txtNoteContents.setText("");
    	// Load an RTF file into the editor
	try {
		java.io.StringReader reader = new java.io.StringReader(contents);
		editor.read(reader, txtNoteContents.getDocument(), 0 );
	}
	catch(Exception e ){
		e.printStackTrace();
	}
    	cmbCategory.setSelectedIndex(Main.getInstance().getMatchingCategoryIndex(toEdit.getID()));
    }
    
    /**
    *  Is called when this gui panel is displayed
    *  method used to add information that changes each time panel is loaded, eg. category data
    */
    public void addNotify(){
    	super.addNotify();
    	cmbCategory.setModel(new javax.swing.DefaultComboBoxModel((getCategories())));
    }
    
    public void reset(){
    	cmbCategory.setModel(new javax.swing.DefaultComboBoxModel((getCategories())));
    	txtNoteContents.setText("");
    	txtNoteTitle.setText("");
    	toEdit = null;
    }
    
    private javax.swing.JButton btnSubmit;
    private javax.swing.JComboBox cmbCategory;
    private javax.swing.JLabel lblCategory;
    private javax.swing.JLabel lblNoteContents;
    private javax.swing.JLabel lblNoteTitle;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JScrollPane scrContents;
    private javax.swing.JEditorPane txtNoteContents;
    private javax.swing.JTextField txtNoteTitle;
    
    private Note toEdit;
    private RTFEditorKit editor = new RTFEditorKit();
    int fontSize = 12;
}
