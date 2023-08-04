package goIt.homework.com.Time_showing;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

//@WebServlet(value = "/time")
public class HomeWorkServlet extends HttpServlet {

    private TemplateEngine engine;

    @Override
    public void init() throws ServletException {
        engine = new TemplateEngine();

        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setPrefix("C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\bin\\templates/");
       // resolver.setPrefix(getClass().getClassLoader().getResource("templates").getPath());
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setOrder(engine.getTemplateResolvers().size());
        resolver.setCacheable(false);
        engine.addTemplateResolver(resolver);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IOException {
        Map<String, Object> respMap = new LinkedHashMap<>();

        HttpSession session = req.getSession(true);

        String timezone = URLEncoder.encode(req.getParameter("timezone"), StandardCharsets.UTF_8);

        String lastTimezone = null;
        if(req.getCookies()!=null) {
            for (Cookie cookie : req.getCookies()) {
                if (cookie.getName().equals("lastReq")) {
                    lastTimezone = cookie.getValue();
                }
            }
        }
        session.setAttribute("lastTimeZone",lastTimezone);

        resp.addCookie(new Cookie("lastReq", timezone));
        respMap.put("timeZone", DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now()));


            resp.setContentType("text/html");
            respMap.put("calcTimeZone", DateTimeFormatter.ISO_DATE_TIME
                    .format(
                            LocalDateTime.now(ZoneId.of(timezone))
                    ));
            resp.addCookie(new Cookie("lastResp", (String) respMap.get("calcTimeZone")));
            session.setAttribute("calcTimeZone",(String) respMap.get("calcTimeZone"));

        Context simpleContext = new Context(
                req.getLocale(),
                respMap
        );
        engine.process("homework2", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}
