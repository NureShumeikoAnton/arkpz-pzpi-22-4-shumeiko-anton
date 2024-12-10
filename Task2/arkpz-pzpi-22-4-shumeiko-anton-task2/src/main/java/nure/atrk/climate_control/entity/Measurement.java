package nure.atrk.climate_control.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "measurements")
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "measurement_id")
    private int measurementId;

    @Column(name = "value")
    private double value;

    @Column(name = "sensor_id")
    private int sensorId;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "sensor_id", insertable = false, updatable = false)
    private Sensor sensor;
}
