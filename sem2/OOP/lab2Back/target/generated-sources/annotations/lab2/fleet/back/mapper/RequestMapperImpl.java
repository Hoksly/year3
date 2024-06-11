package lab2.fleet.back.mapper;

import javax.annotation.processing.Generated;
import lab2.fleet.back.dto.RequestDTO;
import lab2.fleet.back.entity.Request;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-10T13:58:14+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
public class RequestMapperImpl implements RequestMapper {

    @Override
    public RequestDTO toDTO(Request request) {
        if ( request == null ) {
            return null;
        }

        RequestDTO requestDTO = new RequestDTO();

        requestDTO.setId( request.getId() );
        requestDTO.setOrigin( request.getOrigin() );
        requestDTO.setDestination( request.getDestination() );
        requestDTO.setMessage( request.getMessage() );
        requestDTO.setRequirements( request.getRequirements() );
        requestDTO.setIsActive( request.getIsActive() );
        requestDTO.setFare( request.getFare() );
        requestDTO.setPreferredVehicleCategory( request.getPreferredVehicleCategory() );

        return requestDTO;
    }

    @Override
    public Request fromDTO(RequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Request request = new Request();

        request.setId( dto.getId() );
        request.setOrigin( dto.getOrigin() );
        request.setDestination( dto.getDestination() );
        request.setMessage( dto.getMessage() );
        request.setRequirements( dto.getRequirements() );
        request.setIsActive( dto.getIsActive() );
        request.setFare( dto.getFare() );
        request.setPreferredVehicleCategory( dto.getPreferredVehicleCategory() );

        return request;
    }
}
