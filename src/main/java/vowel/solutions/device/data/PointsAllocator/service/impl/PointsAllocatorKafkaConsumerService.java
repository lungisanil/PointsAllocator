package vowel.solutions.device.data.PointsAllocator.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import vowel.solutions.device.data.PointsAllocator.exception.InternalServerErrorException;
import vowel.solutions.device.data.PointsAllocator.service.KafkaConsumerService;
import vowel.solutions.device.data.PointsAllocator.service.KafkaProducerService;
import vowel.solutions.device.data.PointsAllocator.service.PointsAllocatorService;
import vowel.solutions.device.data.device.data.event.notification.PointsNotificationRequest;
import vowel.solutions.device.data.device.data.event.processor.WorkoutEventPointsRequest;

@Service
public class PointsAllocatorKafkaConsumerService implements KafkaConsumerService<WorkoutEventPointsRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PointsAllocatorKafkaConsumerService.class);

    @Value("${spring.kafka.producer.topic.name}")
    private String producerTopic;

    @Value("${spring.kafka.consumer.topic.name}")
    private String consumerTopic;

    private final PointsAllocatorService pointsAllocatorService;
    private final KafkaProducerService<PointsNotificationRequest> kafkaProducerService;

    @Autowired
    public PointsAllocatorKafkaConsumerService(PointsAllocatorService pointsAllocatorService,
                                               KafkaProducerService<PointsNotificationRequest> kafkaProducerService) {
        this.pointsAllocatorService = pointsAllocatorService;
        this.kafkaProducerService = kafkaProducerService;
    }

    @Override
    @KafkaListener(topics = "${spring.kafka.consumer.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void readMessage(WorkoutEventPointsRequest workoutEventPointsRequest) {
        LOGGER.info(String.format("Message received from " + consumerTopic + " -> %s", workoutEventPointsRequest.toString()));
        PointsNotificationRequest pointsNotificationRequest;

        try {
            pointsNotificationRequest = this.pointsAllocatorService.process(workoutEventPointsRequest);
            LOGGER.info(String.format("Points allocated and total points for the member : %s have been updated",
                    workoutEventPointsRequest.getWorkoutEventProcessRequest().getMemberId()));
        } catch (Exception ex) {
            throw new InternalServerErrorException(String.format("Cannot process points for member : %s ",
                    workoutEventPointsRequest.getWorkoutEventProcessRequest().getDeviceName()));
        }

        try {
            this.kafkaProducerService.sendMessage(pointsNotificationRequest);
        } catch (Exception ex) {
            throw new InternalServerErrorException(String.format("Cannot send points notification to kafka topic : %s to member id : %s",
                    producerTopic, workoutEventPointsRequest.getWorkoutEventProcessRequest().getMemberId()));
        }
        LOGGER.info(String.format("Points notification with member Id : %s sent to topic : %s",
                workoutEventPointsRequest.getWorkoutEventProcessRequest().getMemberId(), producerTopic));
    }
}
