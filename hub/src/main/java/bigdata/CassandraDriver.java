package bigdata;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.javaFunctions;
import static com.datastax.spark.connector.japi.CassandraJavaUtil.mapToRow;

public class CassandraDriver implements DataDriver {

    @Override
    public SparkConf config(SparkConf conf) {
        return conf.set("spark.cassandra.connection.host", "cassandra")
                .set("spark.cassandra.auth.username", "cassandra")
                .set("spark.cassandra.auth.password", "cassandra");
    }

    @Override
    public <T> void save(JavaRDD<T> rdd, Class<T> clazz) {
        javaFunctions(rdd).writerBuilder("sensors", "data", mapToRow(clazz)).saveToCassandra();
    }
}
