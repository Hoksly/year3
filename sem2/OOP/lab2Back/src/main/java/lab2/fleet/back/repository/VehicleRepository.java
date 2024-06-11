package lab2.fleet.back.repository;

import lab2.fleet.back.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {
    Optional<List<Vehicle>> findByModel(String model);
    Optional<List<Vehicle>> findByMake(String manufacturer);
    Optional<List<Vehicle>> findByVehicleCategory(String manufacturer);

    @Modifying
    @Query("DELETE Vehicle v WHERE v.id = ?1")
    void deleteByVehicleId(String vehicleId);
}