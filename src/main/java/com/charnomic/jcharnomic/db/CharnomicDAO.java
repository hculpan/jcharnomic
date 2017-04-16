package com.charnomic.jcharnomic.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.jasypt.util.password.StrongPasswordEncryptor;

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
            cpds.setPassword(System.getProperty("password"));

            // the settings below are optional -- c3p0 can work with defaults
            cpds.setMinPoolSize(1);
            cpds.setAcquireIncrement(1);
            cpds.setMaxPoolSize(10);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        return cpds.getConnection();
    }

    public String getStoredPassword(String email) {
        String result = null;

        try (Connection conn = cpds.getConnection()) {
            try (Statement statement = conn.createStatement()) {
                ResultSet set = statement.executeQuery("select password from players where lower(email) = '" + email.toLowerCase() + "'");
                if (set.next()) {
                    result = set.getString("password");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean checkPassword(String email, String password) {
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        return passwordEncryptor.checkPassword(password, getStoredPassword(email));
    }

    protected Player findFirstActivePlayer(List<Player> players) {
        return findFirstActivePlayer(players, null);
    }

    protected Player findFirstActivePlayer(List<Player> players, Player startWith) {
        Player result = null;

        Player startingPlayer = (startWith == null ? players.get(0) : null);
        for (Player player : players) {
            if (startingPlayer != null && !player.getOnLeave()) {
                result = player;
                setActivePlayer(player);
                break;
            }

            if (startingPlayer == null && startWith.getId() == player.getId()) {
                startingPlayer = player;
            }
        }

        return result;
    }

    public void incrementeGameTurn() {
        try (Connection conn = cpds.getConnection()) {
            try (Statement statement = conn.createStatement()) {
                String sql = "update game_state " +
                        "set turn = turn + 1 ";
                if (statement.executeUpdate(sql) != 1) {
                    throw new SQLException("Unable to update game turn");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Player activateNextPlayer() {
        Player result = null;

        List<Player> players = retrievePlayers();
        Player activePlayer = getActivePlayer();
        activePlayer.setTurn(false);
        updatePlayerData(activePlayer, Player.updateablefields.turn);

        result = findFirstActivePlayer(players, activePlayer);
        if (result == null) {
            incrementeGameTurn();
            result = findFirstActivePlayer(players);
        }
        setActivePlayer(result);

        return result;
    }

    public Integer getTurnNum() {
        Integer result = null;

        try (Connection conn = cpds.getConnection()) {
            try (Statement statement = conn.createStatement()) {
                ResultSet set = statement.executeQuery("select turn from game_state");
                if (set.next()) {
                    result = set.getInt("turn");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public Player getPlayerById(Integer id) {
        Player player = null;

        try (Connection conn = cpds.getConnection()) {
            try (Statement statement = conn.createStatement()) {
                ResultSet set = statement.executeQuery("select * from players where id = " + id.toString());
                if (set.next()) {
                    player = createPlayer(set);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return player;
    }

    public void createProposal(Integer num, String name, String text, Player player) {
        try (Connection conn = cpds.getConnection()) {
            try (Statement statement = conn.createStatement()) {
                String sql = "insert into proposals " +
                        " (num, name, proposal, proposedby, proposeddate, status) " +
                        " values (" + num.toString() + ", " +
                        "         " + (name == null || name.isEmpty() ? null : "'" + name + "', ") +
                        "         '" + text + "', " +
                        "         " + Integer.toString(player.getId()) + ", " +
                        "         now(), " +
                        "         'proposed')";
                if (statement.executeUpdate(sql) != 1) {
                    throw new SQLException("Unable to insert proposal");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Player getPlayerByEmail(String email) {
        Player player = null;

        try (Connection conn = cpds.getConnection()) {
            try (Statement statement = conn.createStatement()) {
                ResultSet set = statement.executeQuery("select * from players where lower(email) = '" + email.toLowerCase() + "'");
                if (set.next()) {
                    player = createPlayer(set);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return player;
    }

    public Player getActivePlayer() {
        Player player = null;

        try (Connection conn = cpds.getConnection()) {
            try (Statement statement = conn.createStatement()) {
                ResultSet set = statement.executeQuery("select * from players where turn = 1");
                if (set.next()) {
                    player = createPlayer(set);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return player;
    }

    public void setActivePlayer(Player player) {
        try (Connection conn = cpds.getConnection()) {
            try (Statement statement = conn.createStatement()) {
                if (statement.executeUpdate("update players set turn = 0") == 0) {
                    throw new SQLException("Unable to update all players");
                }
                if (statement.executeUpdate("update players set turn = 1 where id = " + player.getId()) != 1) {
                    throw new SQLException("Unable to update active player");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePlayerUuid(Player player, String uuid, String userInfo) {
        try (Connection conn = cpds.getConnection()) {
            try (Statement statement = conn.createStatement()) {
                if (statement.executeUpdate("insert into sessions " +
                        "(uuid, playerid, info) " +
                        "values('" + uuid + "', " + player.getId().toString() + ", '" + userInfo + "')") != 1) {
                    throw new SQLException("Unable to insert uuid");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePassword(String email, String newPassword) throws SQLException {
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        String encryptedPassword = passwordEncryptor.encryptPassword(newPassword);

        try (Connection conn = cpds.getConnection()) {
            try (Statement statement = conn.createStatement()) {
                if (statement.executeUpdate("update players set password='" + encryptedPassword + "'," +
                        "passwordexpired = 0 " +
                        "  where email='" + email + "'") != 1) {
                    throw new SQLException("Unable to update password");
                }
            }
        }
    }

    public void updatePlayerData(Player p, Player.updateablefields...fields) {
        if (fields == null || fields.length == 0) return;

        try (Connection conn = cpds.getConnection()) {
            try (Statement statement = conn.createStatement()) {
                String sql = "update players set ";

                int index = 0;
                for (Player.updateablefields field : fields) {
                    switch (field) {
                        case firstname: sql += "firstname = '" + p.getFirstname() + "' "; break;
                        case lastname: sql += "lastname = '" + p.getLastname() + "' "; break;
                        case turn: sql += " turn = " + (p.getTurn() ? "1 " : "0 "); break;
                        case gold: sql += " gold = " + p.getGold(); break;
                        case points: sql += " points = " + p.getPoints(); break;
                        case vetoes: sql += " vetoes = " + p.getVetoes(); break;
                        case totalvetoes: sql += " totalvetoes = " + p.getTotalVetoes(); break;
                        case onleave: sql += " onleave = " + (p.getOnLeave() ? "1" : "0");
                        case email: sql += " email = '" + p.getEmail() + "'"; break;
                    }
                    index++;

                    if (index < fields.length) {
                        sql += ",";
                    }
                }

                sql += " where id = " + p.getId();

                if (statement.executeUpdate(sql) == 0) {
                    throw new SQLException("Unable to update player");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String msg = "Data updated for " + p.getFirstname() + " " + p.getLastname() + " [" + p.getId() + "]: ";
        for (Player.updateablefields field : fields) {
            msg += field.toString() + ", ";
        }
    }

    public Player getPlayerByUuid(String uuid) {
        Player player = null;

        try (Connection conn = cpds.getConnection()) {
            try (Statement statement = conn.createStatement()) {
                String sql =
                        "select p.*, s.uuid " +
                        "from players p " +
                        "inner join sessions s on s.playerid = p.id " +
                        "where s.uuid = '"+ uuid + "'";
                ResultSet set = statement.executeQuery(sql);
                if (set.next()) {
                    player = createPlayer(set);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return player;
    }

    protected Player createPlayer(ResultSet set) throws SQLException {
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
        player.setTotalVetoes(set.getInt("totalvetoes"));
        player.setPasswordExpired(set.getBoolean("passwordexpired"));
        player.setEmail(set.getString("email"));

        return player;
    }

    public List<Player> retrievePlayers() {
        List<Player> players = new ArrayList<>();

        try (Connection conn = cpds.getConnection()) {
            try (Statement statement = conn.createStatement()) {
                ResultSet set = statement.executeQuery("select * from players");
                while (set.next()) {
                    players.add(createPlayer(set));
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

    public Integer retrieveNextProposalNum() {
        Integer result = null;

        try (Connection conn = cpds.getConnection()) {
            try (Statement statement = conn.createStatement()) {
                ResultSet set = statement.executeQuery("select max(num) maxnum from proposals");
                if (set.next()) {
                    result = set.getInt("maxnum") + 1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<Proposal> retrieveProposal(String...status) {
        List<Proposal> proposals = new ArrayList<>();

        try (Connection conn =cpds.getConnection()) {
            try (Statement statement = conn.createStatement()) {
                String sql = "select * from proposals ";
                if (status != null) {
                    if (status.length > 1) {
                        sql += " where status in ('" + String.join("','", status) + "')";
                    } else if (status.length == 1) {
                        sql += " where status = '" + status + "'";
                    }
                }
                sql += " order by num desc";
                ResultSet set = statement.executeQuery(sql);
                while (set.next()) {
                    Proposal proposal = new Proposal();
                    proposal.setId(set.getInt("id"));
                    proposal.setProposal(set.getString("proposal"));
                    proposal.setName(set.getString("name"));
                    proposal.setNum(set.getInt("num"));
                    proposal.setProposedBy(getPlayerById(set.getInt("proposedby")));
                    proposal.setProposedDate(set.getDate("proposeddate"));
                    proposal.setVoteOpened(set.getDate("voteopened"));
                    proposal.setVoteClosed(set.getDate("voteclosed"));
                    proposal.setVotesInFavor(set.getInt("votesinfavor"));
                    proposal.setVotesAgainst(set.getInt("votesagainst"));
                    proposal.setVotesAbstained(set.getInt("votesabstained"));
                    proposal.setVotesVeto(set.getInt("votesveto"));
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
