package main.data;

public class User {
    public String name;
    public int year;
    public int month;
    public int date;

    // 为0则是阳历，否则为阴历
    public int isSolar;

    public  User(String name, int year, int month, int date, int isSolar) {
        this.name = name;
        this.year = year;
        this.month = month;
        this.date = date;
        this.isSolar = isSolar;
    }

    public User(String name, int year, int month, int date) {
        this.isSolar = 0;
        this.year = year;
        this.month = month;
        this.date = date;
        this.name = name;
    }

    public static User createUser(String info) {
        String[] strings = info.split("-");

        return new User(strings[0], Integer.parseInt(strings[1]), Integer.parseInt(strings[2]), Integer.parseInt(strings[3]), Integer.parseInt(strings[4]));
    }

    public String[] getString() {
        return new String[]{this.name, this.month + "月" + this.date + "日"};
    }
}
