package server.mysql;

public class MyUser {
    private String fName;
    private String fPassword;

    public MyUser() {}
    public MyUser(String fName, String fPassword) {
        this.fName = fName;
        this.fPassword = fPassword;
    }

    public String getFName() {
        return fName;
    }

    public String getFPassword() {
        return fPassword;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public void setFPassword(String fPassword) {
        this.fPassword = fPassword;
    }
}
