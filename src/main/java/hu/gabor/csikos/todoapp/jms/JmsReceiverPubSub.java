package hu.gabor.csikos.todoapp.jms;

import lombok.Getter;
import lombok.Setter;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class JmsReceiverPubSub {

    private String message = "";


    //@JmsListener(destination = "helloworld.q")
    @JmsListener(destination = JmsConfig.PUB_SUB, containerFactory = "jmsListenerContainerFactory")
    public void receive(String message) {
        this.message = message;
    }
}
