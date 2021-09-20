package model;

import javax.persistence.*;

@Entity
@DiscriminatorValue("1")
public class Writing_Group extends Authoring_Entities{

    private String headWriter;
    private int yearFormed;

    public Writing_Group()
    {}

    public Writing_Group(String email, String name, String writerName, int year)
    {
        super(email, name);
        headWriter = writerName;
        yearFormed = year;
    }

    public String getWriter()
    {
        return headWriter;
    }

    public void setName(String newName)
    {
        headWriter = newName;
    }

    public void setYearFormed(int newYear)
    {
        yearFormed = newYear;
    }

    public int getYearFormed()
    {
        return yearFormed; 
    }    

    @Override
    public String toString()
    {
        return "Name: " + this.getName() + "\nEmail: " + this.getEmail() + "\n Head Writer: " + getWriter() + "\nYear Formed: "  + getYearFormed();
    }
}
