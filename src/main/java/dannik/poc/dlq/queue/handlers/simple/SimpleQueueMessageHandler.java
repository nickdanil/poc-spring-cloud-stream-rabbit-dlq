package dannik.poc.dlq.queue.handlers.simple;

import dannik.poc.dlq.queue.handlers.QueueMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@EnableBinding(SimpleQueueMessageBinding.class)
@RequiredArgsConstructor
public class SimpleQueueMessageHandler {

    private final SimpleQueueMessageBinding binding;

    @StreamListener(
            value = SimpleQueueMessageBinding.INPUT_NAME,
            condition = "headers['source']!='santa'"
    )
    //@SendTo(SimpleQueueMessageBinding.OUTPUT_NAME)
    public void receive( QueueMessage<String> msg) {
        log.info(msg.getType());
        log.info(msg.getPayload());
        //return StringUtils.capitalize(msg.getPayload());
    }

    @StreamListener(
            value = SimpleQueueMessageBinding.INPUT_NAME,
            condition = "headers['source']=='santa'"
    )
    public void receiveOnlyFromSanta( QueueMessage<String> msg) {
        log.info("Подари мне санта антидеприсанты");
    }

    public void publish(QueueMessage<String> msg) {
        binding.output().send(message(msg));
    }

    private static final <T> Message<T> message(T val) {
        return MessageBuilder
                .withPayload(val)
                .build();
    }
}
