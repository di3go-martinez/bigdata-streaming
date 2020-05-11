package bigdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class HubProcessor {

    private static final Logger logger = Logger.getLogger(HubProcessor.class);

    public static void main(String[] args) throws InterruptedException {
        Logger.getLogger("org")
                .setLevel(Level.OFF);
        Logger.getLogger("akka")
                .setLevel(Level.OFF);
        logger.setLevel(Level.INFO);
        Logger.getLogger("org.apache.spark").setLevel(Level.WARN);

        DataDriver driver = new CassandraDriver();

        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", "kafka:9092");
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", StringDeserializer.class);
        kafkaParams.put("group.id", "foo");
        kafkaParams.put("auto.offset.reset", "latest");
        kafkaParams.put("enable.auto.commit", false);

        Collection<String> topics = Arrays.asList("messages");

        SparkConf sparkConf = new SparkConf()
                .setMaster("spark://spark:7077")
                .setAppName("Hub Processor");
        driver.config(sparkConf);

        JavaStreamingContext streamingContext = new JavaStreamingContext(sparkConf, Durations.seconds(1));
        logger.info("Logger Streaming context created " + streamingContext);

        JavaInputDStream<ConsumerRecord<String, String>> messages =
                KafkaUtils.createDirectStream(streamingContext, LocationStrategies.PreferConsistent(),
                        ConsumerStrategies.Subscribe(topics, kafkaParams));
        logger.info("subscribed to kafka topics streams " + messages.inputDStream().name());

        final ObjectMapper om = new ObjectMapper();
        JavaDStream<Message> values = messages.map(record -> om.readValue(record.value(), Message.class));

        JavaPairDStream<String, Message> results = values
                .mapToPair(m -> new Tuple2<>(m.getLabel(), m))
                //.reduceByKeyAndWindow((i1, i2) -> i1.getValue() > i2.getValue()? i1 : i2, Durations.seconds(60));
                .reduceByKey((i1, i2) -> i1.getValue() > i2.getValue()? i1 : i2);

        results.foreachRDD(javaRdd -> {
            javaRdd.collectAsMap().entrySet().stream().forEach(e -> {
                logger.info(">>>>> result for " + e.getKey() + ": " + e.getValue());
                JavaRDD<Message> rdd = streamingContext.sparkContext().parallelize(Arrays.asList(e.getValue()));
                driver.save(rdd, Message.class);
            });

        });

        streamingContext.start();
        streamingContext.awaitTermination();
    }

}