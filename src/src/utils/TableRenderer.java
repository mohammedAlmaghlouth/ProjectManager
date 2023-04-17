package src.utils;
import java.awt.Color;
import java.awt.Component;
import java.awt.Label;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

class TableRenderer extends DefaultTableCellRenderer {
	// This class affects the properties of individual cells in the table 
	
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
 
        Component c = super.getTableCellRendererComponent(table, value, isSelected,
                hasFocus, row, column);

        
        setHorizontalAlignment(JLabel.CENTER);
        
        if (row % 2 == 0) {
            c.setBackground(Color.decode("#eeeeee"));
 
        } else {
            c.setBackground(Color.WHITE);
        }
        
        // Stage 10 = Completed project
        if (column == 2 && (Integer)value == 10) {
        	c.setBackground(Color.decode("#99ff99"));
        }
       
        if (isSelected) {
        	c.setBackground(new Color(184, 207, 229)); //Default
        }
        
        return c;
    }
}
