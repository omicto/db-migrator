package com.omicto.domain;

public class Job {
    public String jobId;
    public String jobTitle;
    public Long minSalary;
    public Long maxSalary;

    public Job(String jobId, String jobTitle, Long minSalary, Long maxSalary) {
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
    }
}
