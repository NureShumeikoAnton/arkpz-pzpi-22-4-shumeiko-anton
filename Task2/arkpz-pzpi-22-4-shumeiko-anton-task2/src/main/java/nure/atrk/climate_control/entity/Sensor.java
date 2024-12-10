package nure.atrk.climate_control.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sensors")
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sensor_id")
    private int sensorId;

    @Column(name = "serial")
    private String serial;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "system_id")
    private int systemId;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "last_sync")
    private Timestamp lastSync;

    @ManyToOne
    @JoinColumn(name = "system_id", insertable = false, updatable = false)
    private ClimateSystem system;

    @OneToMany(mappedBy = "sensor")
    private Set<Measurement> measurements;
}
