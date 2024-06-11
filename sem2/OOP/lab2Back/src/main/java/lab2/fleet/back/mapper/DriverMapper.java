package lab2.fleet.back.mapper;



import lab2.fleet.back.dto.DriverDTO;
import lab2.fleet.back.entity.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DriverMapper {
    DriverMapper INSTANCE = Mappers.getMapper(DriverMapper.class);

    DriverDTO toDTO(Driver brigade);
    Driver fromDTO(DriverDTO dto);
}
