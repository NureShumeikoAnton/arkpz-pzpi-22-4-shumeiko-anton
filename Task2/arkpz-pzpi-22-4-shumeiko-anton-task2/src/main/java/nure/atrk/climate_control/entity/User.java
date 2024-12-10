package nure.atrk.climate_control.entity;

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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<SystemUser> systemUsers;

    @OneToMany(mappedBy = "user")
    private Set<Message> messages;

    @OneToMany(mappedBy = "user")
    private Set<Profile> profiles;

    @OneToMany(mappedBy = "user")
    private Set<Timer> timers;

}
