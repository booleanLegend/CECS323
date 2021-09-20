package model;

import javax.persistence.*;
import java.util.Set;

@Entity
@DiscriminatorValue("3")
public class Ad_Hoc_Team extends Authoring_Entities 
{
    @Id
    Long id;

    @ManyToMany
    Set<Individual_Author> has;
    
    public Ad_Hoc_Team()
    {}

    public Ad_Hoc_Team(String email, String name)
    {
        super(email, name);
    }

}
