package src.objects;
import java.util.ArrayList;
import java.util.Date;

public class Project {
	private String ID;
	private String Customer_Project_ID;
	private Integer Stage;
	private Date Start_Date;
	private Date End_Date;
	private Integer Customer;
	private String Currency;
	private Date Created_On;
	private Date Changed_On;
	private ArrayList<Stages> Stage_list = new ArrayList<Stages>();

	public Project() {}
	
	public Project(String ID, String Customer_Project_ID, Integer Stage, Date Start_Date, Date End_Date, Integer Customer, String Currency, Date Created_On, Date Changed_On) {
		
		this.ID = ID;
		this.Customer_Project_ID = Customer_Project_ID;
		this.Stage = Stage;
		this.Start_Date = Start_Date;
		this.End_Date = End_Date;
		this.Customer = Customer;
		this.Currency = Currency;
		this.Created_On = Created_On;
		this.Changed_On = Changed_On;
	}
	
	public void insert_Stage(Stages Stage) {
		Stage_list.add(Stage);
	}
	
	public String getID() {return ID;}
	public String getCustomer_Project_ID() {return Customer_Project_ID;}
	public Integer getStage() {return Stage; }
	public Date getStart_Date() {return Start_Date ;}
	public Date getEnd_Date() {return End_Date ;}
	public Integer getCustomer() {return Customer ;}
	public String getCurrency() {return Currency;}
	public Date getCreated_On() {return Created_On ;}
	public Date getChanged_On() {return Changed_On ;}
	public ArrayList<Stages> getStages() {return Stage_list;}

	public void SetID(String ID) {this.ID = ID;}
	public void SetCustomer_Project_ID(String Customer_Project_ID) {this.Customer_Project_ID = Customer_Project_ID;}
	public void SetStage(Integer Stage) {this.Stage = Stage; }
	public void SetStart_Date(Date Start_Date) {this.Start_Date = Start_Date ;}
	public void SetEnd_Date(Date End_Date) {this.End_Date = End_Date ;}
	public void SetCustomer(Integer Customer) {this.Customer = Customer ;}
	public void SetCurrency(String Currency) {this.Currency = Currency;}
	public void SetCreated_On(Date Created_On) {this.Created_On = Created_On ;}
	public void SetChanged_On(Date Changed_On) {this.Changed_On = Changed_On ;}
	public void SetProjectStage(ArrayList<Stages> Stage_list) {
		for(Stages item : Stage_list ) {
			this.Stage_list.add(item);
		}
	}
	
	@Override
	public String toString() {
		String str = "\n" + ID + " - " + Customer_Project_ID + " - " + Stage + " - " + Start_Date + " - " + End_Date + " - "
	+ Customer + " - " + Currency + " - " + Created_On + " - " + Changed_On;
		str += "\n		*************************STAGES*************************	\n";
		
		for (Stages stage : Stage_list) {
			str += stage.toString() + "\n";
		}
		
		return str;
	}
	
}
