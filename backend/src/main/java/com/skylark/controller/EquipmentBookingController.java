package com.skylark.controller;

import com.skylark.dto.BookingRequestDTO;
import com.skylark.entity.EquipmentBooking;
import com.skylark.service.EquipmentBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/equipment-bookings")
public class EquipmentBookingController {

    @Autowired
    private EquipmentBookingService equipmentBookingService;

    @GetMapping
    public List<EquipmentBooking> getAll() {
        return equipmentBookingService.getAll();
    }

    @GetMapping("/student/{studentId}")
    public List<EquipmentBooking> getByStudentId(@PathVariable Long studentId) {
        return equipmentBookingService.getByStudentId(studentId);
    }

    @GetMapping("/equipment/{equipmentId}")
    public List<EquipmentBooking> getByEquipmentId(@PathVariable Long equipmentId) {
        return equipmentBookingService.getByEquipmentId(equipmentId);
    }

    @GetMapping("/{id}")
    public Optional<EquipmentBooking> getById(@PathVariable Long id) {
        return equipmentBookingService.getById(id);
    }

    @PostMapping
    public EquipmentBooking create(@RequestBody BookingRequestDTO dto) {
        return equipmentBookingService.createBooking(dto);
    }

    @PutMapping("/{id}/cancel")
    public void cancel(@PathVariable Long id) {
        equipmentBookingService.cancelBooking(id);
    }
}
