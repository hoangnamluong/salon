package ou.lhn.salon.db.service.User_db;

import java.util.ArrayList;

import ou.lhn.salon.db.model.User;

public interface UserService {
    ArrayList<User> getAllUsers();
    User getUserById(int userId);
    ArrayList<User> getUserListByName(String name);
    User getUserByUsername (String username);
    boolean addUser(User user);
    boolean updateUser(User user);
    boolean deleteUser(int id);
    boolean softDeleteUser(int id);
    User isUserAMember(String phone);
}
