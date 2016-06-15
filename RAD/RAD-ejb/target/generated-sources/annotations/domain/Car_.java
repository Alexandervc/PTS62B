package domain;

import domain.FuelType;
import domain.Person;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-06-14T17:27:05")
@StaticMetamodel(Car.class)
public class Car_ { 

    public static volatile SingularAttribute<Car, Person> owner;
    public static volatile SingularAttribute<Car, FuelType> fuel;
    public static volatile SingularAttribute<Car, String> cartrackerId;

}