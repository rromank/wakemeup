package biz.wakemeup.dao;

import java.util.HashMap;
import java.util.Map;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import biz.wakemeup.connection.DBConnection;
import biz.wakemeup.model.DBEntity;
import biz.wakemeup.model.User;

public class UserDao implements BaseDao {
	private Map<String, DBEntity> users = new HashMap<String, DBEntity>();
	Datastore ds;

	public UserDao() {
		ds = DBConnection.instance().getDatabase();
		Query<User> query = ds.find(User.class);
		for (User user : query) {
			users.put(user.getId(), user);
		}
	}

	public void insert(DBEntity entity) {
		try {
			ds.save(entity);
			DBConnection.instance().closeConnection();
		} catch (Exception ex) {
			throw new IllegalArgumentException();
		}
	}

	public Map<String, DBEntity> findAll() {
		return users;
	}

	public DBEntity findById(ObjectId id) {
		DBEntity user = users.get(id.toString());
		DBConnection.instance().closeConnection();
		return user;
	}

	public void remove(ObjectId id) {
		ds.delete(User.class, id);
		DBConnection.instance().closeConnection();
	}

	public void update(DBEntity entity) {
		ds.merge(entity);
		DBConnection.instance().closeConnection();
	}
}
