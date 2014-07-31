package dvla.hackathon.eight.datastorage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Base64;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import dvla.hackathon.eight.Log.qdLog;
import dvla.hackathon.eight.api.JSON.ColourByReg;
import dvla.hackathon.eight.api.JSON.Report;

public class StorageLayer {

	private MongoClient mongoClient;
	private DB db;

	private static final String REPORTS_DATABASE = "Notifications";
	private static final String VEHICLE_DATABASE = "vehicle";
	private static final String PROVIDED_DATABASE_NAME = "data";
	private static final String PROVIDED_DATABASE = "localhost";
	//private static final String PROVIDED_DATABASE = "185.40.9.188";

	public StorageLayer()
	{
		connect();
	}

	public void connect()
	{
		try{
			qdLog.writeLog("looking for database");
			mongoClient = new MongoClient(PROVIDED_DATABASE);
			db = mongoClient.getDB(PROVIDED_DATABASE_NAME);
		}
		catch (Exception e){
			qdLog.writeLog(e.getMessage());
		}

	}

	public ColourByReg getColour(String registration, ColourByReg cBR){

		DBCollection coll = db.getCollection(VEHICLE_DATABASE);

		BasicDBObject query = new BasicDBObject("VRM", registration);

		DBObject result = coll.findOne(query);

		if (result == null){
			cBR.notFound = true;
			return cBR;
		}

		cBR.colour = (String) result.get("Colour");
		cBR.make = (String) result.get("Make");
		cBR.model = (String) result.get("Model");

		cBR.isTaxed = (Boolean) result.get("IsTaxed");


		return cBR;
	}

	public Report getReport(String registration, Report report) {
		DBCollection coll = db.getCollection(REPORTS_DATABASE);


		BasicDBObject query = new BasicDBObject("VRM", report.VRM);

		DBObject result = coll.findOne(query);

		if (result == null){
			report.notFound = true;
			return report;
		}

		report.notFound=false;
		report.CaseNo = (Integer) result.get("CaseNo");
		report.VRM = (String) result.get("VRM");
		report.DateTime =(Date) result.get("DateTime");
		report.MACAddress = (String) result.get("MACAddress");
		BasicDBList b = (BasicDBList) result.get("Photo");

		String[] al = new String[b.size()];
		for (int i=0; i < al.length; i++){
			byte[] bytes = (byte[]) b.get(i);
			al[i]=(Base64.encodeBase64String(bytes));
		}


		report.Photo = al;

		report.GpsLocation = result.get("GpsLocation");
		report.BrowserInfo = (String) result.get("BrowserInfo");


		return report;
	}

	public void createReport(Report report) throws Exception
	{
		DBCollection coll = db.getCollection(REPORTS_DATABASE);

		BasicDBObject dbo = new BasicDBObject();

		dbo.put("CaseNo", report.CaseNo);
		dbo.put("VRM", report.VRM);
		dbo.put("DateTime", report.DateTime);
		dbo.put("MACAddress", report.MACAddress);

		byte[][] bytes = new byte[report.Photo.length][];

		for (int i = 0; i < report.Photo.length; i++){
			bytes[i] = Base64.decodeBase64(report.Photo[i]);
		}



		dbo.put("Photo", bytes);
		dbo.put("GpsLocation", report.GpsLocation);
		dbo.put("BrowserInfo", report.BrowserInfo);

		coll.insert(dbo);

	}

	public List<Report> getAllReports(boolean getPhotos) {
		DBCollection coll = db.getCollection(REPORTS_DATABASE);


		LinkedList<Report> reports = new LinkedList<Report>();

		DBCursor cursor = coll.find();
		try{
			while(cursor.hasNext()){

				Report report = new Report();
				DBObject result = cursor.next();

				report.CaseNo = (Integer) result.get("CaseNo");
				report.VRM = (String) result.get("VRM");
				report.DateTime =(Date) result.get("DateTime");
				report.MACAddress = (String) result.get("MACAddress");
				
				if(getPhotos){
				BasicDBList b = (BasicDBList) result.get("Photo");

				String[] al = new String[b.size()];
				for (int i=0; i < al.length; i++){
					byte[] bytes = (byte[]) b.get(i);
					al[i]=(Base64.encodeBase64String(bytes));
				}


				report.Photo = al;
				}

				report.GpsLocation = result.get("GpsLocation");
				report.BrowserInfo = (String) result.get("BrowserInfo");

				reports.add(report);

			}
		} finally { cursor.close();}


		return reports;
	}


}
