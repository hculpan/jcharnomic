package com.charnomic.jcharnomic;

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
public class ErrorGetHandler extends AbstractHandler {
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) throws IOException,
            ServletException {
        try {
            WebGetService webGetService = new WebGetService();
            Template template = WebGetHandler.configuration.getTemplate("error404.html");
            Map<String, Object> params = new HashMap<>();
            params.put("target", target);
            webGetService.addUserToParams(request, params);
            template.process(params, response.getWriter());
            baseRequest.setHandled(true);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
