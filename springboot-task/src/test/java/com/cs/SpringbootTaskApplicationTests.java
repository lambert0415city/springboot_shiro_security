//package com.cs;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//import org.springframework.mail.javamail.MimeMessageHelper;
//
//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
//import java.io.File;
//
//@SpringBootTest
//class SpringbootTaskApplicationTests {
//
//    @Autowired
//    JavaMailSenderImpl mailSender;
//
//
//    @Test
//    void contextLoads() {
//        //send mail test
//        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//
//        simpleMailMessage.setSubject("subject");
//        simpleMailMessage.setText("content");
//        simpleMailMessage.setTo("");
//        simpleMailMessage.setFrom("");
//
//        mailSender.send(simpleMailMessage);
//    }
//
//    @Test
//    void contextLoads2() throws MessagingException {
//        //complex mail
//        MimeMessage mimeMessage = mailSender.createMimeMessage();
//
//        //compose 开启多文件
//        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
//
//        mimeMessageHelper.setSubject("复杂的邮件");
//        mimeMessageHelper.setText("<p style='color:red'>复杂的邮件</p>",true);
//        mimeMessageHelper.setTo("");
//        mimeMessageHelper.setFrom("");
//
//        mimeMessageHelper.addAttachment("test.jpg",new File("D:\\springboot-task\\src\\main\\resources\\templates\\img\\test.jpg"));
//
//        mailSender.send(mimeMessage);
//
//    }
//
//    /**
//     * @param html 是否支持多文本上传
//     * @param subject 主题
//     * @param text
//     * @throws MessagingException
//     * @author lambert
//     */
//    public void sendMail(Boolean html,String subject,String text) throws MessagingException {
//
//        //complex mail
//        MimeMessage mimeMessage = mailSender.createMimeMessage();
//
//        //compose 是否支持多文本上传
//        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,html);
//
//        mimeMessageHelper.setSubject(subject);
//        mimeMessageHelper.setText(text,true);
//        mimeMessageHelper.setTo("");
//        mimeMessageHelper.setFrom("");
//
//        mimeMessageHelper.addAttachment("test.jpg",new File(""));
//
//        mailSender.send(mimeMessage);
//    }
//
//}
