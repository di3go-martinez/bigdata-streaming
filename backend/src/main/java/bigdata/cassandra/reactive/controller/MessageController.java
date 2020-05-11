package bigdata.cassandra.reactive.controller;


import bigdata.cassandra.reactive.model.Message;
import bigdata.cassandra.reactive.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@CrossOrigin("*")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/messages")
    public Flux<Message> all(){
         Flux<Message> messages = messageService.findAll();
         //messages.subscribe(arrival -> System.out.println(arrival));
         return messages;
    }
}
