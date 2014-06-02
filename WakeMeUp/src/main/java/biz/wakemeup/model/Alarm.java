package biz.wakemeup.model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import biz.wakemeup.utils.DateValidator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mongodb.BasicDBObject;

@Entity("alarm")
public class Alarm implements DBEntity {
	@Id
	private ObjectId id;
	private ObjectId userId;
	private String date;
	private boolean isWaked;

	public String getId() {
		if (id == null) {
			return null;
		}
		return id.toString();
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getUserId() {
		return userId.toString();
	}

	public void setUserId(ObjectId userId) {
		this.userId = userId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public boolean getIsWaked() {
		return isWaked;
	}

	public void setWaked(boolean isWaked) {
		this.isWaked = isWaked;
	}

	@JsonIgnore
	public BasicDBObject getDBObject() {
		BasicDBObject alarm = new BasicDBObject();
		alarm.append("userId", userId);
		alarm.append("date", date);
		alarm.append("isWaked", isWaked);
		return alarm;
	}

	@JsonIgnore
	public boolean isCorrect() {
		DateValidator dateValidator = new DateValidator();
		if (userId != null
				&& (isWaked == true || isWaked == false)
				&& dateValidator
						.validate(date, DateValidator.Format.ALARM_DATE)) {
			return true;
		}
		return false;
	}

}
