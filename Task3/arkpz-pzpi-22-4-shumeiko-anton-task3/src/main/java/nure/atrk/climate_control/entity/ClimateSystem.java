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
@Table(name = "systems")
public class ClimateSystem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "system_id")
    private int systemId;
    @Column(name="name")
    private String name;
    @Column(name="profile_id")
    private Integer profileId;
    @Column(name = "created_at")
    private Timestamp createdAt;

    @OneToMany(mappedBy = "system")
    @JsonIgnore
    private Set<SystemUser> systemUsers;

    @OneToMany(mappedBy = "system")
    @JsonIgnore
    private Set<Device> devices;

    @OneToMany(mappedBy = "system")
    @JsonIgnore
    private Set<Sensor> sensors;

    @ManyToOne
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    @JsonIgnore
    private Profile profile;

    @OneToMany(mappedBy = "system")
    @JsonIgnore
    private Set<Timer> timers;
}
