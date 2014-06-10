
package net.bruncle.notebook;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.List;
import java.util.ArrayList;
import java.text.DecimalFormat;
/**
 *
 * @author  Jeremy
 */
public class TimeLineGUI extends javax.swing.JPanel implements MouseListener, MouseMotionListener{
    
    private TimeLine data;
    private double startYear = 0;
    private double endYear = 0;
    private int lastX, lastY, endX, endY = -1;
    private boolean drawZoomBox = false;
    private TimeLineEntryGUI2 entryGUI;
    private TimeLineEntry selectedEntry;
    
    public TimeLineGUI(TimeLine datag, TimeLineEntryGUI2 entryGUI) {
    	if (datag != null)
    		this.data = datag;
    	else{
    		this.data = new TimeLine("", Main.getInstance().getNextID());
    		Main.getInstance().addTimeLine(this.data, Main.getInstance().getCategories().get(0).getTitle());
    	}	
    	this.entryGUI = entryGUI;
    	setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
	endYear = (data.getEntries().size() > 0) ? (double)(data.getLastEntry().getDoubleYear() + 1.25D) : 100D;
	startYear = (data.getEntries().size() > 0) ? (double)(data.getFirstEntry().getDoubleYear() - 0.25D): 0D;
    	setPreferredSize(new java.awt.Dimension(1020, 600));
    	add(entryGUI, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 0, entryGUI.getPreferredSize().width,
    		entryGUI.getPreferredSize().height));
    	entryGUI.setBoundsText(startYear, endYear);
	addMouseListener(this);
	addMouseMotionListener(this);
	setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));
	selectedEntry = null;
    }
    
    public TimeLineGUI(){
    	data = new TimeLine("", Main.getInstance().getNextID());
    	entryGUI = new TimeLineEntryGUI2();
    	setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
    	setPreferredSize(new java.awt.Dimension(1280, 500));
    	add(entryGUI, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 0, entryGUI.getPreferredSize().width,
    		entryGUI.getPreferredSize().height));
	addMouseListener(this);
	repaint();
    }
    
    /**
    *  Overrides painting method of panel, used to draw timeline onto panel
    */
    public void paintComponent(Graphics g) {
  	super.paintComponent(g);
  	if (data == null)
  		return;
  	Graphics2D gfx = (Graphics2D) g;
  	gfx.draw(new Line2D.Double(0, getSize().height / 2,
                          entryGUI.getX() - 20, getSize().height / 2)); //draws timeline
	drawYears(gfx);
	drawTicks(gfx);
	drawTimeLineEntries(gfx);
	drawZoomBox(gfx);
    }
    
    /**
    *  Calculates the number of pixels each year can occupy on the time line
    */
    private double getScale(){
      return ((entryGUI.getX() - 100) / ((Math.abs(endYear - startYear) > 0) ? Math.abs(endYear - startYear) : 1));
    }

    /**
    *  Draws the year labels onto the timeline
    */
    private void drawYears(Graphics2D gfx){
    	boolean above = false;
	int y = (getSize().height / 2); //draw years slightly above the timeline
	gfx.setFont(new java.awt.Font("Verdana", 0, 10));
	for (String year : getMilestoneYears()){
		int x = (int)(Math.abs((startYear) - (Double.parseDouble(year))) * getScale());
		gfx.drawString(year,x, above ? y - ((int)(((0.010D) * getSize().width))): y + ((int)(((0.020D) * getSize().width))));
		above = !above;
	}		
    }
    
    /**
    *  @return  A list of all the important years on the timeline, eg. the start, end, and every 100 years in between
    */
    private List<String> getMilestoneYears(){
        List<String> years = new ArrayList<String>();
	//creates a list of strings which will be used as text for year labels, for the start and end year, 
	//and every 10 years between that
	int distBetween = (int)(((double)(endYear - startYear)) / 10D);
	if (distBetween == 0)
		distBetween = 1;
	for (double i = startYear; i < (endYear); i += distBetween){
		if (((double)((int)i)) < startYear)
			continue;
		years.add("" + ((int)i));
	}
	//years.add(((int)endYear) + "");
	return years;
    }
    
    /**
    *  Draws the ticks onto the timeline
    */
    private void drawTicks(Graphics2D gfx){
    	boolean above = false;
    	int y = (getSize().height / 2); //draw ticks on the timeline
	for (String year : getMilestoneYears()){
		int x = (int)(Math.abs((startYear) - (Double.parseDouble(year))) * getScale());
		gfx.draw(new Line2D.Double(x, y, x, above ? y - ((int)(((0.010D) * getSize().width))) : y + ((int)(((0.010D) * getSize().width))))); //draws ticks as lines on the timeline
		above = !above;
	}	
    }
	
    /**
    *  Draws the zoombox on the timeline - when the user drags their mouse a dotted rectangle is drawn to indicate where they are zooming on
    */	
    private void drawZoomBox(Graphics2D gfx){
    	if (!drawZoomBox)
    		return;
    	gfx.setColor(Color.BLACK);
    	gfx.setStroke(new BasicStroke(1f,BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,1f,new float[] {5f, 7f }, 0f));
    	drawRectangle(lastX, lastY, endX, endY, gfx);
    	gfx.setStroke(new BasicStroke(1));
    }
    
    /**
    *  Draws a rectangle from point to point
    */
    private void drawRectangle(double x1, double y1, double x2, double y2, Graphics2D gfx){
    	gfx.draw(new Line2D.Double(x1, y1, x2, y1));
    	gfx.draw(new Line2D.Double(x2, y1, x2, y2));
    	gfx.draw(new Line2D.Double(x2, y2, x1, y2));
    	gfx.draw(new Line2D.Double(x1, y2, x1, y1));
    }
    
    /**
    *  Draws the entries of the timeline as circles
    */
    private void drawTimeLineEntries(Graphics2D gfx){
    	gfx.setFont(new java.awt.Font("Verdana", 0, (int)(((0.009D) * getSize().width))));
    	if (data == null){
    		return;
    	}
    	if (data.getEntries().size() == 0)
    		return;
	boolean goingIn = true;
	int edgeDist = getSize().height / 20;
	for (TimeLineEntry entry : data.getEntries()){
		if (entry.getDoubleYear() > endYear || entry.getDoubleYear() < startYear)
			continue;
		int x = (int)((Math.abs(((double)startYear) - ((double)entry.getDoubleYear())) * getScale()));
		int y = (goingIn) ? (edgeDist) : getSize().height - edgeDist;
		if (entry.equals(selectedEntry))	
			gfx.setColor(Color.RED);
		else
			gfx.setColor(Color.YELLOW);
		gfx.fillOval(x, (getSize().height / 2) - 5, ((int)(((0.005D) * getSize().width))), ((int)(((0.005D) * getSize().width))));
		gfx.setColor(Color.BLUE);
		gfx.drawString(entry.getTitle(), x, y);
		gfx.setColor(Color.GREEN);
		gfx.draw(new Line2D.Double(x + (getSize().height / 224), goingIn ? y + (getSize().height / 64) : y - (getSize().height / 64), x + (getSize().height / 224), goingIn ? (getSize().height / 2) - (getSize().height / 64) : (getSize().height / 2) + (getSize().height / 64))); //draws ticks as lines on the timeline
		edgeDist += getSize().height / 20;
		if (edgeDist >= ((getSize().height / 2) - (getSize().height / 20))){
			goingIn = !goingIn;
			edgeDist = getSize().height / 20;
		}
	}
    }

    /**
    *  Mandatory mouse listening methods:
    */

    /**
    *  Invoked when the mouse button has been clicked (pressed and released) on a component. 
    */
    public void mouseClicked(MouseEvent e){
    	if (e.getX() >= entryGUI.getX())
    		return;
	displayEntry(data.getClosestEntry(getYearFromMousePos(e.getX()))); 
    } 
    
    /**
    *  Invoked when the mouse enters a component. 
    */
    public void mouseEntered(MouseEvent e){} 
    
    /**
    *  Invoked when the mouse exits a component. 
    */
    public void mouseExited(MouseEvent e){} 

    /**
    *  Invoked when the mouse is pressed over a component. 
    */
    public void mousePressed(MouseEvent e){
    	lastX = e.getX();
    	lastY = e.getY();
    } 
   
    /**
    *  Invoked when the mouse is released over a component. 
    */
    public void mouseReleased(MouseEvent e){
    	repaint();
    	double finishXYear = getYearFromMousePos(e.getX());
    	double startXYear = getYearFromMousePos(lastX);
    	if (!drawZoomBox) //user didn't drag mouse, just clicked
    		return;
    	drawZoomBox = false;
    	if (finishXYear == -1 || startXYear == -1)
    		return;
    	if (startXYear > finishXYear){
    		double cloneFinish = finishXYear;
    		finishXYear = startXYear;
    		startXYear = cloneFinish;
    	}
    	setBounds(startXYear, finishXYear);
    	lastX = e.getX();
    	lastY = e.getY();
    } 
    
    public void mouseMoved(MouseEvent e){
    	if (e.getX() <= entryGUI.getX())
    		entryGUI.setCurrentYear((int)(((double)startYear) + (((double)e.getX()) / getScale())));
    }
    
    /**
    *  Draws zoom box
    */
    public void mouseDragged(MouseEvent e){
    	endX = e.getX();
    	endY = e.getY();
    	drawZoomBox = true;
    	repaint();
    }
	
    private double getYearFromMousePos(int x){
        if (x >= entryGUI.getX())
        	return -1;
	return (double)(((double)startYear) + (((double)x) / getScale()));
    }
    	
    /**
    *  Displays information about the selected entry in the timeline
    */
    public void displayEntry(TimeLineEntry entry){
    	if (!entryGUI.canSwitchEntries())
    		return;
	entryGUI.setData(entry);
	selectedEntry = entry;
	repaint(); //refresh screen so that selected entry is shown
    }
    
    /**
    *  Substitutes an existing entry with an edited version
    */ 
    public void editEntry(TimeLineEntry newEntry){
    	System.out.println("edit: " + newEntry);
        if (passTheBuck){
            System.out.println("stop bouncing me back and forth edit!");
            passTheBuck = false;
            return;
        }
        int index = data.getIndex(newEntry);
        System.out.println("index: " + index);
    	if (!data.contains(newEntry) || selectedEntry == null){
                passTheBuck = true;
    		addEntry(newEntry);
    		return;
    	}
    	if (!selectedEntry.getTitle().equals(newEntry.getTitle()))
    		if (!Main.getInstance().getGUI().isConfirmed("Are you sure you want to replace " + selectedEntry.getTitle() + " with a new entry?"))
    			return;
    	data.getEntries().set(index, newEntry);
    	synchroniseData();
    	endYear = (data.getEntries().size() > 0) ? (double)(data.getLastEntry().getDoubleYear() + 1.25D) : 100D;
	startYear = (data.getEntries().size() > 0) ? (double)(data.getFirstEntry().getDoubleYear() - 0.25D): 0D;
	entryGUI.setBoundsText(startYear, endYear);
	repaint();
        passTheBuck = false;
    }
	
    /**
    *  Adds a time line entry to the timeline
    */ 
    public void addEntry(TimeLineEntry entry){
    	if (passTheBuck){
            System.out.println("stop bouncing me back and forth add!");
            passTheBuck = false;
            return;
        }
        if (data.contains(entry)){
    		passTheBuck = true;
                editEntry(entry);
    		return;
    	}
    	data.getEntries().add(entry);
    	synchroniseData();
    	endYear = (data.getEntries().size() > 0) ? (double)(data.getLastEntry().getDoubleYear() + 1.25D) : 100D;
	startYear = (data.getEntries().size() > 0) ? (double)(data.getFirstEntry().getDoubleYear() - 0.25D): 0D;
	entryGUI.setBoundsText(startYear, endYear);
    	Main.getInstance().saveData();
	repaint(); //refreshes screen
        passTheBuck = false;
    }
    
    /**
    *  Provides access to the timeline 
    */
    public TimeLine getData(){
    	return data;
    }
    
    public double getStartYear(){
    	return startYear;
    }
    
    public double getEndYear(){
    	return endYear;
    }
    
    /**
    *  Sets the range of data which will be displayed in the timeline from start to end (years)
    */
    public void setBounds(double start, double end){
    	startYear = start;
    	endYear = end;
    	entryGUI.setBoundsText(start, end);
    	repaint();
    }
    
    /**
    *  Displays the previous entry in this timeline's data
    */
    public void showPreviousEntry(){
    	int i = 0;
    	for (TimeLineEntry entry : data.getEntries()){
    		if (entry.equals(selectedEntry)){
    			if (i == 0) //can't show the entry before the first one
    				return;
    			else if (outsideBounds(data.getEntries().get(i - 1).getYear())) //can't show entries that won't be displayed (outside bounds)
    				return;
    			displayEntry(data.getEntries().get(i - 1));
    			break;
    		}
    		i++;
    	}
    	repaint();
    }
    
    /**
    *  Displays the next entry in this timeline's data
    */
    public void showNextEntry(){
    	int i = 0;
    	for (TimeLineEntry entry : data.getEntries()){
    		if (entry.equals(selectedEntry)){
    			if (i == (data.getEntries().size() - 1)) //can't show the after before the last one
    				return;
    			else if (outsideBounds(data.getEntries().get(i + 1).getYear())) //can't show entries that won't be displayed (outside bounds)
    				return;
    			displayEntry(data.getEntries().get(i + 1));
    			break;
    		}
    		i++;
    	}
    	repaint();
    }
    
    /**
    *  Displays all of the timeline entries on one screen
    */
    public void displayAll(){
    	endYear = (data.getEntries().size() > 0) ? (double)(data.getLastEntry().getDoubleYear() + 1.25D) : 100D;
	startYear = (data.getEntries().size() > 0) ? (double)(data.getFirstEntry().getDoubleYear() - 0.25D): 0D;
	repaint();
    }
    
    private boolean outsideBounds(int year){
    	return !(year >= startYear && year <= endYear);
    }
    
    public void setTitle(String title){
    	data.setTitle(title);
    	synchroniseData();
    }
    
    private void synchroniseData(){
    	int idx = Main.getInstance().getTimeLineListIndex(data);
    	if (idx == -1){
    		System.out.println("sdfdsafsa-1");
    		return;
    	}
    	Main.getInstance().getMatchingCategory(data).getTimeLines().set(idx, data);
    	Main.getInstance().saveData();
    }
    
    public void setCategory(String category){
    	Main.getInstance().addTimeLine(data, category);
    }
    
    public void deleteEntry(){
    	int index = data.getIndex(selectedEntry);
    	data.getEntries().remove(index);
    	synchroniseData();
	endYear = (data.getEntries().size() > 0) ? (double)(data.getLastEntry().getDoubleYear() + 1.25D) : 100D;
	startYear = (data.getEntries().size() > 0) ? (double)(data.getFirstEntry().getDoubleYear() - 0.25D): 0D;
	entryGUI.setBoundsText(startYear, endYear);
    	repaint();
    }
    
    public TimeLineEntry getSelectedEntry(){
    	return selectedEntry;
    }
    
    public void addNotify(){
    	super.addNotify();
    }
    private boolean passTheBuck;
}
