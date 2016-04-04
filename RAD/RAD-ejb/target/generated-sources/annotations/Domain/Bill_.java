package domain;

import domain.Person;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-04-04T08:59:30")
@StaticMetamodel(Bill.class)
public class Bill_ { 

    public static volatile SingularAttribute<Bill, Double> totalPrice;
    public static volatile SingularAttribute<Bill, Person> person;
    public static volatile SingularAttribute<Bill, Boolean> paid;
    public static volatile SingularAttribute<Bill, Long> id;

}