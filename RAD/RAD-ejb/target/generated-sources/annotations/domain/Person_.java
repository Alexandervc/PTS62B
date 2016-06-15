package domain;

import domain.Address;
import domain.Bill;
import domain.Car;
import domain.UserGroup;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-06-14T17:27:05")
@StaticMetamodel(Person.class)
public class Person_ { 

    public static volatile SingularAttribute<Person, String> firstName;
    public static volatile SingularAttribute<Person, String> lastName;
    public static volatile ListAttribute<Person, Car> cars;
    public static volatile SingularAttribute<Person, String> password;
    public static volatile SingularAttribute<Person, Address> address;
    public static volatile SingularAttribute<Person, String> initials;
    public static volatile ListAttribute<Person, UserGroup> groups;
    public static volatile ListAttribute<Person, Bill> bills;
    public static volatile SingularAttribute<Person, Long> id;
    public static volatile SingularAttribute<Person, String> username;

}