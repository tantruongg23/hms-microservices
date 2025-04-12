package vn.tayjava.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "doctors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Doctor extends AbstractEntity {

    private String name;

    @OneToMany(mappedBy = "doctor")
    private List<DoctorSchedule> doctorSchedules;

    @ManyToOne
    @JoinColumn(name = "specialty_id")
    private Specialty specialty;
}
