package lab2.fleet.back.repository;

import lab2.fleet.back.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request, String> {
    // Add any custom queries if needed
}