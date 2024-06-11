package lab2.fleet.back.service;

import lab2.fleet.back.dto.FlightDTO;
import lab2.fleet.back.mapper.FlightMapper;
import lab2.fleet.back.repository.FlightRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lab2.fleet.back.entity.Flight;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepo repository;


    private final FlightMapper mapper = FlightMapper.INSTANCE;
    public void update(FlightDTO dto){
        Optional<Flight> record = repository.findById(dto.getId());
        if(record.isEmpty()){
            return;
        }
        Flight obj = record.get();
        obj.setDriverId(dto.getDriverId());
        obj.setUserId(dto.getUserId());
        obj.setRequestId(dto.getRequestId());
        obj.setVehicleId(dto.getVehicleId());
        obj.setStatus(dto.getStatus());
        obj.setActualArrivalTime(dto.getActualArrivalTime());
        obj.setActualDepartureTime(dto.getActualDepartureTime());
        obj.setEstimatedArrivalTime(dto.getEstimatedArrivalTime());

        repository.save(obj);
    }

    public void delete(String Id){
        if(repository.existsById(Id)){
            repository.deleteById(Id);
        }
    }

    public Optional<FlightDTO> get(String Id){
        Optional<Flight> obj = repository.findById(Id);
        return obj.map(mapper::toDTO);
    }

    public List<FlightDTO> getAll(){
        List<Flight> objs = repository.findAll();
        List<FlightDTO> DTOs = new ArrayList<>();
        for (Flight o : objs){
            DTOs.add(mapper.toDTO(o));
        }
        return DTOs;
    }

    public List<String> getIDs(String idCheck){
        switch (idCheck){
            case "driver" -> {
                return repository.findDriverIds().get();
            }
            case "user" -> {
                return repository.findUserIds().get();
            }
            case "vehicle" -> {
                return repository.findVehicleIds().get();
            }
            case "request" -> {
                return repository.findRequestIds().get();
            }
            default -> {
                return new ArrayList<String>();
            }
        }
    }

    public void create(FlightDTO dto){
        dto.setId(UUID.randomUUID().toString());
        Flight o = mapper.fromDTO(dto);
        repository.save(o);
    }
}
