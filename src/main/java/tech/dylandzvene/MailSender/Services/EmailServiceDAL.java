package tech.dylandzvene.MailSender.Services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tech.dylandzvene.MailSender.DTO.Email;

import java.util.ArrayList;
import java.util.List;
@Repository
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EmailServiceDAL {



        private final JdbcTemplate jdbcTemplate;

        public List<Email> getAllEmailToBeSent() {
            String sqlQuery = "SELECT [ID]\n" +
                    "      ,[SUBJECT]\n" +
                    "      ,[BODY]\n" +
                    "      ,[RECIPIENT]\n" +
                    "      ,[COPY]\n" +
                    "      ,[TIMESTAMP]\n" +
                    "      ,[STATUS]\n" +
                    "  FROM [PerformanceManagementSystem].[dbo].[EmailsTab] WHERE STATUS='PENDING'";
            List<Email> emailsDtoList = jdbcTemplate.query(sqlQuery, (rs, rowNum) -> {
                Email email = new Email();
                email.setId(rs.getInt("ID"));
                email.setBody(rs.getString("BODY"));
                email.setRecipient(rs.getString("RECIPIENT"));
                email.setCopy(rs.getString("COPY"));
                email.setStatus(rs.getString("STATUS"));
                email.setSubject(rs.getString("SUBJECT"));

                return email;


            });

            return emailsDtoList.isEmpty()?new ArrayList<>():emailsDtoList;
        }
    public int updateStatus(int emailID) {
        String sqlUpdate = "UPDATE[PerformanceManagementSystem].[dbo].[EmailsTab] SET STATUS='SENT' WHERE ID=?";
        return jdbcTemplate.update(sqlUpdate, emailID);
    }

}
