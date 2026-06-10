package com.skylark.repository;

import com.skylark.entity.EquipmentBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EquipmentBookingRepository extends JpaRepository<EquipmentBooking, Long> {
    List<EquipmentBooking> findByStudentId(Long studentId);
    List<EquipmentBooking> findByEquipmentId(Long equipmentId);
    List<EquipmentBooking> findByEquipmentIdAndBookingDate(Long equipmentId, LocalDate bookingDate);
}
