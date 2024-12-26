package nure.atrk.climate_control.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "timers")
public class Timer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timer_id")
    private int timerId;

    @Column(name = "duration")
    private int duration;

    @Column(name = "start_time", insertable = false, updatable = false)
    private Timestamp startTime;

    @Column(name = "end_time", insertable = false, updatable = false)
    private Timestamp endTime;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "system_id")
    private int systemId;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "system_id", insertable = false, updatable = false)
    @JsonIgnore
    private ClimateSystem system;

    @PrePersist
    public void onCreate() {
        startTime = new Timestamp(System.currentTimeMillis());
        // duration in minutes
        endTime = new Timestamp(startTime.getTime() + duration * 60 * 1000);
        createdAt = new Timestamp(System.currentTimeMillis());
    }
}
