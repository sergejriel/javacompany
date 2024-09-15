package com.exercise.javacompany.service.deep;

import com.exercise.javacompany.model.log.EmployeeQuitLog;
import com.exercise.javacompany.model.log.LogEntry;
import org.springframework.stereotype.Service;

@Service
public class FiringEmailDeepService {

    public void handle(LogEntry log) {
        if (log instanceof EmployeeQuitLog) {
            sendEmailToFiredEmployee((EmployeeQuitLog) log);
        }
    }

    private void sendEmailToFiredEmployee( EmployeeQuitLog log) {
        //Ich habe kein SMTP Server :)
        System.out.println("Sehr geehrte/r " + log.getQuitEmployee().getName() + " , Sie sind leider gefeuert. Hoffentlich nicht für lange. Viel Glück!");
    }
}
