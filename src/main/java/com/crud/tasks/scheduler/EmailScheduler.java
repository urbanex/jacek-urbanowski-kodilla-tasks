package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {

    private static final String SUBJECT = "Tasks: Daily mail";

    @Autowired
    private SimpleEmailService simpleEmailService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AdminConfig adminConfig;

    //co kwadrans
    //@Scheduled(cron = "0 */15 * * * *" )

    //codziennie o 10:00
    //@Scheduled(cron = "0 0 10 * * *")

    @Scheduled(cron = "0 */5 * * * *" )
    public void sendInformationEmail() {
        long size = taskRepository.count();
        simpleEmailService.send(new Mail(adminConfig.getAdminMail(),
                SUBJECT, "Currently in your database you have: " + size + (size == 1 ? " task." : " tasks.")));
    }

}
