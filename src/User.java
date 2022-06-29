public class User {


    String userID,fullName,userName,password,typeUser;
    static long idCounter = 0;



    User (String fullName, String userName,String password,String typeUser){
        this.fullName = fullName;
        this.userName = userName;
        this.password = password;
        this.typeUser = typeUser;

        this.userID = idCounter();
    }
    public static String idCounter(){
        return "UK00"+idCounter++;
    }
    //setter getter
    public String getUserId(){
        return userID;
    }
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(String typeUser) {
        this.typeUser = typeUser;
    }
}
