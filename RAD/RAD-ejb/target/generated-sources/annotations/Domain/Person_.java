package Domain;

import Domain.Bill;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-03-20T15:14:14")
@StaticMetamodel(Person.class)
public class Person_ { 

    public static volatile SingularAttribute<Person, String> name;
    public static volatile ListAttribute<Person, Bill> bills;
    public static volatile SingularAttribute<Person, Long> id;
    public static volatile SingularAttribute<Person, Long> cartracker;

}