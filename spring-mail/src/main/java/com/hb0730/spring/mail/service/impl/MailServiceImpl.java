package com.hb0730.spring.mail.service.impl;

import com.hb0730.spring.mail.properties.EmailProperties;
import com.hb0730.spring.mail.service.AbstractMailService;
import freemarker.template.Template;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * @author bing_huang
 * @date 2020/07/01 13:47
 * @since V1.0
 */
public class MailServiceImpl extends AbstractMailService {
    private final FreeMarkerConfigurer freeMarker;

    public MailServiceImpl(EmailProperties properties, FreeMarkerConfigurer freeMarker) {
        super(properties);
        this.freeMarker = freeMarker;
    }

    @Override
    public void sendTextMail(String to, String subject, String content) {
        sendMailTemplate(false, messageHelper -> {
            messageHelper.setSubject(subject);
            messageHelper.setTo(to);
            messageHelper.setText(content);
        });
    }

    @Override
    public void sendTemplateMail(String to, String subject, Map<String, Object> content, String templateName) {
        sendMailTemplate(false, messageHelper -> {
            // build message content with freemarker
            Template template = freeMarker.getConfiguration().getTemplate(templateName);
            String contentResult = FreeMarkerTemplateUtils.processTemplateIntoString(template, content);

            messageHelper.setSubject(subject);
            messageHelper.setTo(to);
            messageHelper.setText(contentResult, true);
        });
    }

    @Override
    public void sendAttachMail(String to, String subject, Map<String, Object> content, String templateName, String attachFilePath) {
        sendMailTemplate(false, messageHelper -> {
            messageHelper.setSubject(subject);
            messageHelper.setTo(to);
            Path attachmentPath = Paths.get(attachFilePath);
            messageHelper.addAttachment(attachmentPath.getFileName().toString(), attachmentPath.toFile());
        });
    }
}
