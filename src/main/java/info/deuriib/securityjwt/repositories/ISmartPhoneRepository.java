package info.deuriib.securityjwt.repositories;

import info.deuriib.securityjwt.models.SmartPhone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISmartPhoneRepository extends JpaRepository<SmartPhone, Long> {
}
