package cn.cwj.traning.birthday.schedule;

import org.junit.Test;

public class BirthdayGreetingsTaskTest {

    @Test
    public void birthdayGreetingsTaskTest() {
        BirthdayGreetingsTask birthdayGreetingsTask = new BirthdayGreetingsTask();
        birthdayGreetingsTask.checkBirthdayAndSendEmail();
    }

}