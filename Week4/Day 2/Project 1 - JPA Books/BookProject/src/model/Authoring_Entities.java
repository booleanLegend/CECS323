package model;

import javax.persistence.*;
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Authoring_Entities {
    @Id
    @Column(nullable = false,length = 60)
    private String email;

    @Column(nullable = false, length = 45)
    private String name;

    public Authoring_Entities()
    {}
    public Authoring_Entities(String email, String name)
    {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString()
    {
        return "Name: " + this.getName() + "\nEmail: " + this.getEmail();
    }
}
