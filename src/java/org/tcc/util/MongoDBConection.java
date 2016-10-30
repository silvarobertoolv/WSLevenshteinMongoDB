package org.tcc.util;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class MongoDBConection {

	DB db;
	
	public MongoDBConection( String dbName) throws UnknownHostException, MongoException {
		Mongo m = new Mongo( "localhost" , 27017 );
		db = m.getDB(dbName);
	}
	
	public DBCollection getCollection(String name) {
		return db.getCollection(name);
	}
	
	/*public void dropDatabase() {
		db.dropDatabase();
	} */
	
	public void ensureIndex(String col, String field) {
		getCollection(col).createIndex(new BasicDBObject(field, new Integer(1)));
	}
	
	public void save(DBObject object,String col) {
		getCollection(col).save(object);
	}
	
	public DBObject get(DBObject query,String col) {
		return getCollection(col).findOne(query);
	}
	
	public List<BasicDBObject> getAllDocs(String collectionName) {
		BasicDBObject query = new BasicDBObject();
                BasicDBObject field = new BasicDBObject();
                field.put("TIPL_CODIGO",1);
                field.put("TIPL_DESCRICAO",2);
		DBCursor cursor = getCollection(collectionName).find(query,field);
		
		List<BasicDBObject> dbObjects = new ArrayList<BasicDBObject>();
		while(cursor.hasNext()) {
			dbObjects.add((BasicDBObject) cursor.next());
		}
		return dbObjects;
	}
	
        public List<DBObject> getResultsInDescendingOrderByDate(DBCollection collection ) {

        List<DBObject> myList = null;
        DBCursor myCursor=collection.find().sort(new BasicDBObject("date",-1)).limit(10);
        myList = myCursor.toArray();

        return myList;
    }
        
        public List<Medicamentos> doAdvancedSearch(String searchString, String collection) {
        List<Medicamentos> list = new ArrayList<Medicamentos>();

        DBCursor cursor = getCollection(collection).find(new BasicDBObject("$text", new BasicDBObject("$search", searchString)));
        while (cursor.hasNext()) {
            DBObject document = cursor.next();
            Medicamentos data = new Medicamentos();
            data.setCodigo((String) document.get("TIPL_CODIGO"));
            data.setDescricao((String) document.get("TIPL_DESCRICAO"));
            list.add(data);
        }

        return list;
    }
        
	public long cnt(String col) {
		return getCollection(col).count();
	}
	
}
