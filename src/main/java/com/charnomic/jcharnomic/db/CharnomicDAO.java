package com.charnomic.jcharnomic.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by harryculpan on 3/26/17.
 */
public class CharnomicDAO {
    static ComboPooledDataSource cpds;

    static final String jdbcUrl = "jdbc:mysql://localhost:3306/charnomic?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    static {
        try {
            cpds = new ComboPooledDataSource();
            cpds.setDriverClass( "com.mysql.cj.jdbc.Driver" ); //loads the jdbc driver
            cpds.setJdbcUrl( jdbcUrl );
            cpds.setUser("root");
            cpds.setPassword("09Ncd_945");

            // the settings below are optional -- c3p0 can work with defaults
            cpds.setMinPoolSize(1);
            cpds.setAcquireIncrement(1);
            cpds.setMaxPoolSize(10);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    public Player getPlayerById(Integer id) {
        Player player = null;

        try (Connection conn = cpds.getConnection()) {
            try (Statement statement = conn.createStatement()) {
                ResultSet set = statement.executeQuery("select * from players where id = " + id.toString());
                if (set.next()) {
                    player = new Player();
                    player.setId(set.getInt("id"));
                    player.setLastname(set.getString("lastname"));
                    player.setFirstname(set.getString("firstname"));
                    player.setLevel(set.getInt("level"));
                    player.setPoints(set.getInt("points"));
                    player.setGold(set.getInt("gold"));
                    player.setTurn(set.getBoolean("turn"));
                    player.setOnLeave(set.getBoolean("onleave"));
                    player.setJoined(set.getDate("joined"));
                    player.setLeftGame(set.getDate("leftgame"));
                    player.setMonitor(set.getBoolean("monitor"));
                    player.setVetoes(set.getInt("vetoes"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return player;
    }

    public List<Player> retrievePlayers() {
        List<Player> players = new ArrayList<>();

        try (Connection conn = cpds.getConnection()) {
            try (Statement statement = conn.createStatement()) {
                ResultSet set = statement.executeQuery("select * from players");
                while (set.next()) {
                    Player player = new Player();
                    player.setId(set.getInt("id"));
                    player.setLastname(set.getString("lastname"));
                    player.setFirstname(set.getString("firstname"));
                    player.setLevel(set.getInt("level"));
                    player.setPoints(set.getInt("points"));
                    player.setGold(set.getInt("gold"));
                    player.setTurn(set.getBoolean("turn"));
                    player.setOnLeave(set.getBoolean("onleave"));
                    player.setJoined(set.getDate("joined"));
                    player.setLeftGame(set.getDate("leftgame"));
                    player.setMonitor(set.getBoolean("monitor"));
                    player.setVetoes(set.getInt("vetoes"));
                    players.add(player);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return players;
    }

    public List<Rule> retrieveRules() {
        List<Rule> rules = new ArrayList<>();

        try (Connection conn =cpds.getConnection()) {
            try (Statement statement = conn.createStatement()) {
                ResultSet set = statement.executeQuery("select * from rules");
                while (set.next()) {
                    Rule rule = new Rule();
                    rule.setId(set.getInt("id"));
                    rule.setText(set.getString("rule"));
                    rule.setName(set.getString("name"));
                    rule.setNum(set.getInt("num"));
                    rules.add(rule);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rules;
    }

    public List<Proposal> retrieveProposal(String...status) {
        List<Proposal> proposals = new ArrayList<>();

        try (Connection conn =cpds.getConnection()) {
            try (Statement statement = conn.createStatement()) {
                String sql = "select * from proposals order by num desc";
                if (status != null) {
                    if (status.length > 1) {
                        sql += " where status in ('" + String.join("','", status) + "')";
                    } else {
                        sql += " where status = '" + status + "'";
                    }
                }
                ResultSet set = statement.executeQuery(sql);
                while (set.next()) {
                    Proposal proposal = new Proposal();
                    proposal.setId(set.getInt("id"));
                    proposal.setProposal(set.getString("proposal"));
                    proposal.setName(set.getString("name"));
                    proposal.setNum(set.getInt("num"));
                    proposal.setProposedBy(set.getInt("proposedby"));
                    proposal.setProposedDate(set.getDate("proposeddate"));
                    proposal.setVoteOpened(set.getDate("voteopened"));
                    proposal.setVoteClosed(set.getDate("voteclosed"));
                    proposal.setStatus(set.getString("status"));
                    proposals.add(proposal);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return proposals;
    }

    public List<Judgment> retrieveJudgments() {
        List<Judgment> judgments = new ArrayList<>();

        try (Connection conn =cpds.getConnection()) {
            try (Statement statement = conn.createStatement()) {
                ResultSet set = statement.executeQuery("select * from judgments");
                while (set.next()) {
                    Judgment judgment = new Judgment();
                    judgment.setId(set.getInt("id"));
                    judgment.setRequest(set.getString("request"));
                    judgment.setJudgment(set.getString("judgment"));
                    judgment.setRequestedBy(getPlayerById(set.getInt("requestedby")));
                    judgment.setRequestedDate(set.getDate("requesteddate"));
                    judgment.setJudgedBy(getPlayerById(set.getInt("judgedby")));
                    judgment.setJudgedDate(set.getDate("judgeddate"));
                    judgment.setOverruled(set.getBoolean("overruled"));
                    judgments.add(judgment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return judgments;
    }

}
