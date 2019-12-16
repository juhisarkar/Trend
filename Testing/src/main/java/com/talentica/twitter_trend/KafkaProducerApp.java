/* 
 * kafka producer class to read from data file and wrtie it into a topic after definite time interval
 * */

package com.talentica.twitter_trend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.Logger;

public class KafkaProducerApp {
	static Logger logger = Logger.getLogger(KafkaProducerApp.class);

	public static void main(String args[]) 
	    {
		String csvFile = ServiceConstants.PATH_TO_READ; //path for data file
        BufferedReader br = null;
        String readEachLine = "";
		Properties properties = new Properties();
		  //172.29.3.21:9092
	    properties.setProperty("bootstrap.servers", ServiceConstants.BOOTSTRAP_SEVERS);//establish initial connection to Kafka cluster
	    properties.setProperty("key.serializer", ServiceConstants.KEY_SERIALIZER);
	    properties.setProperty("value.serializer", ServiceConstants.VALUE_SERIALIZER);
	       
	    Producer<String, String> producer = new org.apache.kafka.clients.producer.KafkaProducer<String, String>(properties);
	    try {
	    	br = new BufferedReader(new FileReader(csvFile));//read from file
	    	readEachLine = br.readLine();
	        while (readEachLine != null) {
	        	readEachLine=readEachLine.trim();
	        	readEachLine=readEachLine.toLowerCase();

	            ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(ServiceConstants.TOPIC_NAME, readEachLine);//create record with topic name and sending value of the value to it
	            producer.send(producerRecord);//send record to topic 
	            Thread.sleep(1000);//send after every 10secs
	            readEachLine = br.readLine();//read next line
	         }
	        }catch(Exception e) {
	        	logger.error("Error occurred while publishing task on Kafka.");
	        }finally {
	        	 producer.close();//close producer
	        }
	       

	    }
	}


