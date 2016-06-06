package dto;

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
     * Empty constructor for converting to Dto.
     */
    private DtoConverter() {
        // Empty for SonarQube.
    }

    /**
     * Convert Bill to BillDto
     *
     * @param bill The Bill object to convert.
     * @return A converted BillDto object.
     */
    public static BillDto convertBillToBillDto(Bill bill) {
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
        return new CarDto(car.getCartrackerId(), car.getFuel());
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
        return new PersonDto(person.getId(), person.getFirstName(),
                person.getLastName(), person.getInitials(),
                person.getAddress());
    }

    /**
     * Convert list of Person to list of PersonDto
     *
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
