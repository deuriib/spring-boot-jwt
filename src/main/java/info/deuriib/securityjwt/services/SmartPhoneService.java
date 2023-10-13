package info.deuriib.securityjwt.services;

import info.deuriib.securityjwt.models.SmartPhone;
import info.deuriib.securityjwt.repositories.ISmartPhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SmartPhoneService {
    private final ISmartPhoneRepository _smartPhoneRepository;

    @Autowired
    public SmartPhoneService(ISmartPhoneRepository smartPhoneRepository) {
        _smartPhoneRepository = smartPhoneRepository;
    }

    public List<SmartPhone> getSmartPhones() {
        return _smartPhoneRepository.findAll();
    }

    public Optional<SmartPhone> getSmartPhoneById(Long id) {
        return _smartPhoneRepository.findById(id);
    }

    public void save(SmartPhone smartPhone) {
        _smartPhoneRepository.save(smartPhone);
    }

    public void delete(Long id) {
        _smartPhoneRepository.deleteById(id);
    }
}
