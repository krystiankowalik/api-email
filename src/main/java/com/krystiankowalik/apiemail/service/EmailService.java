package com.krystiankowalik.apiemail.service;

import com.krystiankowalik.apiemail.model.EmailMessage;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public interface EmailService {

    void sendEmail(EmailMessage mailMessage) throws MessagingException;
}
