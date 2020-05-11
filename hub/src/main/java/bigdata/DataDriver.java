package bigdata;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;

public interface DataDriver {

    SparkConf config(SparkConf conf);

    <T> void save(JavaRDD<T> rdd, Class<T> clazz);
}
