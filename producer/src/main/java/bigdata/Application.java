package bigdata;

import bigdata.transfer.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;


@SpringBootApplication
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws Exception {

        if (args.length==0)
            throw new IllegalArgumentException("sensor name as parameter is required");

        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

        MessageProducer producer = context.getBean(MessageProducer.class);

        for (int i = 0; i < 10; i++)
            producer.send(message(args[0]));

        logger.info("Closing...");
        context.close();
    }

    private static final Faker faker = new Faker();

    private static Message message(String  key) {
        return new Message(key, faker.random().nextLong(100L));
    }

    @Bean
    public MessageProducer messageProducer() {
        return new MessageProducer();
    }

    public static class MessageProducer {

        @Autowired
        private KafkaTemplate<String, String> kafkaTemplate;

        private final String messagesTopic = "messages";

        public void send(Message message) {
            ListenableFuture<SendResult<String, String>> f = kafkaTemplate.send(messagesTopic, "key", toString(message));

            f.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
                @Override
                public void onSuccess(SendResult<String, String> result) {
                    logger.info("sent " + result.getProducerRecord().value());
                }

                @Override
                public void onFailure(Throwable throwable) {
                    throwable.printStackTrace();
                }
            });

        }

        private String toString(Message message) {
            try {
                return new ObjectMapper().writeValueAsString(message);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
