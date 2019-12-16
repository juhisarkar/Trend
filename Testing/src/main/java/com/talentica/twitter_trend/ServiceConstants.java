package com.talentica.twitter_trend;

import org.apache.kafka.common.serialization.StringSerializer;

public final class ServiceConstants {
	public static final String PATH_TO_READ = "/home/juhisarkar/Downloads/keywords.csv";
	public static final String BOOTSTRAP_SEVERS = "172.29.3.21:9092";
	public static final String KEY_SERIALIZER = StringSerializer.class.getName();
	public static final String VALUE_SERIALIZER = StringSerializer.class.getName();
	public static final String PATH_TO_WRITE = "/home/juhisarkar/Downloads/Files/yDir";
	public static final String ZK_CONN_STRING = "localhost:2182"; 
	public static final String TOPIC_NAME= "mytopic2";
	public static final String SPOUT_NAME= "trend-kafka-spout";
	public static final String BOLT_NAME= "count-bolt";
	public static final String TOPOLOGY_NAME= "KafkaStormSample";
	public static final int TOP_N_WORDS = 10;
	public static final int TICK_TUPLE_FREQ_SECS=60;
	
}
