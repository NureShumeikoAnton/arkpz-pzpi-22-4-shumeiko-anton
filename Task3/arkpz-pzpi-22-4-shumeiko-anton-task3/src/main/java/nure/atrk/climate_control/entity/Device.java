package nure.atrk.climate_control.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "devices")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id")
    private int deviceId;

    @Column(name = "serial")
    private String serial;

    @Column(name = "name")
    private String name;

    @Column(name = "power")
    private float power;

    @Column(name = "mode")
    private String mode;

    @Column(name = "type")
    private String type;

    @Column(name = "system_id")
    private int systemId;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "system_id", insertable = false, updatable = false)
    @JsonIgnore
    private ClimateSystem system;

    @OneToMany(mappedBy = "device")
    @JsonIgnore
    private Set<Command> commands;
}
