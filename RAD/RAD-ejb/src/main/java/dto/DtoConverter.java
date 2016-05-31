package dto;

import domain.Car;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Melanie
 */
public class DtoConverter {
    /**
     * Convert Car to CarDto.
     * @param car Car.
     * @return CarDto.
     */    
    public static CarDto convertCarToDto(Car car) {
        CarDto dto = new CarDto(car.getCartrackerId(), car.getFuel());
        return dto;
    }
    
    /**
     * Convert list of Car to list of CarDto.
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
}
