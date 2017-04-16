package com.charnomic.jcharnomic;

import com.charnomic.jcharnomic.annotation.ServiceMethod;
import com.charnomic.jcharnomic.db.Judgment;
import com.charnomic.jcharnomic.db.Player;
import com.charnomic.jcharnomic.db.Proposal;
import com.charnomic.jcharnomic.db.Rule;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by harryculpan on 3/30/17.
 */
public class WebGetService extends BaseService {
    @ServiceMethod(targetPath = "/judgments.html")
    public void getDataForJudgments(Map<String, Object> params) {
        List<Judgment> judgments = getCharnomicDAO().retrieveJudgments();
        params.put("judgments", judgments);
    }

    @ServiceMethod(targetPath = "/home.html")
    public void getDataForHome(Map<String, Object> params) {
        List<Player> players = getCharnomicDAO().retrievePlayers();
        params.put("players", players);

        List<Rule> rules = getCharnomicDAO().retrieveRules();
        params.put("rules", rules);
    }

    @ServiceMethod(targetPath = "/proposals.html")
    public void getDataForProposals(Map<String, Object> params) {
        List<Proposal> proposals = getCharnomicDAO().retrieveProposal();
        params.put("proposals", proposals);
    }

    @ServiceMethod(targetPath = "/initial_rules.html")
    public void getDataForInitialRules(Map<String, Object> params) {
    }

    @ServiceMethod(targetPath = "/about.html")
    public void getDataForAbout(Map<String, Object> params) {
    }

    @ServiceMethod(targetPath = "/login.html")
    public void getDataForLogin(Map<String, Object> params) {
    }

    @ServiceMethod(targetPath = "/update_password.html")
    public void getDataForUpdatePassword(Map<String, Object> params) {
    }

    @ServiceMethod(targetPath = "/update_username.html")
    public void getDataForUpdateUsername(Map<String, Object> params) {
    }

    @ServiceMethod(targetPath = "/active_player.html")
    public void getDataForActivePlayer(Map<String, Object> params) {
        List<Player> players = getCharnomicDAO().retrievePlayers();
        params.put("players", players);
        params.put("movingplayer", getCharnomicDAO().getActivePlayer());
    }

    @ServiceMethod(targetPath = "/next_player.html")
    public void getDataForNextPlayer(Map<String, Object> params) {
        Player player = getCharnomicDAO().activateNextPlayer();
        params.put("movingplayer", player);
    }

    @ServiceMethod(targetPath = "/add_points.html")
    public void getDataForAddPoints(Map<String, Object> params) {
        List<Player> players = getCharnomicDAO().retrievePlayers();
        params.put("players", players);
    }

    @ServiceMethod(targetPath = "/new_monitor_proposal.html")
    public void getDataForNewMonitorProposal(Map<String, Object> params) {
        Integer nextNum = getCharnomicDAO().retrieveNextProposalNum();
        if (nextNum != null) {
            params.put("proposalnum", nextNum);
        }
        List<Player> players = getCharnomicDAO().retrievePlayers();
        params.put("players", players);
    }

    @ServiceMethod(targetPath = "/new_proposal_start.html")
    public void getDataForNewProposal(Map<String, Object> params) {
    }

    @ServiceMethod(targetPath = "/new_proposal_new.html")
    public void getDataForProposalNew(Map<String, Object> params) {
        Integer nextNum = getCharnomicDAO().retrieveNextProposalNum();
        if (nextNum != null) {
            params.put("proposalnum", nextNum);
        }
    }

    @ServiceMethod(targetPath = "/new_proposal_amend.html")
    public void getDataForProposalAmend(Map<String, Object> params) {
    }

    @ServiceMethod(targetPath = "/new_proposal_repeal.html")
    public void getDataForProposalRepeal(Map<String, Object> params) {
    }

    public Boolean logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie("uuid", UUID.randomUUID().toString());
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        sendMessage(response, request,
                "Logged out of Charnomic",
                "<p>You have been logged out of the Charnomic web application.</p>" +
                        "<p class='center-text'>You may continue to view information about the game.</p>",
                "/home.html",
                "Home", false);

        return true;
    }

    public Map<String, Object> retrieveData(String target, HttpServletRequest request)
            throws InvocationTargetException, IllegalAccessException {
        Map<String, Object> result = null;
        for (Method method : getClass().getMethods()) {
            ServiceMethod serviceMethod = method.getDeclaredAnnotation(ServiceMethod.class);

            if (serviceMethod != null && !serviceMethod.targetPath().isEmpty() && serviceMethod.targetPath().equals(target)) {
                result = new HashMap<>();
                addUserToParams(request, result);
                method.invoke(this, result);
                break;
            }
        }

        return result;
    }
}
