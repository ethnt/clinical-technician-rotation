import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Hospital {
	private String hospital_name;
	private String street_address;
	private String city;
	private String state;
	private String zip;

	/**
	 * Create an empty Album.
	 */
	public Hospital() {
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create a new Album with fields.
	 * 
	 * @param new_album_id
	 * @param new_album_name
	 * @param new_artist_name
	 * @param new_genre
	 * @param new_wholesale_price
	 * @param new_retail_price
	 * @param new_qoh
	 * @param new_min_quantity
	 */
	public Hospital(String name, String address, String cty, String st, String zp) {
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		hospital_name = name;
		street_address = address;
		city = cty;
		state = st;
		zip = zp;
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
	 * Returns all Albums, sorted by AlbumID.
	 *
	 * @return List of albums.
	 */
	public static List<Hospital> all() {
		ResultSet result = null;
		Statement statement = null;

		try {
			statement = connection().createStatement();

			String query = "SELECT * FROM Hospitals ORDER BY HospitalName;";

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
	public static List<Hospital> resultSetToList(ResultSet set) {
		List<Hospital> result = new ArrayList<Hospital>();

		try {
			while (set.next()) {
				Hospital current = new Hospital(
					set.getString("HospitalName"),
					set.getString("StreetAddress"),
					set.getString("City"),
					set.getString("State"),
					set.getString("Zip")
				);

				result.add(current);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
			Connection conn = Hospital.connection();

			statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

			String query = "";
			query += "INSERT INTO Hospitals (HospitalName, StreetAddress, City, State, Zip) VALUES (";
			query += "'" + hospital_name + "', ";
			query += "'" + street_address + "', ";
			query += "'" + city + "', ";
			query += "'" + state + "', ";
			query += "'" + zip + "'";
			query += ")";

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
	
	public String getHospitalName() {
		return hospital_name;
	}
	
	
	/**
	 * Converts the columns into an array for easy access.
	 */
	public Object[] asArray() {
		Object[] row = new Object[]{
			hospital_name,
			street_address,
			city,
			state,
			zip
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
