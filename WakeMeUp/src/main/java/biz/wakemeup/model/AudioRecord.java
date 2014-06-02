package biz.wakemeup.model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mongodb.BasicDBObject;

@Entity("audioRecord")
public class AudioRecord implements DBEntity {
	@Id
	private ObjectId id;
	private int timeLingth; // in seconds

	public String getId() {
		if (id == null) {
			return null;
		}
		return id.toString();
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public int getTimeLength() {
		return timeLingth;
	}

	public void setTimeLength(int timeLingth) {
		this.timeLingth = timeLingth;
	}

	@JsonIgnore
	public BasicDBObject getDBObject() {
		BasicDBObject audioRecord = new BasicDBObject();
		audioRecord.append("timeLingth", String.valueOf(timeLingth));
		return audioRecord;
	}

	@JsonIgnore
	public boolean isCorrect() {
		if (timeLingth > 0) {
			return true;
		}
		return false;
	}

}
