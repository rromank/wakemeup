package biz.wakemeup.dao;

import java.util.HashMap;
import java.util.Map;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import biz.wakemeup.connection.DBConnection;
import biz.wakemeup.model.AudioRecord;
import biz.wakemeup.model.DBEntity;

public class AudioRecordDao implements BaseDao {
	private Map<String, DBEntity> audioRecords = new HashMap<String, DBEntity>();
	Datastore ds;

	public AudioRecordDao() {
		ds = DBConnection.instance().getDatabase();
		Query<AudioRecord> query = ds.find(AudioRecord.class);
		for (AudioRecord audioRecord : query) {
			audioRecords.put(audioRecord.getId(), audioRecord);
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
		return audioRecords;
	}

	public DBEntity findById(ObjectId id) {
		return audioRecords.get(id.toString());
	}

	public void remove(ObjectId id) {
		ds.delete(AudioRecord.class, id);
	}

	public void update(DBEntity entity) {
		ds.merge(entity);
	}

}
