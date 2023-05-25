package com.shu.leettest.utils.mailUtil;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author 54dbd
 */
public class MailUtil {
    public static void main(String[] args) throws MessagingException {
        MailUtil.send_mail("845559360@qq.com", "123");
    }
    /**
     * 使用网易邮箱发送邮件
     <<<<<<< HEAD
     =======
     *
     >>>>>>> 8444f5c (增加vue-particles特效，修复邮箱注册功能)
     * @param to   给谁发
     * @param text 发送内容
     */
    public static void send_mail(String to, String text) {
        //创建连接对象 连接到邮件服务器
        Properties properties = new Properties();
        //设置发送邮件的基本参数
        //发送邮件服务器

        properties.put("mail.smtp.host", "smtp.163.com");
        //发送端口
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", true);

        //设置发送邮件的账号和密码
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {

//                return new javax.mail.PasswordAuthentication("你的网易邮箱", "你的网易邮箱授权码");
                return new javax.mail.PasswordAuthentication("itjx_1610580114@163.com", "IGDXNWGMGLEBOOPF");
            }
        });

        //创建邮件对象
        Message message = new MimeMessage(session);
        //设置发件人
        try {
            message.setFrom(new InternetAddress("itjx_1610580114@163.com"));
            //设置收件人
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            //设置主题
            message.setSubject("[LeetTest考研网]验证码");
            //设置邮件正文  第二个参数是邮件发送的类型
            message.setContent(text, "text/html;charset=UTF-8");
            //发送一封邮件
            Transport.send(message);
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }

    }


/*
    public static void sendCode(String email, String code, String title) {
        MailSendInfo mailSendInfo = new MailSendInfo();
        mailSendInfo.setServerHost("smtp.163.com");
        mailSendInfo.setServerPort("465");
        mailSendInfo.setUserName("itjx_1610580114");
        mailSendInfo.setUserPwd("123zebJSEPQZEQHB");
        mailSendInfo.setSubject(title);
        mailSendInfo.setFromAddress("itjx_1610580114@163.com");
        mailSendInfo.setContent("<h2>" + code + "</h2>");


        List<String> users = new ArrayList<>();
        users.add(email);
        mailSendInfo.setToAddress(users);
        MailSender mailSender = new MailSender();
        mailSender.sendMail(mailSendInfo);
    }*/
}
