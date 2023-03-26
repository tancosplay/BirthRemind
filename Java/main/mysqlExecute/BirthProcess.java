package main.mysqlExecute;

import main.data.User;

import java.lang.reflect.Field;
import  java.sql.*;
import java.util.ArrayList;

// 数据处理转换比较存储的类
public class BirthProcess {
    public static Connection connection;
    private static PreparedStatement preparedStatement;

    // 连接数据库
    public static void setConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/usermessage", "root", "123456");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // 根据用户名来搜索用户信息
    private static ResultSet searchUserBirth(String name) throws SQLException {
        preparedStatement = connection.prepareStatement("select * from birthday_list where name regexp ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        preparedStatement.setString(1, name + "[0-9]*");
        preparedStatement.executeQuery();

        return preparedStatement.getResultSet();
    }

    // 搜索是否具有重名的用户，并返回重名用户的数量
    private static int isUserExisted(String name) throws SQLException {
        ResultSet resultSet = searchUserBirth(name);

        if (resultSet.next()) {
            resultSet.last();

            return resultSet.getRow();
        }
        return 0;
    }

    // 根据id搜索是否存在该用户
    private static boolean isUserExited(int id) throws SQLException {
        preparedStatement = connection.prepareStatement("select * from birthday_list where id = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next();
    }
    // 增加表中成员
    public static void addBirth(String string) throws SQLException, IllegalAccessException {
        User user = User.createUser(string);

        int existedNum = isUserExisted(user.name);
        preparedStatement = connection.prepareStatement("insert into  birthday_list(name, year, month, day, isSolar) values(?, ?, ?, ?, ?)");

        Field[] fields = user.getClass().getFields();

        for (int i = 0; i < fields.length; i++) {
            Class<?> class_type = fields[i].getType();
            if (class_type == String.class) {
                // 如果名字已存在则后缀加数字
                String name = existedNum == 0? (String) fields[i].get(user): (String) fields[i].get(user) + existedNum;
                preparedStatement.setString(1, name);
            }else {
                preparedStatement.setInt(i+1, (int)fields[i].get(user));
            }
        }
        preparedStatement.executeUpdate();
    }

    // 根据isSolar返回阳历/阴历数组
    public static ArrayList<User> getAllBirth(int isSolar) throws SQLException{
        preparedStatement  = connection.prepareStatement("select * from birthday_list where isSolar=?");
        preparedStatement.setInt(1, isSolar);
        preparedStatement.executeQuery();

        ResultSet resultSet = preparedStatement.getResultSet();
        ArrayList<User> userArrayList = new ArrayList<>();

        String name;
        int year, month, date;
        while (resultSet.next()) {
            name = resultSet.getString("name") + (isSolar == 1? "*": "");
            year = resultSet.getInt("year");
            month = resultSet.getInt("month");
            date = resultSet.getInt("day");
            userArrayList.add(new User(name, year, month, date));
        }

        return userArrayList;
    }

    // 返回用户信息字符串用以显示
    public static String UserInfoSearch(String name) throws SQLException {
        ResultSet resultSet = searchUserBirth(name);

        if(!resultSet.next()) {
            return "no such Person";
        }
        resultSet.beforeFirst();

        StringBuilder message = new StringBuilder();
        while (resultSet.next()) {
            String solar = resultSet.getInt("isSolar") == 1? "阴历  ": "阳历  ";
            message.append(solar).append("id：\t").append(resultSet.getInt("id")).append("  ")
                    .append(resultSet.getString("name")).append("  ")
                    .append(resultSet.getString("month")).append("月")
                    .append(resultSet.getString("day")).append("日").append("\n");
        }

        return message.toString();
    }

    // 根据id删除信息
    public static boolean userDelete(int id) throws SQLException {
        if (!isUserExited(id)) {
            return false;
        }
        preparedStatement = connection.prepareStatement("delete from birthday_list where id = ?");
        preparedStatement.setInt(1, id);

        preparedStatement.execute();
        return true;
    }
}
