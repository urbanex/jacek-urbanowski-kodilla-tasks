package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class SimpleEmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);
    private static final String NEW_TRELLO_CARD_MAIL = "New Card: ";
    private static final String SCHEDULER_MAIL = "Currently in your database you have: ";

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailCreatorService mailCreatorService;

    public void send(final Mail mail) {
        LOGGER.info("Starting email preparation...");
        try {
            javaMailSender.send(createMimeMessage(mail));
            if (mail.getMessage().startsWith(NEW_TRELLO_CARD_MAIL)) {
                LOGGER.info("Email reporting about new Trello Card has been sent successfully.");
            } else if (mail.getMessage().startsWith(SCHEDULER_MAIL)) {
                LOGGER.info("Scheduled email reporting about current quantity of tasks has been sent successfully.");
            }
        } catch (MailException e) {
            LOGGER.error("Failed to process email sending: " + e.getMessage(), e);
        }
    }

    private MimeMessagePreparator createMimeMessage(final Mail mail) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());

            if (mail.getMessage().startsWith(NEW_TRELLO_CARD_MAIL)) {
                messageHelper.setText(mailCreatorService.buildTrelloCardEmail(mail.getMessage()), true);
            } else if (mail.getMessage().startsWith(SCHEDULER_MAIL)) {
                messageHelper.setText(mailCreatorService.tasksQuantityEmail(mail.getMessage()), true);
            }

            if (mail.getToCc()!=null && !mail.getToCc().equals("")) {
                messageHelper.setCc(mail.getToCc());
                LOGGER.info("CC (carbon copy) included!");
            }
        };
    }

}
