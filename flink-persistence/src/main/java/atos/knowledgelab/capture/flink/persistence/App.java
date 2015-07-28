package atos.knowledgelab.capture.flink.persistence;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.api.java.typeutils.PojoTypeInfo;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.FromElementsFunction;
import org.apache.flink.streaming.api.functions.source.FromIteratorFunction;
import org.apache.flink.streaming.connectors.kafka.Utils;
import org.apache.flink.streaming.connectors.kafka.api.KafkaSource;
import org.apache.flink.streaming.util.serialization.DeserializationSchema;
import org.apache.flink.streaming.util.serialization.JavaDefaultStringSchema;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;

//import atos.knowledgelab.capture.bean.stream.StreamItem;


public class App {
	
	static Logger LOGGER = Logger.getLogger(App.class.getName());
	private static JAXBContext jc;
	private static Unmarshaller unmarshaller;
	
	public static void main(String[] args) throws Exception {
		
        //jc = JAXBContext.newInstance(StreamItem.class);
		unmarshaller = jc.createUnmarshaller();

		StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment().setParallelism(4);
		
		// Adding a source of items
						
		//DataStreamSource<SerializableStreamItem> stream = env.fromCollection(getSICollection());
			
		DataStreamSource<String> stream = env.addSource(
				new KafkaSource<String>("?", "?", new JavaDefaultStringSchema()));
		
		//stream.groupBy("dataChannelId").print();
		
		stream.print();

		env.execute();
	}
	
	/*private static Collection<SerializableStreamItem> getSICollection() {
		ArrayList <SerializableStreamItem> collection = new ArrayList<SerializableStreamItem>();
		for (int i = 0; i < 100; i++){
			SerializableStreamItem it = new SerializableStreamItem();
			it.setDataChannelId(Integer.toString(i % 4));
			it.setDataSourceId(Integer.toString(i % 8));
			collection.add(it);
		}
		return collection;
	}*/

}
