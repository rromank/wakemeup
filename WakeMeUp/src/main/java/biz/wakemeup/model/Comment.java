package biz.wakemeup.model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mongodb.BasicDBObject;

public class Comment implements DBEntity {
	@Id
	private ObjectId id;
	private ObjectId audioRecordId;
	private String text;

	public String getId() {
		if (id == null) {
			return null;
		}
		return id.toString();
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getAudioRecordId() {
		return audioRecordId.toString();
	}

	public void setAudioRecordId(ObjectId audioRecordId) {
		this.audioRecordId = audioRecordId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@JsonIgnore
	public BasicDBObject getDBObject() {
		BasicDBObject comment = new BasicDBObject();
		comment.append("audioRecordId", audioRecordId.toString());
		comment.append("text", text);
		return comment;
	}

	@JsonIgnore
	public boolean isCorrect() {
		if (audioRecordId != null && text != null && !text.equals("")) {
			return true;
		}
		return false;
	}
}
