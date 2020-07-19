package gr.unipi.geotextualindexing.sfc;

import java.util.Arrays;

import org.bson.Document;
import org.davidmoten.hilbert.HilbertCurve;
import org.davidmoten.hilbert.SmallHilbertCurve;
import org.junit.Test;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class BoxRangeQueriesTest {
	
	 @Test
	 public void main() throws Exception { 
		 // Mongodb initialization parameters.
	        int port_no = 27017;
	        String host_name = "localhost", db_name = "mongodb", db_coll_name = "hilbertcollection";
	        //hilbertcollection
	        // Mongodb connection string.
	        String client_url = "mongodb://" + host_name + ":" + port_no + "/" + db_name;
	        MongoClientURI uri = new MongoClientURI(client_url);
	 
	        // Connecting to the mongodb server using the given client uri.
	        MongoClient mongo_client = new MongoClient(uri);
	 
	        // Fetching the database from the mongodb.
	        MongoDatabase db = mongo_client.getDatabase(db_name);

	        // Fetching the collection from the mongodb.
	        MongoCollection<Document> coll = db.getCollection(db_coll_name);
	        
	        int bits = 5;
	        int dimensions = 2;
	        SmallHilbertCurve h = HilbertCurve.small().bits(bits).dimensions(dimensions);
	        long max = 1L << bits;
	        double lon1=-30.937563;
	        double lat1= 60.19198813;
	        double lon2=-70.6995;
	        double lat2= 20.974964;
	        double maxlon=180d;
	        double minlon=-180d;
	        double maxlat=90d;
	        double minlat=-90d;
	        String[] keywords = {"Cambodian","Lebanese","Persian"};
	 
	       BoxRangeQueries rq= new BoxRangeQueries(coll);
	       //rq.executeQuery(coll, Arrays.asList(Document.parse(rq.getJsonQuery(lon1,lon2, minlon, maxlon,  lat1,lat2, minlat, maxlat, keywords , max, h))));
	       //rq.executeQuery(coll, Arrays.asList(Document.parse(rq.getJsonQuery(lon1,lon2, minlon, maxlon,  lat1,lat2, minlat, maxlat, keywords , max, h)),Document.parse(rq.getDoucumentsCount())));    
	       //rq.executeQuery(coll, Arrays.asList(Document.parse(rq.getJsonQuery(lon1,lon2, minlon, maxlon,  lat1,lat2, minlat, maxlat, keywords , max, h)),Document.parse(rq.getDoucumentsGroupidfirst()),Document.parse(rq.getDoucumentsCountWithGroupid())));    
		 
	       
	       //executionStats
	       Gson gson = new Gson();
	       System.out.println(gson.toJson(db.runCommand(Document.parse("{ \"explain\": { \"aggregate\": \""+db_coll_name+"\", pipeline:["+rq.getJsonQuery(lon1,lon2, minlon, maxlon,  lat1,lat2, minlat, maxlat, keywords , max, h)+"], \"cursor\":{} }}"))));
	       //System.out.println(gson.toJson(db.runCommand(Document.parse("{ \"explain\": { \"aggregate\": \""+db_coll_name+"\", pipeline:["+rq.getJsonQuery(lon1,lon2, minlon, maxlon,  lat1,lat2, minlat, maxlat, keywords , max, h)+","+rq.getDoucumentsGroupidfirst()+","+rq.getDoucumentsCountWithGroupid()+"], \"cursor\":{} }}"))));

	        mongo_client.close();
	 }

}
