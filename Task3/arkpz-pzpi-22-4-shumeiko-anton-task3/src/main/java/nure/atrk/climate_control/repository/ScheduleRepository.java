package nure.atrk.climate_control.repository;

import nure.atrk.climate_control.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Time;
import java.util.Date;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer>{
    @Query("SELECT s FROM Schedule s " +
            "WHERE s.profileId = :profileId " +
            "AND :currentDate BETWEEN s.startDate AND s.endDate " +
            "AND :currentTime BETWEEN s.startTime AND s.endTime")
    Schedule findActiveSchedule(@Param("profileId") int profileId,
                                          @Param("currentDate") Date currentDate,
                                          @Param("currentTime") Time currentTime);
}
