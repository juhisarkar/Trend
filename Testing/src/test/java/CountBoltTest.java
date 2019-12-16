/*

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.common.metrics.stats.Count;
import org.apache.storm.Config;
import org.apache.storm.task.GeneralTopologyContext;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.tuple.TupleImpl;
import org.apache.storm.utils.*;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.MessageId;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.junit.Assert;
import org.junit.Test;
import static org.fest.assertions.api.Assertions.assertThat;
//import com.in28minutes.junit.helper.StringHelper;
import com.talentica.twitter_trend.CountBolt;
import org.apache.storm.utils.Utils;

public class CountBoltTest {

	/*@SuppressWarnings("unchecked")
	@Test
	public void test() {
		// using Mockito.mock() method
		//List<String> mockList = mock(List.class);
		CountBolt mockList = mock(CountBolt.class);
		assertTrue(true);
	}

    @Test
    public void testWriteToFile() {
        Tuple t = mock(Tuple.class);
        Map stormConf = new HashMap();

        stormConf.put("dirToWrite", "outFile");
        OutputCollector collector = mock(OutputCollector.class);
        TopologyContext context = mock(TopologyContext.class);
        Map<String, Long> result = new HashMap<String, Long>();
        result.put("vishal", 10L);
        result.put("sachin", 30L);
        result.put("vaibhav", 20L);
        CountBolt b = new CountBolt();
        b.prepare(stormConf, context, collector);
        b.writeToFile(result);

    }

    @Test
    public void testExecuteTick() {
        Tuple t = mock(Tuple.class);
        when(t.getFields()).thenReturn(new Fields("myAttribute"));
        when(t.getSourceComponent()).thenReturn("__system");
        when(t.getSourceStreamId()).thenReturn("__tick");
        Map stormConf = new HashMap();
        stormConf.put("dirToWrite", "outFile");
        OutputCollector collector = mock(OutputCollector.class);
        TopologyContext context = mock(TopologyContext.class);
        
        CountBolt b = new CountBolt();
        b.prepare(stormConf, context, collector);
        b.execute(t);
        

    }
    
    @SuppressWarnings("rawtypes")
    @Test
    public void testExecuteTick_Success() {
        Tuple t = mock(Tuple.class);
        when(t.getFields()).thenReturn(new Fields("myAttribute"));
        when(t.getSourceComponent()).thenReturn("__system");
        when(t.getSourceStreamId()).thenReturn("__tick");
        OutputCollector collector = mock(OutputCollector.class);
        TopologyContext context = mock(TopologyContext.class);
        // MockContext mockContext = new MockContext()
        List<Object> data = new ArrayList();
        data.add("abc");
        data.add("abc");
        data.add("gmail");
        t = new TupleImpl(context, data, t.getSourceTask(), "__system");
        Map stormConf = new HashMap();
        stormConf.put("dirToWrite", "outFile");


        CountBolt b = new CountBolt();
        b.prepare(stormConf, context, collector);
        b.execute(t);
        
        // verify(collector).emit(any(Values.class));
    }
	   /* @SuppressWarnings("rawtypes")
	    @Test
	    public void shouldEmitNothingIfNoObjectHasBeenCountedYetAndTickTupleIsReceived() {
	      // given
	      Tuple tickTuple = MockTupleHelpers.mockTickTuple();
	      CountBolt bolt = new CountBolt();
	      Map conf = mock(Map.class);
	      TopologyContext context = mock(TopologyContext.class);
	      OutputCollector collector = mock(OutputCollector.class);
	      bolt.prepare(conf, context, collector);

	      // when
	      bolt.execute(tickTuple);

	      // then
	      verifyZeroInteractions(collector);
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
	public void	execute_DuplicateWord_Success()
	{
		Tuple t = mock(Tuple.class);
		when(t.getFields()).thenReturn(new Fields("myAttribute"));
		when(t.getSourceComponent()).thenReturn("__not_system");
		when(t.getSourceStreamId()).thenReturn("__tick");
		Map stormConf = new HashMap();
		stormConf.put("dirToWrite", "outFile");
		OutputCollector collector = mock(OutputCollector.class);
		TopologyContext context = mock(TopologyContext.class);

		CountBolt b = new CountBolt();
		b.prepare(stormConf, context, collector);
		b.execute(t);
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

}
class MockContext extends GeneralTopologyContext {

    private final Fields fields;

    public MockContext(String[] fieldNames) {
        super(null, new HashMap<>(), null, null, null, null);
        this.fields = new Fields(fieldNames);
    }

    @Override
    public String getComponentId(int taskId) {
        return "component";
    }

    @Override
    public Fields getComponentOutputFields(String componentId, String streamId) {
        return fields;
    }
   

}*/
