package ou.lhn.salon.db.repository.Employee;

import java.util.ArrayList;

import ou.lhn.salon.db.DatabaseHelper;
import ou.lhn.salon.db.model.Employee;

public class EmployeeRepositoryImpl implements EmployeeRepository {
    private static EmployeeRepositoryImpl INSTANCE;
    private final DatabaseHelper databaseHelper = DatabaseHelper.getInstance();

    private EmployeeRepositoryImpl() {
    }

    public static EmployeeRepositoryImpl getInstance() {
        if(INSTANCE == null) {
            synchronized (EmployeeRepositoryImpl.class) {
                if(INSTANCE == null)
                    INSTANCE = new EmployeeRepositoryImpl();
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
