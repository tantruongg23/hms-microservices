package vn.tayjava.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import vn.tayjava.controller.request.AppointmentCreationRequest;
import vn.tayjava.controller.request.ProductCreationRequest;
import vn.tayjava.controller.request.ProductUpdateRequest;
import vn.tayjava.controller.response.ResponseData;
import vn.tayjava.controller.response.ResponseError;
import vn.tayjava.model.ProductDocument;
import vn.tayjava.service.AppointmentService;

import java.util.List;

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
            return new ResponseData<>(HttpStatus.CREATED.value(), "Create appointment successfully", appointmentId);
        } catch (Exception e) {
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Create appointment failed");
        }
    }

    // @PutMapping("/upd")
    // public void updateProduct(@RequestBody ProductUpdateRequest request) {
    // log.info("Update request: {}", request);
    // appointmentService.updateUser(request);
    // }

    // @DeleteMapping("/del/{productId}")
    // public void deleteProduct(@PathVariable long productId) {
    // log.info("Delete request: {}", productId);
    // appointmentService.deleteProduct(productId);
    // }
}
