package lab2.fleet.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import lab2.fleet.back.entity.Flight;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepo extends JpaRepository<Flight, String> {
    void deleteAllByDriverId(String driverId);
    void deleteAllByUserId(String userId);
    void deleteAllByVehicleId(String vehicleId);
    void deleteAllByRequestId(String requestId);

    @Query("SELECT f.driverId FROM Flight f")
    Optional<List<String>> findDriverIds();
    @Query("SELECT f.userId FROM Flight f")
    Optional<List<String>> findUserIds();
    @Query("SELECT f.vehicleId FROM Flight f")
    Optional<List<String>> findVehicleIds();
    @Query("SELECT f.requestId FROM Flight f")
    Optional<List<String>> findRequestIds();

}
