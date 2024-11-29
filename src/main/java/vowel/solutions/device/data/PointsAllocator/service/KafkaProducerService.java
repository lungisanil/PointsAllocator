package vowel.solutions.device.data.PointsAllocator.service;

public interface KafkaProducerService<M> {
    void sendMessage(M message);
}
