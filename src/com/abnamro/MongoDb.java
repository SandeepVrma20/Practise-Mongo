package com.abnamro;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

public class MongoDb {

	public static void main(String[] args) throws FileNotFoundException, IOException {

	    
		  MongoClient mongoClient = new MongoClient("localhost", 27017);
		   // MongoDatabase mongoDb = mongoClient.getDatabase("test");
		    DB db =mongoClient.getDB("test");
		    DBCollection dbCollection =  db.getCollection("sandeep");
		    System.out.println("Connect to database successfully");
		   
		    System.out.println("Connect to database successfully");
		   
		    
		    GridFS fileStore = new GridFS(db,"filestore");
		   // File f = new File("C:\\Users\\C33129\\Documents\\file.txt");
		    File f = new File("C:\\Users\\C33129\\Documents\\Sandeep_pic.jpg");
		    
		   // File f = new File("C:\\Users\\C33129\\Documents\\sandeep_resume.doc");
		    GridFSInputFile inputFile;
			try {
				inputFile = fileStore.createFile(f);
				inputFile.setId(f.getName()); 
			    inputFile.setFilename(f.getName());
			     inputFile.save();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
			BasicDBObject query = new BasicDBObject();
			query.put("_id", f.getName());
			GridFSDBFile gridFile = fileStore.findOne(query);
			System.out.println(gridFile.getFilename() + gridFile.getId());

			 BasicDBObject dbobject= new BasicDBObject();
			    dbobject.put("Name","Sandeep Verma");
			    dbobject.put("Age",28);
			    dbobject.put("Phone","8869549534");
			   // dbobject.put("file", gridFile.getId());
			    dbobject.put("Photo", gridFile.getId());
			    dbCollection.insert(dbobject);
			 
			   // fileStore.remove(gridFile);
			 //Writing file into original state
			    InputStream in = gridFile.getInputStream();
			    ByteArrayOutputStream out = new ByteArrayOutputStream();
			     int data;
				try {
					data = in.read();
					 while (data >= 0) {
						   out.write((char) data);
						    data = in.read();
						   }
						  out.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try(OutputStream outputStream = new FileOutputStream("C:\\Users\\C33129\\Documents\\output\\"+gridFile.getId())) {
					out.writeTo(outputStream);
				}
			    
	}

	

}
