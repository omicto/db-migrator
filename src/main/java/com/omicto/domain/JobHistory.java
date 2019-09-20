package com.omicto.domain;

import java.time.LocalDate;
import java.util.Objects;

public class JobHistory {
    public Long employeeId;
    public LocalDate startDate;
    public LocalDate endDate;
    public String jobId;
    public Long departmentId;
    private Id id;

    public JobHistory(Long employeeId, LocalDate startDate, LocalDate endDate, String jobId, Long departmentId) {
        this.employeeId = employeeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.jobId = jobId;
        this.departmentId = departmentId;
        this.id = new Id(employeeId, startDate);
    }

    public class Id {
        public Long employeeId;
        public LocalDate startDate;

        public Id(Long employeeId, LocalDate startDate) {
            this.employeeId = employeeId;
            this.startDate = startDate;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Id id = (Id) o;
            return employeeId.equals(id.employeeId) &&
                    startDate.equals(id.startDate);
        }

        @Override
        public int hashCode() {
            return Objects.hash(employeeId, startDate);
        }
    }
}
