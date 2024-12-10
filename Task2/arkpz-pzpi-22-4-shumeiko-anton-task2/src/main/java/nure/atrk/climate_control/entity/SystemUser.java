package nure.atrk.climate_control.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "system_users")
public class SystemUser {
    @EmbeddedId
    private SystemUserId id;

    @Column(name = "role")
    private String role;

    @ManyToOne
    @MapsId("systemId")
    @JoinColumn(name = "system_id")
    @JsonIgnore
    private ClimateSystem system;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
}
