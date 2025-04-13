package vn.tayjava.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vn.tayjava.common.enumerate.AppointmentStatus;
import vn.tayjava.controller.request.AppointmentCreationRequest;
import vn.tayjava.controller.response.ResponseData;
import vn.tayjava.controller.response.ResponseError;
import vn.tayjava.model.Appointment;
import vn.tayjava.service.AppointmentService;

@RestController
@Slf4j(topic = "APPOINTMENT-CONTROLLER")
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping()
    public ResponseData<?> createAppointment(@RequestBody AppointmentCreationRequest request) {
        log.info("Request create appointment: {}", request);
        // not return error message because Security
        try {
            long appointmentId = appointmentService.create(request);
            return new ResponseData<>(HttpStatus.CREATED.value(), "Đặt lịch khám thành công", appointmentId);
        } catch (Exception e) {
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Đặt lịch khám thất bại");
        }
    }

    @PatchMapping("/{id}")
    public ResponseData<?> updateAppointmentStatus(@PathVariable Long id,
            @RequestParam AppointmentStatus status) {
        log.info("Request update appointment status: {}", id, status);
        try {
            Appointment updated = appointmentService.updateAppointmentStatus(id, status);
            return new ResponseData<>(HttpStatus.OK.value(), "Cập nhật trạng thái thành công", updated);
        } catch (Exception e) {
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Cập nhật trạng thái thất bại");
        }
    }

    @GetMapping("/patient/{patientId}")
    public ResponseData<?> getAppointmentsByPatient(@PathVariable Long patientId) {
        log.info("Request get appointments by patient id: {}", patientId);

        try {
            List<Appointment> appointments = appointmentService.getAppointmentsByPatientId(patientId);
            return new ResponseData<>(HttpStatus.OK.value(), "Lấy lịch hẹn thành công", appointments);
        } catch (Exception e) {
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Lấy lịch hẹn thất bại");
        }
    }

}
