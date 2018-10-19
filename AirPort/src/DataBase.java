import java.sql.*;
public class DataBase {
	private Connection conn;
	private Statement stmt;

	//----------------------CONSTRUCTOR -----------------------------------------
	public DataBase(){
		try {

			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/test";
			conn=DriverManager.getConnection(url, "root", "root");
			stmt=conn.createStatement();

		} 
		catch (ClassNotFoundException e) {

			e.printStackTrace(); }
		catch (SQLException e) { 

			e.printStackTrace(); }


	} // constructor


	//----------------------CREATE TABLES METHOD -----------------------------------------
	public void createTables(String table1Name, String table2Name, boolean tablesExistAlready){
		try {

			if (tablesExistAlready){
				stmt.executeUpdate("Drop TABLE "+table1Name);
				stmt.executeUpdate("Drop TABLE "+table2Name);
			}

			String table1="CREATE TABLE "+ table1Name+" (ID Varchar(10), Passengers int, Cargo int, Cost int, isSecurityIssue boolean, timeInAirfield int)"; // string to create table 1
			String table2="CREATE TABLE "+table2Name +" (ID Varchar(10), Passengers int, Destination Varchar(30), timeInAirfield int)"; //string to create table 2

			stmt.executeUpdate(table1);
			stmt.executeUpdate(table2);

		} catch (SQLException e) { 

			e.printStackTrace(); }

	}//createTables

	//----------------------INSERT TO A TABLE METHOD -----------------------------------------



	public void insertToTable(String tableName, FlightDetails l){
		String str="";
		if(tableName.equals("landings")){//inserting details of one landing flight to the table
			 str="INSERT INTO " +tableName+" VALUES("
					+"'"+l.getID()+"'"+"," 
					+ l.getPassangers()+","
					+ l.getCargo()+","
					+ l.getTechnicalCost()+ ","
					+ l.getSuspiciousObject()+ ","
					+ (int)l.getTime()+")" ;
		}
		
		if(tableName.equals("takeoffs")){//inserting details of one landing flight to the table
			 str="INSERT INTO " +tableName+" VALUES("
						+"'"+l.getID()+"'"+"," 
						+ l.getPassangers()+","
						+"'"+l.getDestenation()+"'"+","
						+ (int)l.getTime()+")" ;
		}
			
			try {
				stmt.executeUpdate(str);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	

	//----------------------PULL OUT DATA METHOD -----------------------------------------
	public String extract(String tableName,String colName){
		ResultSet result=null;
		String ans="";
		String str="select * from " + tableName;
		try {

			result=stmt.executeQuery(str);
			result.next(); // move to the first line
			ans=result.getString(colName);


		} catch (SQLException e) { e.printStackTrace();	}

		return ans;


	}
}

