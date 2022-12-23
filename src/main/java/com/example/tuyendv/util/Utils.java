package com.example.tuyendv.util;

import com.example.tuyendv.entity.ERole;
import com.example.tuyendv.entity.User;
import com.example.tuyendv.entity.Work;
import com.example.tuyendv.repository.UserRepository;
import com.example.tuyendv.repository.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.List;

@Component
public class Utils {

    @Autowired
    UserRepository userRepo;

    @Autowired
    WorkRepository workRepo;

    public User getInfoUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return userRepo.findByUserName(authentication.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found " + authentication.getName()));
        }
        return null;
    }

    public boolean checkRoleExist(String role) {
        if (role.equalsIgnoreCase(ERole.ROLE_MEMBER.toString())) return false;
        if (role.equalsIgnoreCase(ERole.ROLE_ADMIN.toString())) return false;
        return !role.equalsIgnoreCase(ERole.ROLE_SUPER_ADMIN.toString());
    }

    public Integer numberDayInMonth(int month, int year) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if (year % 400 == 0 || (year % 400 == 0 && year % 100 != 0)) {
                    return 29;
                } else return 28;
            default:
                return 0;
        }
    }

    public Double getTimeUserWork(String timeIn, String timeOut) {
        int timeInWork = Integer.parseInt(timeIn.substring(0, 2)) * 60 + Integer.parseInt(timeIn.substring(3));
        int timeOutWork = Integer.parseInt(timeOut.substring(0, 2)) * 60 + Integer.parseInt(timeOut.substring(3));
        return (double) ((timeOutWork - timeInWork) / 60);
    }

    public Integer getDayWork(Integer month, Integer year) throws ParseException {
        if (!ValueUtils.isMonth(month) && !ValueUtils.isYear(year)) return 0;
        Integer dayWork = numberDayInMonth(month, year);
        for (int i = 1; i < numberDayInMonth(month, year); i++) {
            String dateStr = i + "-" + month + "-" + year;
            String dayOfWeek = DateUtils.getDayOfWeek(DateUtils.toDate(dateStr));
            if (dayOfWeek.equalsIgnoreCase("Sat") || dayOfWeek.equalsIgnoreCase("Sun"))
                dayWork--;
        }
        return dayWork;
    }

    public Double getSalaryUser(Double salaryContract, Integer month, Integer year, Double timeWork) throws ParseException {
        Integer dayWork = getDayWork(month, year);
        return (salaryContract * timeWork) / (dayWork * 8);
    }

    public String getTimeWork(String timeCheck, String timeWork) {
        int hour = Math.min(Integer.parseInt(timeCheck.substring(0, 2)), Integer.parseInt(timeWork.substring(0, 2)));
        int minute = Math.min(Integer.parseInt(timeCheck.substring(3)), Integer.parseInt(timeWork.substring(3)));
        return (hour > 10 ? hour : "0" + hour) + ":" + (minute > 10 ? minute : "0" + minute);
    }

    public Work checkWork(Work work) {
        List<Work> lstCheck = workRepo.findByIdUser(work.getIdUser());
        if (!lstCheck.isEmpty()) {
            for (Work workCheck : lstCheck) {
                if (work.getDay().equals(workCheck.getDay()) &&
                        work.getMonth().equals(workCheck.getMonth()) &&
                        work.getYear().equals(workCheck.getYear())) {
                    work.setTimeInWork(getTimeWork(workCheck.getTimeInWork(), work.getTimeInWork()));
                    work.setTimeOutWork(getTimeWork(workCheck.getTimeOutWork(), work.getTimeOutWork()));
                    break;
                }
            }
        }
        return work;
    }

}
