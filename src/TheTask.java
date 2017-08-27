import java.util.Date;

public class TheTask {
    private String name;
    private String detail;
    private Date date;
    private boolean status;

    public TheTask(String name) {
        this.name = name;
        date = new Date();
        status = false;
    }

    public TheTask(String name, String detail) {
        this.name = name;
        this.detail = detail;
        date = new Date();
        status = false;
    }

    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
    }

    public String getDate() {
        return date.toString();
    }

    public boolean getStatus() {
        return status;
    }
}
