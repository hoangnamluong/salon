package ou.lhn.salon.db.repository.Role;

import java.util.ArrayList;

import ou.lhn.salon.db.model.Role;

public interface RoleRepository {
    ArrayList<Role> getAllRoles();
    Role getRoleById(int id);
    boolean addRole(Role role);
    boolean updateRole(Role role);
    boolean deleteRole(int id);
    boolean softDeleteRole(int id);
}
