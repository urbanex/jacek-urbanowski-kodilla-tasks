package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.EmailType;
import com.crud.tasks.service.SimpleEmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmailSchedulerTestSuite {

    @InjectMocks
    private EmailScheduler emailScheduler;

    @Mock
    private SimpleEmailService simpleEmailService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private AdminConfig adminConfig;

    @Test
    public void sendInformationEmail() {
        //given
        when(adminConfig.getAdminMail()).thenReturn("test@test.test");
        when(taskRepository.count()).thenReturn(20L);
        //when
        emailScheduler.sendInformationEmail();
        //then
        verify(taskRepository, times(1)).count();
        verify(simpleEmailService, times(1)).send(new Mail("test@test.test",
                "Tasks: Daily mail", "Currently in your database you have: 20 tasks."), EmailType.SCHEDULED_MAIL);
        verify(adminConfig, times(1)).getAdminMail();
    }
}