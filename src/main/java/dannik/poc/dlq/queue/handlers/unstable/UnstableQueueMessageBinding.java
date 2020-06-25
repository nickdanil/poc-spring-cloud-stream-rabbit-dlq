package dannik.poc.dlq.queue.handlers.unstable;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface UnstableQueueMessageBinding {

    String INPUT_NAME = "unstable-message-in";

    //needed to create a bean
    @Input(INPUT_NAME)
    SubscribableChannel input();
}
