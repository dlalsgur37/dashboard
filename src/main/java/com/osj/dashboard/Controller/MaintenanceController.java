package com.osj.dashboard.Controller;

import com.osj.dashboard.dto.MaintenanceDTO;
import com.osj.dashboard.service.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class MaintenanceController {
    private final MaintenanceService maintenanceService;

    @Autowired
    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @GetMapping("/maintenance")
    public List<MaintenanceDTO> getMaintenanceList() {
        return maintenanceService.selectMaintenance(true);
    }

    @PostMapping("/maintenance")
    public int addMaintenance(String name, String description, String solve, String request_date) {
        MaintenanceDTO newMaintenance = MaintenanceDTO.builder()
                                            .id("")
                                            .name(name)
                                            .solve(solve)
                                            .request_date(request_date)
                                            .description(description).build();
        int resultCode;

        resultCode = maintenanceService.insertMaintenance(newMaintenance);

        if (resultCode == 409)
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        else if (resultCode == 500)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);

        return resultCode;
    }

    @DeleteMapping("/maintenance/{id}")
    public int delMaintenance(@PathVariable String id) {
        MaintenanceDTO targetMaintenance = MaintenanceDTO.builder()
                                                .id(id).build();

        int resultCode;
        resultCode = maintenanceService.deleteMaintenance(targetMaintenance);

        if (resultCode == 500)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);

        return resultCode;
    }

    @PutMapping("/maintenance")
    public int updateMaintenance(@RequestBody MaintenanceDTO targetMaintenance) {
        /*MaintenanceDTO targetMaintenance = MaintenanceDTO.builder()
                                                .id(id)
                                                .name(name)
                                                .information(information).build();*/

        int resultCode;
        resultCode = maintenanceService.updateMaintenance(targetMaintenance);

        if (resultCode == 500)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);

        return resultCode;
    }


}
