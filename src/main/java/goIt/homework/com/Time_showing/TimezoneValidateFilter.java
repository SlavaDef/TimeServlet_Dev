package goIt.homework.com.Time_showing;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneId;

@WebFilter(value = "/time")
public class TimezoneValidateFilter extends HttpFilter {

    protected void doFilter(HttpServletRequest req,
                            HttpServletResponse res,
                            FilterChain chain) throws IOException, ServletException {

        String timezone = req.getParameter("timezone");

        if (timezone == null) {
            chain.doFilter(req, res);
        } else {
            String tz = timezone.replace(" ", "+");
            boolean is = ZoneId.getAvailableZoneIds().contains(tz);
            if (is) {
                chain.doFilter(req, res);
            } else {
                res.setStatus(400);
                res.getWriter().write("Invalid timezone");
                res.getWriter().close();
            }
        }
    }
}