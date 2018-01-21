package com.teslaboat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Service
public class EmailService {

    @Autowired
    private HttpServletRequest context;

    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    private TemplateEngine templateEngine;


    public void sendEmailFromTemplate(String to,
                                      String subject,
                                      String template,
                                      HashMap<String, ? extends Object> params) {
        // TODO: implement
    }


    public void sendConfirmationEmail(String to, String code) throws MessagingException {

        String cancelUrl = String.format("%s://%s:%d/cancel/%s",context.getScheme(),
                context.getServerName(), context.getServerPort(), code);

        Context context = new Context();
        context.setVariable("code", code);
        context.setVariable("cancelUrl", cancelUrl);

        String text = templateEngine.process("email-confirmation", context);

        MimeMessage mail = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
        helper.setTo(to);
        helper.setSubject("Your TeslaBoat reservation");
        helper.setText(text, true);
        emailSender.send(mail);
    }
}