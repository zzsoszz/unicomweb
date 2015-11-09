//package dinamica.db;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//
//import org.bson.*;
//import org.bson.io.BasicOutputBuffer;
//import org.bson.io.OutputBuffer;
//import org.jongo.marshall.jackson.bson4jackson.MongoBsonFactory;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.ObjectReader;
//import com.fasterxml.jackson.databind.ObjectWriter;
//import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
//import com.mongodb.BasicDBObject;
//import com.mongodb.DBEncoder;
//import com.mongodb.DBObject;
//import com.mongodb.DefaultDBEncoder;
////http://stackoverflow.com/questions/15789471/efficient-pojo-mapping-to-from-java-mongo-dbobject-using-jackson
//public class JongoUtils {
//
//    private final static ObjectMapper mapper = new ObjectMapper(MongoBsonFactory.createFactory());
//    
//    static {
//        mapper.setVisibilityChecker(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
//    }
//    
//    
//    public static DBObject getDbObject(Object o) throws IOException {
//        ObjectWriter writer = mapper.writer();
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        writer.writeValue(baos, o);
//        DBObject dbo = new LazyWriteableDBObject(baos.toByteArray(), new LazyBSONCallback());
//        //turn it into a proper DBObject otherwise it can't be edited.
//        DBObject result = new BasicDBObject();
//        result.putAll(dbo);
//        return result;
//    }
//
//    public static <T> T getPojo(DBObject o, Class<T> clazz) throws IOException {
//        ObjectReader reader = mapper.reader(clazz);
//        DBEncoder dbEncoder = DefaultDBEncoder.FACTORY.create();
//        OutputBuffer buffer = new BasicOutputBuffer();
//        dbEncoder.writeObject(buffer, o);
//        T pojo = reader.readValue(buffer.toByteArray());
//        return pojo;
//    }
//}
