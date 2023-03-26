package main.utils;

import main.data.MyDate;
import main.data.User;
import main.windowShow.WindowShow;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class ComponentUtils {

    static public int gridY = 0;
    static public GridBagConstraints gridBagConstraints = new GridBagConstraints();

    // 对于传入的组件，将其字体颜色设置为黑色，背景设置为白色，设置无边界
    static public <T extends JComponent> void JAdjustComponent(T jAdjustComponent) {
        jAdjustComponent.setBackground(Color.white);
        jAdjustComponent.setBorder(null);
    }

    // 设置toolBar为不可浮动
    static public void JAdjustToolBar(JToolBar jToolBar) {
        JAdjustComponent(jToolBar);
        jToolBar.setFloatable(false);
    }

    // 设置JButton图标、边界样式和事件监听
    static public void JAdjustButton(JButton jButton) {
        JAdjustComponent(jButton);
        jButton.setFocusPainted(false);
    }

    // 设置label
    static public JLabel JAdjustLabel(String info, int fontSize) {
        JLabel jLabel = new JLabel(info);

        JAdjustComponent(jLabel);
        jLabel.setFont(new Font("宋体", Font.PLAIN, fontSize));
        return jLabel;
    }

    // 获取约束
    static public void getGridBagConstraints(int gridx, int gridy) {

        gridBagConstraints.gridx = gridx;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
    }

    // 为toolBar添加button
    static public void addToolBarButtons(JToolBar jToolBar, String[] tags) {
        for (String tag : tags) {
            JButton jButton = new JButton(tag);
            JAdjustButton(jButton);

            // 添加事件监听
            switch (jButton.getText()) {
                case "搜索":
                    System.out.println("***");
                    jButton.addActionListener(e -> {
                        try {
                            JActionListenerFunc.searchUser();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    });
                    break;
                case "添加":
                    jButton.addActionListener(e -> {
                        try {
                            JActionListenerFunc.addUser();
                        } catch (SQLException | IllegalAccessException ex) {
                            throw new RuntimeException(ex);
                        }
                    });
                    break;
                case "删除":
                    jButton.addActionListener(e -> {
                        try {
                            JActionListenerFunc.deleteUser();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    });
            }

            jToolBar.add(jButton);
            jToolBar.addSeparator();
        }
    }

    // 得到指定的JLabels
    static public ArrayList<JLabel> getLabels(int[] fontSizes, String[] infoList) {
        int num = infoList.length;
        ArrayList<JLabel> jLabels = new ArrayList<>();

        for (int i = 0; i < num; i++) {
            jLabels.add(JAdjustLabel(infoList[i], fontSizes[i]));
        }

        return jLabels;
    }

    static public void addBirthLabels(WindowShow windowShow, MyDate myDate,int[] fontSize, int[] gridX) throws SQLException {
        ArrayList<User> users = DateComputeUtils.nextBirth(myDate.mYear, myDate.mMonth, myDate.mDay, myDate.isSolar);

        users.forEach(e -> {
            ArrayList<JLabel> jLabelArrayList = ComponentUtils.getLabels(fontSize, e.getString());
            windowShow.addComponents(jLabelArrayList, gridX);
        });
    }
}
