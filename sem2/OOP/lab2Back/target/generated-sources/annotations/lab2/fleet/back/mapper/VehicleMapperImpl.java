package lab2.fleet.back.mapper;

import javax.annotation.processing.Generated;
import lab2.fleet.back.dto.VehicleDTO;
import lab2.fleet.back.entity.Vehicle;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-10T13:58:14+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
public class VehicleMapperImpl implements VehicleMapper {

    @Override
    public VehicleDTO toDTO(Vehicle vehicle) {
        if ( vehicle == null ) {
            return null;
        }

        VehicleDTO vehicleDTO = new VehicleDTO();

        vehicleDTO.setId( vehicle.getId() );
        vehicleDTO.setModel( vehicle.getModel() );
        vehicleDTO.setMake( vehicle.getMake() );
        vehicleDTO.setPlateNumber( vehicle.getPlateNumber() );
        vehicleDTO.setVehicleCategory( vehicle.getVehicleCategory() );
        vehicleDTO.setYear( vehicle.getYear() );
        vehicleDTO.setColor( vehicle.getColor() );
        vehicleDTO.setMileage( vehicle.getMileage() );
        vehicleDTO.setLastService( vehicle.getLastService() );
        vehicleDTO.setHasAirbags( vehicle.isHasAirbags() );
        vehicleDTO.setHasABS( vehicle.isHasABS() );
        vehicleDTO.setWheelchairAccessible( vehicle.isWheelchairAccessible() );
        vehicleDTO.setCondition( vehicle.getCondition() );
        vehicleDTO.setPetFriendly( vehicle.isPetFriendly() );
        vehicleDTO.setChildSeatRequired( vehicle.isChildSeatRequired() );
        vehicleDTO.setInfo( vehicle.getInfo() );

        return vehicleDTO;
    }

    @Override
    public Vehicle fromDTO(VehicleDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Vehicle vehicle = new Vehicle();

        vehicle.setId( dto.getId() );
        vehicle.setModel( dto.getModel() );
        vehicle.setMake( dto.getMake() );
        vehicle.setPlateNumber( dto.getPlateNumber() );
        vehicle.setVehicleCategory( dto.getVehicleCategory() );
        vehicle.setYear( dto.getYear() );
        vehicle.setColor( dto.getColor() );
        vehicle.setMileage( dto.getMileage() );
        vehicle.setLastService( dto.getLastService() );
        vehicle.setHasAirbags( dto.isHasAirbags() );
        vehicle.setHasABS( dto.isHasABS() );
        vehicle.setWheelchairAccessible( dto.isWheelchairAccessible() );
        vehicle.setCondition( dto.getCondition() );
        vehicle.setPetFriendly( dto.isPetFriendly() );
        vehicle.setChildSeatRequired( dto.isChildSeatRequired() );
        vehicle.setInfo( dto.getInfo() );

        return vehicle;
    }
}
