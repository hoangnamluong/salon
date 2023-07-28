package ou.lhn.salon.db.service.User_db;

import java.util.ArrayList;

import ou.lhn.salon.db.model.User;

public interface UserService {
    ArrayList<User> getAllUsers();
    User getUserById(int id);
    boolean addUser(User user);
    boolean updateUser(User user);
    boolean deleteUser(int id);
    boolean softDeleteUser(int id);
    User isUserAMember(String phone);
}
