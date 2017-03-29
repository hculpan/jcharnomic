package com.charnomic.jcharnomic;

import com.charnomic.jcharnomic.db.CharnomicDAO;
import com.charnomic.jcharnomic.db.Player;
import com.charnomic.jcharnomic.db.Rule;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by harry on 3/28/17.
 */
public class LoginHandler extends AbstractHandler {
    protected void newLoginForPlayer(HttpServletResponse response, Player player) throws IOException {
        CharnomicDAO charnomicDAO = new CharnomicDAO();

        Cookie cookie = new Cookie("uuid", UUID.randomUUID().toString());
        cookie.setMaxAge(Integer.MAX_VALUE);
        charnomicDAO.updatePlayerUuid(player, cookie.getValue());
        response.addCookie(cookie);
        response.sendRedirect("/home");
    }

    protected void sendMessage(HttpServletResponse response,
                               String header, String message, String destination, String destinationName)
            throws IOException, TemplateException {
        Template template = WebGetHandler.configuration.getTemplate("message.html");
        Map<String, Object> params = new HashMap<>();
        params.put("messageheader", header);
        params.put("message", message);
        params.put("messageurl", destination);
        params.put("messageurlname", destinationName);
        template.process(params, response.getWriter());
    }

    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) throws IOException,
            ServletException {
        if (request.getMethod().equalsIgnoreCase("post")) {
            if (target.equals("/authenticate")) {
                CharnomicDAO charnomicDAO = new CharnomicDAO();
                if (charnomicDAO.checkPassword(request.getParameter("email"), request.getParameter("password"))) {
                    Player player = charnomicDAO.getPlayerByEmail(request.getParameter("email"));

                    if (player.getPasswordExpired()) {
                        response.sendRedirect("/update_password");
                    } else {
                        newLoginForPlayer(response, player);
                    }
                } else {
                    try {
                        sendMessage(response,
                                "Login Failure",
                                "Invalid email or password",
                                "/login",
                                "Back to Login");
                    } catch (TemplateException e) {
                        e.printStackTrace();
                    }
                }

                baseRequest.setHandled(true);
            } else if (target.equals("/update_password")) {
                String email = request.getParameter("email");
                String p1 = request.getParameter("newpassword1");
                String p2 = request.getParameter("newpassword2");

                if (p1 == null || p1.trim().length() == 0) {
                    try {
                        sendMessage(response,
                                "Illegal Password",
                                "New password cannot be blank",
                                "/update_password",
                                "Change Password");
                    } catch (TemplateException e) {
                        e.printStackTrace();
                    }
                } else if (!p1.equals(p2)) {
                    try {
                        sendMessage(response,
                                "Password Entries don't Match",
                                "New passwords do not match",
                                "/update_password",
                                "Change Password");
                    } catch (TemplateException e) {
                        e.printStackTrace();
                    }
                }
                baseRequest.setHandled(true);
            }
        }
    }
}
