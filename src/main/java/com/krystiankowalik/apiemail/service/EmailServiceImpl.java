package com.krystiankowalik.apiemail.service;

import com.krystiankowalik.apiemail.exception.MissingEmailPropertyException;
import com.krystiankowalik.apiemail.model.EmailMessage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.internet.MimeMessage;
import java.util.Optional;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender sender;

    @Override
    public void sendEmail(EmailMessage mailMessage) throws MessagingException {
        try {
            doSendEmail(mailMessage);
        } catch (MessagingException e) {
            throw new SendFailedException("Unable to send email", e);
        }
    }

    private void doSendEmail(EmailMessage mailMessage) throws MessagingException, MissingEmailPropertyException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        if (mailMessage.getFrom() != null) {
            helper.setFrom(mailMessage.getFrom());
        }

        helper.setTo(Optional
                .ofNullable(mailMessage.getTo())
                .orElseThrow(() -> new MissingEmailPropertyException("Missing property 'to'")));
        if (mailMessage.getCc() != null) {
            helper.setCc(mailMessage.getCc());
        }
        if (mailMessage.getBcc() != null) {
            helper.setBcc(mailMessage.getBcc());
        }
        helper.setSubject(Optional
                .ofNullable(mailMessage.getSubject())
                .orElse(""));
        helper.setText("", Optional
                .ofNullable(mailMessage.getText())
                .orElse(""));

        sender.send(message);
    }

}
