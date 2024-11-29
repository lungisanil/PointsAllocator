package vowel.solutions.device.data.PointsAllocator.translator;

import vowel.solutions.device.data.PointsAllocator.entity.MemberTotalPointsEntity;
import vowel.solutions.device.data.PointsAllocator.entity.WorkoutPointsEntity;
import vowel.solutions.device.data.PointsAllocator.exception.InternalServerErrorException;
import vowel.solutions.device.data.device.data.event.notification.PointsNotificationRequest;
import vowel.solutions.device.data.device.data.event.processor.WorkoutEventPointsRequest;

import java.time.LocalDate;

public class PointsAllocatorTranslator {
    private PointsAllocatorTranslator() {
        throw new InternalServerErrorException("You are a naughty developer, you shouldn't be creating objects of this class since " +
                "it only has static method(s)");
    }

    public static WorkoutPointsEntity getWorkoutPointsEntity(WorkoutEventPointsRequest request) {
        return new WorkoutPointsEntity()
                .setDateAllocated(LocalDate.now().toString())
                .setEventId(request.getWorkoutEventProcessRequest().getWorkoutId())
                .setMemberId(request.getWorkoutEventProcessRequest().getMemberId())
                .setMnemonic(request.getWorkoutEventPointsMnemonic().getMnemonic())
                .setPointsValue(request.getWorkoutEventPointsMnemonic().getPointsValue());
    }

    public static MemberTotalPointsEntity getMemberTotalPoints(WorkoutPointsEntity eventPoints, Integer pointsValue) {
        return new MemberTotalPointsEntity()
                .setMemberId(eventPoints.getMemberId())
                .setPointsValue(pointsValue)
                .setDateUpdated(LocalDate.now().toString());
    }

    public static PointsNotificationRequest getPointsNotificationRequest(MemberTotalPointsEntity updatedMemberTotalPointsEntity, String eventDescription,
                                                                         Integer eventPointsValue, String memberEmailAddress) {
        return new PointsNotificationRequest()
                .setMemberEmailAddress(memberEmailAddress)
                .setMemberTotalPoints(updatedMemberTotalPointsEntity.getPointsValue())
                .setMemberEventPoints(eventPointsValue)
                .setMnemonicDescription(eventDescription);
    }
}
