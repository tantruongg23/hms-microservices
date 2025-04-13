package vn.tayjava.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.tayjava.controller.request.DoctorScheduleCreationReq;
import vn.tayjava.exception.InvalidDataException;
import vn.tayjava.model.Doctor;
import vn.tayjava.model.DoctorSchedule;
import vn.tayjava.repository.DoctorRepository;
import vn.tayjava.repository.DoctorScheduleRepository;
import vn.tayjava.service.DoctorScheduleService;
import vn.tayjava.service.DoctorService;

@Service
@RequiredArgsConstructor
public class DoctorScheduleServiceImpl implements DoctorScheduleService {

    private final DoctorScheduleRepository doctorScheduleRepository;

    private final DoctorService doctorService;

    public List<DoctorSchedule> getAllSchedules() {
        return doctorScheduleRepository.findAll();
    }

    public DoctorSchedule create(DoctorScheduleCreationReq request) {
        long doctorId = this.doctorService.findById(request.getDoctorId());

        // check if exists
        if (this.doctorScheduleRepository.existsByDoctorIdAndDateAndPeriod(
                doctorId,
                request.getDate(),
                request.getPeriod())) {
            throw new InvalidDataException("Đã tồn tại lịch này");
        }
        DoctorSchedule schedule = new DoctorSchedule();
        schedule.setDoctorId(doctorId);
        schedule.setDate(request.getDate());
        schedule.setPeriod(request.getPeriod());
        schedule.setAvailable(true);

        return this.doctorScheduleRepository.save(schedule);
    }

    public DoctorSchedule changeScheduleStatus(Long id, boolean active) {
        DoctorSchedule schedule = this.doctorScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lịch không tồn tại"));

        schedule.setAvailable(active);
        return this.doctorScheduleRepository.save(schedule);
    }

    public void deleteSchedule(Long id) {
        this.doctorScheduleRepository.deleteById(id);
    }

}
