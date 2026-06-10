package com.skylark.service;

import com.skylark.dto.BookingRequestDTO;
import com.skylark.entity.Equipment;
import com.skylark.entity.EquipmentBooking;
import com.skylark.entity.TrainingVideo;
import com.skylark.repository.EquipmentBookingRepository;
import com.skylark.repository.EquipmentRepository;
import com.skylark.repository.TrainingVideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EquipmentBookingService {

    @Autowired
    private EquipmentBookingRepository equipmentBookingRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private TrainingVideoRepository trainingVideoRepository;

    @Autowired
    private QuizAttemptService quizAttemptService;

    public List<EquipmentBooking> getAll() {
        return equipmentBookingRepository.findAll();
    }

    public List<EquipmentBooking> getByStudentId(Long studentId) {
        return equipmentBookingRepository.findByStudentId(studentId);
    }

    public List<EquipmentBooking> getByEquipmentId(Long equipmentId) {
        return equipmentBookingRepository.findByEquipmentId(equipmentId);
    }

    public Optional<EquipmentBooking> getById(Long id) {
        return equipmentBookingRepository.findById(id);
    }

    @Transactional
    public EquipmentBooking createBooking(BookingRequestDTO dto) {
        Equipment equipment = equipmentRepository.findById(dto.getEquipmentId())
            .orElseThrow(() -> new RuntimeException("设备不存在"));

        if (!"AVAILABLE".equals(equipment.getStatus())) {
            throw new RuntimeException("设备当前不可用");
        }

        List<TrainingVideo> videos = trainingVideoRepository.findByEquipmentId(dto.getEquipmentId());
        boolean allPassed = true;

        for (TrainingVideo video : videos) {
            if (!quizAttemptService.hasPassedAllQuestions(dto.getStudentId(), video.getId())) {
                allPassed = false;
                break;
            }
        }

        if (!allPassed) {
            throw new RuntimeException("您还未完成该设备的培训测验，请先完成所有测验题目");
        }

        List<EquipmentBooking> existingBookings = equipmentBookingRepository.findByEquipmentIdAndBookingDate(
            dto.getEquipmentId(), dto.getBookingDate());

        boolean timeSlotConflict = existingBookings.stream()
            .anyMatch(b -> b.getTimeSlot().equals(dto.getTimeSlot()) &&
                !"CANCELLED".equals(b.getStatus()));

        if (timeSlotConflict) {
            throw new RuntimeException("该时段已被预约，请选择其他时段");
        }

        EquipmentBooking booking = new EquipmentBooking();
        booking.setEquipmentId(dto.getEquipmentId());
        booking.setStudentId(dto.getStudentId());
        booking.setStudentName(dto.getStudentName());
        booking.setBookingDate(dto.getBookingDate());
        booking.setTimeSlot(dto.getTimeSlot());
        booking.setStatus("CONFIRMED");
        booking.setTrainingPassed(true);

        return equipmentBookingRepository.save(booking);
    }

    public void cancelBooking(Long id) {
        EquipmentBooking booking = equipmentBookingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("预约记录不存在"));
        booking.setStatus("CANCELLED");
        equipmentBookingRepository.save(booking);
    }
}
