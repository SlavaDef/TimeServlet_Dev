package goIt.homework.com.Time_showing;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.TimeZone;

@WebFilter(value = "/time")
public class TimezoneValidateFilter extends HttpFilter {

    private TimeZone time;

    protected void doFilter(HttpServletRequest req,
                            HttpServletResponse res,
                            FilterChain chain) throws IOException, ServletException {

        if (!req.getParameterMap().containsKey("timezone")) {
            chain.doFilter(req, res);
            return;
        }
        if ((!time.getID().equals("null") || (!time.getID().equals("")))) {
            chain.doFilter(req, res);

        } else {
            res.setStatus(400);
            res.getWriter().write("Invalid timezone");
            res.getWriter().close();
        }
    }
}
