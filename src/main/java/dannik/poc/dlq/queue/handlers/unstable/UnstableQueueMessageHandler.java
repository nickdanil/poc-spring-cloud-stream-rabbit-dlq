package dannik.poc.dlq.queue.handlers.unstable;

import dannik.poc.dlq.queue.handlers.QueueMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.ImmediateAcknowledgeAmqpException;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@EnableBinding(UnstableQueueMessageBinding.class)
@RequiredArgsConstructor
public class UnstableQueueMessageHandler {

    @StreamListener(UnstableQueueMessageBinding.INPUT_NAME)
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
}
