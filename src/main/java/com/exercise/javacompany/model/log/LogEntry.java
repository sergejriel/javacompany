package com.exercise.javacompany.model.log;

import com.exercise.javacompany.model.BaseEntity;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@MappedSuperclass
public abstract class LogEntry extends BaseEntity {
    public LocalDateTime occurred = LocalDateTime.now();
}
