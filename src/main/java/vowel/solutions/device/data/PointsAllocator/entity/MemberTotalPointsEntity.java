package vowel.solutions.device.data.PointsAllocator.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "MEMBER_TOTAL_POINTS")
public class MemberTotalPointsEntity {
    @Id
    @Column(name = "MEMBER_ID", updatable = false, nullable = false)
    private Long memberId;

    @Column(name = "TOTAL_POINTS_VALUE", updatable = true, nullable = false)
    private Integer pointsValue;

    @Column(name = "DATE_UPDATED", updatable = false, nullable = false)
    private String dateUpdated;

    public Long getMemberId() {
        return memberId;
    }

    public MemberTotalPointsEntity setMemberId(Long memberId) {
        this.memberId = memberId;
        return this;
    }

    public Integer getPointsValue() {
        return pointsValue;
    }

    public MemberTotalPointsEntity setPointsValue(Integer pointsValue) {
        this.pointsValue = pointsValue;
        return this;
    }

    public String getDateUpdated() {
        return dateUpdated;
    }

    public MemberTotalPointsEntity setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    @Override
    public String toString() {
        return "MemberTotalPointsEntity{" +
                "memberId=" + memberId +
                ", pointsValue=" + pointsValue +
                ", dateUpdated='" + dateUpdated + '\'' +
                '}';
    }
}
