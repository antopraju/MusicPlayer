import java.io.*;


import org.blinkenlights.jid3.*;
import org.blinkenlights.jid3.v1.*;
//import org.blinkenlights.jid3.v2.*;
import java.sql.*;

/*************************************************************************************************************************
 * The file operations class. Scans the user selected directory recursively for media of MP3 type, retrieves the ID3 Tags
 * and enters the values into the database. 

 * *************************************************************************************************************************/

class FileOps {
	static Connection conn = null;

	//The constructor. Clears the database, everytime a new parent directory is selected. 
	FileOps(String path) throws SQLException {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/";
		String db = "music";
		String user = "root";
		String pass = "anto";

		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url+db, user, pass);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("delete from main");
			conn.close();
			getTrackList(path);
		}
		catch (Exception e) {}
	}


	// Scans the directory for MP3 files
	static void getTrackList(String dirPath) throws ID3Exception, IOException, SQLException{
		MediaFile iMediaFile;
		String track = new String();
		String album = new String();
		String artist = new String();
		try {
			File dir = new File(dirPath);
			if (dir.isDirectory()) {
				String[] flList = dir.list();
				int len = flList.length;

				for (int i = 0; i < len; i++) {
					File f = new File(dirPath + "/" + flList[i]);
					String fileName = dirPath + "/" + flList[i];
					if (!f.isDirectory()) {
						if (fileName.endsWith("mp3")){
							iMediaFile = new MP3File(f);
							ID3Tag[] oTags = iMediaFile.getTags();
							if (oTags[0] instanceof ID3V1_0Tag) {
								ID3V1_0Tag oActTag = (ID3V1_0Tag)oTags[0];
								track = oActTag.getTitle();
								album = oActTag.getAlbum();
								artist = oActTag.getArtist();
								insertIntoDb(fileName, track, album, artist);
							}
							else if (oTags[0] instanceof ID3V1_1Tag) {
								ID3V1_1Tag oActTag = (ID3V1_1Tag)oTags[0];
								track = oActTag.getTitle();
								album = oActTag.getAlbum();
								artist = oActTag.getArtist();
								insertIntoDb(fileName, track, album, artist);
							}
						}
					}
					// Recursive call if current File object points to a directory
					else
						getTrackList(fileName);
				}
			}
			else 
				throw new FileNotFoundException();
			
			}
		catch (IOException e) {
			System.out.println("Path doesn't exist or is not a directory");
		}
		catch(ID3Exception e) {System.out.println("Other");}
	}
	
	//Populates the database 
	static void insertIntoDb(String fileName, String track, String album, String artist) throws SQLException  {
		System.out.println(fileName+track+album+artist);
		String url = "jdbc:mysql://localhost:3306/";
		String dbName = "music";
		String user = "root";
		String pass = "anto";
		String driver = "com.mysql.jdbc.Driver";
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url+dbName, user, pass);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("Insert into main values ('" + fileName + "', '" + track + "', '" + album + "', '" + artist + "')");
			conn.close();
		}
		catch(ClassNotFoundException e) {}
		catch(SQLException e) {}
		catch(Exception e) {}
	}
	
}



