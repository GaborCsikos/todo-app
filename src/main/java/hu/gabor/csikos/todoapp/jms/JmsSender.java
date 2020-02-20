package hu.gabor.csikos.todoapp.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class JmsSender {
    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMessasge(String message) {
        jmsTemplate.convertAndSend(JmsConfig.PUB_SUB, message);
        jmsTemplate.convertAndSend("helloworld.q", message);
    }
}
