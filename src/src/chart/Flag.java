package src.chart;

import java.awt.Color;
import java.awt.Graphics;

public class Flag extends ChartObject {
	private String num;
	private int x;
	private int y;
	private String label;
	private int length = 10;
	private int height = 5;
	
	protected Flag(int x, int y, String num, Color color, String label) {
        this.x = x;
        this.y = y;
		this.num = num;
		this.color = color;
		this.label = label;
	}
	
	public void paint(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, length, height);
		g.drawString(num, x + length, y);
		g.drawString(label, x - 25, y + 50);
	}
}
