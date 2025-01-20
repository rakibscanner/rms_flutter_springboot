package com.Project.RMSSpring.service;


import com.Project.RMSSpring.entity.Tables;
import com.Project.RMSSpring.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TableService {

    @Autowired
    private TableRepository tableRepository;

    public List<Tables> getAllTables() {
        return tableRepository.findAll();
    }

    public Tables findTableById(long id) {
        return tableRepository.findById(id).get();
    }

    public void saveTable(Tables tables) {
        tableRepository.save(tables);
    }
    public void deleteTable(long id) {
        tableRepository.deleteById(id);
    }

    public void updateTable(Tables tables, long id) {
        tableRepository.save(tables);
    }
}
