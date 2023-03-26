package main.windowShow;

import main.utils.DateComputeUtils;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

import static main.utils.ComponentUtils.*;

public class WindowShow extends JFrame {
    JPanel jPanel = new JPanel();
    GridBagLayout gridBagLayout = new GridBagLayout();

    public static int[] fontSize;
    public static int[] gridX;
    // 初始化标题，设置网格布局
    public WindowShow(String title) {
        this.setTitle(title);
        this.setBackground(Color.white);
        // 设置图标
        this.setIconImage(new ImageIcon("D:/main.png").getImage());
        // 关闭程序即停止运行
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);

        this.jPanel.setLayout(gridBagLayout);
        JAdjustComponent(jPanel);
    }

    // 设置label字体
    public static void setFontSize(int[] fontSize1) {
        fontSize = fontSize1;
    }

    // 设置label水平距离
    public static void setGridX(int[] gridX1) {
        gridX = gridX1;
    }

    // 增加组件
    public void addComponent(Component component, int gridx) {
        getGridBagConstraints(gridx, gridY);
        gridY ++;
        this.jPanel.add(component, gridBagConstraints);
    }

    // 增加组件
    public <T extends Component> void addComponents(ArrayList<T> arrayList, int[] gridX) {
        for (int i = 0; i < arrayList.size(); i++) {
            addComponent(arrayList.get(i), gridX[i]);
        }
    }

    // 添加toolbar
    public void addToolBar(String[] buttonTags) {
        JToolBar jToolBar = new JToolBar();
        JAdjustToolBar(jToolBar);

        addToolBarButtons(jToolBar, buttonTags);
        addComponent(jToolBar, 0);
    }

    // 添加日期显示
    public void addCalender() throws SQLException {
        addBirthLabels(this, DateComputeUtils.myDate, fontSize, gridX);
        addBirthLabels(this, DateComputeUtils.myDate1, fontSize, gridX);
        addTodayCalender(10);
        jPanel.revalidate();
        jPanel.repaint();
    }

    // 添加当天的日期
    private void addTodayCalender(int fontsize) {
        JLabel jLabel =  JAdjustLabel(DateComputeUtils.myDate.toString(), fontsize);
        JLabel jLabel1 = JAdjustLabel(DateComputeUtils.myDate1.toString(), fontsize);

        this.addComponent(jLabel, 1);
        this.addComponent(jLabel1, 1);
    }

    // 移除所有日期显示
    private void clearCalender() {
        for (int i = 1; i < gridY; i++) {
            jPanel.remove(1);
        }
        gridY = 1;
    }

    // 更新JFrame
    public void reloadCalender() throws SQLException {
        clearCalender();
        addCalender();
        this.pack();
    }

    // 展现组件并最优化窗体
    public void showMyProject() {
        this.add(this.jPanel);
        this.setLocation(1200, 0);
        this.pack();
        this.setVisible(true);
    }
}
