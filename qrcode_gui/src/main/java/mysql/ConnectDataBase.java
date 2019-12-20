package mysql;

import entity.Entity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectDataBase {
    private String ip;
    private int port;
    private String username;
    private String password;
    private String database;

    private List<String> tables;
    private DatabaseMetaData dbMetaData;

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public ConnectDataBase(String ip, int port, String database, String username, String password) {
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.password = password;
        this.database = database;
    }

    public boolean connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://" + ip + ":" + port + "/" + database + "?useSSL=false";
            connection = DriverManager.getConnection(url, username, password);
            // 连接成功，读取数据库的所有列
            dbMetaData = connection.getMetaData();
            getTables(dbMetaData);
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 获取所有的表名
    private void getTables(DatabaseMetaData dbMetaData) throws SQLException {
        tables = new ArrayList<>();
        ResultSet resultSet = dbMetaData.getTables(null, null, null, new String[] {"TABLE"});

        while (resultSet.next()) {
            tables.add(resultSet.getString("TABLE_NAME"));
        }
    }

    // 获取所有的表名称
    public List<String> getTables() {
        return tables;
    }

    // 获取一个表的所有字段
    public List<String> getClumns(String tableName) {
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from " + tableName + " limit 1");
            ResultSetMetaData metaData = resultSet.getMetaData();
            int count = metaData.getColumnCount();

            List<String> columnNames = new ArrayList<>();
            for (int i = 1; i <= count; i++) {
                columnNames.add(metaData.getColumnName(i));
            }

            return columnNames;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 根据给定的 id，content，name 字段名查询数据库信息
    public List<Entity> getEntities(String tableName, String content, String name) {
        try {
            statement = connection.createStatement();
            String sql = "select " + content + "," + name + " from " + tableName;
            ResultSet resultSet = statement.executeQuery(sql);

            List<Entity> entities = new ArrayList<>();
            while (resultSet.next()) {
                entities.add(new Entity(resultSet.getString(content), resultSet.getString(name)));
            }

//            System.out.println(entities);
            return entities;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
