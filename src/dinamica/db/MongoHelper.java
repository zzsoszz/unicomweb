package dinamica.db;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
public class MongoHelper {
	private static MongoDatabase database;
	public static  MongoDatabase getDatabase()
	{
		if(database==null)
		{
			init();
		}
		return database;
	}
	public static  void init()
	{
		//MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
		MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://111.111.111.111:27017"));
		database=mongoClient.getDatabase("mydb");
	}
}


//
//public static void test()
//{
//	
//	MongoCollection<Document> collection = MongoHelper.getDatabase().getCollection("test");
//	Document doc = new Document("name", "MongoDB")
//    .append("type", "database")
//    .append("count", 1)
//    .append("info", new Document("x", 203).append("y", 102));
//	collection.insertOne(doc);
//	
//	
//	
//	List<Document> documents = new ArrayList<Document>();
//	for (int i = 0; i < 100; i++) {
//	    documents.add(new Document("i", i));
//	}
//	collection.insertMany(documents);
//	
//	
//	MongoCursor<Document> cursor = collection.find().iterator();
//	try {
//	    while (cursor.hasNext()) {
//	        System.out.println(cursor.next());
//	    }
//	} finally {
//	    cursor.close();
//	}
//	
//	for (Document cur : collection.find()) {
//	    System.out.println(cur);
//	}
//	
//	// now use a range query to get a larger subset
//	cursor = collection.find(gt("i", 50)).iterator();
//	
//	try {
//	    while (cursor.hasNext()) {
//	        System.out.println(cursor.next());
//	    }
//	} finally {
//	    cursor.close();
//	}
//	
//	
//	cursor = collection.find(and(gt("i", 50), lte("i", 100))).iterator();
//
//	try {
//	    while (cursor.hasNext()) {
//	        System.out.println(cursor.next());
//	    }
//	} finally {
//	    cursor.close();
//	}
//}
//


//// To directly connect to a single MongoDB server (note that this will not auto-discover the primary even
//// if it's a member of a replica set:
//MongoClient mongoClient = new MongoClient();
//// or
//MongoClient mongoClient = new MongoClient( "localhost" );
//// or
//MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
//// or, to connect to a replica set, with auto-discovery of the primary, supply a seed list of members
//MongoClient mongoClient = new MongoClient(Arrays.asList(new ServerAddress("localhost", 27017),
//                                      new ServerAddress("localhost", 27018),
//                                      new ServerAddress("localhost", 27019)));
//// or use a connection string
//MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017,localhost:27018,localhost:27019"));




