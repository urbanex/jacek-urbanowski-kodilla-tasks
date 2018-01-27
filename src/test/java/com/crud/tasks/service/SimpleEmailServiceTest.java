package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SimpleEmailServiceTest {

    @InjectMocks
    private SimpleEmailService simpleEmailService;

    @Mock
    private MailCreatorService mailCreatorService;

    @Mock
    private JavaMailSender javaMailSender;

    @Captor
    private ArgumentCaptor<MimeMessagePreparator> registerMessageLambdaCaptorWithCC;

    @Captor
    private ArgumentCaptor<MimeMessagePreparator> registerMessageLambdaCaptorWithoutCC;

    private final static Properties mailProperties = new Properties();

    @BeforeClass()
    public static void setup() {
        mailProperties.setProperty("mail.smtp.host", "host");
        mailProperties.setProperty("mail.smtp.port", Integer.toString(8080));
        mailProperties.setProperty("mail.user", "user");
        mailProperties.setProperty("mail.password", "pass");
        mailProperties.setProperty("mail.store.protocol", "imap");
    }

    @Test
    public void shouldSendEmailWithCc() throws Exception {
        //given
        Mail mail = new Mail("test@test.com", "test subject", "test message", "test2@test2.com");

        MimeMessagePreparator expectedLambda = mimeMessage -> {
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
        verify(javaMailSender, times(1)).send(registerMessageLambdaCaptorWithCC.capture());
        MimeMessagePreparator actualLambda = registerMessageLambdaCaptorWithCC.getValue();
        Session session = Session.getDefaultInstance(mailProperties);
        MimeMessage actualMimeMessage = new MimeMessage(session);
        actualLambda.prepare(actualMimeMessage);
        MimeMessage expectedMimeMessage = new MimeMessage(session);
        expectedLambda.prepare(expectedMimeMessage);

        comparingHeaders(actualMimeMessage, expectedMimeMessage);
    }

    @Test
    public void shouldSendEmailWithoutCc() throws Exception {
        //given
        Mail mail = new Mail("test@test.com", "test subject", "test message");

        MimeMessagePreparator expectedLambda = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mail.getMessage(), true);
        };

        when(mailCreatorService.buildTrelloCardEmail(mail.getMessage())).thenReturn("test message");

        //when
        simpleEmailService.send(mail, EmailType.TRELLO_CARD_MAIL);

        //then
        verify(javaMailSender, times(1)).send(registerMessageLambdaCaptorWithoutCC.capture());
        MimeMessagePreparator actualLambda = registerMessageLambdaCaptorWithoutCC.getValue();
        Session session = Session.getDefaultInstance(mailProperties);
        MimeMessage actualMimeMessage = new MimeMessage(session);
        actualLambda.prepare(actualMimeMessage);
        MimeMessage expectedMimeMessage = new MimeMessage(session);
        expectedLambda.prepare(expectedMimeMessage);

        comparingHeaders(actualMimeMessage, expectedMimeMessage);
    }

    public static void comparingHeaders(MimeMessage actualMimeMessage, MimeMessage expectedMimeMessage) throws MessagingException, IOException {
        assertEquals(actualMimeMessage.getDataHandler().getContent(),expectedMimeMessage.getDataHandler().getContent());
        assertArrayEquals(actualMimeMessage.getHeader("To"), expectedMimeMessage.getHeader("To"));
        assertArrayEquals(actualMimeMessage.getHeader("Cc"), expectedMimeMessage.getHeader("Cc"));
        assertArrayEquals(actualMimeMessage.getHeader("Subject"), expectedMimeMessage.getHeader("Subject"));
    }

}