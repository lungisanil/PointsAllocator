package vowel.solutions.device.data.PointsAllocator.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import vowel.solutions.device.data.PointsAllocator.exception.InternalServerErrorException;
import vowel.solutions.device.data.PointsAllocator.service.KafkaProducerService;
import vowel.solutions.device.data.device.data.event.notification.PointsNotificationRequest;

@Service
public class PointsAllocatorKafkaProducerService implements KafkaProducerService<PointsNotificationRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PointsAllocatorKafkaProducerService.class);
    private final KafkaTemplate<String, PointsNotificationRequest> kafkaTemplate;

    @Value("${spring.kafka.producer.topic.name}")
    private String producerTopic;

    @Autowired
    public PointsAllocatorKafkaProducerService(KafkaTemplate<String, PointsNotificationRequest> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendMessage(PointsNotificationRequest message) {
        LOGGER.info("Preparing to send the message to the topic : {}", producerTopic);

        Message<PointsNotificationRequest> notificationRequestMessage = MessageBuilder
                .withPayload(message)
                .setHeader(KafkaHeaders.TOPIC, producerTopic)
                .build();

        try {
            this.kafkaTemplate.send(notificationRequestMessage);
        } catch (Exception ex) {
            throw new InternalServerErrorException(String.format("Failed to send the message : %s to the topic",
                    notificationRequestMessage.getPayload()));
        }
        LOGGER.info(String.format("Message sent %s", message));
    }
}
