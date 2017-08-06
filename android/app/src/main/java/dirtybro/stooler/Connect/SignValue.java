package dirtybro.stooler.Connect;

/**
 * Created by root1 on 2017. 8. 5..
 */

public class SignValue {

    private String id;
    private String pw;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public SignValue(String id, String pw) {
        this.id = id;
        this.pw = pw;
    }
}
