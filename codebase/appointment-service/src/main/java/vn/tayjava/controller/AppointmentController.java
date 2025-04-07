package vn.tayjava.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;
import vn.tayjava.controller.request.ProductCreationRequest;
import vn.tayjava.controller.request.ProductUpdateRequest;
import vn.tayjava.model.ProductDocument;
import vn.tayjava.service.AppointmentService;

import java.util.List;

@RestController
@Slf4j(topic = "APPOINTMENT-CONTROLLER")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping("/list")
    public List<ProductDocument> getProductList(@RequestParam(required = false) String name) {
        log.info("-----[ getProductList ]-----");
        return appointmentService.searchProducts(name);
    }

    @PostMapping("/add")
    public long addProduct(@RequestBody ProductCreationRequest request) {
        log.info("Add request: {}", request);
        return appointmentService.addProduct(request);
    }

    @PutMapping("/upd")
    public void updateProduct(@RequestBody ProductUpdateRequest request) {
        log.info("Update request: {}", request);
        appointmentService.updateUser(request);
    }

    @DeleteMapping("/del/{productId}")
    public void deleteProduct(@PathVariable long productId) {
        log.info("Delete request: {}", productId);
        appointmentService.deleteProduct(productId);
    }
}
