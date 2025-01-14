package com.osj.dashboard.service;

import com.osj.dashboard.dto.MaintenanceDTO;
import com.osj.dashboard.mapper.MaintenanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaintenanceService {
    private final MaintenanceMapper maintenanceMapper;

    @Autowired
    public MaintenanceService(MaintenanceMapper maintenanceMapper) {
        this.maintenanceMapper = maintenanceMapper;
    }

    public List<MaintenanceDTO> selectMaintenance() {
        return maintenanceMapper.selectMaintenance();
    }

    public List<MaintenanceDTO> selectMaintenance(boolean markdown) {
        List<MaintenanceDTO> maintenanceList = selectMaintenance();

        return maintenanceList;
    }

    /*
    * Maintenance Insert to DB
    * @param newMaintenance : MaintenanceDTO
    * @return int - resultCode
    * resultCode = 201 : success
    * resultCode = 500 : fail - unknown error
    * resultCode = 409 : fail - duplicate name
    * */
    public int insertMaintenance(MaintenanceDTO newMaintenance) {
        List<MaintenanceDTO> maintenanceList = selectMaintenance();


            if (maintenanceList.isEmpty()) {
                newMaintenance.setId("M0001");
            } else {
                String lastId = maintenanceList.get(maintenanceList.size() - 1).getId();
                String lastIdx = lastId.split("M")[1];
                int idx = Integer.parseInt(lastIdx) + 1;
                newMaintenance.setId(String.format("M%04d", idx));
            }

        String newId = newMaintenance.getId();
        try {
            for (MaintenanceDTO maintenance : maintenanceList) {
                if (maintenance.getId().equals(newId)) {
                    System.out.println("Error : duplicate name !!");
                    return HttpStatus.CONFLICT.value();
                }
            }

            maintenanceMapper.insertMaintenance(newMaintenance.getId(), newMaintenance.getRequest_user(), newMaintenance.getDescription(), newMaintenance.getTitle(), newMaintenance.getSolve(),newMaintenance.getRequest_date(), newMaintenance.getType());

            maintenanceMapper.insertMaintenanceList("2",newId,newMaintenance.getCustomerId(), newMaintenance.getDomainId());

            return HttpStatus.CREATED.value();
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR.value();
        }

    }

    /*
     * Maintenance Delete from DB
     * @param maintenance : MaintenanceDTO
     * @return int - resultCode
     * resultCode = 200 : success
     * resultCode = 500 : fail - unknown error
     * */
    public int deleteMaintenance(MaintenanceDTO maintenance) {
        String maintenanceId = maintenance.getId();

        try {
            MaintenanceDTO realMaintenance = maintenanceMapper.selectMaintenance(maintenanceId);

            if (realMaintenance.getId().equalsIgnoreCase(maintenanceId)) {
                maintenanceMapper.deleteMaintenance(maintenanceId);
            }

            return HttpStatus.OK.value();
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR.value();
        }

    }

    /*
     * Maintenance Update to DB
     * @param maintenance : MaintenanceDTO
     * @return int - resultCode
     * resultCode = 200 : success
     * resultCode = 500 : fail - unknown error
     * */
    public int updateMaintenance(MaintenanceDTO maintenance) {
        try {
            maintenanceMapper.updateMaintenance(maintenance.getId(), maintenance.getRequest_user(), maintenance.getDescription(), maintenance.getSolve());

            return HttpStatus.OK.value();
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR.value();
        }

    }
}
