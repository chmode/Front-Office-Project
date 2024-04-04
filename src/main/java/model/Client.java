package model;

public class Client extends User {

    public Client() {
        super();
    }

    public Client(int id, String name, String email, String phone, String role, String password) {
        super(id, name, email, phone,role,password);
    }

    public Client(String name, String email, String phone, String role, String password) {
        super(name, email, phone,role,password);
    }



    @Override
    public String toString() {
        return "Client [id=" + id + ", name=" + name + ", email=" + email + ", phone=" + phone + ", role=" + role
                + ", password=" + password + "]";
    }

}
