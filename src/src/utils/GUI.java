package src.utils;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import src.chart.ChartLogic;
import src.chart.ChartMaker;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.Date;
import java.awt.Color;
import java.awt.Cursor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import src.objects.*;

public class GUI extends JFrame {
	
	int width = 1600;
	int height = 640;
	private ArrayList<Project> project_list = new ArrayList<Project>();
	
	public GUI(ArrayList<Project> projects) {
		project_list = projects;
		
		setVisible(true);
		setResizable(false);
		setTitle("Project Illustrator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds((1920 - width) / 2, (1080 - height) / 2, width, height);		// x, y, width, height
		
		
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 30, 230, height - 100);
		contentPane.add(scrollPane);
		
		//// TABLE
		String cols[] = {"#", "Project-ID", "Stages"};
		
		
		DefaultTableModel tableModel = new DefaultTableModel(cols, 0) {
		    public boolean isCellEditable(int i, int i1) {
		        return false; // Disable cell editing
		    }
		};
		
		
		// Insert rows from project_list
		for (int i = 0; i < project_list.size(); i++) {
			Project project = project_list.get(i);
			Object[] row = {i+1, project.getCustomer_Project_ID(), project.getStage()};
			tableModel.addRow(row);
		}

		JTable table = new JTable(tableModel);
		table.setDefaultRenderer(Object.class, new TableRenderer());
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		table.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


		table.getColumnModel().getColumn(1).setPreferredWidth(120);
		
		
		
		scrollPane.setViewportView(table);
		//// TABLE END
		
		
		JPanel figurePanel = new JPanel();
		figurePanel.setBounds(250, 30, width - 300, height - 100);
		contentPane.add(figurePanel);
		figurePanel.setLayout(null);
		
		JLabel projLbl = new JLabel("Project ID");
		projLbl.setFont(new Font("MS UI Gothic", Font.BOLD, 16));
		projLbl.setBounds(10, 10, 80, 30);
		figurePanel.add(projLbl);
		

		JTextField projTf = new JTextField();
		projTf.setHorizontalAlignment(SwingConstants.LEFT);
		projTf.setFont(new Font("Courier New", Font.BOLD, 22));
		projTf.setBackground(Color.LIGHT_GRAY);
		projTf.setEditable(false);
		projTf.setBounds(132, 10, 200, 30);
		figurePanel.add(projTf);
		
		// EVENT LISTENERS
		ListSelectionModel choice = table.getSelectionModel();
	    choice.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				
			    if (!e.getValueIsAdjusting()) {	//This line prevents double events
			        
			        // Fetch project when it is chosen
			        Project project = project_list.get((int)table.getValueAt(table.getSelectedRow(), 0) - 1);
			        ArrayList<Stages> stages = (ArrayList<Stages>) project.getStages();
			        
			    	// Set Project Title
			        projTf.setText(project.getCustomer_Project_ID());


			        // -------------------DRAWING----------------------
			        ChartMaker fig = ChartMaker.getInstance();
			        
			        // Erase previous chart
			        fig.clear();
			        figurePanel.update(figurePanel.getGraphics());
			        
			        // Variables Relative to the figurePanel
			        int length = figurePanel.getWidth() - 100;
			        int y = 450;
			        int x1 = 50;	
			        int x2 = x1 + length;
			        fig.setChart(x1, x2, y, y-150, project);
			        
			        fig.addBaseLine();
			        
			        fig.addDurationLine();
			        
			        //// Execute the drawing.
			        fig.paintComponent(figurePanel.getGraphics()); 
			    }
			}
	    });
	}
}