package dannik.poc.dlq.queue.handlers.simple;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface SimpleQueueMessageBinding {

    String INPUT_NAME = "simple-message-in";
    String OUTPUT_NAME = "simple-message-out";

    //needed to create a bean
    @Input(INPUT_NAME)
    SubscribableChannel input();

    //needed to create a bean
    @Output(OUTPUT_NAME)
    MessageChannel output();
}
