package hu.gabor.csikos.todoapp.jms;

import lombok.Getter;
import lombok.Setter;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class JmsReceiver {

    private String message = "";


    @JmsListener(destination = "helloworld.q")
    public void receive(String message) {
        this.message = message;
    }
}
