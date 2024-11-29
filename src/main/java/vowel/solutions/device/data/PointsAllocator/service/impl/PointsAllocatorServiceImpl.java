package vowel.solutions.device.data.PointsAllocator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vowel.solutions.device.data.PointsAllocator.entity.MemberTotalPointsEntity;
import vowel.solutions.device.data.PointsAllocator.entity.WorkoutPointsEntity;
import vowel.solutions.device.data.PointsAllocator.exception.InternalServerErrorException;
import vowel.solutions.device.data.PointsAllocator.repository.EventPointsRepository;
import vowel.solutions.device.data.PointsAllocator.repository.MemberTotalPointsRepository;
import vowel.solutions.device.data.PointsAllocator.service.PointsAllocatorService;
import vowel.solutions.device.data.PointsAllocator.translator.PointsAllocatorTranslator;
import vowel.solutions.device.data.device.data.event.notification.PointsNotificationRequest;
import vowel.solutions.device.data.device.data.event.processor.WorkoutEventPointsRequest;

@Service
public class PointsAllocatorServiceImpl implements PointsAllocatorService {
    private final EventPointsRepository eventPointsRepository;
    private final MemberTotalPointsRepository memberTotalPointsRepository;

    @Autowired
    public PointsAllocatorServiceImpl(EventPointsRepository eventPointsRepository, MemberTotalPointsRepository memberTotalPointsRepository) {
        this.eventPointsRepository = eventPointsRepository;
        this.memberTotalPointsRepository = memberTotalPointsRepository;
    }

    @Override
    public PointsNotificationRequest process(WorkoutEventPointsRequest request) {
        WorkoutPointsEntity workoutPointsEntity = PointsAllocatorTranslator.getWorkoutPointsEntity(request);
        try {
            MemberTotalPointsEntity updatedMemberTotalPointsEntity;
            WorkoutPointsEntity eventPoints = this.eventPointsRepository.save(workoutPointsEntity);
            //Get the number of points the member currently have
            MemberTotalPointsEntity memberTotalPointsEntity = this.memberTotalPointsRepository.findByMemberId(eventPoints.getMemberId());

            Integer eventPointsValue = eventPoints.getPointsValue();
            //This means no record of the member exists in the DB, which means the member has never earned any points
            if (memberTotalPointsEntity == null) {
                updatedMemberTotalPointsEntity = this.memberTotalPointsRepository.save(PointsAllocatorTranslator.getMemberTotalPoints(eventPoints, eventPointsValue));
            } else {
                int totalPoints = memberTotalPointsEntity.getPointsValue() + eventPointsValue;
                updatedMemberTotalPointsEntity = this.memberTotalPointsRepository.save(PointsAllocatorTranslator.getMemberTotalPoints(eventPoints, totalPoints));
            }

            String memberEmailAddress = "luvunolungisani@gmail.com";
            return PointsAllocatorTranslator.getPointsNotificationRequest(updatedMemberTotalPointsEntity,
                    request.getWorkoutEventPointsMnemonic().getDescription(),
                    eventPointsValue,
                    memberEmailAddress);
        } catch (Exception ex) {
            throw new InternalServerErrorException(String.format("Error occurred when trying to allocate points for event Id : %s",
                    workoutPointsEntity.getEventId()));
        }
    }
}
