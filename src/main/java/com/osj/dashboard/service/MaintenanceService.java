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

        if (markdown) {
            for (MaintenanceDTO maintenance : maintenanceList) {
                //markdown 이미지 처리 필요 시
                /*Parser parser = Parser.builder().build();
                Node document = new Document();
                document.appendChild(parser.parse("<div class=\"markdown-body\">" + maintenance.getInformation() + "</div>"));
                maintenance.setInformation(HtmlRenderer.builder().build().render(document));*/
            }
        } else {
            maintenanceList = selectMaintenance();
        }
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
        String newName = newMaintenance.getName();
        try {
            for (MaintenanceDTO maintenance : maintenanceList) {
                if (maintenance.getName().equals(newName)) {
                    System.out.println("Error : duplicate name !!");
                    return HttpStatus.CONFLICT.value();
                }
            }

            if (maintenanceList.isEmpty()) {
                newMaintenance.setId("M001");
            } else {
                String lastId = maintenanceList.get(maintenanceList.size() - 1).getId();
                String lastIdx = lastId.split("M")[1];
                int idx = Integer.parseInt(lastIdx) + 1;
                newMaintenance.setId(String.format("M%03d", idx));
            }

            maintenanceMapper.insertMaintenance(newMaintenance.getId(), newMaintenance.getName(), newMaintenance.getDescription(), newMaintenance.getSolve(),newMaintenance.getRequest_date());
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
            maintenanceMapper.updateMaintenance(maintenance.getId(), maintenance.getName(), maintenance.getDescription(), maintenance.getSolve());

            return HttpStatus.OK.value();
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR.value();
        }

    }
}
