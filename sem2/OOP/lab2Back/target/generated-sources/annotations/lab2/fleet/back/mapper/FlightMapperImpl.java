package lab2.fleet.back.mapper;

import javax.annotation.processing.Generated;
import lab2.fleet.back.dto.FlightDTO;
import lab2.fleet.back.entity.Flight;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-10T13:58:14+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
public class FlightMapperImpl implements FlightMapper {

    @Override
    public FlightDTO toDTO(Flight obj) {
        if ( obj == null ) {
            return null;
        }

        FlightDTO flightDTO = new FlightDTO();

        flightDTO.setId( obj.getId() );
        flightDTO.setEstimatedArrivalTime( obj.getEstimatedArrivalTime() );
        flightDTO.setActualDepartureTime( obj.getActualDepartureTime() );
        flightDTO.setActualArrivalTime( obj.getActualArrivalTime() );
        flightDTO.setRequestId( obj.getRequestId() );
        flightDTO.setDriverId( obj.getDriverId() );
        flightDTO.setVehicleId( obj.getVehicleId() );
        flightDTO.setUserId( obj.getUserId() );
        flightDTO.setStatus( obj.getStatus() );

        return flightDTO;
    }

    @Override
    public Flight fromDTO(FlightDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Flight flight = new Flight();

        flight.setId( dto.getId() );
        flight.setEstimatedArrivalTime( dto.getEstimatedArrivalTime() );
        flight.setActualDepartureTime( dto.getActualDepartureTime() );
        flight.setActualArrivalTime( dto.getActualArrivalTime() );
        flight.setRequestId( dto.getRequestId() );
        flight.setDriverId( dto.getDriverId() );
        flight.setVehicleId( dto.getVehicleId() );
        flight.setUserId( dto.getUserId() );
        flight.setStatus( dto.getStatus() );

        return flight;
    }
}
