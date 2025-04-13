package vn.tayjava.service;

import java.util.Date;
import java.util.List;

import vn.tayjava.common.enumerate.PeriodEnum;
import vn.tayjava.controller.request.DoctorScheduleCreationReq;
import vn.tayjava.model.DoctorSchedule;

public interface DoctorScheduleService {

    List<DoctorSchedule> getAllSchedules();

    DoctorSchedule create(DoctorScheduleCreationReq request);

    DoctorSchedule changeScheduleStatus(Long id, boolean active);
}
