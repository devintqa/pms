package com.psk.pms.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.psk.pms.Constants;
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
		int total = jdbcTemplate.queryForObject(sql, Integer.class, new Object[] { userName, password  });
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
		String sql = "SELECT * FROM employee where empId = ?";	 
		jdbcTemplate = new JdbcTemplate(dataSource);
		Employee employee = (Employee)jdbcTemplate.queryForObject(sql, new Object[] { empId }, new EmployeeRowMapper());	 
		return employee;
	}

	public int isEmployeeExisting(String userName){
		String sql = "SELECT COUNT(*) FROM employee where empId = ? and enabled ="+Constants.ENABLE_EMPLOYEE_ACCESS;	 
		jdbcTemplate = new JdbcTemplate(dataSource);
		int total = jdbcTemplate.queryForObject(sql, Integer.class, new Object[] { userName });
		return total;
	}

	public boolean signupEmployee(Employee employee){
		String sql = "INSERT INTO employee" +
				"(empId, empPassword, empFirstName, empLastName, empTeam, empAddress, empGender, "
				+ "empMobNum, empMail, empMotherName, enabled) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sql, new Object[] { employee.getEmployeeId(),
				employee.getEmployeePwd(),employee.getEmployeeFName(), 
				employee.getEmployeeLName(), employee.getEmployeeTeam(),
				employee.getEmployeeAddress(), employee.getEmployeeGender(),
				employee.getEmployeeMobile(), employee.getEmployeeMail(), 
				employee.getEmployeeMotherMaidenName(), employee.getEnabled()
		});
		
		String sql2 = "INSERT INTO userroles" +
				"(empId, userRole) " +
				"VALUES (?, ?)";
		jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sql2, new Object[] { employee.getEmployeeId(),"ROLE_USER"});

		return true;
	}

	public boolean updateEmployee(Employee employee){
		String sql = "UPDATE employee set empAddress  = ?, empMobNum = ?, empMail = ? WHERE empId = ?"; 
		jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sql, new Object[] {
				employee.getEmployeeAddress(), employee.getEmployeeMobile(),
				employee.getEmployeeMail(), employee.getEmployeeId()
		});		
		return true;
	}


	public boolean isEmployeeMotherMaidenExisting(String userName,
			String motherMaiden) {
		String sql = "SELECT COUNT(*) FROM employee where empId = ? and empMotherName = ?";	 
		jdbcTemplate = new JdbcTemplate(dataSource);
		int total = jdbcTemplate.queryForObject(sql, Integer.class, new Object[] { userName, motherMaiden });		
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

	@Override
	public List<String> getUserNames(String userName) {
		String name = userName+"%";
		String sql = "SELECT employee.empId FROM employee where employee.empId like ? order by employee.empId asc";		
		jdbcTemplate = new JdbcTemplate(dataSource);			
		List<String> employeeList = new ArrayList<String>();
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[] { name });		 
		for (Map<String, Object> row : rows) {
			String empID = (String) row.get("empId");
			employeeList.add(empID);
		}
		return employeeList;
	}

	@Override
	public List<Employee> getNewRegistrationRequest(String fromDate) {

		String sql = "SELECT employee.empId, employee.empPassword, employee.empFirstName, "
				+ "employee.empLastName, employee.empAddress, employee.empGender, "
				+ "employee.empMobNum, employee.empMail, employee.empMotherName, employee.enabled, "
				+ "employee.empTeam FROM pms.employee where employee.enabled = 0";

		jdbcTemplate = new JdbcTemplate(dataSource);             

		List<Employee> newRequestList = new ArrayList<Employee>();
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

		for (Map<String, Object> row : rows) {
			Employee employee = new Employee();
			employee.setEmployeeId((String) row.get("empId"));
			employee.setEmployeePwd((String) row.get("empPassword"));
			employee.setEmployeeFName((String) row.get("empFirstName"));
			employee.setEmployeeLName((String) row.get("empLastName"));
			employee.setEmployeeAddress((String) row.get("empAddress"));
			employee.setEmployeeTeam((String) row.get("empTeam"));
			employee.setEmployeeGender((String) row.get("empGender"));
			employee.setEmployeeMobile((String) row.get("empMobNum"));
			employee.setEmployeeMail((String) row.get("empMail"));
			employee.setEnabled(row.get("enabled").toString());
			newRequestList.add(employee);
		} 
		return newRequestList;
	}

	@Override
	public int manageUserAccess(Employee employee) {
			String sql = "UPDATE employee set enabled = ? WHERE empId = ? "; 
			jdbcTemplate = new JdbcTemplate(dataSource);
			int status = jdbcTemplate.update(sql, new Object[] {employee.getEnabled(), employee.getEmployeeId()});
			System.out.println("status: "+status);
		return status;
	}
	
}
