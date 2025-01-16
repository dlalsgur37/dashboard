package com.osj.dashboard.mapper;

import com.osj.dashboard.dto.MaintenanceDTO;
import com.osj.dashboard.dto.MaintenanceListDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MaintenanceMapper {
    List<MaintenanceDTO> selectMaintenance();

    List<MaintenanceListDTO> selectMaintenanceList();
    List<MaintenanceListDTO> selectMaintenanceListWithId(String userId, String maintenanceId, String customerId, String departmentId);

    MaintenanceDTO selectMaintenance(String id);

    void insertMaintenance(String id, String requestUser, String title, String description, String solve, String requestDate, String type);

    void insertMaintenanceList(String userId, String newId, String customerId, String domainId);

    void deleteMaintenance(String id);

    void updateMaintenance(String id, String name, String description, String solve);
}
