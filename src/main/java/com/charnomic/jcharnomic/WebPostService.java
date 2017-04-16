package com.charnomic.jcharnomic;

import com.charnomic.jcharnomic.annotation.ServiceMethod;
import com.charnomic.jcharnomic.db.CharnomicDAO;
import com.charnomic.jcharnomic.db.Player;
import org.eclipse.jetty.server.Request;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * Created by harry on 3/31/17.
 */
public class WebPostService extends BaseService {
    @ServiceMethod( targetPath = "/new_monitor_proposal.html")
    public Boolean newMonitorProposal(HttpServletRequest request, HttpServletResponse response) {
        int propNum = Integer.parseInt(request.getParameter("propnum"));
        String name = request.getParameter("propname");
        String text = request.getParameter("proptext");
        int playerId = Integer.parseInt(request.getParameter("propplayerid"));

        Player p = getCharnomicDAO().getPlayerById(playerId);
        if (text == null || text.isEmpty()) {
            sendMessage(response, request,
                    "Illegal Proposal",
                    "Text cannot be blank",
                    "/new_monitor_proposal.html",
                    "New Proposal");
            return true;
        }

        getCharnomicDAO().createProposal(propNum, name, text, p);
        sendMessage(response, request,
                "Proposal Added",
                "The proposal has been added",
                "/home.html",
                "Home");

        return true;
    }

    @ServiceMethod( targetPath = "/new_proposal_start.html")
    public Boolean newProposalStart(HttpServletRequest request, HttpServletResponse response) {
        String proptype = request.getParameter("proptype");
        try {
            if (proptype.equals("new")) {
                response.sendRedirect("/new_proposal_new.html");
            } else if (proptype.equals("amend")) {
                    response.sendRedirect("/new_proposal_amend.html");
            } else if (proptype.equals("repeal")) {
                response.sendRedirect("/new_proposal_repeal.html");
            }
        } catch (IOException e) {
            sendMessage(response, request,
                    "Unable to Continue",
                    "We were unable to process this request.  Please contact the system administrator.",
                    "/home.html",
                    "Home");
        }
        return true;
    }

    @ServiceMethod( targetPath = "/update_username.html")
    public Boolean updateUsername(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("username");
        String newEmail = request.getParameter("newusername");

        if (newEmail == null || newEmail.isEmpty()) {
            sendMessage(response, request,
                    "Illegal Email/Username",
                    "New email/username cannot be blank",
                    "/update_username.html",
                    "Change Email/Username");
            return true;
        }

        Player p = getCharnomicDAO().getPlayerByEmail(email);
        if (p == null) {
            sendMessage(response, request,
                    "Username not found",
                    "The original username is not found",
                    "/update_username.html",
                    "Change Email/Username");
            return true;
        }

        Player p2 = getCharnomicDAO().getPlayerByEmail(newEmail);
        if (p2 != null) {
            sendMessage(response, request,
                    "Username Taken",
                    "The new username is already in use",
                    "/update_username.html",
                    "Change Email/Username");
            return true;
        }

        p.setEmail(newEmail);
        getCharnomicDAO().updatePlayerData(p, Player.updateablefields.email);
        sendMessage(response, request,
                "Username/Email Updated",
                "Your username and email has been updated",
                "/home.html",
                "Home");

        return true;
    }

    @ServiceMethod( targetPath = "/update_password.html")
    public Boolean updatePassword(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("username");
        String p1 = request.getParameter("newpassword1");
        String p2 = request.getParameter("newpassword2");

        if (p1 == null || p1.trim().length() == 0) {
            sendMessage(response, request,
                    "Illegal Password",
                    "New password cannot be blank",
                    "/update_password.html",
                    "Change Password");
        } else if (!p1.equals(p2)) {
            sendMessage(response, request,
                    "Password Entries don't Match",
                    "New passwords do not match",
                    "/update_password.html",
                    "Change Password");
        } else if (!getCharnomicDAO().checkPassword(email, request.getParameter("password"))) {
            sendMessage(response, request,
                    "Login Failure",
                    "Email or password is not valid",
                    "/update_password.html",
                    "Change Password");
        } else {
            try {
                getCharnomicDAO().updatePassword(email, p1);
                Player player = getCharnomicDAO().getPlayerByEmail(email);
                newLoginForPlayer(request, response, player);
            } catch (SQLException | IOException e) {
                e.printStackTrace();
                sendMessage(response, request,
                        "Login Failure",
                        "Unable to change password: " + e.getLocalizedMessage() +
                                "<br><div class='center-text'>Please contact administrator</div>",
                        "/update_password.html",
                        "Change Password");
            }
        }

        return true;
    }

    @ServiceMethod( targetPath = "/authenticate.html")
    public Boolean authenticate(HttpServletRequest request, HttpServletResponse response) {
        CharnomicDAO charnomicDAO = new CharnomicDAO();
        if (charnomicDAO.checkPassword(request.getParameter("username"), request.getParameter("password"))) {
            Player player = charnomicDAO.getPlayerByEmail(request.getParameter("username"));

            try {
                if (player.getPasswordExpired()) {
                    response.sendRedirect("/update_password.html");
                } else {
                    newLoginForPlayer(request, response, player);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            sendMessage(response, request,
                    "Login Failure",
                    "Invalid email or password",
                    "/login.html",
                    "Back to Login");
        }

        return true;
    }

    protected void newLoginForPlayer(HttpServletRequest request, HttpServletResponse response, Player player) throws IOException {
        Cookie cookie = new Cookie("uuid", UUID.randomUUID().toString());
        cookie.setMaxAge(Integer.MAX_VALUE);
        getCharnomicDAO().updatePlayerUuid(player, cookie.getValue(), request.getHeader("User-Agent"));
        response.addCookie(cookie);
        response.sendRedirect("/home");
    }

    @ServiceMethod( targetPath = "/add_points.html")
    public Boolean changeAddPoints(HttpServletRequest request, HttpServletResponse response) {
        List<Player> players = getCharnomicDAO().retrievePlayers();
        for (Player player : players) {
            Integer points = 0;
            try {
                player.setPoints(player.getPoints() + Integer.parseInt(request.getParameter("p_" + player.getId())));
                getCharnomicDAO().updatePlayerData(player, Player.updateablefields.points);
            } catch (NumberFormatException e) {
                // Nothing to do here, just don't persist
            }
        }
        sendMessage(response, request,
                "Points Updated",
                "Point totals updated!",
                "/home.html",
                "Home");

        return true;
    }

    @ServiceMethod( targetPath = "/active_player.html")
    public Boolean changeActivePlayer(HttpServletRequest request, HttpServletResponse response) {
        Integer newId = Integer.parseInt(request.getParameter("newactive"));
        Player p = getCharnomicDAO().getPlayerById(newId);
        getCharnomicDAO().setActivePlayer(p);

        sendMessage(response, request,
                "New Active Player",
                p.getFirstname() + " " + p.getLastname() + " is now the active player",
                "/home.html",
                "Home");

        return true;
    }

    public void submitData(String target,
                           Request baseRequest,
                           HttpServletRequest request,
                           HttpServletResponse response                           )
            throws InvocationTargetException, IllegalAccessException {
        for (Method method : getClass().getMethods()) {
            ServiceMethod serviceMethod = method.getDeclaredAnnotation(ServiceMethod.class);

            if (serviceMethod != null && !serviceMethod.targetPath().isEmpty() && serviceMethod.targetPath().equals(target)) {
                baseRequest.setHandled((Boolean)method.invoke(this, request, response));
                break;
            }
        }
    }

}
