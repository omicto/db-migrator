package com.omicto.domain;

import java.time.LocalDate;

public class Employee {
    public long employeeId;
    public String firstName;
    public String lastName;
    public String email;
    public String phoneNumber;
    public LocalDate hireDate;
    public String jobId;
    public double salary;
    public double commission;
    public long managerId;
    public long departmentId;

    public Employee(long employeeId, String firstName, String lastName, String email, String phoneNumber, LocalDate hireDate, String jobId, double salary, double commission, long managerId, long departmentId) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.hireDate = hireDate;
        this.jobId = jobId;
        this.salary = salary;
        this.commission = commission;
        this.managerId = managerId;
        this.departmentId = departmentId;
    }
}
