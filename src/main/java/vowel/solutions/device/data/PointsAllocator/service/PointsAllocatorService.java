package vowel.solutions.device.data.PointsAllocator.service;

import vowel.solutions.device.data.device.data.event.notification.PointsNotificationRequest;
import vowel.solutions.device.data.device.data.event.processor.WorkoutEventPointsRequest;

public interface PointsAllocatorService {
    PointsNotificationRequest process(WorkoutEventPointsRequest request);
}
