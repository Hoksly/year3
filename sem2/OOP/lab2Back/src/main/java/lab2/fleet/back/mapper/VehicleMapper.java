package lab2.fleet.back.mapper;

import lab2.fleet.back.dto.VehicleDTO;
import lab2.fleet.back.entity.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VehicleMapper {
    VehicleMapper INSTANCE = Mappers.getMapper(VehicleMapper.class);

    VehicleDTO toDTO(Vehicle vehicle);
    Vehicle fromDTO(VehicleDTO dto);
}