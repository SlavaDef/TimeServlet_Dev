package goIt.homework.com.Time_showing;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {
    private String date;
    private TemplateEngine engine;

    @Override
    public void init() {
        engine = new TemplateEngine();

        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setPrefix("C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\bin\\templates/");
        // resolver.setPrefix("./homework");
        // resolver.setPrefix(getClass().getClassLoader().getResource("templates").getPath());
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setOrder(engine.getTemplateResolvers().size());
        resolver.setCacheable(false);
        engine.addTemplateResolver(resolver);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=utf-8");
        Cookie[] lastTimezone = req.getCookies();

        String timezone = req.getParameterMap().containsKey("timezone")
                ? req.getParameter("timezone").replace(" ", "+")
                : lastTimezone[0].getValue();

        if (req.getCookies() == null) {
            date = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss 'UTC'").format(new Date());
        }
        if (!req.getParameterMap().containsKey("timezone") & req.getCookies() != null) {
            date = DateTimeFormatter.ofPattern(" yyyy-MM-dd HH:mm:ss ")
                    .format(LocalDateTime.now(ZoneId.of(timezone))) + "" + timezone;
        }

        if (req.getParameterMap().containsKey("timezone")) {
            resp.addCookie(new Cookie("timezone", timezone));
            date = DateTimeFormatter.ofPattern(" yyyy-MM-dd HH:mm:ss ")
                    .format(LocalDateTime.now(ZoneId.of(timezone))) + "" + timezone;
        }

        Context simpleContext = new Context(
                req.getLocale(),
                Map.of("params", date)
        );

        engine.process("homework", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}


