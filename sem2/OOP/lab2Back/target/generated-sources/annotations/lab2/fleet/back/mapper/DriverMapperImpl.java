package lab2.fleet.back.mapper;

import javax.annotation.processing.Generated;
import lab2.fleet.back.dto.DriverDTO;
import lab2.fleet.back.entity.Driver;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-10T13:58:14+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
public class DriverMapperImpl implements DriverMapper {

    @Override
    public DriverDTO toDTO(Driver brigade) {
        if ( brigade == null ) {
            return null;
        }

        DriverDTO driverDTO = new DriverDTO();

        driverDTO.setId( brigade.getId() );
        driverDTO.setName( brigade.getName() );
        driverDTO.setEmail( brigade.getEmail() );
        driverDTO.setPhone( brigade.getPhone() );
        driverDTO.setRegistrationDate( brigade.getRegistrationDate() );
        driverDTO.setLicenseNumber( brigade.getLicenseNumber() );
        driverDTO.setLicenseState( brigade.getLicenseState() );
        driverDTO.setLicenseExpiry( brigade.getLicenseExpiry() );
        driverDTO.setLicenseCategory( brigade.getLicenseCategory() );
        driverDTO.setLicenseInfo( brigade.getLicenseInfo() );
        driverDTO.setRating( brigade.getRating() );

        return driverDTO;
    }

    @Override
    public Driver fromDTO(DriverDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Driver driver = new Driver();

        driver.setId( dto.getId() );
        driver.setName( dto.getName() );
        driver.setEmail( dto.getEmail() );
        driver.setPhone( dto.getPhone() );
        driver.setRegistrationDate( dto.getRegistrationDate() );
        driver.setLicenseNumber( dto.getLicenseNumber() );
        driver.setLicenseState( dto.getLicenseState() );
        driver.setLicenseExpiry( dto.getLicenseExpiry() );
        driver.setLicenseCategory( dto.getLicenseCategory() );
        driver.setLicenseInfo( dto.getLicenseInfo() );
        driver.setRating( dto.getRating() );

        return driver;
    }
}
