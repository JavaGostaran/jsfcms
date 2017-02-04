/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soda.jsf;

import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author mnm
 */
public  class emailing extends Thread {
        public String send_email(String msg, String subject, String hed, String email) {
        String _to = email;
        String smtpServer = "mail.sodasell.com";
        String _from = "admin@sodasell.com";

        String fail_msg = "fail";
        String suc = "success";
        try {
            Properties props = System.getProperties();
            props.put("mail.transport.protocol", "smtp");
//            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", smtpServer);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");

            Authenticator auth = new SMTPAuthenticator();
            Session session = Session.getInstance(props, auth);
            Message mssg = new MimeMessage(session);
            // -- Set the FROM and TO fields -- 
            mssg.setFrom(new InternetAddress(_from));
            mssg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(_to, false));
            mssg.setSubject(subject);

            mssg.setText(msg);
            // -- Set some other header information -- 
            mssg.setHeader("Content-Type", hed);
            mssg.setSentDate(new Date());

            // -- Send the message -- 
            Transport.send(mssg);
            System.out.println("Message sent to" + _to + " OK.");
            return suc;


        } catch (Exception ex) {
            System.out.println("Exception " + ex);
            return fail_msg;

        }

    }
        String ms, su, hed, email;

        public void run() {
            send_email(ms, su, hed, email);
        }

        public emailing(String mes, String sub, String h, String em) {
            ms = mes;
            su = sub;
            hed = h;
            email = em;
        }
    private class SMTPAuthenticator extends javax.mail.Authenticator {

        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            String username = "admin@sodasell.com";//"no-reply@khazeshgar.com";           // specify your email id here (sender's email id)
            String password = "f12OIvOi";//"";                                      // specify your password here
            return new PasswordAuthentication(username, password);
        }
    }
    public static void main(String ap[]){
        
            emailing mnm = new emailing("message", "subject", "text/plain", "mohammad.ghasemy@gmail.com");
//        mnm.send_email("j2eelab.net", "admin@j2eelab.net", "behopebehope");
        mnm.run();
        
    }
    }
