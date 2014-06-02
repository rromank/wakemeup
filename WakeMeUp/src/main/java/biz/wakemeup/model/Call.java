package biz.wakemeup.model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mongodb.BasicDBObject;

@Entity("call")
public class Call implements DBEntity {
	@Id
	private ObjectId id;
	private ObjectId wakerId;
	private ObjectId audioRecordId;
	private ObjectId alarmId;

	public String getId() {
		if (id == null) {
			return null;
		}
		return id.toString();
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getWakerId() {
		return wakerId.toString();
	}

	public void setWakerId(ObjectId wakerId) {
		this.wakerId = wakerId;
	}

	public String getAudioRecordId() {
		return audioRecordId.toString();
	}

	public void setAudioRecordId(ObjectId audioRecordId) {
		this.audioRecordId = audioRecordId;
	}

	public String getAlarmId() {
		return alarmId.toString();
	}

	public void setAlarmId(ObjectId alarmId) {
		this.alarmId = alarmId;
	}

	@JsonIgnore
	public BasicDBObject getDBObject() {
		BasicDBObject alarm = new BasicDBObject();
		alarm.append("wakerId", wakerId);
		alarm.append("audioRecordId", audioRecordId);
		alarm.append("alarmId", alarmId);
		return alarm;
	}

	@JsonIgnore
	public boolean isCorrect() {
		if (wakerId != null && audioRecordId != null && alarmId != null) {
			return true;
		}
		return false;
	}
}
