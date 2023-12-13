package filter;

import model.User;
import security.AuthenticatedRequest;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/room/*")
public class webFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String username = request.getParameter("username");
        String avatar = request.getParameter("avatar");

        /*HttpSession session = request.getSession();
        session.setAttribute("username",username);
        session.setAttribute("avatar",avatar);*/
        User user = new User();
        user.setAvatar(avatar);
        user.setUsername(username);

        filterChain.doFilter(new AuthenticatedRequest(request,username,avatar,user),servletResponse);
    }

    @Override
    public void destroy() {

    }
}
