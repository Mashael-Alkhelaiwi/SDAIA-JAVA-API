package controller;

import jakarta.ws.rs.*;
import org.example.dao.EmployeeDAO;
import org.example.models.Employee;

import java.util.ArrayList;

@Path("/employees")
public class EmployeeController
{
    EmployeeDAO employeeDAO = new EmployeeDAO();

    @GET
    public ArrayList<Employee> getAllEmployees() {
        try {
            return employeeDAO.selectAllEmployees();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GET
    @Path("{employeeId}")
    public Employee getEmployee(@PathParam("employeeId") int employeeId) {
        try {
            return employeeDAO.selectEmployee(employeeId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DELETE
    @Path("{employeeId}")
    public void deleteEmployee(@PathParam("employeeId") int employeeId) {
        try {
            employeeDAO.deleteEmployee(employeeId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @POST
    public void insertEmployee(Employee employee) {
        try {
            employeeDAO.insertEmployee(employee);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PUT
    @Path("{employeeId}")
    public void updateEmployee(@PathParam("employeeId") int employeeId, Employee employee) {
        try {
            employee.setEmployee_id(employeeId);
            employeeDAO.updateEmployee(employee);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
