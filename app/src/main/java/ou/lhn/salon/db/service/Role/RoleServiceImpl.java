package ou.lhn.salon.db.service.Role;

import java.util.ArrayList;

import ou.lhn.salon.db.DatabaseHelper;
import ou.lhn.salon.db.model.Role;

public class RoleServiceImpl implements RoleService {
    private static RoleServiceImpl INSTANCE;
    private final DatabaseHelper databaseHelper = DatabaseHelper.getInstance();

    private RoleServiceImpl() {
    }

    public static RoleServiceImpl getInstance() {
        if(INSTANCE == null) {
            synchronized (RoleServiceImpl.class) {
                if(INSTANCE == null)
                    INSTANCE = new RoleServiceImpl();
            }
        }

        return INSTANCE;
    }

    @Override
    public ArrayList<Role> getAllRoles() {
        return null;
    }

    @Override
    public Role getRoleById(int id) {
        return null;
    }

    @Override
    public boolean addRole(Role role) {
        return false;
    }

    @Override
    public boolean updateRole(Role role) {
        return false;
    }

    @Override
    public boolean deleteRole(int id) {
        return false;
    }

    @Override
    public boolean softDeleteRole(int id) {
        return false;
    }
}
