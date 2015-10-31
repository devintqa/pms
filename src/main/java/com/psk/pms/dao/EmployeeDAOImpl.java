package com.psk.pms.dao;

import static com.psk.pms.dao.PmsMasterQuery.DELETE_AUTHORIZE_PROJECT;
import static com.psk.pms.dao.PmsMasterQuery.DELETE_EMPLOYEE;
import static com.psk.pms.dao.PmsMasterQuery.GET_ALL_EMPLOYEE;
import static com.psk.pms.dao.PmsMasterQuery.GET_AVAILABLE_EMPLOYEES;
import static com.psk.pms.dao.PmsMasterQuery.GET_ROLES;
import static com.psk.pms.dao.PmsMasterQuery.GET_SELECTED_EMPLOYEES;
import static com.psk.pms.dao.PmsMasterQuery.INSERT_INTO_AUTHORISE_PROJECT;
import static com.psk.pms.dao.PmsMasterQuery.SAVE_ROLES;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import com.psk.pms.Constants;
import com.psk.pms.model.Employee;
import com.psk.pms.model.Team;

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
    
    @Cacheable(value="employeeCache")
    public Employee getEmployeeDetails(String empId) {
    	System.out.println("calling getEmployeeDetails#"+empId);
        String sql = "SELECT * FROM employee where empId = ?";
        return (Employee) jdbcTemplate.queryForObject(sql, new Object[]{empId}, new EmployeeRowMapper());
    }

    public int isEmployeeExisting(String userName) {
        String sql = "SELECT COUNT(*) FROM employee where empId = ? and enabled =" + Constants.ENABLE_EMPLOYEE_ACCESS;
        int total = jdbcTemplate.queryForObject(sql, Integer.class, new Object[]{userName});
        return total;
    }

    public boolean signupEmployee(Employee employee) {
        String sql = "INSERT INTO employee" +
                "(empId, empPassword, empFirstName, empLastName, empTeam, empAddress, empGender, "
                + "empMobNum, empMail, empMotherName, enabled,empRole) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, new Object[]{employee.getEmployeeId(),
                employee.getEmployeePwd(), employee.getEmployeeFName(),
                employee.getEmployeeLName(), employee.getEmployeeTeam(),
                employee.getEmployeeAddress(), employee.getEmployeeGender(),
                employee.getEmployeeMobile(), employee.getEmployeeMail(),
                employee.getEmployeeMotherMaidenName(), employee.getEnabled(), employee.getEmployeeRole()
        });

        String sql2 = "INSERT INTO userroles" +
                "(empId, userRole) " +
                "VALUES (?, ?)";
        jdbcTemplate.update(sql2, new Object[]{employee.getEmployeeId(), "ROLE_USER"});

        return true;
    }

    public boolean updateEmployee(Employee employee) {
        String sql = "UPDATE employee set empAddress  = ?, empMobNum = ?, empMail = ? , empRole = ? WHERE empId = ?";
        jdbcTemplate.update(sql, new Object[]{
                employee.getEmployeeAddress(), employee.getEmployeeMobile(),
                employee.getEmployeeMail(), employee.getEmployeeRole(), employee.getEmployeeId()
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
    public List<Employee> getNewRegistrationRequest(String fromDate) {

        String sql = "SELECT employee.empId, employee.empPassword, employee.empFirstName, "
                + "employee.empLastName, employee.empAddress, employee.empGender, "
                + "employee.empMobNum, employee.empMail, employee.empMotherName, employee.enabled, "
                + "employee.empTeam FROM pms.employee where employee.enabled = 0";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        return buildEmployeeDetail(rows);
    }

    private List<Employee> buildEmployeeDetail(List<Map<String, Object>> rows) {
        List<Employee> newRequestList = new ArrayList<Employee>();
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


    @Override
    public boolean isRoleExists(String roleName, String teamName) {
        String sql = "SELECT COUNT(*) FROM teamRole where roleName = ? and teamName=?";
        int total = jdbcTemplate.queryForObject(sql, Integer.class,
                new Object[]{roleName, teamName});
        if (total == 0) {
            return false;
        }
        return true;
    }


    @Override
    public boolean saveTeamRoles(Team team) {
        jdbcTemplate.update(
                SAVE_ROLES,
                new Object[]{team.getTeamName(),
                        team.getRoleName()});
        return true;
    }

    @Override
    public List<Employee> getAllEmployeeDetails() {
        return buildEmployeeDetail(jdbcTemplate.queryForList(GET_ALL_EMPLOYEE));
    }


    @Override
    public List<String> getAvailableEmployees(String teamName, String projectId) {
        return jdbcTemplate.queryForList(GET_AVAILABLE_EMPLOYEES, String.class,new Object[]{teamName,projectId,teamName});
    }

    @Override
    public void deleteEmployee(String employeeId) {
        jdbcTemplate.update(
                DELETE_EMPLOYEE,
                employeeId);
    }

    @Override
    public List<String> getRolesForTeam(String teamName) {
        List<String> roles = jdbcTemplate.queryForList(GET_ROLES, new Object[]{teamName}, String.class);
        return roles;
    }

    @Override
    public void saveProjectUserPrivilege(final String projectId, final List<String> users, final String teamName) {
        jdbcTemplate.batchUpdate(INSERT_INTO_AUTHORISE_PROJECT, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                String empId = users.get(i);
                ps.setString(1, projectId);
                ps.setString(2, empId);
                ps.setString(3, teamName);
            }

            @Override
            public int getBatchSize() {
                return users.size();
            }
        });
    }

    @Override
    public List<String> getSelectedEmployees(String teamName, String projectId) {
        return jdbcTemplate.queryForList(GET_SELECTED_EMPLOYEES, String.class,new Object[]{teamName,projectId});
    }

    @Override
    public void deleteProjectUserPrivilege(final String projectId, final List<String> users, final String teamName) {
        jdbcTemplate.batchUpdate(DELETE_AUTHORIZE_PROJECT, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                String empId = users.get(i);
                ps.setString(1, teamName);
                ps.setString(2, empId);
                ps.setString(3, projectId);
            }

            @Override
            public int getBatchSize() {
                return users.size();
            }
        });
    }
}
