package ou.lhn.salon.db.repository.Auth;

import ou.lhn.salon.db.model.User;

public interface AuthRepository {
    boolean registerUser(User user);
    boolean loginUser(String username, String password);
}
