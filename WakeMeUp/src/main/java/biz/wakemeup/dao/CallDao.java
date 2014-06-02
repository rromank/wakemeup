package biz.wakemeup.dao;

import java.util.HashMap;
import java.util.Map;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import biz.wakemeup.connection.DBConnection;
import biz.wakemeup.model.Alarm;
import biz.wakemeup.model.AudioRecord;
import biz.wakemeup.model.Call;
import biz.wakemeup.model.DBEntity;
import biz.wakemeup.model.User;

public class CallDao implements BaseDao {

	private Map<String, DBEntity> calls = new HashMap<String, DBEntity>();
	Datastore ds;

	public CallDao() {
		ds = DBConnection.instance().getDatabase();
		Query<Call> query = ds.find(Call.class);
		for (Call call : query) {
			calls.put(call.getId(), call);
		}
	}

	public void insert(DBEntity entity) {
		Call call = (Call) entity;

		if (!isUserInDB(call.getWakerId())) {
			throw new IllegalArgumentException();
		}

		if (!isAudioRecordInDB(call.getAudioRecordId())) {
			throw new IllegalArgumentException();
		}

		if (!isAarmInDB(call.getAlarmId())) {
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
		// TODO Auto-generated method stub
		return null;
	}

	public DBEntity findById(ObjectId id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(ObjectId id) {
		// TODO Auto-generated method stub

	}

	public void update(DBEntity entity) {
		// TODO Auto-generated method stub

	}

	private boolean isUserInDB(String id) {
		UserDao dao = new UserDao();
		for (DBEntity e : dao.findAll().values()) {
			User user = (User) e;
			if (user.getId().toString().equals(id)) {
				return true;
			}
		}
		return false;
	}

	private boolean isAudioRecordInDB(String id) {
		AudioRecordDao dao = new AudioRecordDao();
		for (DBEntity e : dao.findAll().values()) {
			AudioRecord audioRecord = (AudioRecord) e;
			if (audioRecord.getId().toString().equals(id)) {
				return true;
			}
		}
		return false;
	}

	private boolean isAarmInDB(String id) {
		AlarmDao dao = new AlarmDao();
		for (DBEntity e : dao.findAll().values()) {
			Alarm alarm = (Alarm) e;
			if (alarm.getId().toString().equals(id)) {
				return true;
			}
		}
		return false;
	}

}
