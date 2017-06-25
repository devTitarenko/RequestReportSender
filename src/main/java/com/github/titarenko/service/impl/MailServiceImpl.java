package com.github.titarenko.service.impl;

import com.github.titarenko.dao.impl.RequestDaoImpl;
import com.github.titarenko.model.DocumentFormat;
import com.github.titarenko.service.MailService;
import com.github.titarenko.validation.EmailValidator;
import com.github.titarenko.validation.Validator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.io.File;

@Service("mailService")
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired(required = false)
    private ServletContext servletContext;
    private String email;
    private DocumentFormat format;
    private static final String FILE_NAME = "report";

    private static final Logger LOGGER = Logger.getLogger(RequestDaoImpl.class);

    @Override
    public void sendEmail(String email, DocumentFormat format) {
        Validator validator = new EmailValidator(email);
        if (!validator.validate()) {
            LOGGER.error("Email doesn't match pattern");
            throw new IllegalArgumentException("Invalid email");
        }

        this.email = email;
        this.format = (format == null) ? DocumentFormat.DOC : format;

        MimeMessagePreparator preparator = getContentWtihAttachementMessagePreparator();

        try {
            mailSender.send(preparator);
            LOGGER.info("Message With Attachment has been sent to " + email);
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private MimeMessagePreparator getContentWtihAttachementMessagePreparator() {
        return mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setSubject("Report of request data by users");
            helper.setTo(email);
            String content = "Thank you for using the REST service and checking out my application!" +
                    System.lineSeparator() + "For more visit my repository: github.com/devTitarenko";

            // Add a resource as an attachment
            String uploadPath = servletContext.getRealPath("") +
                    File.separator + FILE_NAME + format.getName();
            File attachment = new File(uploadPath);
            if (attachment.exists()) {
                helper.addAttachment(FILE_NAME + format.getName(), attachment);
            } else {
                content += System.lineSeparator() + "Sorry, but there are no requests in DB with this filter";
            }

            LOGGER.info("Is document exists: " + uploadPath + "? - " + attachment.exists());
            helper.setText(content);
        };
    }
}