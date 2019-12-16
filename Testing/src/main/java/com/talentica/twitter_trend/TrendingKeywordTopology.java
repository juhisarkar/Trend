/*
 * topology class to set spout and set bolt and finally,to create topology
 */

package com.talentica.twitter_trend;

import java.util.UUID;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.StringScheme;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;

public class TrendingKeywordTopology {
	
	public static void main(String[] args) throws InterruptedException {
		
		Config config = new Config();
	    config.setDebug(true); //for debug method
	    config.put("dirToWrite",ServiceConstants.PATH_TO_WRITE); //path for writing the file
	    String zkConnString = ServiceConstants.ZK_CONN_STRING; //Zookeeper host
	    String topic = ServiceConstants.TOPIC_NAME;//topic name
	    
	    BrokerHosts hosts = new ZkHosts(zkConnString);
	    
	    SpoutConfig kafkaSpoutConfig = new SpoutConfig (hosts, topic, "/" + topic,    
	            UUID.randomUUID().toString());
	    
	    kafkaSpoutConfig.bufferSizeBytes = 1024 * 1024 * 4;
	    kafkaSpoutConfig.fetchSizeBytes = 1024 * 1024 * 4;
	    
	    kafkaSpoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme()); //parse byte buffer to string
		
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout(ServiceConstants.SPOUT_NAME, new KafkaSpout(kafkaSpoutConfig)); 
		builder.setBolt(ServiceConstants.BOLT_NAME, new CountBolt()).shuffleGrouping(ServiceConstants.SPOUT_NAME);
		
		LocalCluster cluster = new LocalCluster(); //setting up cluster in local mode
	    cluster.submitTopology(ServiceConstants.TOPOLOGY_NAME, config, builder.createTopology());

	    //Thread.sleep(10000);
	    //cluster.shutdown();
	}

}
