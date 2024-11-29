package vowel.solutions.device.data.PointsAllocator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vowel.solutions.device.data.PointsAllocator.entity.WorkoutPointsEntity;

@Repository
public interface EventPointsRepository extends JpaRepository<WorkoutPointsEntity, Long> {
}
