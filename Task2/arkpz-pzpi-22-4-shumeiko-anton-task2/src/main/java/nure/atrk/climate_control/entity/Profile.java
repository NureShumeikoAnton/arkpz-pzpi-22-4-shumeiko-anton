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
@Table(name = "profiles")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private int profileId;

    @Column(name = "name")
    private String name;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "system_id")
    private int systemId;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "system_id", insertable = false, updatable = false)
    private ClimateSystem system;

    @OneToMany(mappedBy = "profile")
    private Set<Shedule> shedules;
}
