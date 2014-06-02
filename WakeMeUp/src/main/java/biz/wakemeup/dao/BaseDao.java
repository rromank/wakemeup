package biz.wakemeup.dao;

import java.util.Map;

import org.bson.types.ObjectId;

import biz.wakemeup.model.DBEntity;

public interface BaseDao {
	public void insert(DBEntity entity);

	public Map<String, DBEntity> findAll();

	public DBEntity findById(ObjectId id);
	
	public void remove(ObjectId id);
	
	public void update(DBEntity entity);
}