//
//
//
//
//
//Java MongoDB : Insert Document(s) in Collection Examples
// 
//
//
//
//Follow @HowToDoInJava, Read RSS Feed
// 
//
//In MongoDB learning series, we have already covered the MongoDB basics, MongoDB installation in windows, and how to query/select documents from a collection. In this tutorial, I am listing 4 ways you can utilize to insert or add document(s) into a collection in MongoDB.
// 
//
// 
//
//
// List of examples in this tutorial
//
//1) Insert BasicDBObject in collection
//2) Use DBObjectBuilder to insert document in collection
//3) Use java.util.HashMap to build BasicDBObject and insert into collection
//4) Parse JSON to build DBObject and insert into collection
//
//Sample document which we will insert into collection
//{
//    "name":"lokesh",
//    "website":"howtodoinjava.com",
//    "address":{
//        "addressLine1":"Some address",
//        "addressLine2":"Karol Bagh",
//        "addressLine3":"New Delhi, India"
//    }
//}
//
//
//1) Insert BasicDBObject in collection
//
//This one is simplest. Create an instance of BasicDBObject, populate data and call collection.insert() method.
//
//
// 
//BasicDBObject document = new BasicDBObject();
//document.put("name", "lokesh");
//document.put("website", "howtodoinjava.com");
// 
//BasicDBObject documentDetail = new BasicDBObject();
//documentDetail.put("addressLine1", "Sweet Home");
//documentDetail.put("addressLine2", "Karol Bagh");
//documentDetail.put("addressLine3", "New Delhi, India");
// 
//document.put("address", documentDetail);
// 
//collection.insert(document);
// 
//Output:
// 
//{ "_id" : { "$oid" : "538d56a3364192189d4f98fe"} , "name" : "lokesh" , "website" : "howtodoinjava.com" , 
//"address" : { "addressLine1" : "Sweet Home" , "addressLine2" : "Karol Bagh" , "addressLine3" : "New Delhi, India"}}
// 
//
//2) Use DBObjectBuilder to insert document in collection
//
//Very similar to above example, only uses DBObjectBuilder to build DBObject.
//
// 
//BasicDBObjectBuilder documentBuilder = BasicDBObjectBuilder.start()
//        .add("name", "lokesh")
//        .add("website", "howtodoinjava.com");
//  
//BasicDBObjectBuilder documentBuilderDetail = BasicDBObjectBuilder.start()
//.add("addressLine1", "Some address")
//.add("addressLine2", "Karol Bagh")
//.add("addressLine3", "New Delhi, India");
// 
//documentBuilder.add("address", documentBuilderDetail.get());
// 
//collection.insert(documentBuilder.get());
// 
//Output:
// 
//{ "_id" : { "$oid" : "538d56a3364192189d4f98ff"} , "name" : "lokesh" , "website" : "howtodoinjava.com" , 
//"address" : { "addressLine1" : "Sweet Home" , "addressLine2" : "Karol Bagh" , "addressLine3" : "New Delhi, India"}}
// 
//
//3) Use java.util.HashMap to build BasicDBObject and insert into collection
//
//Here, you fist put the data in hashmap and then use this hashmap to build BasicDBObject.
//
// 
//Map<String, Object> documentMap = new HashMap<String, Object>();
//documentMap.put("name", "lokesh");
//documentMap.put("website", "howtodoinjava.com");
// 
//Map<String, Object> documentMapDetail = new HashMap<String, Object>();
//documentMapDetail.put("addressLine1", "Some address");
//documentMapDetail.put("addressLine2", "Karol Bagh");
//documentMapDetail.put("addressLine3", "New Delhi, India");
// 
//documentMap.put("address", documentMapDetail);
// 
//collection.insert(new BasicDBObject(documentMap));
// 
//Output:
// 
//{ "_id" : { "$oid" : "538d56a3364192189d4f98fg"} , "name" : "lokesh" , "website" : "howtodoinjava.com" , 
//"address" : { "addressLine1" : "Sweet Home" , "addressLine2" : "Karol Bagh" , "addressLine3" : "New Delhi, India"}}
// 
//
//4) Parse JSON to build DBObject and insert into collection
//
// 
//String json = "{ 'name' : 'lokesh' , " +
//                "'website' : 'howtodoinjava.com' , " +
//                "'address' : { 'addressLine1' : 'Some address' , " +
//                              "'addressLine2' : 'Karol Bagh' , " +
//                              "'addressLine3' : 'New Delhi, India'}" +
//                            "}";
//      
//DBObject dbObject = (DBObject)JSON.parse(json);
// 
//collection.insert(dbObject);
// 
//Output:
// 
//{ "_id" : { "$oid" : "538d56a3364192189d4f98fg"} , "name" : "lokesh" , "website" : "howtodoinjava.com" , 
//"address" : { "addressLine1" : "Sweet Home" , "addressLine2" : "Karol Bagh" , "addressLine3" : "New Delhi, India"}}
// 
//
//Complete Example and Sourcecode
//
//The complete working code for all above examples is as below:
//
// 
//package examples.mongodb.crud;
// 
//import java.net.UnknownHostException;
//import java.util.HashMap;
//import java.util.Map;
// 
//import com.mongodb.BasicDBObject;
//import com.mongodb.BasicDBObjectBuilder;
//import com.mongodb.DB;
//import com.mongodb.DBCollection;
//import com.mongodb.DBCursor;
//import com.mongodb.DBObject;
//import com.mongodb.MongoClient;
//import com.mongodb.WriteResult;
//import com.mongodb.util.JSON;
// 
//public class MongoDBInsertDataExample 
//{
//    public static void main(String[] args) throws UnknownHostException 
//    {
//        MongoClient mongo = new MongoClient("localhost", 27017);
//        DB db = mongo.getDB("howtodoinjava");
//        DBCollection collection = db.getCollection("users");
//         
//        ///Delete All documents before running example again
//        WriteResult result = collection.remove(new BasicDBObject());
//        System.out.println(result.toString());
//         
//        basicDBObject_Example(collection);
//         
//        basicDBObjectBuilder_Example(collection);
//         
//        hashMap_Example(collection);
//         
//        parseJSON_Example(collection);
//         
//        DBCursor cursor = collection.find();
//        while(cursor.hasNext()) {
//            System.out.println(cursor.next());
//        }
//    }
//     
//    private static void basicDBObject_Example(DBCollection collection){
//        BasicDBObject document = new BasicDBObject();
//        document.put("name", "lokesh");
//        document.put("website", "howtodoinjava.com");
//      
//        BasicDBObject documentDetail = new BasicDBObject();
//        documentDetail.put("addressLine1", "Sweet Home");
//        documentDetail.put("addressLine2", "Karol Bagh");
//        documentDetail.put("addressLine3", "New Delhi, India");
//      
//        document.put("address", documentDetail);
//      
//        collection.insert(document);
//    } 
//     
//    private static void basicDBObjectBuilder_Example(DBCollection collection){
//        BasicDBObjectBuilder documentBuilder = BasicDBObjectBuilder.start()
//                .add("name", "lokesh")
//                .add("website", "howtodoinjava.com");
//          
//        BasicDBObjectBuilder documentBuilderDetail = BasicDBObjectBuilder.start()
//        .add("addressLine1", "Some address")
//        .add("addressLine2", "Karol Bagh")
//        .add("addressLine3", "New Delhi, India");
//      
//        documentBuilder.add("address", documentBuilderDetail.get());
//      
//        collection.insert(documentBuilder.get());
//    }
//     
//    private static void hashMap_Example(DBCollection collection){
//        Map<String, Object> documentMap = new HashMap<String, Object>();
//        documentMap.put("name", "lokesh");
//        documentMap.put("website", "howtodoinjava.com");
//      
//        Map<String, Object> documentMapDetail = new HashMap<String, Object>();
//        documentMapDetail.put("addressLine1", "Some address");
//        documentMapDetail.put("addressLine2", "Karol Bagh");
//        documentMapDetail.put("addressLine3", "New Delhi, India");
//      
//        documentMap.put("address", documentMapDetail);
//      
//        collection.insert(new BasicDBObject(documentMap));
//    }
//     
//    private static void parseJSON_Example(DBCollection collection){
//        String json = "{ 'name' : 'lokesh' , " +
//                        "'website' : 'howtodoinjava.com' , " +
//                        "'address' : { 'addressLine1' : 'Some address' , " +
//                                      "'addressLine2' : 'Karol Bagh' , " +
//                                      "'addressLine3' : 'New Delhi, India'}" +
//                                    "}";
//        DBObject dbObject = (DBObject)JSON.parse(json);
//        collection.insert(dbObject);
//    }
//     
//}
// 
//Output:
// 
//{ "serverUsed" : "localhost/127.0.0.1:27017" , "connectionId" : 3 , "n" : 4 , "syncMillis" : 0 , "writtenTo" :  null  , "err" :  null  , "ok" : 1.0}
//{ "_id" : { "$oid" : "538d5b3936417871aa391d20"} , "name" : "lokesh" , "website" : "howtodoinjava.com" , "address" : { "addressLine1" : "Sweet Home" , "addressLine2" : "Karol Bagh" , "addressLine3" : "New Delhi, India"}}
//{ "_id" : { "$oid" : "538d5b3936417871aa391d21"} , "name" : "lokesh" , "website" : "howtodoinjava.com" , "address" : { "addressLine1" : "Some address" , "addressLine2" : "Karol Bagh" , "addressLine3" : "New Delhi, India"}}
//{ "_id" : { "$oid" : "538d5b3936417871aa391d22"} , "address" : { "addressLine3" : "New Delhi, India" , "addressLine2" : "Karol Bagh" , "addressLine1" : "Some address"} , "website" : "howtodoinjava.com" , "name" : "lokesh"}
//{ "_id" : { "$oid" : "538d5b3936417871aa391d23"} , "name" : "lokesh" , "website" : "howtodoinjava.com" , "address" : { "addressLine1" : "Some address" , "addressLine2" : "Karol Bagh" , "addressLine3" : "New Delhi, India"}}
//Happy Learning !!
