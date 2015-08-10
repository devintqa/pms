package com.psk.pms.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import com.psk.pms.Constants;
import com.psk.pms.model.Employee;

public class EmployeeDAOImpl implements EmployeeDAO {

    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger LOGGER = Logger.getLogger(EmployeeDAOImpl.class);


    public int getEmployeeLoginDetails(String userName, String password) {
        String sql = "SELECT COUNT(*) FROM employee where empId = ? and empPassword = ?";
        int total = jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{userName, password});
        return total;
    }

    public String getUserRole(String empId) {
        String sql = "SELECT userRole FROM userroles where empId = ?";
        String role = jdbcTemplate.queryForObject(
                sql, String.class, empId);
        return role;
    }

    @SuppressWarnings("unchecked")
    public Employee getEmployeeDetails(String empId) {
        String sql = "SELECT * FROM employee where empId = ?";
        Employee employee = (Employee) jdbcTemplate.queryForObject(sql, new Object[]{empId}, new EmployeeRowMapper());
        return employee;
    }

    public int isEmployeeExisting(String userName) {
        String sql = "SELECT COUNT(*) FROM employee where empId = ? and enabled =" + Constants.ENABLE_EMPLOYEE_ACCESS;
        int total = jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{userName});
        return total;
    }

    public boolean signupEmployee(Employee employee) {
        String sql = "INSERT INTO employee" +
                "(empId, empPassword, empFirstName, empLastName, empTeam, empAddress, empGender, "
                + "empMobNum, empMail, empMotherName, enabled) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, new Object[]{employee.getEmployeeId(),
                employee.getEmployeePwd(), employee.getEmployeeFName(),
                employee.getEmployeeLName(), employee.getEmployeeTeam(),
                employee.getEmployeeAddress(), employee.getEmployeeGender(),
                employee.getEmployeeMobile(), employee.getEmployeeMail(),
                employee.getEmployeeMotherMaidenName(), employee.getEnabled()
        });

        String sql2 = "INSERT INTO userroles" +
                "(empId, userRole) " +
                "VALUES (?, ?)";
        jdbcTemplate.update(sql2, new Object[]{employee.getEmployeeId(), "ROLE_USER"});

        return true;
    }

    public boolean updateEmployee(Employee employee) {
        String sql = "UPDATE employee set empAddress  = ?, empMobNum = ?, empMail = ? WHERE empId = ?";
        jdbcTemplate.update(sql, new Object[]{
                employee.getEmployeeAddress(), employee.getEmployeeMobile(),
                employee.getEmployeeMail(), employee.getEmployeeId()
        });
        return true;
    }


    public boolean isEmployeeMotherMaidenExisting(String userName,
                                                  String motherMaiden) {
        String sql = "SELECT COUNT(*) FROM employee where empId = ? and empMotherName = ?";
        int total = jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{userName, motherMaiden});
        if (total == 0) {
            return false;
        }
        return true;
    }


    public boolean isEmployeeMailExisting(String mail) {
        String sql = "SELECT COUNT(*) FROM employee where empMail = ?";
        int total = jdbcTemplate.queryForObject(sql, Integer.class,
                new Object[]{mail});
        if (total == 0) {
            return false;
        }
        return true;
    }

    public boolean isEmployeeMobNumExisting(String mobile) {
        String sql = "SELECT COUNT(*) FROM employee where empMobNum = ?";
        int total = jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{mobile});
        if (total == 0) {
            return false;
        }
        return true;
    }


    public boolean resetpassword(String userName, String password) {

        String sql = "UPDATE employee set empPassword = ? WHERE empId = ? ";
        int count = jdbcTemplate.update(sql, new Object[]{password, userName
        });
        if (count == 0) {
            return false;
        }
        return true;
    }

    @Override
    public int getUserNamePatternCount(String userName) {
        String name = userName + "%";
        String sql = "SELECT count(employee.empId) FROM employee where employee.empId like ? order by employee.empId asc";
        int count = jdbcTemplate.queryForObject(
                sql, new Object[]{name}, Integer.class);
        return count;
    }

    @Override
    public Map<String, String> fetchTeamNames() {
        String sql = "select teamName from team";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        Map<String, String> teamNames = new LinkedHashMap<String, String>();
        for (Map<String, Object> row : rows) {
            teamNames.put((String) row.get("teamName"), (String) row.get("teamName"));
        }
        return teamNames;
    }

    @Override
    public List<Employee> getNewRegistrationRequest(String fromDate) {

        String sql = "SELECT employee.empId, employee.empPassword, employee.empFirstName, "
                + "employee.empLastName, employee.empAddress, employee.empGender, "
                + "employee.empMobNum, employee.empMail, employee.empMotherName, employee.enabled, "
                + "employee.empTeam FROM pms.employee where employee.enabled = 0";


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
        int status = jdbcTemplate.update(sql, new Object[]{employee.getEnabled(), employee.getEmployeeId()});
        LOGGER.info("method =manageUserAccess() , status: " + status);
        return status;
    }

}
