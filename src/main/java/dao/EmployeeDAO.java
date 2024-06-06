package dao;

import org.example.models.Employee;

import java.sql.*;
import java.util.ArrayList;

public class EmployeeDAO
{

    private static final String URL = "jdbc:sqlite:C:\\Users\\dev\\Desktop\\Homework\\src\\main\\java\\day4\\hr.db";
    private static final String SELECT_ALL_Employee = "select * from employees";
    private static final String SELECT_ONE_Employee = "select * from employees where employee_id = ?";
    private static final String INSERT_Employee = "insert into employees values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_Employee = "update employees set salary = ?, manager_id = ?, department_id = ? where employee_id = ?";
    private static final String DELETE_Employee = "delete from employees where employee_id = ?";


    public ArrayList<Employee> selectAllEmployees() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement st = conn.prepareStatement(SELECT_ALL_Employee);

        ResultSet rs = st.executeQuery();
        ArrayList<Employee> employees = new ArrayList<>();

        while (rs.next()) {
            employees.add(new Employee(rs));
        }

        return employees;
    }

    public Employee selectEmployee(int id) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement st = conn.prepareStatement(SELECT_ONE_Employee);

        st.setInt(1, id);

        ResultSet rs = st.executeQuery();

        if(rs.next()) {
            return new Employee(rs);
        }
        else {
            return null;
        }

    }

    public void insertEmployee(Employee employee) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement st = conn.prepareStatement(INSERT_Employee);

        st.setInt(1, employee.getEmployee_id());
        st.setString(2, employee.getFirst_name());
        st.setString(3, employee.getLast_name());
        st.setString(4, employee.getEmail());
        st.setString(5, employee.getPhone_number());
        st.setString(6, employee.getHire_date());
        st.setInt(7, employee.getJob_id());
        st.setDouble(8, employee.getSalary());
        st.setInt(9, employee.getManager_id());
        st.setInt(10, employee.getDepartment_id());

        st.executeUpdate();
    }

    public void updateEmployee(Employee employee) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement st = conn.prepareStatement(UPDATE_Employee);

        st.setInt(4, employee.getEmployee_id());
        st.setDouble(1, employee.getSalary());
        st.setInt(2, employee.getManager_id());
        st.setInt(3, employee.getDepartment_id());

        st.executeUpdate();
    }

    public void deleteEmployee(int id) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement st = conn.prepareStatement(DELETE_Employee);

        st.setInt(1, id);
        st.executeUpdate();
    }


}
