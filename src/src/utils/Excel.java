package src.utils;

import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import src.objects.*;

public class Excel {
	private static Excel excel = null;
	private static ArrayList<?> tmp = new ArrayList<>();

	private Excel() {}
	public static Excel getInstanse() {
		if(excel == null) {
			excel = new Excel();
		}
		return excel;
	}

	// --------------------Excel Methods--------------------

	public ArrayList<?> Get_ExcelObjects(String name){
		ArrayList<Project> listProject = new ArrayList<Project>();
		ArrayList<Stages> listStage = new ArrayList<Stages>();
		try {			
			FileInputStream file = new FileInputStream(new File(name));
			Workbook workbook = new HSSFWorkbook(file);
			DataFormatter dataFormatter = new DataFormatter();
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = sheet.iterator();
			Row row = iterator.next();
			while(iterator.hasNext()) {
				row = iterator.next();
				Iterator<Cell> cellIterator = row.iterator();
				Project project = new Project();
				Stages stage = new Stages();		
				String str = "";
				while(cellIterator.hasNext()) 
				{
					Cell cell = cellIterator.next();
					String cellValue = dataFormatter.formatCellValue(cell);
					if(name.equalsIgnoreCase("Projects.xls")) {
						Excel_Project(project, cell, cellValue);
					}
					if(name.equalsIgnoreCase("Stages.xls")) {
						Excel_Stages(stage, cell, cellValue);
					}
					if(name.equalsIgnoreCase("Stages_Detailed.xls")) {
						str = str + Excel_StagesDetiled(cell, row, cellValue, str);
					}
				}
				if (name.equalsIgnoreCase("Projects.xls")){listProject.add(project);}
				else if(name.equalsIgnoreCase("Stages.xls")) {listStage.add(stage);}
			}
			workbook.close();
		}catch(Exception e) {}

		if (name.equalsIgnoreCase("Projects.xls"))
		{
			return listProject;
		}
		else if(name.equalsIgnoreCase("Stages.xls")) 
		{
			return listStage;
		}

		return null;
	}
	public void Combine_Excel(String string, ArrayList<Project> projectList, ArrayList<Stages> stageList) {
		tmp = stageList;
		Get_ExcelObjects("Stages_Detailed.xls");
		for(Project pro : projectList) {
			for(Stages stg : stageList) {
				if ((pro.getID()).equalsIgnoreCase(stg.getObject_Value())){
					pro.insert_Stage(stg);
				}
			}
		}
	}

	// --------------------Excel Files Process--------------------
	private void Excel_Project(Project project, Cell cell,  String cellValue) throws ParseException {
		if(cell.getColumnIndex() == 0) {project.SetID(cellValue);}
		else if(cell.getColumnIndex() == 1) {project.SetCustomer_Project_ID(cellValue);}
		else if(cell.getColumnIndex() == 2) {project.SetStage(Integer.parseInt(cellValue));}
		else if(cell.getColumnIndex() == 3) {project.SetStart_Date(new SimpleDateFormat("MM/dd/yyyy").parse(cellValue));}
		else if(cell.getColumnIndex() == 4) {project.SetEnd_Date(new SimpleDateFormat("MM/dd/yyyy").parse(cellValue));}						
		else if(cell.getColumnIndex() == 5) {project.SetCustomer(Integer.parseInt(cellValue));}
		else if(cell.getColumnIndex() == 6) {project.SetCurrency(cellValue);}
		else if(cell.getColumnIndex() == 7) {project.SetCreated_On(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(cellValue));}
		else if(cell.getColumnIndex() == 8) {project.SetChanged_On(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(cellValue));}			
		
	}
	private void Excel_Stages(Stages stage, Cell cell,  String cellValue) {
		if(cell.getColumnIndex() == 0) {stage.SetObject_Value(cellValue);}
		else if(cell.getColumnIndex() == 1) {stage.SetDocument_Number(Integer.parseInt(cellValue));}
		else if(cell.getColumnIndex() == 2) {stage.SetField_Name(cellValue);}
		else if(cell.getColumnIndex() == 3) {stage.SetChange_Indicator(cellValue.charAt(0));}
		else if(cell.getColumnIndex() == 4) {stage.SetText_flag(Integer.parseInt(cellValue));}
		else if(cell.getColumnIndex() == 5) {stage.SetNew_value(Integer.parseInt(cellValue));}
		else if(cell.getColumnIndex() == 6) {stage.SetOld_value(Integer.parseInt(cellValue));}
	}
	private String Excel_StagesDetiled(Cell cell,Row row, String cellValue, String str)  {
		@SuppressWarnings("unchecked")
		ArrayList<Stages> stage_list = (ArrayList<Stages>) tmp;
		try {
			if(cell.getColumnIndex() == 2) {str += cellValue;}
			else if(cell.getColumnIndex() == 3) 
			{
				str = str + " " + cellValue;
				if( str.substring(str.length()-2, str.length()).equalsIgnoreCase("AM"))
				{
					stage_list.get(row.getRowNum()-1).SetDate(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(str.substring(0, str.length()-3))); 
				}
				else {
					SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
					Calendar cal = Calendar.getInstance();  
					try{  
						cal.setTime(dateFormat.parse(str.substring(0, str.length()-3)));  
					}catch(ParseException e) {} 
					cal.add(Calendar.HOUR_OF_DAY, 12);
					stage_list.get(row.getRowNum()-1).SetDate( cal.getTime() ); 
				}						
			}
			else if(cell.getColumnIndex() == 4) {
				stage_list.get(row.getRowNum()-1).SetLanguage_Key(cellValue);		
			}
		}catch(Exception e) {}
		
		return str;
	}
}
