/*
 * bolt class to read the tuple,count occurence of each tuple, compute N max occurences of the tuples per M seconds and write them in a file
 */

package com.talentica.twitter_trend;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.storm.Config;
import org.apache.storm.Constants;
import org.apache.storm.shade.org.joda.time.DateTime;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;

public class CountBolt implements IRichBolt{
	
	OutputCollector collector;
	Map<String, Long> countOccurencesMap;
	int topNWords;
	private String fileName;
	
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
		this.countOccurencesMap = new HashMap<String, Long>();
		this.topNWords = ServiceConstants.TOP_N_WORDS; //configure number of maximum occurences to compute
		this.fileName=stormConf.get("dirToWrite").toString()+"Output"+"-"+ DateTime.now().toString("yyyy-dd-M--HH-mm-ss")+".txt"; //output file name format
	}
	
	/*method to sort given hashMap in reversed order and then return the value of first n maximum occurences in another hashMap*/
	public Map<String, Long> getMaxKWords(Map<String, Long> countOccurencesMap){
		Map<String, Long> resultMap = new LinkedHashMap<String, Long>();
		Map<String, Long> sortedByCountMap = countOccurencesMap.entrySet().stream().sorted(Map.Entry.<String,Long>comparingByValue().reversed())
				   .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
				   Iterator<Entry<String, Long>> iterator = sortedByCountMap.entrySet().iterator();//perform sorting in descending order
					int count = 1;
				   while(count <= topNWords && iterator.hasNext())
				   {
					 Entry<String, Long> entry = iterator.next();
					 resultMap.put(entry.getKey(), entry.getValue());
					 count++;
				   }
				   return resultMap;
	}
	/*method to write the hashMap passed as an argument to a file */
	public void writeToFile(Map<String, Long> resultMap) {
		try {
			//this.fileName= fileName+ DateTime.now().toString("yyyy-dd-M--HH-mm-ss")+".txt";
			 File file = new File(fileName);
			 FileWriter fw=new FileWriter(file,true);
			 for(Map.Entry<String, Long>entry : resultMap.entrySet()) {
				fw.write(entry.getKey()+": "+entry.getValue()+ "\n");
				}
				fw.write("Record for This Minute Ends \n ");
				fw.write("******************************************* \n \n \n");
				fw.close();    
				
			}catch(Exception e) {
				e.printStackTrace ();
		     }
		}
	/*calculate number of occurences of each record and call getMaxKWords() to check topmost occurences of the records for M seconds using ticktuple */
	public void execute(Tuple input) {
		
		if(input.getSourceComponent().equals(Constants.SYSTEM_COMPONENT_ID)
		        && input.getSourceStreamId().equals(Constants.SYSTEM_TICK_STREAM_ID)) {
			//System.out.println("***************** INSIDE TICK ***********************");
			Map<String, Long> resultMap = getMaxKWords(countOccurencesMap);
			writeToFile(resultMap);
			countOccurencesMap.clear(); 
			collector.ack(input); //finished processing an individual tuple
			return;
		}
		
		String word = input.getString(0);
		//System.out.println("*******************" + word);
		if(countOccurencesMap.containsKey(word)) {
			countOccurencesMap.put(word, countOccurencesMap.get(word)+1);
		} else {
			countOccurencesMap.put(word, 1L);
		}
		collector.ack(input);
	}

	public void cleanup() {
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		
	}
	/*configured the tick tuple time to M seconds*/
	public Map<String, Object> getComponentConfiguration() {
		Config config = new Config();
	    int tickFrequencyInSeconds = ServiceConstants.TICK_TUPLE_FREQ_SECS;
        config.put(Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, tickFrequencyInSeconds);
        return config;
	}

}
