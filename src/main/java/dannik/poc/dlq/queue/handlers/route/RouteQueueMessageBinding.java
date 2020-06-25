package dannik.poc.dlq.queue.handlers.route;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface RouteQueueMessageBinding {

    String INPUT_NAME = "route-message-in";

    @Input(INPUT_NAME)
    SubscribableChannel input();
}
