package biz.wakemeup.connection;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import biz.wakemeup.model.User;

import com.mongodb.Mongo;

public class DBConnection {
	private static DBConnection INSTANCE = new DBConnection();
	private Mongo mongo;
	private final Datastore datastore;
	public static final String DB_NAME = "MyBase";

	private DBConnection() {
		try {
			mongo = new Mongo("127.0.0.1", 27017);
			datastore = new Morphia().map(User.class).createDatastore(mongo,
					DB_NAME);
			datastore.ensureIndexes();
		} catch (Exception e) {
			throw new RuntimeException(
					"Error initializing PretechMongoDBConnection", e);
		}
	}

	public static DBConnection instance() {
		if (INSTANCE == null) {
			INSTANCE = new DBConnection();
		}
		return INSTANCE;
	}

	public Datastore getDatabase() {
		return datastore;
	}

	public void closeConnection() {
		mongo.close();
		INSTANCE = null;
	}
}
