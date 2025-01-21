package com.osj.dashboard.Controller;

import com.osj.dashboard.dto.MaintenanceDTO;
import com.osj.dashboard.service.MaintenanceService;
import jakarta.servlet.http.HttpSession;
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
    public List<MaintenanceDTO> getMaintenanceList(HttpSession session) {
        return maintenanceService.selectMaintenance(true);
    }

    @PostMapping("/maintenance")
    public int addMaintenance(@RequestBody MaintenanceDTO targetMaintenance) {
    //public int addMaintenance(String requestUser, String title, String description, String solve, String requestDate, String owner, String customerId, String domainId, String type) {
        /*MaintenanceDTO newMaintenance = MaintenanceDTO.builder()
                                            .id("")
                                            .requestUser(requestUser)
                                            .owner(owner)
                                            .solve(solve)
                                            .requestDate(requestDate)
                                            .customerId(customerId)
                                            .domainId(domainId)
                                            .type(type)
                                            .title(title)
                                            .description(description).build();*/


        int resultCode;

        resultCode = maintenanceService.insertMaintenance(targetMaintenance);

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
