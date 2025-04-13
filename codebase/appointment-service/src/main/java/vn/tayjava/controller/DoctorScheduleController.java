package vn.tayjava.controller;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vn.tayjava.common.enumerate.PeriodEnum;
import vn.tayjava.controller.request.DoctorScheduleCreationReq;
import vn.tayjava.controller.response.ResponseData;
import vn.tayjava.controller.response.ResponseError;
import vn.tayjava.model.DoctorSchedule;
import vn.tayjava.service.DoctorScheduleService;

@RestController
@RequestMapping("/doctor-schedules")
@Slf4j(topic = "DOCTOR-SCHEDULE-CONTROLLER")
@RequiredArgsConstructor
public class DoctorScheduleController {

    private final DoctorScheduleService doctorScheduleService;

    @PostMapping
    public ResponseData<?> createDoctorSchedule1(@Valid @RequestBody DoctorScheduleCreationReq request) {
        log.info("Request create a doctor schedule: {}", request);
        try {
            DoctorSchedule doctorSchedule = this.doctorScheduleService.create(request);
            return new ResponseData<>(HttpStatus.CREATED.value(), "Tạo lịch khám thành công", doctorSchedule);
        } catch (Exception e) {
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Tạo lịch khám thất bại");
        }
    }
}
