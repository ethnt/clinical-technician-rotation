import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lab {
	private String lab_name;
	private Integer week_number;
	private Integer length;
	private String hospital_name;

	/**
	 * Create an empty Lab.
	 */
	public Lab() {
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create a new Lab with fields.
	 */
	public Lab(String name, Integer week, Integer len, String hospital) {
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		lab_name = name;
		week_number = week;
		length = len;
		hospital_name = hospital;
	}
	
	
	/**********************
	 * Class Methods
	 **********************/

	/**
	 * Establish a connection with the database.
	 *
	 * @return Connection ODBC connection.
	 */
	private static Connection connection() {
		Connection connection = null;

		try {
			connection = DriverManager.getConnection("jdbc:odbc:ClinicalDBTurkeltaub");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return connection;
	}

	/**
	 * Returns all Labs, sorted by Hospital Name.
	 *
	 * @return List of albums.
	 */
	public static List<Lab> all() {
		ResultSet result = null;
		Statement statement = null;

		try {
			statement = connection().createStatement();

			String query = "SELECT * FROM Labs ORDER BY HospitalName;";
			
			System.out.println(query);

			result = statement.executeQuery(query);
		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.getMessage());
			System.out.println("SQL State: " + e.getSQLState());
			System.out.println("Vendor Error: " + e.getErrorCode());
			e.printStackTrace();
		}

		return resultSetToList(result);
	}
	
	public static List<Lab> forHospital(String hospital) {
		ResultSet result = null;
		Statement statement = null;

		try {
			statement = connection().createStatement();

			String query = "SELECT * FROM Labs WHERE HospitalName = '" + hospital + "';";
			
			System.out.println(query);

			result = statement.executeQuery(query);
		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.getMessage());
			System.out.println("SQL State: " + e.getSQLState());
			System.out.println("Vendor Error: " + e.getErrorCode());
			e.printStackTrace();
		}

		return resultSetToList(result);
	}
	
	public static List<Lab> forTable(String hospital, String lab_name) {
		ResultSet result = null;
		Statement statement = null;

		try {
			statement = connection().createStatement();

			String query = null;
			
			if (hospital == "All" && lab_name == "All") {
				query = "SELECT * FROM Labs;";
			} else if (hospital == "All" && lab_name != "All") {
				query = "SELECT * FROM Labs WHERE LabName = '" + lab_name + "';";
			} else if (hospital != "All" && lab_name == "All") {
				query = "SELECT * FROM Labs WHERE HospitalName = '" + hospital + "';";
			} else {
				query = "SELECT * FROM Labs WHERE HospitalName = '" + hospital + "' AND LabName = '" + lab_name + "';";
			}
			
			System.out.println(query);

			result = statement.executeQuery(query);
		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.getMessage());
			System.out.println("SQL State: " + e.getSQLState());
			System.out.println("Vendor Error: " + e.getErrorCode());
			e.printStackTrace();
		}

		return resultSetToList(result);
	}
	
	public static boolean deleteMultipleWeeks(List<Integer> weeks) {
		boolean result = true;
		
		if (weeks.size() > 0) {
			String removeOrStatement = null;
			
			for (Integer week : weeks) {
				removeOrStatement += "Week = " + week + " OR ";
			}
			
			removeOrStatement = removeOrStatement.substring(4, removeOrStatement.length() - 4);
			String removeOrQuery = "DELETE * FROM Labs WHERE " + removeOrStatement + ";";
			
			System.out.println(removeOrQuery);
			
			Statement statement = null;
			
			try {
				Connection conn = Lab.connection();
				
				statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				
				int resultInt = statement.executeUpdate(removeOrQuery);
				
				conn.commit();
				
				conn.close();
			} catch (SQLException e) {
				System.out.println("SQL Exception: " + e.getMessage());
				System.out.println("SQL State: " + e.getSQLState());
				System.out.println("Vendor Error: " + e.getErrorCode());
				e.printStackTrace();
			}
		}

		return result;
	}
	
	/**
	 * Save the lab information for a given hospital and lab.
	 * 
	 * @param hospital
	 * @param labName
	 * @param weeks
	 * @param length
	 * @return
	 */
	public static boolean fromForm(String hospital, String labName, List<Integer> weeks, String len) {
		boolean result = false;
		
		List<Integer> toAdd = new ArrayList<Integer>();
		List<Integer> toRemove = new ArrayList<Integer>();
		
		for (int i = 1; i < 25; i++) {
			if (weeks.contains(i)) {
				toAdd.add(i);
			} else {
				toRemove.add(i);
			}
		}
		
		
		/*
		 *  Remove all that are to be removed.
		 */
		
		Lab.deleteMultipleWeeks(toRemove);
		
		
		/*
		 * Add all that are to be added.
		 */
		
		for (Integer week : toAdd) {
			if (Lab.where(hospital, labName, week, Integer.parseInt(len)).size() == 0) {
				Lab lab = new Lab(labName, week, Integer.parseInt(len), hospital);
				lab.save();
			}
		}
		
		
		return result;
	}
	
	/**
	 * Do a where query with all columns.
	 */
	public static List<Lab> where(String hospital, String labName, Integer week, Integer len) {
		ResultSet result = null;
		Statement statement = null;

		try {
			statement = connection().createStatement();

			String query = "SELECT * FROM Labs WHERE ";
			
			query += "HospitalName = '" + hospital + "' AND ";
			query += "LabName = '" + labName + "' AND ";
			query += "WeekNumber = " + week + " AND ";
			query += "Length = " + len + ";";
			
			System.out.println(query);

			result = statement.executeQuery(query);
		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.getMessage());
			System.out.println("SQL State: " + e.getSQLState());
			System.out.println("Vendor Error: " + e.getErrorCode());
			e.printStackTrace();
		}

		return resultSetToList(result);
	}
	
	/**
	 * Converts a ResultSet to an array of Albums.
	 */
	public static List<Lab> resultSetToList(ResultSet set) {
		List<Lab> result = new ArrayList<Lab>();

		try {
			while (set.next()) {
				Lab current = new Lab(
					set.getString("LabName"),
					set.getInt("WeekNumber"),
					set.getInt("Length"),
					set.getString("HospitalName")
				);

				result.add(current);
			}
		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.getMessage());
			System.out.println("SQL State: " + e.getSQLState());
			System.out.println("Vendor Error: " + e.getErrorCode());
			e.printStackTrace();
		}

		return result;
	}
	
	
	/**********************
	 * Instance Methods
	 **********************/
	
	public boolean save() {
		boolean result = false;
		Statement statement = null;

		try {
			Connection conn = Lab.connection();

			statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

			String query = "";
			query += "INSERT INTO Labs (LabName, WeekNumber, Length, HospitalName) VALUES (";
			query += "'" + lab_name + "', ";
			query += "" + week_number + ", ";
			query += "" + length + ", ";
			query += "'" + hospital_name + "'";
			query += ")";

			System.out.println(query);
			
			int resultInt = statement.executeUpdate(query);

			conn.commit();

			// FIXME
			result = true;

			conn.close();
		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.getMessage());
			System.out.println("SQL State: " + e.getSQLState());
			System.out.println("Vendor Error: " + e.getErrorCode());
			e.printStackTrace();
		}

		return result;
	}

	
	/**********************
	 * Getters and Setters
	 **********************/
	
	public String getLabName() {
		return lab_name;
	}
	
	public Integer getWeekNumber() {
		return week_number;
	}
	
	public Integer getLength() {
		return length;
	}
	
	public String getHospitalName() {
		return hospital_name;
	}
	
	/**
	 * Converts the columns into an array for easy access.
	 */
	public Object[] asArray() {
		Object[] row = new Object[]{
			lab_name,
			week_number,
			length,
			hospital_name
		};

		return row;
	}

	/**
	 * Return the album as a String.
	 */
	public String toString() {
		return Arrays.toString(this.asArray());
	}
}
