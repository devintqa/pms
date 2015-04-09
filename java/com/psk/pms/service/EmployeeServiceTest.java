package com.psk.pms.service;

import java.util.Random;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.psk.pms.model.Employee;
import com.psk.pms.service.EmployeeService;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployeeServiceTest {
	
	ApplicationContext context = 
		new ClassPathXmlApplicationContext("applicationContextTest.xml");
	String empId = "";
		
	@Before
    public void setUp() {
		empId = generateRandomNumber(100000, 900000);
    }
	 	
	 	@Test
	    public void aTestSignupEmployee() { 
	 		EmployeeService employeeService = (EmployeeService) context.getBean("employeeService");
	    	Employee employee = new Employee();
	    	System.out.println("Employee Signup for emp ID: " + empId);
	    	employee.setEmpId(empId);
	    	employee.setEmpPassword("password");
	    	employee.setEmployeeFName("Sriram");
	    	employee.setEmployeeLName("Kumar");
	    	employee.setEmployeeAddress("address");
	    	employee.setEmployeeGender("M");
	    	employee.setEmployeeDOJ("11-11-2014");
	    	employee.setEmployeeMobile("123456789");
	    	employee.setEmployeeMail("a@b.com");
	    	employee.setEmployeeMotherMaidenName("Mother");
	 		boolean isSignUpSuccessful= employeeService.signupEmployee(employee);
	        System.out.println("Employee SignUp status: " + isSignUpSuccessful);
	    }
	 	
	 	@Test
	 	public void bTestUpdateEmployeeDetails(){
	 		EmployeeService employeeService = (EmployeeService) context.getBean("employeeService");
	 		Employee employee = new Employee();
	 		System.out.println("Employee Update for emp ID: " + empId);
	 		employee.setEmpId(empId);
	 		employee.setEmpPassword("test1");
	    	employee.setEmployeeFName("Maharaja");
	    	employee.setEmployeeLName("Murugan");
	    	employee.setEmployeeAddress("address");
	    	employee.setEmployeeDOJ("25-05-1991");
	    	employee.setEmployeeMobile("7387363873");
	    	employee.setEmployeeMail("smrgce@gmail.com");
	 		employeeService.updateEmployee(employee);
	 	}
	 	
		@Test
	    public void cTestIsEmployeeAvailable() {
	 		EmployeeService employeeService = (EmployeeService) context.getBean("employeeService");
	    	boolean isEmployeeAvailable = employeeService.isValidLogin(empId, "test1");
	        System.out.println("Employee availability status: " + isEmployeeAvailable);
	    }

	 	public static String generateRandomNumber(int min, int max) {
	 	    Random rand = new Random();
	 	    int randomNum = rand.nextInt((max - min) + 1) + min;
	 	    return String.valueOf(randomNum);
	 	}

}
