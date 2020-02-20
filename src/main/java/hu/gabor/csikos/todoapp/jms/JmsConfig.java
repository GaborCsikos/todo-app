package hu.gabor.csikos.todoapp.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@Profile("local")
public class JmsConfig {
    @Value("${activemq.broker-url}")
    private String brokerUrl;

    @Bean
    @Primary
    public ActiveMQConnectionFactory senderActiveMQConnectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory =
                new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL(brokerUrl);

        return activeMQConnectionFactory;
    }

    @Bean
    public CachingConnectionFactory cachingConnectionFactory() {
        return new CachingConnectionFactory(
                senderActiveMQConnectionFactory());
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate(cachingConnectionFactory());
        jmsTemplate.setPubSubDomain(true); //Can be configured
        return new JmsTemplate(cachingConnectionFactory());
    }

    public static final String PUB_SUB = "TEST";
    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerFactory(
            CachingConnectionFactory jmsConnectionFactory,
            DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(jmsConnectionFactory);
        configurer.configure(factory, jmsConnectionFactory);
        factory.setPubSubDomain(true);
        return factory;
    }

/*
    public static final String PUB_SUB = "TEST";
@Bean
    public JmsConnectionFactory jmsConnectionFactory() {
        JmsConnectionFactory factory = new JmsConnectionFactory();
        factory.setUsername(userName);
        factory.setPassword(password);
        factory.setRemoteURI(remoteURI);
        return factory;
    }

    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerFactory(
            ConnectionFactory jmsConnectionFactory,
            DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(jmsConnectionFactory);
        configurer.configure(factory, jmsConnectionFactory);
        factory.setPubSubDomain(true);
        return factory;
    }

    @Bean
    public JmsTemplate jmsTemplate(JmsConnectionFactory jmsConnectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(jmsConnectionFactory);
        jmsTemplate.setDefaultDestinationName(DESTINATION_NAME);
        jmsTemplate.setPubSubDomain(true);
        return jmsTemplate;
    }


}
 */
}