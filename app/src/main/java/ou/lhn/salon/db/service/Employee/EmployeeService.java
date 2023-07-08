package ou.lhn.salon.db.service.Employee;

import java.util.ArrayList;

import ou.lhn.salon.db.model.Employee;

public interface EmployeeService {
    ArrayList<Employee> getAllEmployees();
    ArrayList<Employee> getEmployeesByBranchId(int branchId);
    Employee getEmployeeById(int id);
    boolean addEmployee(Employee employee);
    boolean updateEmployee(Employee employee);
    boolean deleteEmployee(int id);
    boolean softDeleteEmployee(int id);
}
