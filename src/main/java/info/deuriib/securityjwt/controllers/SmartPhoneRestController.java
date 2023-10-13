package info.deuriib.securityjwt.controllers;

import info.deuriib.securityjwt.models.SmartPhone;
import info.deuriib.securityjwt.services.SmartPhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/smartphones")
public class SmartPhoneRestController {
    private final SmartPhoneService _smartPhoneService;

    @Autowired
    public SmartPhoneRestController(SmartPhoneService smartPhoneService) {
        _smartPhoneService = smartPhoneService;
    }

    @GetMapping(value = "", headers = "Accept=application/json")
    public ResponseEntity Get() {
        return ResponseEntity.ok(_smartPhoneService.getSmartPhones());
    }

    @GetMapping(value = "{id}", headers = "Accept=application/json")
    public ResponseEntity Get(@PathVariable Long id) {
        return ResponseEntity.ok(_smartPhoneService.getSmartPhoneById(id));
    }

    @PostMapping(value = "", headers = "Accept=application/json")
    public ResponseEntity Post(@RequestBody SmartPhone smartPhone) {
        _smartPhoneService.save(smartPhone);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(value = "", headers = "Accept=application/json")
    public ResponseEntity Put(@RequestBody SmartPhone smartPhone) {
        if (_smartPhoneService.getSmartPhoneById(smartPhone.getId()).isEmpty()) {
            return ResponseEntity.badRequest().body("Smartphone not found");
        }

        _smartPhoneService.save(smartPhone);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping(value = "{id}", headers = "Accept=application/json")
    public ResponseEntity Delete(@PathVariable Long id) {
        _smartPhoneService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
