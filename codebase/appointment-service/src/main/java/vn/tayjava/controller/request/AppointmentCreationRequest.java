package vn.tayjava.controller.request;

import java.io.Serializable;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import vn.tayjava.common.enumerate.AppointmentStatus;
import vn.tayjava.common.enumerate.BookingTypeEnum;
import vn.tayjava.common.enumerate.ExaminationType;
import vn.tayjava.model.Doctor;
import vn.tayjava.model.DoctorSchedule;
import vn.tayjava.model.Patient;

@Getter
public class AppointmentCreationRequest implements Serializable {
    private String department;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private ExaminationType examinationType;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private AppointmentStatus status;

    private DoctorSchedule doctorSchedule;

    private String note;

    private Patient patient;

}
