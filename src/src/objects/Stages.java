package src.objects;
import java.util.Date;

public class Stages {
	private String Object_Value;
	private Integer Document_Number;
	private String Field_Name;
	private Character Change_Indicator;
	private Integer Text_flag ;
	private Integer New_value ;
	private Integer Old_value ;
	private Date date;
	private String Language_Key;
	
	public Stages() {}

	public void SetObject_Value(String Object_Value) {this.Object_Value = Object_Value;}
	public void SetDocument_Number(Integer Document_Number) {this.Document_Number = Document_Number;}
	public void SetField_Name(String Field_Name) {this.Field_Name = Field_Name;}
	public void SetChange_Indicator(Character Change_Indicator) {this.Change_Indicator = Change_Indicator;}
	public void SetText_flag(Integer Text_flag) {this.Text_flag = Text_flag;}
	public void SetNew_value(Integer New_value) {this.New_value = New_value;}
	public void SetOld_value(Integer Old_value) {this.Old_value = Old_value;}
	public void SetDate(Date date) {this.date = date;}
	public void SetLanguage_Key(String Language_Key) {this.Language_Key = Language_Key;}

	public String getObject_Value() {return Object_Value;}
	public Integer getDocument_Number() {return Document_Number;}
	public String getField_Name() {return Field_Name;}
	public Character getChange_Indicator() {return Change_Indicator;}
	public Integer getText_flag() {return Text_flag;}
	public Integer getNew_value() {return New_value;}
	public Integer getOld_value() {return Old_value;}
	public Date getDate() {return date;}
	public String getLanguage_Key() {return Language_Key;}
	
 	@Override
	public String toString() {
		return Object_Value + ", " + Document_Number + ", " + Field_Name + ", " + Change_Indicator + ", " + Text_flag + ", "
				+ New_value + ", " + Old_value + ", " + date;
	}
		
}