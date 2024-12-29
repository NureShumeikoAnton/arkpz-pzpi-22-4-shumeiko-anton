package nure.atrk.climate_control.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class SystemUserId implements Serializable {
    @Column(name = "system_id")
    private int systemId;
    @Column(name = "user_id")
    private int userId;
}
