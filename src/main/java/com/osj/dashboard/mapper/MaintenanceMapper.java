package com.osj.dashboard.mapper;

import com.osj.dashboard.dto.MaintenanceDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MaintenanceMapper {
    List<MaintenanceDTO> selectMaintenance();

    MaintenanceDTO selectMaintenance(String id);

    void insertMaintenance(String id, String name, String description, String solve, String request_date);

    void deleteMaintenance(String id);

    void updateMaintenance(String id, String name, String description, String solve);
}
