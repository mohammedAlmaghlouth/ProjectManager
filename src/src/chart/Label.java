package src.chart;

import java.awt.Color;
import java.awt.Graphics;

public class Label extends ChartObject {
	private String lbl;
	private int x;
	private int y;
	
	protected Label(String lbl, int x, int y, Color color) {
        this.x = x;
        this.y = y;
		this.lbl = lbl;
		this.color = color;
	}
	
	public void paint(Graphics g) {
		g.setColor(this.color);
		g.drawString(lbl, x, y);
	}
}