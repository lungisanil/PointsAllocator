package vowel.solutions.device.data.PointsAllocator.service;

public interface KafkaConsumerService<M> {
    void readMessage(M message);
}
