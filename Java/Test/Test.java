package Test;

import main.mysqlExecute.BirthProcess;
import main.utils.JActionListenerFunc;
import main.windowShow.WindowShow;

import java.sql.SQLException;

public class Test {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        WindowShow.setFontSize(new int[]{25, 18});
        WindowShow.setGridX(new int[]{0, 0});

        BirthProcess.setConnection();
        WindowShow windowShow = new WindowShow("birth");
        JActionListenerFunc.setParent(windowShow);

        windowShow.addToolBar(new String[]{"添加", "删除", "搜索"});
        windowShow.addCalender();
        windowShow.showMyProject();
    }
}
