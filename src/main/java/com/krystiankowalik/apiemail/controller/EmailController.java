package com.krystiankowalik.apiemail.controller;

import com.krystiankowalik.apiemail.model.EmailMessage;
import com.krystiankowalik.apiemail.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.security.Principal;

@RestController
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/secure") //[1]
    public String secured() {
        return "Secured Hello World";
    }

    @GetMapping("/normal") //[1]
    public String normal() {
        return "Normal Hello World";
    }

    @RequestMapping("/user")
    public Principal user(Principal principal) {
        return principal;

    }


    @PostMapping("/email")
    public ResponseEntity<EmailMessage> sendEmail(@RequestBody EmailMessage emailMessage) throws MessagingException {
        System.out.println(emailMessage.toString());
        emailService.sendEmail(emailMessage);
        return new ResponseEntity<>(emailMessage, HttpStatus.OK);
    }


}

