package by.aurorasoft.kafka.replication.it.crud.entity;

import by.nhorushko.crudgeneric.v2.domain.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;
import static java.util.Objects.hash;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
@Entity
@Table(name = "person_replications")
public class PersonReplicationEntity implements AbstractEntity<Long> {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Override
    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    public final boolean equals(final Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null) {
            return false;
        }
        if (Hibernate.getClass(this) != Hibernate.getClass(otherObject)) {
            return false;
        }
        final PersonReplicationEntity other = (PersonReplicationEntity) otherObject;
        return Objects.equals(getId(), other.getId());
    }

    @Override
    public final int hashCode() {
        return hash(getId());
    }
}
