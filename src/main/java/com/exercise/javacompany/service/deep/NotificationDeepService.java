package com.exercise.javacompany.service.deep;

import com.exercise.javacompany.model.log.LogEntry;
import com.exercise.javacompany.model.log.ProjectResponsibleEmployeeChangeLog;
import org.springframework.stereotype.Service;

@Service
public class NotificationDeepService {

    public void handle(LogEntry log) {
        if (log instanceof ProjectResponsibleEmployeeChangeLog) {
            handleProjectLog((ProjectResponsibleEmployeeChangeLog) log);
        }
    }

    private void handleProjectLog( ProjectResponsibleEmployeeChangeLog log) {
        System.out.println(
                "Der neue Hauptverantwortlicher für das Projekt " + log.getResponsibleProject().getName() + " heißt nun " + log.getResponsibleEmployee().getName()
        );
    }
}
