package com.charnomic.jcharnomic;

import com.charnomic.jcharnomic.db.CharnomicDAO;
import com.charnomic.jcharnomic.db.Player;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by harry on 3/28/17.
 */
public class WebPostHandler extends AbstractHandler {
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) throws IOException,
            ServletException {
        if (request.getMethod().equalsIgnoreCase("post")) {
            if (target.equals("/active_player.html")) {
                CharnomicDAO charnomicDAO = new CharnomicDAO();
                Integer newId = Integer.parseInt(request.getParameter("newactive"));
                Player p = charnomicDAO.getPlayerById(newId);
                charnomicDAO.setActivePlayer(p);
                baseRequest.setHandled(true);
                try {
                    sendMessage(response, request,
                            "New Active Player",
                            p.getFirstname() + " " + p.getLastname() + " is now the active player",
                            "/home.html",
                            "Home");
                } catch (TemplateException e) {
                    e.printStackTrace();
                }
            } else if (target.equals("/add_points.html")) {
                CharnomicDAO charnomicDAO = new CharnomicDAO();
                List<Player> players = charnomicDAO.retrievePlayers();
                for (Player player : players) {
                    Integer points = 0;
                    try {
                        player.setPoints(player.getPoints() + Integer.parseInt(request.getParameter("p_" + player.getId())));
                        charnomicDAO.updatePlayerData(player, Player.updateablefields.points);
                    } catch (NumberFormatException e) {
                        // Nothing to do here, just don't persist
                    }
                }
                baseRequest.setHandled(true);
                try {
                    sendMessage(response, request,
                            "Points Updated",
                            "Point totals updated!",
                            "/home.html",
                            "Home");
                } catch (TemplateException e) {
                    e.printStackTrace();
                }
            } else if (target.equals("/authenticate.html")) {
                CharnomicDAO charnomicDAO = new CharnomicDAO();
                if (charnomicDAO.checkPassword(request.getParameter("username"), request.getParameter("password"))) {
                    Player player = charnomicDAO.getPlayerByEmail(request.getParameter("username"));

                    if (player.getPasswordExpired()) {
                        response.sendRedirect("/update_password.html");
                    } else {
                        newLoginForPlayer(response, player);
                    }
                } else {
                    try {
                        sendMessage(response, request,
                                "Login Failure",
                                "Invalid email or password",
                                "/login.html",
                                "Back to Login");
                    } catch (TemplateException e) {
                        e.printStackTrace();
                    }
                }

                baseRequest.setHandled(true);
            } else if (target.equals("/update_password.html")) {
                CharnomicDAO charnomicDAO = new CharnomicDAO();

                String email = request.getParameter("username");
                String p1 = request.getParameter("newpassword1");
                String p2 = request.getParameter("newpassword2");

                if (p1 == null || p1.trim().length() == 0) {
                    try {
                        sendMessage(response, request,
                                "Illegal Password",
                                "New password cannot be blank",
                                "/update_password.html",
                                "Change Password");
                    } catch (TemplateException e) {
                        e.printStackTrace();
                    }
                } else if (!p1.equals(p2)) {
                    try {
                        sendMessage(response, request,
                                "Password Entries don't Match",
                                "New passwords do not match",
                                "/update_password.html",
                                "Change Password");
                    } catch (TemplateException e) {
                        e.printStackTrace();
                    }
                } else if (!charnomicDAO.checkPassword(email, request.getParameter("password"))) {
                    try {
                        sendMessage(response, request,
                                "Login Failure",
                                "Email or password is not valid",
                                "/update_password.html",
                                "Change Password");
                    } catch (TemplateException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        charnomicDAO.updatePassword(email, p1);
                        Player player = charnomicDAO.getPlayerByEmail(email);
                        newLoginForPlayer(response, player);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        try {
                            sendMessage(response, request,
                                    "Login Failure",
                                    "Unable to change password: " + e.getLocalizedMessage() +
                                    "<br><div class='center-text'>Please contact administrator</div>",
                                    "/update_password.html",
                                    "Change Password");
                        } catch (TemplateException te) {
                            te.printStackTrace();
                        }
                    }
                }
                baseRequest.setHandled(true);
            }
        } else if (target.equals("/logout.html")) {
            Cookie cookie = new Cookie("uuid", UUID.randomUUID().toString());
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            baseRequest.setHandled(true);
            try {
                sendMessage(response, request,
                        "Logged out of Charnomic",
                        "<p>You have been logged out of the Charnomic web application.</p>" +
                        "<p class='center-text'>You may continue to view information about the game.</p>",
                        "/home.html",
                        "Home", false);
            } catch (TemplateException te) {
                te.printStackTrace();
            }
        }
    }

    protected void newLoginForPlayer(HttpServletResponse response, Player player) throws IOException {
        CharnomicDAO charnomicDAO = new CharnomicDAO();

        Cookie cookie = new Cookie("uuid", UUID.randomUUID().toString());
        cookie.setMaxAge(Integer.MAX_VALUE);
        charnomicDAO.updatePlayerUuid(player, cookie.getValue());
        response.addCookie(cookie);
        response.sendRedirect("/home");
    }

    protected void sendMessage(HttpServletResponse response, HttpServletRequest request,
                               String header, String message, String destination, String destinationName)
            throws IOException, TemplateException {
        sendMessage(response, request, header, message, destination, destinationName, true);
    }

    protected void sendMessage(HttpServletResponse response, HttpServletRequest request,
                               String header, String message, String destination, String destinationName, Boolean addPlayer)
            throws IOException, TemplateException {
        Template template = WebGetHandler.configuration.getTemplate("message.html");
        Map<String, Object> params = new HashMap<>();
        params.put("messageheader", header);
        params.put("message", message);
        params.put("messageurl", destination);
        params.put("messageurlname", destinationName);
        if (addPlayer) {
            WebGetService webGetService = new WebGetService();
            webGetService.addUserToParams(request, params);
        }
        template.process(params, response.getWriter());
    }

}
