package goIt.homework.com.Time_showing;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.ZoneId;

@WebFilter(value = "/time")
public class TimezoneValidateFilter extends HttpFilter {

    protected void doFilter(HttpServletRequest req,
                            HttpServletResponse res,
                            FilterChain chain) throws IOException, ServletException {

        String timezone = req.getParameter("timezone");

        if (!req.getParameterMap().containsKey("timezone") & req.getCookies() == null) {
            res.addCookie(new Cookie("timezone", "UTC"));

            chain.doFilter(req, res);
        }
        if (timezone == null) {
            chain.doFilter(req, res);
        } else {
            try {
                ZoneId.of(timezone.replace(" ", "+"));
                chain.doFilter(req, res);
            } catch (Exception e) {
                res.setStatus(400);
                res.getWriter().write("Invalid timezone");
                res.getWriter().close();
            }
        }
    }
}