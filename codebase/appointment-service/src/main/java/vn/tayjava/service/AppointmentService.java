package vn.tayjava.service;

import java.util.List;

import vn.tayjava.common.enumerate.AppointmentStatus;
import vn.tayjava.controller.request.AppointmentCreationRequest;
import vn.tayjava.model.Appointment;

public interface AppointmentService {

    long create(AppointmentCreationRequest request);

    Appointment updateAppointmentStatus(long id, AppointmentStatus status);

    List<Appointment> getAppointmentsByPatientId(Long patientId);

}
