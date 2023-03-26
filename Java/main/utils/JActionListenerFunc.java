package main.utils;

import main.mysqlExecute.BirthProcess;
import main.windowShow.WindowShow;

import javax.swing.*;
import java.sql.SQLException;

public class JActionListenerFunc {
    static WindowShow parent;

    // 设置父组件
    public static void setParent(WindowShow windowShow) {
        parent = windowShow;
    }

    // 显示查询的数据
    public static void searchUser() throws SQLException {
        String name = JOptionPane.showInputDialog(parent, "请输入人名", "搜索", JOptionPane.PLAIN_MESSAGE);
        if (name != null) {
            String message = BirthProcess.UserInfoSearch(name);
            JOptionPane.showMessageDialog(parent, message);
        }
    }

    // 添加数据
    public static void addUser() throws SQLException, IllegalAccessException {
        String message = JOptionPane.showInputDialog(parent, "请输入形如name-xxxx-xx-xx-x(阳历为0，阴历为1)", "添加", JOptionPane.PLAIN_MESSAGE);

        if (message != null) {
            BirthProcess.addBirth(message);
            parent.reloadCalender();
        }
    }

    // 删除数据
    public static void deleteUser() throws SQLException {
        String message = JOptionPane.showInputDialog(parent, "请输入id", "删除", JOptionPane.PLAIN_MESSAGE);
        if (message != null) {
            int id = Integer.parseInt(message);

            if (BirthProcess.userDelete(id)) {
                JOptionPane.showMessageDialog(parent, "删除成功", "通知", JOptionPane.PLAIN_MESSAGE);
                parent.reloadCalender();
            } else {
                JOptionPane.showMessageDialog(parent, "查无此人", "通知", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }
}
