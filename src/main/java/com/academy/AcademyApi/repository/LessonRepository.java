package com.academy.AcademyApi.repository;

import com.academy.AcademyApi.model.Lesson;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    @Query("SELECT l FROM Lesson l WHERE l.career.careerId = :careerId AND l.numBimester = :bimesterNum AND l.numWeek = :weekNum")
    List<Lesson> findByCareerIdAndBimesterNumAndWeekNum(@Param("careerId") Long careerId,
                                                        @Param("bimesterNum") Integer bimesterNum,
                                                        @Param("weekNum") Integer weekNum);
}
