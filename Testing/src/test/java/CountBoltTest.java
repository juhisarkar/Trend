import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.tuple.Tuple;
import org.junit.Test;
import com.talentica.twitter_trend.CountBolt;

public class CountBoltTest {
	static Logger logger = Logger.getLogger(CountBoltTest.class);
    @Test
    public void testExecuteTick() {
        Tuple tuple = mock(Tuple.class);
        when(tuple.getSourceComponent()).thenReturn("__system");
        when(tuple.getSourceStreamId()).thenReturn("__tick");
        Map stormConf = new HashMap();
        stormConf.put("dirToWrite", "outFile");
        OutputCollector collector = mock(OutputCollector.class);
        TopologyContext context = mock(TopologyContext.class);
        
        CountBolt bolt = new CountBolt();
        bolt.prepare(stormConf, context, collector);
        bolt.execute(tuple);
       }
    
    @SuppressWarnings("rawtypes")
    @Test
    public void testExecuteTick_Success() {
        Tuple wordOne = mock(Tuple.class);
        when(wordOne.getSourceComponent()).thenReturn("__not_system");
		when(wordOne.getSourceStreamId()).thenReturn("__tick");
		Tuple wordTwo = mock(Tuple.class);
	    when(wordTwo.getSourceComponent()).thenReturn("__not_system");
	    when(wordTwo.getSourceStreamId()).thenReturn("__not_tick");
		Tuple wordThree = mock(Tuple.class);
		when(wordThree.getSourceComponent()).thenReturn("__not_system");
		when(wordThree.getSourceStreamId()).thenReturn("__tick");
        Tuple tickTuple = mock(Tuple.class);
        when(tickTuple.getSourceComponent()).thenReturn("__system");
        when(tickTuple.getSourceStreamId()).thenReturn("__tick");
        OutputCollector collector = mock(OutputCollector.class);
        TopologyContext context = mock(TopologyContext.class);
        Map stormConf = new HashMap();
        stormConf.put("dirToWrite", "outFile");


        CountBolt bolt = new CountBolt();
        bolt.prepare(stormConf, context, collector);
        when(wordOne.getString(0)).thenReturn("google maps");
        when(wordTwo.getString(0)).thenReturn("google maps");
        when(wordThree.getString(0)).thenReturn("maps");
        bolt.execute(wordOne);
       
        bolt.execute(wordTwo);
        bolt.execute(wordThree);
        bolt.execute(tickTuple);
         
    }
	   

    @Test
    public void getMaxKWords_Top3Records_GetsTop3() {
        // given

        CountBolt bolt = new CountBolt();
        Map<String, Long> result = new HashMap<String, Long>();
        result.put("vishal", 90L);
        result.put("sachin", 80L);
        result.put("vaibhav", 70L);
        result.put("vishal1", 60L);
        result.put("sachin1", 50L);
        result.put("vaibhav1", 40L);
        result.put("vishal2", 100L);
        result.put("sachin2", 30L);
        result.put("vaibhav2", 20L);
        result.put("vishal3", 10L);
        result.put("sachin3", 300L);
        result.put("vaibhav3", 200L);
        Map<String, Long> expected = new HashMap<String, Long>();
        expected.put("sachin3", 300L);
        expected.put("vaibhav3", 200L);
        expected.put("vishal2", 100L);
        Map stormConf = new HashMap();
        stormConf.put("dirToWrite", "outFile");
        OutputCollector collector = mock(OutputCollector.class);
        TopologyContext context = mock(TopologyContext.class);
        bolt.prepare(stormConf, context, collector);
        Map<String, Long> actual = bolt.getMaxKWords(result);
        assertEquals(expected, actual);
    }

  

	@Test
    public void writeToFile_Exception()
    {
        Map<String, Long> result = new HashMap<String, Long>();
        result.put("vishal", 90L);
        result.put("sachin", 80L);
        result.put("vaibhav", 70L);
        result.put("vishal1", 60L);
        result.put("sachin1", 50L);
        result.put("vaibhav1", 40L);
        result.put("vishal2", 100L);
        result.put("sachin2", 30L);
        result.put("vaibhav2", 20L);
        result.put("vishal3", 10L);
        result.put("sachin3", 300L);
        result.put("vaibhav3", 200L);
        Map<String, Long> expected = new HashMap<String, Long>();
        expected.put("sachin3", 300L);
        expected.put("vaibhav3", 200L);
        expected.put("vishal2", 100L);
        CountBolt bolt = new CountBolt();
        bolt.writeToFile(result);
    }
    @Test
    public void writeToFile_Success()
    {
        Map<String, Long> result = new HashMap<String, Long>();
        result.put("vishal", 90L);
        result.put("sachin", 80L);
        result.put("vaibhav", 70L);
        result.put("vishal1", 60L);
        result.put("sachin1", 50L);
        result.put("vaibhav1", 40L);
        result.put("vishal2", 100L);
        result.put("sachin2", 30L);
        result.put("vaibhav2", 20L);
        result.put("vishal3", 10L);
        result.put("sachin3", 300L);
        result.put("vaibhav3", 200L);

        CountBolt bolt = new CountBolt();
        Map stormConf = new HashMap();
        stormConf.put("dirToWrite", "outFile");
        OutputCollector collector = mock(OutputCollector.class);
        TopologyContext context = mock(TopologyContext.class);

        bolt.prepare(stormConf,context,collector);
        bolt.writeToFile(result);
    }
    

    @Test
	public void	test_Wrong_Input()
	{
		Tuple wordOne = mock(Tuple.class);
		when(wordOne.getSourceComponent()).thenReturn("__not_system");
		when(wordOne.getSourceStreamId()).thenReturn("__tick");
		Tuple wordTwo = mock(Tuple.class);
		when(wordTwo.getSourceComponent()).thenReturn("__not_system");
		when(wordTwo.getSourceStreamId()).thenReturn("__tick");
		Tuple tickTuple = mock(Tuple.class);
	    when(tickTuple.getSourceComponent()).thenReturn("__system");
	    when(tickTuple.getSourceStreamId()).thenReturn("__tick");
		Map stormConf = new HashMap();
		stormConf.put("dirToWrite", "outFile");
		OutputCollector collector = mock(OutputCollector.class);
		TopologyContext context = mock(TopologyContext.class);

		CountBolt bolt = new CountBolt();
		bolt.prepare(stormConf, context, collector);
		when(wordOne.getString(0)).thenReturn(" ");
		when(wordTwo.getString(0)).thenReturn("google maps ");
		bolt.execute(wordTwo);
		bolt.execute(wordOne);
		bolt.execute(tickTuple);
		try {
			
		}catch(Exception e) {
		
		}
	}

}



