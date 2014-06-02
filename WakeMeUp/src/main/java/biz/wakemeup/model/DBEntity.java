package biz.wakemeup.model;

import org.mongodb.morphia.annotations.Entity;

import com.mongodb.BasicDBObject;

@Entity
public interface DBEntity {
	public BasicDBObject getDBObject();
	
	public boolean isCorrect();
}
