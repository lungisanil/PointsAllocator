package vowel.solutions.device.data.PointsAllocator.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "WORKOUT_POINTS")
public class WorkoutPointsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "POINTS_ID", updatable = false, nullable = false)
    private Long pointsId;

    @Column(name = "MEMBER_ID", updatable = false, nullable = false)
    private Long memberId;

    @Column(name = "POINTS_VALUE", updatable = true, nullable = false)
    private Integer pointsValue;

    @Column(name = "DATE_ALLOCATED", updatable = false, nullable = false)
    private String dateAllocated;

    @Column(name = "MNEMONIC", updatable = false, nullable = false)
    private String mnemonic;

    @Column(name = "EVENT_ID", updatable = false, nullable = false)
    private String eventId;

    public Long getPointsId() {
        return pointsId;
    }

    public WorkoutPointsEntity setPointsId(Long pointsId) {
        this.pointsId = pointsId;
        return this;
    }

    public Long getMemberId() {
        return memberId;
    }

    public WorkoutPointsEntity setMemberId(Long memberId) {
        this.memberId = memberId;
        return this;
    }

    public Integer getPointsValue() {
        return pointsValue;
    }

    public WorkoutPointsEntity setPointsValue(Integer pointsValue) {
        this.pointsValue = pointsValue;
        return this;
    }

    public String getDateAllocated() {
        return dateAllocated;
    }

    public WorkoutPointsEntity setDateAllocated(String dateAllocated) {
        this.dateAllocated = dateAllocated;
        return this;
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public WorkoutPointsEntity setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
        return this;
    }

    public String getEventId() {
        return eventId;
    }

    public WorkoutPointsEntity setEventId(String eventId) {
        this.eventId = eventId;
        return this;
    }

    @Override
    public String toString() {
        return "WorkoutPointsEntity{" +
                "pointsId=" + pointsId +
                ", memberId='" + memberId + '\'' +
                ", pointsValue=" + pointsValue +
                ", dateAllocated='" + dateAllocated + '\'' +
                ", mnemonic='" + mnemonic + '\'' +
                ", eventId='" + eventId + '\'' +
                '}';
    }
}
