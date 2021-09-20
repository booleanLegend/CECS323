package model;

import java.util.Set;
import javax.persistence.*;

@Entity
@DiscriminatorValue("2")
public class Individual_Author extends Authoring_Entities
{
    @Id
    Long id;
    
    @ManyToMany
    Set<Ad_Hoc_Team> partOf;

    public Individual_Author()
    {}

    public Individual_Author(String email, String name, String writerName, int year)
    {
        super(email, name);   
    }
}