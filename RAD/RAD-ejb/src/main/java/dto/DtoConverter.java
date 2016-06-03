package dto;

import domain.Address;
import domain.Bill;
import domain.Car;
import domain.Person;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides the functionality needed to convert objects into DTO object.
 *
 * @author Melanie
 */
public class DtoConverter {

    /**
     * Convert Bill to BillDto
     *
     * @param bill The Bill object to convert.
     * @return A converted BillDto object.
     */
    public static BillDto convertBillToBillDto(Bill bill) {
        // TODO: bill.getBillRoadUsages instead of getRoadUsages()
        return new BillDto(
                bill.getCartrackerId(),
                bill.getRoadUsages(),
                bill.getTotalPrice(),
                bill.isPaid(),
                bill.getBillMonth(),
                bill.getBillYear());
    }

    /**
     * Convert Car to CarDto.
     *
     * @param car Car.
     * @return CarDto.
     */
    public static CarDto convertCarToDto(Car car) {
        CarDto dto = new CarDto(car.getCartrackerId(), car.getFuel());
        return dto;
    }

    /**
     * Convert list of Car to list of CarDto.
     *
     * @param cars list of Car.
     * @return list of CarDto.
     */
    public static List<CarDto> convertCarsToDto(List<Car> cars) {
        List<CarDto> dtos = new ArrayList<>();

        for (Car car : cars) {
            dtos.add(convertCarToDto(car));
        }

        return dtos;
    }

    /**
     * Person to PersonDto.
     *
     * @param person Person.
     * @return PersonDto.
     */
    public static PersonDto convertPersonToDto(Person person) {
        PersonDto dto = new PersonDto(person.getId(), person.getFirstName(),
                person.getLastName(), person.getInitials(),
                person.getAddress());
        return dto;
    }

    /**
     * Convert list of Person to list of PersonDto
     * @param persons list of Person.
     * @return list of PersonDto.
     */
    public static List<PersonDto> convertPersonsToDto(List<Person> persons) {
        List<PersonDto> dtos = new ArrayList<>();

        for (Person person : persons) {
            dtos.add(convertPersonToDto(person));
        }

        return dtos;
    }
}
