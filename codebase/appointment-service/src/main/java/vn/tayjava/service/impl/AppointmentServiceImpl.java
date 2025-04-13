package vn.tayjava.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vn.tayjava.common.enumerate.AppointmentStatus;
import vn.tayjava.controller.request.AppointmentCreationRequest;
import vn.tayjava.exception.IdInvalidException;
import vn.tayjava.exception.InvalidDataException;
import vn.tayjava.model.Appointment;
import vn.tayjava.model.Doctor;
import vn.tayjava.model.Patient;
import vn.tayjava.repository.AppointmentRepository;
import vn.tayjava.repository.DoctorRepository;
import vn.tayjava.repository.DoctorScheduleRepository;
import vn.tayjava.repository.PatientRepository;
import vn.tayjava.service.AppointmentService;
import vn.tayjava.service.DoctorService;
import vn.tayjava.service.PatientService;

@Service
@Slf4j(topic = "APPOINTMENT-SERVICE")
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final DoctorScheduleRepository doctorScheduleRepository;

    private final PatientService patientService;

    private final DoctorService doctorService;

    @Override
    public long create(AppointmentCreationRequest request) {

        // check doctor schedule
        if (request == null) {
            throw new InvalidDataException("Dữ liệu không hợp lệ");
        }

        // check doctor & time
        if (!this.doctorScheduleRepository
                .existsByDoctorIdAndDateAndPeriod(
                        request.getDoctorId(),
                        request.getAppointmentDate(),
                        request.getPeriod())) {

            throw new InvalidDataException("Vui lòng chọn lịch hẹn khác");
        }

        // check patient
        if (request.getPatient() == null) {
            throw new InvalidDataException("Dữ liệu bệnh nhân không hợp lệ");
        }

        // call patient service
        long patientId = this.patientService.save(request.getPatient());

        // call doctor service
        long doctorId = this.doctorService.findById(request.getDoctorId());

        Appointment appointment = new Appointment();
        appointment.setExaminationType(request.getExaminationType());
        appointment.setDoctorId(doctorId);
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setPeriod(request.getPeriod());
        appointment.setSymptoms(request.getSymptoms());
        appointment.setPatientId(patientId);
        appointment.setStatus(AppointmentStatus.PENDING);

        appointment = this.appointmentRepository.save(appointment);

        log.info("Appointment has saved successfully: {}", appointment);
        return appointment.getId();
    }

    @Override
    @Transactional
    public Appointment updateAppointmentStatus(long id, AppointmentStatus status) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setStatus(status);
        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> getAppointmentsByPatientId(Long patientId) {
        // check patient id

        //

        return appointmentRepository.findByPatientId(patientId);
    }

}
