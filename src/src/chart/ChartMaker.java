package src.chart;

import java.awt.Color;
import java.awt.Graphics;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import src.objects.Project;
import src.objects.Stages;


public class ChartMaker {

	
	private static ChartMaker instance = null;
	private final ArrayList<ChartObject> objects = new ArrayList<ChartObject>();
	
	ChartLogic calc;
	Project project;
	int x1, x2, y1, y2;
	DateFormat formatter;
	// Set variables
    Color baseClr = Color.decode("#000044");
    Color altClr = Color.decode("#991111");
    Color greenClr = Color.decode("#00aa00");
    Color redClr = Color.red;
	
    private ChartMaker() {}
    
    public static ChartMaker getInstance() {
    	if (instance == null) {
    		instance = new ChartMaker();
    	}
    	return instance;
    }
	

    
    public void setChart(int x1, int x2, int y1, int y2, Project p) {
    	clear();
    	project = p;
    	calc = new ChartLogic(project);
    	this.x1=x1; this.x2=x2; this.y1=y1; this.y2=y2;
    }
    
    public void addBaseLine() {
    	
    	addLine(x1, y1, x2, y1, baseClr);


	    Date timelineFirst = calc.getTimelineStart();
	    Date timelineLast = calc.getTimelineEnd();
    	
    	
    	formatter = new SimpleDateFormat("MMM 2yyy");
        addMonthTicker(formatter.format(timelineFirst), x1, y1, baseClr);
        addMonthTicker(formatter.format(timelineLast), x2, y1, baseClr);
        
        placeTickers(timelineFirst, timelineLast);
    }
    
    public void placeTickers(Date first, Date last) {
    	// Normal tickers
    	Calendar cal = Calendar.getInstance();
        cal.setTime(first);
        ArrayList<Integer> registeredFlags = new ArrayList<Integer>();
        int n = 0;
        
        while (cal.getTime().compareTo(last) < 0) {	// While the pointer is still inline, continue
        	int range = n * (x2 - x1) / calc.getDuration(first, last);
        	
        	addTicker(x1 + range, y1, baseClr);
        	
        	if (cal.get(Calendar.DAY_OF_MONTH) == 1) {
        		formatter = new SimpleDateFormat("MMM 2yyy");
        		addMonthTicker(formatter.format(cal.getTime()), x1 + range, y1, baseClr);
        	}
        	
        	
        	// Comparing format
        	formatter = new SimpleDateFormat("yyyyMMdd");
        	for (Stages stage : project.getStages()) {
	        	if (formatter.format(stage.getDate()).equals(formatter.format(cal.getTime()))) {
	        		// If there is a flag in this day
		        	
		        	int flagX = x1 + range;
		        	int flagY = y1-20;
		        	
		        	int oldValue = stage.getOld_value() != null ? stage.getOld_value() : 0;
		        	int diff = stage.getNew_value() - oldValue;
		        	int flagNum = stage.getNew_value();
		        	Color flagClr = diff >= 0 ? greenClr : redClr;

		        	
		        	formatter = new SimpleDateFormat("M/d/2yyy");
		        	String flagLabel = formatter.format(stage.getDate());

		        	
		        	for (int x : registeredFlags) {
		        		
		        		int difference = Math.abs(flagX - x); 
		        		
		        		if (difference < 50) {
		        			flagLabel = "";
		        		}
		        		
		        		if (difference == 0) {
		        			flagY -= 15;
		        		}
		        	}
		        	
		        	
		        	addFlag(flagX, flagY, flagClr, Integer.toString(flagNum), flagLabel);
		        	registeredFlags.add(flagX);
	        	}
        	}
        	
        	
        	
        	// Increment day
        	cal.add(Calendar.DAY_OF_MONTH, 1);
        	n++;
        }

    }
    
    public void addDurationLine() {
        Date stage_start = calc.getStageStart();
        Date stage_end = calc.getStageEnd();
        int duration = calc.getDuration(stage_start, stage_end);
	    Date timelineFirst = calc.getTimelineStart();
	    Date timelineLast = calc.getTimelineEnd();
	    int x1, x2, y = this.y2;
	    int interval = calc.getDuration(timelineFirst, timelineLast);
        
        x1 = this.x1 + (int)((stage_start.getTime() - timelineFirst.getTime()) * (this.x2 - this.x1) / 1000 / 60 / 60 / 24 / interval);
        x2 = x1 + duration * (this.x2 - this.x1) / interval;
        
        System.out.print(x2 + " " + x1);
        
        addLine(x1, y, x2, y, altClr);
        addString("Duration: " + duration + " days", (x1+x2)/2 - 40, y-10, altClr);
        
        // Start and end tickers
        addLargeTicker(x1, y, altClr);
        addLargeTicker(x2, y, altClr);
    }
    
    
    // Smaller parts
    public void addLine(int x1, int y1, int x2, int y2, Color color) {
    	addObject(new Line(x1, y1, x2, y2, color));
    }
    
    public void addTicker(int x1, int y1, Color color) {
    	addObject(new Line(x1, y1, x1, y1-3, color));
    }

    public void addLargeTicker(int x, int y, Color color) {
    	addObject(new Line(x, y+5, x, y-5, color));
    }
    
    public void addString(String lbl, int x, int y, Color color) {
    	addObject(new Label(lbl, x, y, color));
    }
    
    public void addMonthTicker(String date, int x, int y, Color color) {
    	addLargeTicker(x, y, color);
        addString(date, x-20, y+15, color);
    }
    
    public void addFlag(int x, int y, Color color, String num, String label) {
    	addObject(new Flag(x, y, num, color, label));
    	addFlagPole(x, y);
    }
    
    public void addFlagPole (int x, int y) {
    	addLine(x, y, x, y + 17, Color.decode("#8B4000"));
    }
    
    
    // Main operations
    public void addObject(ChartObject o) {
    	this.objects.add(o);
    }
    
    public void paintComponent(Graphics g) {
    	for (ChartObject o : objects) {
    		o.paint(g);
    	}
    }
    
    public void clear() {
    	objects.clear();
    }
}
