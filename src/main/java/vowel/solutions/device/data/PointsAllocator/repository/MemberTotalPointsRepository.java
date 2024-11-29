package vowel.solutions.device.data.PointsAllocator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vowel.solutions.device.data.PointsAllocator.entity.MemberTotalPointsEntity;

@Repository
public interface MemberTotalPointsRepository extends JpaRepository<MemberTotalPointsEntity, Long> {
    MemberTotalPointsEntity findByMemberId(Long memberId);
}
