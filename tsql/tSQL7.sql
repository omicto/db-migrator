CREATE FUNCTION getManagerName(
    @id_dept INT 
)
RETURNS VARCHAR(200)
AS
BEGIN
    DECLARE @manager_name varchar(200);
 
    SELECT @manager_name = 
	first_name + ' ' + last_name
    FROM hr.employees
    WHERE employee_id = (SELECT manager_id FROM departments WHERE department_id = @id_dept);
    
    RETURN @manager_name;
END;



CREATE FUNCTION getEmployeeName(
    @id_emp INT 
)
RETURNS VARCHAR(200)
AS
BEGIN
    DECLARE @employee_name varchar(200);
 
    SELECT @employee_name = 
	first_name + ' ' + last_name
    FROM hr.employees
    WHERE employee_id = @id_emp;
    
    RETURN @employee_name;
END;


CREATE FUNCTION getEmployeeYears(
    @id_emp INT 
)
RETURNS FLOAT
AS
BEGIN
    DECLARE @emp_years SMALLINT;
 
    SELECT @emp_years = YEAR(GETDATE()) - YEAR(hire_date)
    FROM hr.employees
    WHERE employee_id = @id_emp;
    
    RETURN @emp_years;
END;

CREATE FUNCTION haveCommission(
    @id_emp INT 
)
RETURNS FLOAT
AS
BEGIN
    DECLARE @commission FLOAT;
    DECLARE @flag SMALLINT;
 
    SELECT @commission = commission_pct
    FROM hr.employees
    WHERE employee_id = @id_emp;
    
    IF @commission = 0 OR @commission IS NULL BEGIN
        SET @flag = 0;
    END
    ELSE BEGIN
        SET @flag = 1;
    END 
    
    RETURN @flag;
END;

CREATE FUNCTION haveEmployees(
    @id_dept INT 
)
RETURNS FLOAT
AS
BEGIN
    DECLARE @employees_count INT;
    DECLARE @flag SMALLINT;
 
    SELECT @employees_count = COUNT(*)
    FROM hr.departments d
    INNER JOIN hr.employees e
    ON d.department_id = e.department_id
    WHERE d.department_id = @id_dept;
    
    IF @employees_count = 0 BEGIN
        SET @flag = 0;
    END
    ELSE BEGIN
        SET @flag = 1;
    END 
    
    RETURN @flag;
END;
