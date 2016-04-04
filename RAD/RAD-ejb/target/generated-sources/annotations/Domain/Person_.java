package domain;

import domain.Bill;
import domain.Car;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-04-04T08:59:30")
@StaticMetamodel(Person.class)
public class Person_ { 

    public static volatile SingularAttribute<Person, String> firstName;
    public static volatile SingularAttribute<Person, String> lastName;
    public static volatile SingularAttribute<Person, String> number;
    public static volatile SingularAttribute<Person, String> zipCode;
    public static volatile SingularAttribute<Person, String> country;
    public static volatile ListAttribute<Person, Car> cars;
    public static volatile SingularAttribute<Person, String> streetName;
    public static volatile SingularAttribute<Person, String> city;
    public static volatile SingularAttribute<Person, String> initials;
    public static volatile ListAttribute<Person, Bill> bills;
    public static volatile SingularAttribute<Person, Long> id;

}