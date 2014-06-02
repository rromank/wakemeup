package biz.wakemeup.connection;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class MongoDBConnection {
	private static Mongo m;
	private static DB db;
	private static boolean flag = false;

	public static DBCollection getDBCollection(String collection) {
		if (!flag) {
			flag = true;
			try {
				m = new Mongo("localhost", 27017);
			} catch (Exception ex) {
				System.out.println("Exception with Mongo connection");
			}
			db = m.getDB("wakeMeUpDB");
			System.out.println("tuta");
		}
		db.cleanCursors(true);
		return db.getCollection(collection);
	}
	
	public static Mongo getMongo() {
		return m;
	}
	
	public static void closeConnection() {
		m.close();
		flag = false;
	}
	
}
