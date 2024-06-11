package lab2.fleet.back.repository;

import lab2.fleet.back.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, String> {
    Optional<List<Driver>> findByName(String name);
    Optional<Driver> findByLicenseNumber(String licenseNumber);
    @Modifying
    @Query("DELETE Driver d WHERE d.id = ?1")
    void deleteByDriverId(String driverId);
}