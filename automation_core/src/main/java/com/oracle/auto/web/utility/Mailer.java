package com.oracle.auto.web.utility;

import com.sun.mail.pop3.POP3Store;

import javax.mail.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

public class Mailer {
    private String host;
    private String user;
    private String password;

    private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(Mailer.class);

    private int EMAIL_TIME_OUT = PropertyConfiger.instance().getEnvData("email.new.time.out", 30);


    public static String getDomainName(String url) throws URISyntaxException {
        URI uri = new URI(url);
        String domain = uri.getHost();
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }

    public Mailer(String host, String user, String password) {
        try {
            this.host = getDomainName(host);
        } catch (URISyntaxException e) {
            throw new RuntimeException("cannot parse domain name from host: " + host);
        }
        this.user = user;
        this.password = password;
    }

    public void waitForEmail(Mailer mailer, int index) {
        int i = 0;
        int waitUnit = 1;
        while (mailer.getMessages().length < index) {
            if ((i += waitUnit) > EMAIL_TIME_OUT) {
                throw new RuntimeException("Email not found after " + i + " seconds...");
            }
            try {
                Thread.sleep(waitUnit * 1000);
            } catch (Exception ex) {
            }
        }
    }

    public Email[] getMessages(int timeout) {
        int i = 0;
        int waitUnit = 1;
        Email[] mails = null;
        while ((mails = getMessages()).length <= 0) {
            if ((i += waitUnit) > timeout) {
                throw new RuntimeException("time out to wait for receiving an email.");
            }
            try {
                Thread.sleep(waitUnit * 1000);
                waitUnit = 1;
            } catch (Exception ex) {
            }
        }

        return mails;
    }

    private String getText(Part p) throws MessagingException, IOException {
        if (p.isMimeType("text/*")) {
            String s = (String) p.getContent();
            //textIsHtml = p.isMimeType("text/html");
            return s;
        }

        if (p.isMimeType("multipart/alternative")) {
            // prefer html text over plain text
            Multipart mp = (Multipart) p.getContent();
            String text = null;
            for (int i = 0; i < mp.getCount(); i++) {
                Part bp = mp.getBodyPart(i);
                if (bp.isMimeType("text/plain")) {
                    if (text == null)
                        text = getText(bp);
                    continue;
                } else if (bp.isMimeType("text/html")) {
                    String s = getText(bp);
                    if (s != null)
                        return s;
                } else {
                    return getText(bp);
                }
            }
            return text;
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = getText(mp.getBodyPart(i));
                if (s != null)
                    return s;
            }
        }

        return "";
    }

    public Email[] getMessages() {
        try {
            Properties props = new Properties();
            props.setProperty("mail.pop3.ssl.enable", "false");
            Session session = Session.getDefaultInstance(props, null);
            session.setDebug(true);
            POP3Store store = (POP3Store) session.getStore("pop3");
            store.connect(host, user, password);

            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);

            Message[] messages = folder.getMessages();

            Email[] emails = new Email[messages.length];
            for (int i = 0; i < messages.length; i++) {
                int number = messages[i].getMessageNumber();
                String subject = messages[i].getSubject();
                Address[] addresses = messages[i].getFrom();

                String content = getText(messages[i]); //String.valueOf(messages[i].getContent());

                String[] froms = new String[addresses.length];
                for (int j = 0; j < addresses.length; j++) {
                    froms[j] = String.valueOf(addresses[j]);
                }

                emails[i] = new Email(number, subject, froms, content);
            }

            folder.close(false);
            store.close();

            return emails;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get messages.", e);
        }
    }

    public void deleteMessages() {
        try {
            Properties props = new Properties();
            props.setProperty("mail.pop3.ssl.enable", "false");
            Session session = Session.getDefaultInstance(props, null);
            POP3Store store = (POP3Store) session.getStore("pop3");
            store.connect(host, user, password);

            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_WRITE);

            Message[] messages = folder.getMessages();
            for (Message message : messages) {
                message.setFlag(Flags.Flag.DELETED, true);
            }

            folder.close(true);
            store.close();
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete messages.", e);
        }
    }

    public static class Email {
        public int number;
        public String subject;
        public String[] from;
        public String content;

        public Email() {
        }

        public Email(int number, String subject, String[] from, String content) {
            this.number = number;
            this.subject = subject;
            this.from = from;
            this.content = content;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("Email [");
            builder.append("number=").append(number);
            builder.append(", subject=").append(subject);
            builder.append(", from=").append(from.length > 0 ? from[0] : "");
            builder.append(", content=").append(content);
            builder.append("]");
            return builder.toString();
        }

        public String storeToTemp(String filePrefix, String fileSuffix, String contentPrefix, String contentSubffix) {
            try {

                //create a temp file
                String browser_home = PropertyConfiger.instance().getEnvData("browser.home.remote", "");

                File temp = (browser_home.isEmpty()) ? File.createTempFile(filePrefix, fileSuffix) : File.createTempFile(filePrefix, fileSuffix, new File(browser_home));

                log.debug("Email storing at --> " + browser_home);

                //write it
                BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
                bw.write(contentPrefix + content + contentSubffix);
                bw.flush();
                bw.close();

                return temp.getAbsolutePath();
            } catch (IOException ex) {
                throw new RuntimeException("fail to save email to temp file", ex);
            }
        }

        public String storeAsHTMLWithHead() {
            return storeToTemp("tempfile", ".html", "<html><head><meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\"></head><body>", "</body></html>");
        }
    }

}
