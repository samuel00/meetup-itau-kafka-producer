package sls.meetup.itau.kafka.producer.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfiguration {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.topic.purchased}")
    private String topicPurchased;

    @Value("${spring.topic.canceled}")
    private String topicCanceled;

    @Value("${spring.topic.restarted}")
    private String topicRestarted;


    @Bean
    public NewTopic topicPurchased() {
        return TopicBuilder.name(this.topicPurchased).partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic topicCanceled() {
        return TopicBuilder.name(this.topicCanceled).partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic topicRestarted() {
        return TopicBuilder.name(this.topicRestarted).partitions(1).replicas(1).build();
    }
}
