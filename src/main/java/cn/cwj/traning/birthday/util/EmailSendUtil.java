package cn.cwj.traning.birthday.util;

import cn.cwj.traning.birthday.exception.FileReadException;
import cn.cwj.traning.birthday.model.Email;
import cn.cwj.traning.birthday.model.Employee;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class EmailSendUtil {

    private static String emailSubject;
    private static String emailContent;

    static {
        Properties properties = new Properties();
        try (InputStream in = EmailSendUtil.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(Objects.requireNonNull(in));
            emailSubject = properties.getProperty("emailSubject");
            emailContent = properties.getProperty("emailContent");
        } catch (IOException e) {
            throw new FileReadException("读取配置文件异常", e);
        }
    }

    public static void sendEmail(List<Employee> sendGreetingsList) {
        List<Email> emails = new ArrayList<>();
        sendGreetingsList.forEach(employee -> {
            String emailBody = emailContent + employee.getLastName();
            Email email = new Email(emailSubject, emailBody);
            emails.add(email);
        });
        System.out.println(emails);
    }
}
