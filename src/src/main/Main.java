package src.main;
import src.objects.*;
import java.awt.EventQueue;
import java.util.ArrayList;
import src.utils.*;

public class Main {
	
	public static ArrayList<Project> project_list = new ArrayList<Project>();
	public static ArrayList<Stages> stage_list = new ArrayList<Stages>();
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Excel reader = Excel.getInstanse();
		
		project_list = (ArrayList<Project>) reader.Get_ExcelObjects("Projects.xls");
		stage_list = (ArrayList<Stages>) reader.Get_ExcelObjects("Stages.xls");
		reader.Combine_Excel("Stages_Detailed.xls", project_list, stage_list);
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				GUI frame = new GUI(project_list);
				frame.setVisible(true);
			}
		});
	}
}
