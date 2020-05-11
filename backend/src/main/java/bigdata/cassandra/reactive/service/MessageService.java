package bigdata.cassandra.reactive.service;

import bigdata.cassandra.reactive.model.Message;
import bigdata.cassandra.reactive.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class MessageService {

    @Autowired
    private MessageRepository repository;

    public Flux<Message> findAll(){
        return repository.findAll();
    }
}
