package org.biacbiac.manage.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by Song on 2016/7/19.
 */
public class MailUtil {
    public static Properties props;
    static {
        try{
            props = new Properties();
            props.load(new InputStreamReader(MailUtil.class.getClassLoader().getResourceAsStream("setting/mail.properties"), "UTF-8"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 发送邮件
     * @param toMail 收件人邮箱
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    public static void sendEmail(String toMail,String subject,String content){
        try{
            // 构建授权信息，用于进行SMTP进行身份验证
            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    // 用户名、密码
                    String userName = props.getProperty("mail.user");
                    String password = props.getProperty("mail.password");
                    return new PasswordAuthentication(userName, password);
                }
            };
            // 使用环境属性和授权信息，创建邮件会话
            Session mailSession = Session.getInstance(props, authenticator);
            // 创建邮件消息
            MimeMessage message = new MimeMessage(mailSession);
            // 设置发件人
            InternetAddress form = new InternetAddress(
                    props.getProperty("mail.user"));
            message.setFrom(form);

            // 设置收件人
            InternetAddress to = new InternetAddress(toMail);
            message.setRecipient(MimeMessage.RecipientType.TO, to);
/*            // 设置抄送
            InternetAddress cc = new InternetAddress("1173126448@qq.com");
            message.setRecipient(MimeMessage.RecipientType.CC, cc);
            // 设置密送，其他的收件人不能看到密送的邮件地址
            InternetAddress bcc = new InternetAddress("1173126448@qq.com");
            message.setRecipient(MimeMessage.RecipientType.BCC, bcc);*/

            /*// 设置邮件标题
            message.setSubject("BIAC账号激活邮件");*/
            message.setSubject(subject);

            // 设置邮件的内容体
          /*  message.setContent("<a href='http://1u5186s163.51mypc.cn/manege/agent/login'>测试的HTML邮件</a>" +
                    "<br>如果以上链接无法点击，请将上面的地址复制到你的浏览器(如IE)的地址栏进入微信开放平台。" +
                    "<br> （该链接在48小时内有效，48小时后需要重新注册）", "text/html;charset=UTF-8");*/
            message.setContent(content,"text/html;charset=UTF-8");

            // 发送邮件
            Transport.send(message);
        }catch (Exception e){
            //设置发件人失败
            e.printStackTrace();
        }



    }
}
