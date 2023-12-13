package security;

import model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.nio.file.attribute.UserPrincipal;
import java.security.Principal;

public class AuthenticatedRequest extends HttpServletRequestWrapper {

    private String username;
    private String avatar;

    private User user;

    public AuthenticatedRequest(HttpServletRequest request, String username, String avatar, User user) {
        super(request);

        this.username = username;
        this.avatar = avatar;
        this.user = user;
    }

    @Override
    public Principal getUserPrincipal() {
        return () -> user.toString();
    }
}