package com.charnomic.jcharnomic;

import com.charnomic.jcharnomic.annotation.ServiceMethod;
import com.charnomic.jcharnomic.db.*;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by harryculpan on 3/30/17.
 */
public class WebDataService {

    CharnomicDAO charnomicDAO = new CharnomicDAO();

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
        List<Proposal> proposals = getCharnomicDAO().retrieveProposal(null);
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

    public Player getUserFromCookies(HttpServletRequest request) {
        Player result = null;
        String uuid = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("uuid")) {
                    uuid = cookie.getValue();
                }
            }
        }

        if (uuid != null && uuid.trim().length() > 0) {
            result = getCharnomicDAO().getPlayerByUuid(uuid);
            if (result.getPasswordExpired()) {
                result = null;
            }
        }

        return result;
    }

    public void addUserToParams(HttpServletRequest request, Map<String, Object> params) {
        Player player = getUserFromCookies(request);
        if (player != null) {
            params.put("activeplayer", player);
        }
    }

    public CharnomicDAO getCharnomicDAO() {
        return charnomicDAO;
    }

    public void setCharnomicDAO(CharnomicDAO charnomicDAO) {
        this.charnomicDAO = charnomicDAO;
    }
}
