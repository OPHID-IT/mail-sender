package tech.dylandzvene.MailSender.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Email {

    private int id;
    private String subject;
    private String body;

    private String recipient;
    private String copy;
    private String Status;

}
