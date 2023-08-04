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
import java.util.LinkedHashMap;
import java.util.Map;

//@WebServlet(value = "/time")
public class TimeServlet_3 extends HttpServlet {

    private String lastTimezone = null;
    private String timezone;

    private TemplateEngine engine;

    @Override
    public void init() {
        engine = new TemplateEngine();

        FileTemplateResolver resolver = new FileTemplateResolver();
        // String templates = getClass().getClassLoader().getResource("templates").getPath();
        resolver.setPrefix("C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\bin\\templates/");
        // resolver.setPrefix("./homework");
        // resolver.setPrefix(templates);
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setOrder(engine.getTemplateResolvers().size());
        resolver.setCacheable(false);
        engine.addTemplateResolver(resolver);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Map<String, Object> respMap = new LinkedHashMap<>();
        resp.setContentType("text/html; charset=utf-8");

        timezone = req.getParameter("timezone").replace(" ", "+");

        if(req.getCookies()!=null) {
            for (Cookie cookie : req.getCookies()) {
                if (cookie.getName().equals("lastTimezone")) {
                    lastTimezone = cookie.getValue();
                }
            }
        }

       // String date = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss 'UTC' ").format(new Date());


         respMap.put("lastTimezone",DateTimeFormatter.ofPattern(" yyyy-MM-dd HH:mm:ss ")
                 .format(LocalDateTime.now(ZoneId.of(lastTimezone))) + "" + lastTimezone);


    /*    if (lastTimezone != null) {
            date = DateTimeFormatter.ofPattern(" yyyy-MM-dd HH:mm:ss ")
                    .format(LocalDateTime.now(ZoneId.of(lastTimezone))) + "" + lastTimezone;
        }

        if (req.getParameterMap().containsKey("timezone")) {
            timezone = req.getParameter("timezone").replace(" ", "+");
            //date = DateTimeFormatter.ofPattern(" yyyy-MM-dd HH:mm:ss ")
                  //  .format(LocalDateTime.now(ZoneId.of(timezone))) + "" + timezone;

*/
        respMap.put("timeZone", DateTimeFormatter.ofPattern(" yyyy-MM-dd HH:mm:ss ")
                .format(LocalDateTime.now(ZoneId.of(timezone))) + "" + timezone);
        resp.addCookie(new Cookie("lastTimezone", timezone));

        respMap.put("timezone", timezone);
        Context simpleContext = new Context(
                req.getLocale(),
                respMap
        );

        engine.process("homework2", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}


