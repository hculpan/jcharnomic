package com.charnomic.jcharnomic;

import com.charnomic.jcharnomic.db.CharnomicDAO;
import com.charnomic.jcharnomic.db.Player;
import com.charnomic.jcharnomic.db.Proposal;
import com.charnomic.jcharnomic.db.Rule;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by harryculpan on 3/26/17.
 */
public class JsonDataHandler extends AbstractHandler {
    public void handle( String target,
                        Request baseRequest,
                        HttpServletRequest request,
                        HttpServletResponse response ) throws IOException,
            ServletException {
        if (target.equals("/v1/players")) {
            handlePlayersResponse(target, baseRequest, request, response);
            baseRequest.setHandled(true);
        } else if (target.equals("/v1/rules")) {
            handleRulesResponse(target, baseRequest, request, response);
            baseRequest.setHandled(true);
        } else if (target.equals("/v1/proposals")) {
            handleProposalsResponse(target, baseRequest, request, response);
            baseRequest.setHandled(true);
        } else if (target.equals("/v1/players_rules")) {
            handlePlayersRulesResponse(target, baseRequest, request, response);
            baseRequest.setHandled(true);
        }
    }

    CharnomicDAO charnomicDAO = new CharnomicDAO();

    String retrivePlayersJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        StringWriter writer = new StringWriter();
        writer.write("\"players\": ");
        objectMapper.writeValue(writer, getCharnomicDAO().retrievePlayers());
        return writer.toString();
    }

    String retriveRulesJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        StringWriter writer = new StringWriter();
        writer.write("\"rules\": ");
        objectMapper.writeValue(writer, getCharnomicDAO().retrieveRules());
        return writer.toString();
    }

    String retriveProposalsJson(String status) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        StringWriter writer = new StringWriter();
        writer.write("\"proposals\": ");
        objectMapper.writeValue(writer, getCharnomicDAO().retrieveProposal(status));
        return writer.toString();
    }

    void handlePlayersResponse(String target,
                              Request baseRequest,
                              HttpServletRequest request,
                              HttpServletResponse response) throws IOException {

        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        PrintWriter out = response.getWriter();
        out.print("{");
        out.print(retrivePlayersJson());
        out.print("}");
    }

    void handleRulesResponse(String target,
                               Request baseRequest,
                               HttpServletRequest request,
                               HttpServletResponse response) throws IOException {

        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);


        PrintWriter out = response.getWriter();

        out.print("{");
        out.print(retriveRulesJson());
        out.print("}");
    }

    void handlePlayersRulesResponse(String target,
                             Request baseRequest,
                             HttpServletRequest request,
                             HttpServletResponse response) throws IOException {

        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);


        PrintWriter out = response.getWriter();

        out.print("{ \"data\": [");
        out.print(retrivePlayersJson());
        out.print(",");
        out.print(retriveRulesJson());
        out.print("]}");
    }

    void handleProposalsResponse(String target,
                                    Request baseRequest,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws IOException {

        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);


        PrintWriter out = response.getWriter();

        out.print("{");
        out.print(retriveProposalsJson(request.getParameter("status")));
        out.print("}");
    }

    public CharnomicDAO getCharnomicDAO() {
        return charnomicDAO;
    }

    public void setCharnomicDAO(CharnomicDAO charnomicDAO) {
        this.charnomicDAO = charnomicDAO;
    }
}
