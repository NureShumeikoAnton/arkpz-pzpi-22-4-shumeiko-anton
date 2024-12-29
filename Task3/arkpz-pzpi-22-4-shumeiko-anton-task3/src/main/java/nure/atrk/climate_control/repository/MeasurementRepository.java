package nure.atrk.climate_control.repository;

import nure.atrk.climate_control.entity.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;


public interface MeasurementRepository extends JpaRepository<Measurement, Integer>{
    @Query("SELECT m FROM Measurement m " +
            "JOIN m.sensor s " +
            "JOIN s.system sys " +
            "WHERE sys.systemId = :systemId " +
            "AND m.createdAt BETWEEN :startDate AND :endDate")
    List<Measurement> findAllBySystemIdAndDateBetween(@Param("systemId") int systemId,
                                                      @Param("startDate") Date startDate,
                                                      @Param("endDate") Date endDate);

    List<Measurement> findAllBySensorId(int sensorId);

}
