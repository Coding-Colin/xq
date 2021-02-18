package com.community.system.util;


import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;


/**
 * 邮件发送工具类
 */

public class SendEmail {


    private String to;//收件人邮箱地址
    private String from;//发件人邮箱地址
    private String smtpServer;//SMTP服务器地址
    private String username ;//登录SMTP服务器的用户名
    private String password ;//登录SMTP服务器的密码
    private String subject;//邮件主题
    private String content;//邮件正文
    List<String> attachments = new ArrayList<String>();//记录所有附件文件的集合

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSmtpServer() {
        return smtpServer;
    }

    public void setSmtpServer(String smtpServer) {
        this.smtpServer = smtpServer;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SendEmail( ) {
    }

    public SendEmail(String to, String from, String smtpServer, String username, String password, String subject, String content) {
        this.to = to;
        this.from = from;
        this.smtpServer = smtpServer;
        this.username = username;
        this.password = password;
        this.subject = subject;
        this.content = content;
    }


    //增加附件，将附件文件名添加的List集合中
    public void attachfile(String fname){
        attachments.add(fname);
    }
    //发送邮件
    public  boolean send(){
        //创建邮件Session所需的Properties对象
        Properties props = new Properties();
        props.put("mail.smtp.host" , smtpServer);
        props.put("mail.smtp.auth" , "true");
        props.put("mail.smtp.debug" , "true");
        //创建Session对象
        Session session = Session.getDefaultInstance(props
                //以匿名内部类的形式创建登录服务器的认证对象
                , new Authenticator(){
                    public PasswordAuthentication getPasswordAuthentication(){
                        return new PasswordAuthentication(username, password);
                    }
                });
        try{
            //构造MimeMessage并设置相关属性值
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));//设置发件人
            InternetAddress[] addresses = {new InternetAddress(to)};//设置收件人
            msg.setRecipients(Message.RecipientType.TO , addresses);
            msg.setSubject(subject);//设置邮件主题
            Multipart mp = new MimeMultipart();//构造Multipart
            MimeBodyPart mbpContent = new MimeBodyPart();//向Multipart添加正文
            mbpContent.setText(content);
            mp.addBodyPart(mbpContent);//将BodyPart添加到MultiPart中
            //向Multipart添加附件
            //遍历附件列表，将所有文件添加到邮件消息里
            for(String efile : attachments) {
                MimeBodyPart mbpFile = new MimeBodyPart();
                //以文件名创建FileDataSource对象
                FileDataSource fds = new FileDataSource(efile);
                //处理附件
                mbpFile.setDataHandler(new DataHandler(fds));
                mbpFile.setFileName(fds.getName());
                //将BodyPart添加到MultiPart中
                mp.addBodyPart(mbpFile);
            }
            attachments.clear();//清空附件列表
            msg.setContent(mp);//向Multipart添加MimeMessage
            msg.setSentDate(new Date());//设置发送日期
            Transport.send(msg);//发送邮件
        }
        catch (MessagingException mex){
            mex.printStackTrace();
            return false;
        }
        return true;
    }
}
