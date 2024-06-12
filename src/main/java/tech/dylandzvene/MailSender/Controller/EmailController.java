package tech.dylandzvene.MailSender.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.dylandzvene.MailSender.Services.EmailService;


import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v2")
@RequiredArgsConstructor
@Slf4j
public class EmailController {

    private  final EmailService emailService;

    @GetMapping("/getemails")
    public ResponseEntity<List> getAllForSending (){
        return ResponseEntity.ok().body(emailService.getAllEmailsForSending());
    }
}
