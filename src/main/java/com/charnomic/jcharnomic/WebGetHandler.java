package com.charnomic.jcharnomic;

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
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by harryculpan on 3/26/17.
 */
public class WebGetHandler extends AbstractHandler {

    public static Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);

    static {
        try {
            configuration.setDirectoryForTemplateLoading(new File("./src/templates"));
            configuration.setDefaultEncoding("UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    protected void handleError(Throwable t, HttpServletResponse response)  {
        try {
            sendMessage(response,
                    "Unexpected Error",
                    t.getLocalizedMessage() +
                            "<br><div class='center-text'>Please contact administrator</div>",
                    "/home.html",
                    "Home");
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }

    }

    public void handle( String target,
                        Request baseRequest,
                        HttpServletRequest request,
                         HttpServletResponse response ) throws IOException,
            ServletException {
        if (request.getMethod().equalsIgnoreCase("get")) {
            if (target.equals("/") || target.equals("/index.html") || target.equals("/home")) {
                response.sendRedirect("/home.html");
                baseRequest.setHandled(true);
            } else {
                try {
                    WebGetService webGetService = new WebGetService();
                    Map<String, Object> data = webGetService.retrieveData(target, request);
                    if (data != null) {
                        Template template = configuration.getTemplate(target);
                        response.setContentType("text/html");
                        template.process(data, response.getWriter());
                        baseRequest.setHandled(true);
                    }
                } catch (TemplateException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                    handleError(e, response);
                }
            }
/*
            } else if (target.equals("/proposals")) {
                try {
                    Template template = configuration.getTemplate("proposals.html");
                    List<Proposal> proposals = getCharnomicDAO().retrieveProposal(null);
                    params.put("proposals", proposals);
                    response.setContentType("text/html");
                    template.process(params, response.getWriter());
                    baseRequest.setHandled(true);
                } catch (TemplateException e) {
                    e.printStackTrace();
                }
            } else if (target.equals("/judgments")) {
                try {
                    Template template = configuration.getTemplate("judgments.html");
                    List<Judgment> judgments = getCharnomicDAO().retrieveJudgments();
                    params.put("judgments", judgments);
                    response.setContentType("text/html");
                    template.process(params, response.getWriter());
                    baseRequest.setHandled(true);
                } catch (TemplateException e) {
                    e.printStackTrace();
                }
            } else if (target.equals("/about")) {
                try {
                    Template template = configuration.getTemplate("about.html");
                    response.setContentType("text/html");
                    template.process(params, response.getWriter());
                    baseRequest.setHandled(true);
                } catch (TemplateException e) {
                    e.printStackTrace();
                }
            } else if (target.equals("/initial_rules")) {
                try {
                    Template template = configuration.getTemplate("initial_rules.html");
                    response.setContentType("text/html");
                    template.process(params, response.getWriter());
                    baseRequest.setHandled(true);
                } catch (TemplateException e) {
                    e.printStackTrace();
                }
            } else if (target.equals("/login")) {
                try {
                    Template template = configuration.getTemplate("login.html");
                    template.process(params, response.getWriter());
                    baseRequest.setHandled(true);
                } catch (TemplateException e) {
                    e.printStackTrace();
                }
            } else if (target.equals("/update_password")) {
                try {
                    Template template = configuration.getTemplate("update_password.html");
                    template.process(params, response.getWriter());
                    baseRequest.setHandled(true);
                } catch (TemplateException e) {
                    e.printStackTrace();
                }
            }
            */
        }
    }
}
