package com.demo.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.mongodb.WriteResult;

@Transactional
public abstract class IBaseDAO<T extends Serializable> {
	 
	private Class<T> clazz;
	
	@Autowired
	protected MongoTemplate mongoTemplate;

	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	public Class<T> getClazz() {
		return this.clazz;
	}
	 
	public T findOne(String id){
		return mongoTemplate.findById(id, getClazz());
	}
		
	public List<T> findAll() {
		return mongoTemplate.findAll(clazz);
	}
	
	public void insert(T entity){
		mongoTemplate.insert(entity);
	}
	
	public T update(T entity){
		mongoTemplate.save(entity);
		
		return entity;
	}

	public boolean delete(T entity){
		WriteResult wr = mongoTemplate.remove(entity);
		if (wr.getN() > 0) return true;
		
		return false;
	}

	public boolean deleteById(String id) {
		T obj = mongoTemplate.findById(id, getClazz());
		
		WriteResult wr = mongoTemplate.remove(obj);
		if (wr.getN() > 0) return true;
		
		return false;
	}
}
