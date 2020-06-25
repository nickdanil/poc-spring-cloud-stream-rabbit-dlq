package dannik.poc.dlq.queue.handlers.route;

import dannik.poc.dlq.queue.handlers.QueueMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@EnableBinding(RouteQueueMessageBinding.class)
@RequiredArgsConstructor
public class RouteQueueMessageHandler {

    @StreamListener(RouteQueueMessageBinding.INPUT_NAME)
    public void receive(
            QueueMessage<String> msg
    ) {
        log.info(msg.getType());
        log.info(msg.getPayload());
    }
}
