package com.charnomic.jcharnomic;

import com.charnomic.jcharnomic.db.*;
import freemarker.ext.beans.HashAdapter;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by harryculpan on 3/26/17.
 */
public class WebGetHandler extends AbstractHandler {

    public static Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);

    CharnomicDAO charnomicDAO = new CharnomicDAO();

    static {
        try {
            configuration.setDirectoryForTemplateLoading(new File("./src/templates"));
            configuration.setDefaultEncoding("UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handle( String target,
                        Request baseRequest,
                        HttpServletRequest request,
                         HttpServletResponse response ) throws IOException,
            ServletException {
        if (request.getMethod().equalsIgnoreCase("get")) {
            if (target.equals("/") || target.equals("/index.html")) {
                response.sendRedirect("/home");
                baseRequest.setHandled(true);
            } else if (target.equals("/home")) {
                try {
                    Template template = configuration.getTemplate("home.html");

                    Map<String, Object> params = new HashMap<>();
                    List<Player> players = getCharnomicDAO().retrievePlayers();
                    params.put("players", players);

                    List<Rule> rules = getCharnomicDAO().retrieveRules();
                    params.put("rules", rules);

                    template.process(params, response.getWriter());
                    baseRequest.setHandled(true);
                } catch (TemplateException e) {
                    e.printStackTrace();
                }
            } else if (target.equals("/proposals")) {
                try {
                    Template template = configuration.getTemplate("proposals.html");
                    Map<String, Object> params = new HashMap<>();
                    List<Proposal> proposals = getCharnomicDAO().retrieveProposal(null);
                    params.put("proposals", proposals);
                    template.process(params, response.getWriter());
                    baseRequest.setHandled(true);
                } catch (TemplateException e) {
                    e.printStackTrace();
                }
            } else if (target.equals("/judgments")) {
                try {
                    Template template = configuration.getTemplate("judgments.html");
                    Map<String, Object> params = new HashMap<>();
                    List<Judgment> judgments = getCharnomicDAO().retrieveJudgments();
                    params.put("judgments", judgments);
                    template.process(params, response.getWriter());
                    baseRequest.setHandled(true);
                } catch (TemplateException e) {
                    e.printStackTrace();
                }
            } else if (target.equals("/about")) {
                try {
                    Template template = configuration.getTemplate("about.html");
                    Map<String, Object> params = new HashMap<>();
                    template.process(params, response.getWriter());
                    baseRequest.setHandled(true);
                } catch (TemplateException e) {
                    e.printStackTrace();
                }
            } else if (target.equals("/initial_rules")) {
                try {
                    Template template = configuration.getTemplate("initial_rules.html");
                    Map<String, Object> params = new HashMap<>();
                    template.process(params, response.getWriter());
                    baseRequest.setHandled(true);
                } catch (TemplateException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public CharnomicDAO getCharnomicDAO() {
        return charnomicDAO;
    }

    public void setCharnomicDAO(CharnomicDAO charnomicDAO) {
        this.charnomicDAO = charnomicDAO;
    }
}
