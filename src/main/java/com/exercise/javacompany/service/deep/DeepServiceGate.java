package com.exercise.javacompany.service.deep;

import com.exercise.javacompany.model.log.LogEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeepServiceGate {

    private final FiringEmailDeepService firingEmailDeepService;
    private final NotificationDeepService notificationDeepService;

    @Autowired
    public DeepServiceGate(FiringEmailDeepService firingEmailDeepService,
                           NotificationDeepService notificationDeepService
    ) {
        this.firingEmailDeepService = firingEmailDeepService;
        this.notificationDeepService = notificationDeepService;
    }

    public void handle(LogEntry log) {
        notificationDeepService.handle(log);
        firingEmailDeepService.handle(log);
    }
}