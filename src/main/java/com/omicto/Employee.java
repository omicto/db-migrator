package com.omicto;

import java.time.LocalDate;

public class Employee {
    public Long employeeId;
    public String firstName;
    public String lastName;
    public String email;
    public String phoneNumber;
    public String jobId;
    public Long managerId;
    public LocalDate hireDate;
    public double salary;
    public double commission;
    public Long departmentId;

    public Employee(Long employeeId, String firstName, String lastName, String email,
                    String phoneNumber, String jobId, Long managerId, LocalDate hireDate,
                    double salary, double commission, Long departmentId) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.jobId = jobId;
        this.managerId = managerId;
        this.hireDate = hireDate;
        this.salary = salary;
        this.commission = commission;
        this.departmentId = departmentId;
    }
}
