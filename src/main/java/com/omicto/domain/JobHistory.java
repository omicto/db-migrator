package com.omicto.domain;

import java.time.LocalDate;

public class JobHistory {
    public Long employeeId;
    public LocalDate startDate;
    public LocalDate endDate;
    public String jobId;
    public Long departmentId;

    public JobHistory(Long employeeId, LocalDate startDate, LocalDate endDate, String jobId, Long departmentId) {
        this.employeeId = employeeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.jobId = jobId;
        this.departmentId = departmentId;
    }
}
