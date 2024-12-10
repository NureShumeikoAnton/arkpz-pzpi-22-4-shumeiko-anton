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
@Table(name = "timers")
public class Timer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timer_id")
    private int timerId;

    @Column(name = "duration")
    private int duration;

    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name = "end_time")
    private Timestamp endTime;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "system_id")
    private ClimateSystem system;
}
