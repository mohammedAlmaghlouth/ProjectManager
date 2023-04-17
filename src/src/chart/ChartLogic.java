package src.chart;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import src.objects.*;

public class ChartLogic {

	private Calendar cal = Calendar.getInstance();
	private ArrayList<Stages> stages;
	private Date stage_start;
	private Date stage_end;
	private Date timeline_start;
	private Date timeline_end;
	
	public ChartLogic(Project project) {
		this.stages = (ArrayList<Stages>) project.getStages();
		setStages();
		setTimeline();
	}
	
	
	private void setStages() {
        // For the min and max date of stages
		stage_start = stages.get(0).getDate();
        stage_end = stages.get(0).getDate();
        
        for (Stages stage : stages) {

        	
        	Date current = stage.getDate();
        	
        	if (stage_end.compareTo(current) <= 0) {stage_end = current;}
        	if (stage_start.compareTo(current) > 0) {stage_start = current;}
        }
	}
	
	public int getDuration(Date first, Date last) {
		int duration = (int)((last.getTime() - first.getTime()) / 1000 / 60 / 60 / 24); //ms -> s -> min -> hour -> days
        duration = duration == 0 ? 1 : duration;	// Minimum duration is 1
        
        return duration;
	}

	private void setTimeline() {
		
        cal.setTime(stage_start);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        timeline_start = cal.getTime();
		
		cal.setTime(stage_end);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        cal.add(Calendar.MONTH, 1);		//Adding 1 month
        timeline_end = cal.getTime();
	}
	
	public Date getStageStart() {
		return stage_start;
	}
	
	public Date getStageEnd() {
		return stage_end;
	}
	
	public Date getTimelineStart() {
		return timeline_start;
	}
	
	public Date getTimelineEnd() {
		return timeline_end;
	}
}
