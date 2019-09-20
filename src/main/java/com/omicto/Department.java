package com.omicto;

public class Department {
    public Long departmentId;
    public String departmentName;
    public Long managerId;
    public Long locationId;

    public Department(Long departmentId, String departmentName, Long managerId, Long locationId) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.managerId = managerId;
        this.locationId = locationId;
    }
}
