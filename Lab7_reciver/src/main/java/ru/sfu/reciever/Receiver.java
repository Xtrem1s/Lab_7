package ru.sfu.reciever;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.sfu.model.Message;

@Component
public class Receiver {
    @RabbitListener(queues = "tg-queue", containerFactory =
            "rabbitListenerContainerFactory")
    public void listen(Message message) {
        System.out.println(message.getMessage() + message.getTableGame().getId());
    }
}

