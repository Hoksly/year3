package com.fleet.dao.interfaces;

import com.fleet.models.Flight;

import java.util.List;

public interface FlightDAO {
    void saveFlight(Flight flight);

    Flight getFlightById(Long id);

    List<Flight> getAllFlights();

    void updateFlight(Flight flight);

    void deleteFlight(Flight flight);
}
