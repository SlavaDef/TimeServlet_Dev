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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {

    private TemplateEngine engine;

    @Override
    public void init() {
        engine = new TemplateEngine();

        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setPrefix("C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\bin\\templates/");
        // resolver.setPrefix("./homework");
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

        String timezone =
                req.getParameterMap().containsKey("timezone")
                        ? req.getParameter("timezone").replace(" ", "+")
                        : lastTimezone[0].getValue();


        resp.addCookie(new Cookie("timezone", timezone.replace(" ", "+")));

        // String timeDate = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ")
        // .format(new Date()) +""+ parseTime(req);

        String timeDate = DateTimeFormatter.ofPattern(" yyyy-MM-dd HH:mm:ss ")
                .format(LocalDateTime.now(ZoneId.of(timezone))) + " " + parseTime(req);


        Context simpleContext = new Context(
                req.getLocale(),
                Map.of("params", timeDate)
        );


        engine.process("homework", simpleContext, resp.getWriter());

        resp.getWriter().close();
    }

    private String parseTime(HttpServletRequest req) {
        if (req.getParameterMap().containsKey("timezone")) {
            return req.getParameter("timezone").replace(" ", "+");
        }
        return "UTC";
    }

}
