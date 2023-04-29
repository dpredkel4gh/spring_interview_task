package com.yellow.test.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "runnings")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Running {

    @Id
    @GenericGenerator(
            name = "runningsSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "runnings_seq")
            }
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "runningsSequenceGenerator")
    private long id;

    @Column(name = "uuid", nullable = false, unique = true, length = 100)
    private String uuid;

    // in meters
    @Column(name = "distance", nullable = false)
    private Integer distance;

    // in seconds
    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_runnings_users"))
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Running running = (Running) o;
        return Objects.equals(getUuid(), running.getUuid()) &&
                Objects.equals(getDistance(), running.getDistance()) &&
                Objects.equals(getDuration(), running.getDuration()) &&
                Objects.equals(getDate(), running.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid(), getDistance(), getDuration(), getDate());
    }

}
