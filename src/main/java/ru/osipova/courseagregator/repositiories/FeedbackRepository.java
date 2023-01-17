package ru.osipova.courseagregator.repositiories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.osipova.courseagregator.entity.Feedback;

import java.util.List;

public interface FeedbackRepository  extends JpaRepository<Feedback, Long> {
    List<Feedback> findAllByCourseId(Long id);
}
