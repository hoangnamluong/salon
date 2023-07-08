package ou.lhn.salon.db.service.Employee;

import java.util.ArrayList;

import ou.lhn.salon.db.DatabaseHelper;
import ou.lhn.salon.db.model.Employee;
import ou.lhn.salon.db.repository.Employee.EmployeeRepository;
import ou.lhn.salon.db.repository.Employee.EmployeeRepositoryImpl;

public class EmployeeServiceImpl implements EmployeeService {
    private static EmployeeServiceImpl INSTANCE;
    private final EmployeeRepository employeeRepository = EmployeeRepositoryImpl.getInstance();

    private EmployeeServiceImpl() {
    }

    public static EmployeeServiceImpl getInstance() {
        if(INSTANCE == null) {
            synchronized (EmployeeServiceImpl.class) {
                if(INSTANCE == null)
                    INSTANCE = new EmployeeServiceImpl();
            }
        }

        return INSTANCE;
    }

    @Override
    public ArrayList<Employee> getAllEmployees() {
        return null;
    }

    @Override
    public ArrayList<Employee> getEmployeesByBranchId(int branchId) {
        return null;
    }

    @Override
    public Employee getEmployeeById(int id) {
        return null;
    }

    @Override
    public boolean addEmployee(Employee employee) {
        return false;
    }

    @Override
    public boolean updateEmployee(Employee employee) {
        return false;
    }

    @Override
    public boolean deleteEmployee(int id) {
        return false;
    }

    @Override
    public boolean softDeleteEmployee(int id) {
        return false;
    }
}
