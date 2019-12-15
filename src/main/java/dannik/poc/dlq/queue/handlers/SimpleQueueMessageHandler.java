package dannik.poc.dlq.queue.handlers;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.ImmediateAcknowledgeAmqpException;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@EnableBinding(SimpleQueueMessageProcessor.class)
@RequiredArgsConstructor
public class SimpleQueueMessageHandler {

    private final SimpleQueueMessageProcessor processor;

    @StreamListener(SimpleQueueMessageProcessor.INPUT_NAME)
    public void receive(
            QueueMessage<String> msg,
            @Header(name = "x-death", required = false) Map<?,?> death
    ) {

        log.info(msg.getType());

        if (death != null && death.get("count").equals(3L)) { //Also check to more then
            // giving up - don't send to DLQ
            // You can also save to the database, after fixing the problem, reprocess the messages again
            throw new ImmediateAcknowledgeAmqpException("Failed after 3 attempts");
        }
        // if "requeue-rejected: false"  You can move message to DLQ by next Exception
        throw new AmqpRejectAndDontRequeueException("failed");
    }

    @StreamListener(SimpleQueueMessageProcessor.INPUT_NAME)
    public void publish(QueueMessage<String> msg) {

        processor.output().send(message(msg));
    }

    private static final <T> Message<T> message(T val) {
        return MessageBuilder
                .withPayload(val)
                .build();
    }

    @Data
    public static class QueueMessage<T> {

        private String type;

        private T payload;
    }
}
