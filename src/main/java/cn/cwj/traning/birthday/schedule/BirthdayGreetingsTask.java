package cn.cwj.traning.birthday.schedule;

import cn.cwj.traning.birthday.exception.FileReadException;
import cn.cwj.traning.birthday.model.Employee;
import cn.cwj.traning.birthday.util.EmailSendUtil;
import com.google.common.io.Files;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BirthdayGreetingsTask {

    private final DateTimeFormatter birthdayPattern = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private final String infoSeparator = ", ";

    public void checkBirthdayAndSendEmail() {
        List<String> lineData = readFile("employee.txt");
        List<Employee> sendGreetingsList = getSendGreetingsList(lineData);

        EmailSendUtil.sendEmail(sendGreetingsList);
    }

    private List<String> readFile(String filePath) {
        File file = new File(filePath);
        try {
            return Files.readLines(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new FileReadException("文件读取错误", e);
        }
    }

    private List<Employee> getSendGreetingsList(List<String> lineData) {
        if (CollectionUtils.isEmpty(lineData)) {
            return Collections.emptyList();
        }

        LocalDate today = LocalDate.now();
        Month monthOfToday = today.getMonth();
        int dayOfToday = today.getDayOfMonth();

        List<Employee> employeesWhoNeedSendGreetings = new ArrayList<>();
        lineData.forEach(line -> {
            String[] splitData = line.split(infoSeparator);
            LocalDate birthdayOfCurrentEmployee = LocalDate.parse(splitData[2], birthdayPattern);
            Month monthOfBirthday = birthdayOfCurrentEmployee.getMonth();
            int dayOfBirthday = birthdayOfCurrentEmployee.getDayOfMonth();

            if (isTodayBirthday(monthOfToday, dayOfToday, monthOfBirthday, dayOfBirthday)) {
                Employee employee = buildEmployee(splitData, birthdayOfCurrentEmployee);
                employeesWhoNeedSendGreetings.add(employee);
            }
        });
        return employeesWhoNeedSendGreetings;
    }

    private boolean isTodayBirthday(Month monthOfToday, int dayOfToday, Month monthOfBirthday, int dayOfBirthday) {
        return monthOfToday.getValue() == monthOfBirthday.getValue() && dayOfToday == dayOfBirthday;
    }

    private Employee buildEmployee(String[] splitData, LocalDate birthdayOfCurrentEmployee) {
        Employee employee = new Employee();
        employee.setFirstName(splitData[0]);
        employee.setLastName(splitData[1]);
        employee.setBirthday(birthdayOfCurrentEmployee);
        employee.setEmail(splitData[3]);
        return employee;
    }

}
