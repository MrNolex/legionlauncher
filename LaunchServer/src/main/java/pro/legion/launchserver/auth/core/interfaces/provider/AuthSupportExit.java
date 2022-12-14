package pro.legion.launchserver.auth.core.interfaces.provider;

import pro.legion.launchserver.auth.core.User;
import pro.legion.launchserver.auth.core.UserSession;

public interface AuthSupportExit extends AuthSupport {
    boolean deleteSession(UserSession session);

    boolean exitUser(User user);
}
