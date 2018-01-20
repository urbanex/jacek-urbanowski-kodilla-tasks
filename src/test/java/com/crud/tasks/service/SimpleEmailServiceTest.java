package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SimpleEmailServiceTest {

    @InjectMocks
    private SimpleEmailService simpleEmailService;

    @Mock
    private MailCreatorService mailCreatorService;

    @Mock
    private JavaMailSender javaMailSender;

    @Test
    public void shouldSendEmailWithCc() {
        //given
        Mail mail = new Mail("test@test.com", "test subject", "test message", "test2@test2.com");

        MimeMessagePreparator message = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mail.getMessage(), true);
            messageHelper.setCc(mail.getToCc());
        };

        when(mailCreatorService.buildTrelloCardEmail(mail.getMessage())).thenReturn("test message");

        //when
        simpleEmailService.send(mail, EmailType.TRELLO_CARD_MAIL);

        //then
        verify(javaMailSender, times(1)).send(message);

    }

    @Test
    public void shouldSendEmailWithoutCc() {
        //given
        Mail mail = new Mail("test@test.com", "test subject", "test message", "");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());

        //when
        simpleEmailService.send(mail,  EmailType.TRELLO_CARD_MAIL);

        //then
        verify(javaMailSender, times(1)).send(mailMessage);

    }
}