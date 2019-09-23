--1. Procedimientos Almacenados
CREATE PROCEDURE add_employees(
    @empno              INT,
    @fname              VARCHAR(50),
    @lname              VARCHAR(50),
    @mail               VARCHAR(50),
    @phone              VARCHAR(50),
    @hiredate           DATETIME,
    @jobid              VARCHAR(50),
    @sal                INT,
    @comm               INT,
    @mangerid           INT,
    @departid           INT
)
AS
BEGIN
    INSERT INTO employees VALUES (
        @empno,
        @fname,
        @lname,
        @mail,
        @phone,
        @hiredate,
        @jobid,
        @sal,
        @comm,
        @mangerid,
        @departid
    );

END;

BEGIN TRY  
    EXEC add_employees  @empno = 101, @fname = "Julieta", @lname= "Navarro", @mail ="juliiy98@gmail.com",@phone="614122457",
						@hiredate='15/04/1998', @jobid="IT_PROG", @sal=6555,@comm=1,@mangerid=101,@departid=20;
END TRY  
BEGIN CATCH  
    SELECT   
        ERROR_NUMBER() AS ErrorNumber,
		ERROR_MESSAGE() AS ErrorMessage;   
END CATCH;

--Problema 2. Procedimientos Almacenados
CREATE PROCEDURE add_jh_entry @emp_id INT, @job_id varchar(50), @dept_id INT
AS
BEGIN

	DECLARE @jobs_count int = 0;
	DECLARE @job_start_date DATETIME;
    DECLARE @job_end_date DATETIME;
	DECLARE @job_hire_date DATETIME;
	DECLARE @ultimo_dia DATETIME;

	SELECT @jobs_count =
	COUNT(*)
    FROM job_history
    WHERE employee_id = @emp_id;

	SELECT @job_hire_date =
	hire_date
    FROM employees
    WHERE employee_id = @emp_id;
    

    SELECT @ultimo_dia =
    end_date
    FROM job_history
    WHERE employee_id = @emp_id;
    
    IF @jobs_count = 0 
	begin
        SET @job_start_date = @job_hire_date;
        SET @job_end_date = SYSDATETIME();
	END
    ELSE
	BEGIN
        SET @job_start_date = @ultimo_dia + 1;
        SET @job_end_date = SYSDATETIME();
	END
  
    
    INSERT INTO job_history VALUES (
        @emp_id,
        @job_start_date,
        @job_end_date,
        @job_id,
        @dept_id
    );
    
END;

BEGIN TRY  
    EXEC add_jh_entry  @emp_id = 101, @job_id = "IT_PROG", @dept_id= 20;
END TRY  
BEGIN CATCH  
    SELECT   
        ERROR_NUMBER() AS ErrorNumber,
		ERROR_MESSAGE() AS ErrorMessage;   
END CATCH;   
