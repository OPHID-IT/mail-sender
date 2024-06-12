package tech.dylandzvene.MailSender.Services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.dylandzvene.MailSender.DTO.Email;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final EmailServiceDAL emailServiceDAL;
    private final JavaMailSender mailSender;

    public List<Email> getAllEmailsForSending() {

        List<Email> response = emailServiceDAL.getAllEmailToBeSent();

        return response;
    }

    public boolean sendMail(String employeeEmail, String body, String subject, String[] ccEmails) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("hradmin@ophid.co.zw");
            helper.setTo(employeeEmail);
            helper.setText(body, true);

            if (ccEmails != null && ccEmails.length > 0) {
                helper.setCc(ccEmails);
            }

            helper.setSubject(subject);

            mailSender.send(message);
            System.out.println("---------->The message has been sent successfully.");

            return true; // Email sent successfully
        } catch (MessagingException e) {
            System.err.println("---------->Failed to send email: " + e.getMessage());
            return false; // Failed to send email
        }
    }
    @Scheduled(fixedDelay = 3000) //
    public void cronEmailSender() {

        List<Email> emails = getAllEmailsForSending();
        System.out.println("------->Emails to be sent are totalling ::"+emails.size());
        for (Email em : getAllEmailsForSending()) {
            String[] copies = em.getCopy().split(";");

            if (sendMail(em.getRecipient(), em.getBody(), em.getSubject(), copies)) {
                int resultOnUpdate = emailServiceDAL.updateStatus(em.getId());
                System.out.println("---------> S");
                if(resultOnUpdate>0){
                    log.info("---------->Success in updating the status of email sent ");
                }else{
                    log.error("---------->ERROR in updating the status of email sent ");
                }


            } else {
                log.error("---------->ERROR in sending the Email");
            }

        }

    }


}
