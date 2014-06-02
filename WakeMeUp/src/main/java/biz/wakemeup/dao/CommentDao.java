package biz.wakemeup.dao;

import java.util.HashMap;
import java.util.Map;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import biz.wakemeup.connection.DBConnection;
import biz.wakemeup.model.Comment;
import biz.wakemeup.model.DBEntity;

public class CommentDao implements BaseDao {

	private Map<String, DBEntity> comments = new HashMap<String, DBEntity>();
	Datastore ds;

	public CommentDao() {
		ds = DBConnection.instance().getDatabase();
		Query<Comment> query = ds.find(Comment.class);
		for (Comment comment : query) {
			comments.put(comment.getId(), comment);
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
		return comments;
	}

	public DBEntity findById(ObjectId id) {
		return comments.get(id.toString());
	}

	public void remove(ObjectId id) {
		ds.delete(Comment.class, id);
	}

	public void update(DBEntity entity) {
		ds.merge(entity);
	}

}
