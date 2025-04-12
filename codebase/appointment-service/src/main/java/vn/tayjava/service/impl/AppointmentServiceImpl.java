package vn.tayjava.service.impl;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vn.tayjava.common.enumerate.AppointmentStatus;
import vn.tayjava.common.enumerate.ExaminationType;
import vn.tayjava.controller.request.AppointmentCreationRequest;
import vn.tayjava.controller.request.ProductCreationRequest;
import vn.tayjava.controller.request.ProductUpdateRequest;
import vn.tayjava.exception.InvalidDataException;
import vn.tayjava.model.Appointment;
import vn.tayjava.model.DoctorSchedule;
import vn.tayjava.model.Patient;
import vn.tayjava.model.ProductDocument;
import vn.tayjava.repository.AppointmentRepository;
import vn.tayjava.repository.DoctorScheduleRepository;
import vn.tayjava.repository.PatientRepository;
import vn.tayjava.service.AppointmentService;

@Service
@Slf4j(topic = "APPOINTMENT-SERVICE")
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorScheduleRepository doctorScheduleRepository;

    @Override
    public long addProduct(ProductCreationRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addProduct'");
    }

    @Override
    public List<ProductDocument> searchProducts(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchProducts'");
    }

    @Override
    public void updateUser(ProductUpdateRequest product) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
    }

    @Override
    public void deleteProduct(long productId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteProduct'");
    }

    @Override
    public long create(AppointmentCreationRequest request) {

        // check doctor schedule
        if (request == null) {
            throw new InvalidDataException("Data must not be null");
        }

        if (request.getDoctorSchedule() == null) {
            throw new InvalidDataException("Doctor schedule must not be null");
        }

        if (request.getDoctorSchedule().getDoctor() == null) {
            throw new InvalidDataException("Doctor must not be null");

        }

        if (request.getDoctorSchedule().getTimeSlot() == null) {
            throw new InvalidDataException("Time slot must not be null");

        }

        // check patient
        if (request.getPatient() == null) {
            throw new InvalidDataException("Patient must not be null");

        }

        // check phone exists
        Patient existedPatient = this.patientRepository.findByPhone(request.getPatient().getPhone());
        if (existedPatient == null) {
            existedPatient = new Patient();
            existedPatient.setPhone(request.getPatient().getPhone());
            existedPatient.setFullName(request.getPatient().getFullName());
            existedPatient.setDob(request.getPatient().getDob());
            // check if id exists
            existedPatient.setCustomerId(request.getPatient().getCustomerId());
            existedPatient.setGender(request.getPatient().getGender());
            existedPatient.setNationality(request.getPatient().getNationality());
            existedPatient.setAddress(request.getPatient().getAddress());

            existedPatient = this.patientRepository.save(existedPatient);
        } else {
            // update address if change

        }

        DoctorSchedule doctorSchedule = doctorScheduleRepository
                .findByDoctorIdAndTimeSlotId(
                        request.getDoctorSchedule().getDoctor().getId(),
                        request.getDoctorSchedule().getTimeSlot().getId())
                .orElseThrow(() -> new InvalidDataException("Doctor schedule is wrong"));

        Appointment appointment = new Appointment();
        appointment.setStatus(AppointmentStatus.PENDING);
        appointment.setExaminationType(request.getExaminationType());
        appointment.setDoctorSchedule(doctorSchedule);
        appointment.setNote(request.getNote());
        appointment.setPatient(existedPatient);

        appointment = this.appointmentRepository.save(appointment);
        log.info("Appointment has saved successfully: {}", appointment);
        return appointment.getId();
    }

}
