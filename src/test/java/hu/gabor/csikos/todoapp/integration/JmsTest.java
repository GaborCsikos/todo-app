package hu.gabor.csikos.todoapp.integration;


import hu.gabor.csikos.todoapp.jms.JmsReceiverPubSub;
import hu.gabor.csikos.todoapp.jms.JmsSender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JmsTest extends IntegrationTest {

    @Autowired
    private JmsSender sender;

    @Autowired
    private JmsReceiverPubSub pubSubReceiver;

    @Autowired
    private JmsReceiverPubSub receiver;

    @Test
    public void queue() throws InterruptedException {
        assertEquals("", pubSubReceiver.getMessage());
        assertEquals("", receiver.getMessage());
        sender.sendMessasge("23:34");
        Thread.sleep(3000);
        assertEquals("23:34", receiver.getMessage());
        assertEquals("23:34", pubSubReceiver.getMessage());
    }
}
