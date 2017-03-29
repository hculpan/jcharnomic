package com.charnomic.jcharnomic;

import com.charnomic.jcharnomic.db.CharnomicDAO;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by harry on 3/28/17.
 */
public class LoginHandler extends AbstractHandler {
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) throws IOException,
            ServletException {
        if (request.getMethod().equalsIgnoreCase("post")) {
            if (target.equals("/authenticate")) {
                CharnomicDAO charnomicDAO = new CharnomicDAO();
                if (charnomicDAO.checkPassword(request.getParameter("email"), request.getParameter("password"))) {
                    request.getSession().setAttribute("user", "validated");
                    response.sendRedirect("/home");
                } else {
                    try {
                        Template template = WebGetHandler.configuration.getTemplate("login.html");
                        Map<String, Object> params = new HashMap<>();
                        params.put("message", "Invalid email or password");
                        template.process(params, response.getWriter());
                    } catch (TemplateException e) {
                        e.printStackTrace();
                    }
                }

                baseRequest.setHandled(true);
            }
        }
    }
}
