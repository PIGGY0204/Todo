import java.util.Date;

public class TheTask {
    private String name;
    private Date date;

    public TheTask(String name) {
        this.name = name;
        date = new Date();
    }

    public TheTask(Date date, String name) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date.toString();
    }
}
