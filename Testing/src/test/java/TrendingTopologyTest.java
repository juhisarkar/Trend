/*import static org.junit.Assert.*;

import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.state.State;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.IStatefulBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.topology.base.BaseStatefulBolt;
import org.apache.storm.tuple.Tuple;
import org.junit.Test;

import com.talentica.twitter_trend.TrendingKeywordTopology;

public class TrendingTopologyTest {
	   private final TrendingKeywordTopology builder = new TrendingKeywordTopology();

	private IStatefulBolt makeDummyStatefulBolt() {
        return new BaseStatefulBolt() {
            @Override
            public void execute(Tuple input) {}

            private void writeObject(java.io.ObjectOutputStream stream) {}

			@Override
			public void initState(State state) {
				// TODO Auto-generated method stub
				
			}
        };
    }

    private IRichSpout makeDummySpout() {
        return new BaseRichSpout() {
            @Override
            public void declareOutputFields(OutputFieldsDeclarer declarer) {}

             @Override
            public void nextTuple() {}

            private void writeObject(java.io.ObjectOutputStream stream) {}

			@Override
			public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
				// TODO Auto-generated method stub
				
			}
        };
    }
    @Test(expected = IllegalArgumentException.class)




*/
    public void testSetSpout() {
      //  builder.setSpout("spout", mock(IRichSpout.class), 0);
    }
}
