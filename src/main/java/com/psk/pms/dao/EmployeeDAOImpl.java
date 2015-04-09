package com.psk.pms.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.psk.pms.model.Employee;

public class EmployeeDAOImpl implements EmployeeDAO {
	
	private DriverManagerDataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DriverManagerDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public int getEmployeeLoginDetails(String userName, String password){
		String sql = "SELECT COUNT(*) FROM employee where empId = ? and empPassword = ?";	 
		jdbcTemplate = new JdbcTemplate(dataSource);
		int total = jdbcTemplate.queryForInt(sql, new Object[] { userName, password  });
		return total;
	}
	
	public String getUserRole(String empId){
		String sql = "SELECT userRole FROM userroles where empId = ?";	 
		jdbcTemplate = new JdbcTemplate(dataSource);
		String role = jdbcTemplate.queryForObject(
				sql, String.class, empId);
		return role;
	}
	
	@SuppressWarnings("unchecked")
	public Employee getEmployeeDetails(String empId){
		System.out.println(empId);
		String sql = "SELECT * FROM employee where empId = ?";	 
		jdbcTemplate = new JdbcTemplate(dataSource);
		Employee employee = (Employee)jdbcTemplate.queryForObject(sql, new Object[] { empId }, new EmployeeRowMapper());	 
		return employee;
	}
	
	public int isEmployeeExisting(String userName){
		String sql = "SELECT COUNT(*) FROM employee where empId = ?";	 
		jdbcTemplate = new JdbcTemplate(dataSource);
		int total = jdbcTemplate.queryForInt(sql, new Object[] { userName });
		return total;
	}
	
	public boolean signupEmployee(Employee employee){
	String sql = "INSERT INTO employee" +
		"(empId, empPassword, empFirstName, empLastName, empAddress, empGender, empDOJ, empMobNum, empMail,empMotherName) " +
		"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
	jdbcTemplate = new JdbcTemplate(dataSource);
	jdbcTemplate.update(sql, new Object[] { employee.getEmpId(),
		employee.getEmpPassword(),employee.getEmployeeFName(), employee.getEmployeeLName(),
		employee.getEmployeeAddress(), employee.getEmployeeGender(),
		employee.getEmployeeDOJ(), employee.getEmployeeMobile(),
		employee.getEmployeeMail(), employee.getEmployeeMotherMaidenName()
	});
	String sql2 = "INSERT INTO userroles" +
	"(empId, userRole) " +
	"VALUES (?, ?)";
	jdbcTemplate = new JdbcTemplate(dataSource);
	jdbcTemplate.update(sql2, new Object[] { employee.getEmpId(),"ROLE_USER"});
	
	return true;
	}
	
	public boolean updateEmployee(Employee employee){
		String sql = "UPDATE employee set empPassword = ?, empFirstName = ?, empLastName = ?, empAddress  = ?, empDOJ = ?, empMobNum = ?, empMail = ?, empTeam = ? WHERE empId = ? "; 
		jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sql, new Object[] {
			employee.getEmpPassword(),employee.getEmployeeFName(), employee.getEmployeeLName(),
			employee.getEmployeeAddress(),employee.getEmployeeDOJ(), employee.getEmployeeMobile(),
			employee.getEmployeeMail(),employee.getEmployeeTeam(),employee.getEmpId()
		});		
			return true;
		}
	public boolean isEmployeeDOJExisting(String userName, String DOJ) {
		
		String sql = "SELECT COUNT(*) FROM employee where empId = ? and empDOJ = ?";	 
		jdbcTemplate = new JdbcTemplate(dataSource);
		int total = jdbcTemplate.queryForInt(sql, new Object[] { userName, DOJ });
		if(total == 0){
			return false;			
		}
		return true;	
	}


	public boolean isEmployeeMotherMaidenExisting(String userName,
			String motherMaiden) {
		String sql = "SELECT COUNT(*) FROM employee where empId = ? and empMotherName = ?";	 
		jdbcTemplate = new JdbcTemplate(dataSource);
		int total = jdbcTemplate.queryForInt(sql, new Object[] { userName, motherMaiden });		
		if(total == 0){
			return false;
		}
		return true;
	}


	public boolean resetpassword(String userName, String password) {
		
		String sql = "UPDATE employee set empPassword = ? WHERE empId = ? "; 
		jdbcTemplate = new JdbcTemplate(dataSource);
		int count = jdbcTemplate.update(sql, new Object[] {password,userName
		});		
		if(count == 0){			
			return false;
		}
		return true;
	}
	
	
}
