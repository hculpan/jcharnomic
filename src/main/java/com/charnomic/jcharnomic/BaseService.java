package com.charnomic.jcharnomic;

import com.charnomic.jcharnomic.annotation.ServiceMethod;
import com.charnomic.jcharnomic.db.CharnomicDAO;
import com.charnomic.jcharnomic.db.Player;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by harry on 3/31/17.
 */
public class BaseService {

    CharnomicDAO charnomicDAO = new CharnomicDAO();

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
            if (result != null && result.getPasswordExpired()) {
                result = null;
            }
        }

        return result;
    }

    public Player addUserToParams(HttpServletRequest request, Map<String, Object> params) {
        Player player = getUserFromCookies(request);
        if (player != null) {
            params.put("activeplayer", player);
        }

        Integer turn = getCharnomicDAO().getTurnNum();
        params.put("gameturn", turn);
        params.put("daycycle", ( turn % 2 == 0 ? "Night" : "Day"));

        return player;
    }

    protected void sendMessage(HttpServletResponse response, HttpServletRequest request,
                               String header, String message, String destination, String destinationName) {
        sendMessage(response, request, header, message, destination, destinationName, true);
    }

    protected void sendMessage(HttpServletResponse response, HttpServletRequest request,
                               String header, String message, String destination, String destinationName, Boolean addPlayer) {
        try {
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
            response.setContentType("text/html");
            template.process(params, response.getWriter());
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    public CharnomicDAO getCharnomicDAO() {
        return charnomicDAO;
    }

    public void setCharnomicDAO(CharnomicDAO charnomicDAO) {
        this.charnomicDAO = charnomicDAO;
    }
}
