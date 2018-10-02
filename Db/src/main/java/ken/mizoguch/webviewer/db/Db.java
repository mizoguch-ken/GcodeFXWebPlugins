/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ken.mizoguch.webviewer.db;

import com.google.gson.Gson;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.concurrent.Worker;
import javafx.scene.image.Image;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;
import ken.mizoguch.webviewer.plugin.WebViewerPlugin;

/**
 *
 * @author mizoguch-ken
 */
public class Db implements WebViewerPlugin {

    private WebViewerPlugin webViewer_;
    private static final String FUNCTION_NAME = "db";

    private String dbHost_, dbName_, dbUserName_, dbUserPassword_;
    private int dbPort_;
    private final Gson gson_ = new Gson();

    /**
     *
     * @throws java.lang.ClassNotFoundException
     */
    public Db() throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        dbHost_ = null;
        dbPort_ = -1;
        dbName_ = null;
        dbUserName_ = null;
        dbUserPassword_ = null;
    }

    /**
     *
     */
    public void licenses() {
        new Licenses().show();
    }

    /**
     *
     * @param host
     * @param port
     * @param name
     * @param user
     * @param pass
     */
    public void set(String host, int port, String name, String user, String pass) {
        dbHost_ = host;
        dbPort_ = port;
        dbName_ = name;
        dbUserName_ = user;
        dbUserPassword_ = pass;
    }

    /**
     *
     * @param sql
     * @return
     * @throws java.sql.SQLException
     */
    public String query(String sql) throws SQLException {
        if ((dbHost_ != null) && (dbPort_ != -1) && (dbName_ != null) && (dbUserName_ != null) && (dbUserPassword_ != null)) {
            try (Connection con = DriverManager.getConnection("jdbc:postgresql://" + dbHost_ + ":" + dbPort_ + "/" + dbName_, dbUserName_, dbUserPassword_);
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(sql)) {
                List<Map<String, String>> table = new ArrayList<>();
                List<String> columnNames = new ArrayList<>();
                ResultSetMetaData rsmd = rs.getMetaData();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    columnNames.add(rsmd.getColumnName(i));
                }

                while (rs.next()) {
                    Map<String, String> recode = new HashMap<>();
                    for (String name : columnNames) {
                        recode.put(name, rs.getString(name));
                    }
                    table.add(recode);
                }
                return gson_.toJson(table);
            }
        } else {
            webViewer_.write(FUNCTION_NAME, "Incorrect database argument", true);
        }
        return null;
    }

    /**
     *
     * @param sql
     * @return
     * @throws java.sql.SQLException
     */
    public Integer update(String sql) throws SQLException {
        if ((dbHost_ != null) && (dbPort_ != -1) && (dbName_ != null) && (dbUserName_ != null) && (dbUserPassword_ != null)) {
            try (Connection con = DriverManager.getConnection("jdbc:postgresql://" + dbHost_ + ":" + dbPort_ + "/" + dbName_, dbUserName_, dbUserPassword_);
                    Statement stmt = con.createStatement()) {
                int ret = stmt.executeUpdate(sql);
                stmt.close();
                return ret;
            }
        } else {
            webViewer_.write(FUNCTION_NAME, "Incorrect database argument", true);
        }
        return null;
    }

    @Override
    public void initialize(WebViewerPlugin webViewer) {
        webViewer_ = webViewer;
    }

    @Override
    public String functionName() {
        return FUNCTION_NAME;
    }

    @Override
    public void state(Worker.State state) {
    }

    @Override
    public void close() {
    }

    @Override
    public Stage stage() {
        return webViewer_.stage();
    }

    @Override
    public List<Image> icons() {
        return webViewer_.icons();
    }

    @Override
    public WebEngine webEngine() {
        return webViewer_.webEngine();
    }

    @Override
    public Path webPath() {
        return webViewer_.webPath();
    }

    @Override
    public void writeStackTrace(String name, Throwable throwable) {
        webViewer_.writeStackTrace(name, throwable);
    }

    @Override
    public void write(String name, String msg, boolean err) {
        webViewer_.write(name, msg, err);
    }
}
