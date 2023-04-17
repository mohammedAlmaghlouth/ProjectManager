package src.chart;
import java.awt.Color;
import java.awt.Graphics;

public class Line extends ChartObject {
    private int x;
    private int x2;
    private int y;
    private int y2;
    
    public Line(int x1, int y1, int x2, int y2, Color color) {
        this.x = x1;
        this.x2 = x2;
        this.y = y1;
        this.y2 = y2;
        this.color = color;
    }
    
    public void paint(Graphics g) {
    	
    	g.setColor(color);
        g.drawLine(this.x, this.y, this.x2, this.y2);
    }
}
