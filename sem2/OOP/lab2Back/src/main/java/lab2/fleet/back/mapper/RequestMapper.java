package lab2.fleet.back.mapper;


import lab2.fleet.back.dto.RequestDTO;
import lab2.fleet.back.entity.Request;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RequestMapper {
    RequestMapper INSTANCE = Mappers.getMapper(RequestMapper.class);

    RequestDTO toDTO(Request request);
    Request fromDTO(RequestDTO dto);
}