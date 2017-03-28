package com.charnomic.jcharnomic;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
                System.out.println("email=" + request.getParameter("email"));
                System.out.println("password=" + request.getParameter("password"));
            }
            baseRequest.setHandled(true);
        }
    }
}
