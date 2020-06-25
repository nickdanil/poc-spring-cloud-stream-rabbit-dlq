package dannik.poc.dlq.queue.handlers;

import lombok.Data;

@Data
public class QueueMessage<T> {

    private String type;

    private T payload;
}
