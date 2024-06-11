package lab2.fleet.back.mapper;

import lab2.fleet.back.dto.FlightDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import lab2.fleet.back.entity.Flight;

@Mapper
public interface FlightMapper {
    FlightMapper INSTANCE = Mappers.getMapper(FlightMapper.class);

    FlightDTO toDTO(Flight obj);
    Flight fromDTO(FlightDTO dto);
}
