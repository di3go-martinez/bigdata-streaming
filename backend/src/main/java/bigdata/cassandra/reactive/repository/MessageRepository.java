package bigdata.cassandra.reactive.repository;

import bigdata.cassandra.reactive.model.Message;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

public interface MessageRepository extends ReactiveCassandraRepository<Message, String> {

    }
