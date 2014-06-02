package biz.wakemeup.dao;

import java.util.HashMap;
import java.util.Map;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import biz.wakemeup.connection.DBConnection;
import biz.wakemeup.model.Alarm;
import biz.wakemeup.model.DBEntity;
import biz.wakemeup.model.User;

public class AlarmDao implements BaseDao {

	private Map<String, DBEntity> alarms = new HashMap<String, DBEntity>();
	Datastore ds;

	public AlarmDao() {
		ds = DBConnection.instance().getDatabase();
		Query<Alarm> query = ds.find(Alarm.class);
		for (Alarm alarm : query) {
			alarms.put(alarm.getId(), alarm);
		}
	}

	public void insert(DBEntity entity) {
		Alarm alarm = (Alarm) entity;
		boolean isUserInDB = isUserInDB(alarm.getUserId().toString());

		Map<String, DBEntity> userAlarms = findByUserId(new ObjectId(
				alarm.getUserId()));
		boolean isAlarmInDB = false;
		for (DBEntity e : userAlarms.values()) {
			Alarm a = (Alarm) e;
			if (alarm.getDate().equals(a.getDate())) {
				isAlarmInDB = true;
				break;
			}
		}

		if (isAlarmInDB || !isUserInDB) {
			throw new IllegalArgumentException();
		}
		try {
			ds.save(entity);
			DBConnection.instance().closeConnection();
		} catch (Exception ex) {
			throw new IllegalArgumentException();
		}
	}

	public Map<String, DBEntity> findAll() {
		return alarms;
	}

	public DBEntity findById(ObjectId id) {
		return alarms.get(id.toString());
	}

	public void remove(ObjectId id) {
		ds.delete(Alarm.class, id);
	}

	public void update(DBEntity entity) {
		ds.merge(entity);
	}

	public Map<String, DBEntity> findByUserId(ObjectId id) {
		Map<String, DBEntity> map = new HashMap<String, DBEntity>();
		for (DBEntity entity : alarms.values()) {
			Alarm alarm = (Alarm) entity;
			if (alarm.getUserId().equals(id.toString())) {
				map.put(alarm.getId(), alarm);
			}
		}
		return map;
	}

	private boolean isUserInDB(String id) {
		UserDao userDao = new UserDao();
		for (DBEntity e : userDao.findAll().values()) {
			User user = (User) e;
			if (user.getId().toString().equals(id)) {
				return true;
			}
		}
		return false;
	}

}
