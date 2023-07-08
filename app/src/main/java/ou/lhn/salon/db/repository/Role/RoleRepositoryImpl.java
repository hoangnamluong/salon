package ou.lhn.salon.db.repository.Role;

import java.util.ArrayList;

import ou.lhn.salon.db.DatabaseHelper;
import ou.lhn.salon.db.model.Role;

public class RoleRepositoryImpl implements RoleRepository {
    private static RoleRepositoryImpl INSTANCE;
    private final DatabaseHelper databaseHelper = DatabaseHelper.getInstance();

    private RoleRepositoryImpl() {
    }

    public static RoleRepositoryImpl getInstance() {
        if(INSTANCE == null) {
            synchronized (RoleRepositoryImpl.class) {
                if(INSTANCE == null)
                    INSTANCE = new RoleRepositoryImpl();
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
