package goIt.homework.com.Time_showing;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String timezone = req.getParameter("timezone");
        resp.setContentType("text/html; charset=utf-8");

        String date = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ").format(new Date());

        resp.getWriter().write(date);
        resp.getWriter()
                .write("${timezone}"
                        .replace("${timezone}", parseName(req)).replace(" ","+"));
        resp.getWriter().close();
    }

    private String parseName(HttpServletRequest req) { // парс метод щоб не викидало помилку якщо не внисимо значень
        if (req.getParameterMap().containsKey("timezone")) {
            return req.getParameter("timezone");
        }
        return "UTC";
    }
}
