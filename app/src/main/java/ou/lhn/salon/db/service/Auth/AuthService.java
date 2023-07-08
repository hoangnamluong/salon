package ou.lhn.salon.db.service.Auth;

import ou.lhn.salon.db.model.User;

public interface AuthService {
    boolean registerUser(User user);
    boolean loginUser(String username, String password);
}
