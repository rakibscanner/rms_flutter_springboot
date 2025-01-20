package com.Project.RMSSpring.restcontroller;


import com.Project.RMSSpring.entity.Tables;
import com.Project.RMSSpring.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/table")
@CrossOrigin("*")
public class TableRestController {

    @Autowired
    private TableService tableService;

    @GetMapping("/view")
    public ResponseEntity<List<Tables>> getAllTables() {
        List<Tables> tables = tableService.getAllTables();
        return new ResponseEntity<>(tables, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Tables> saveTable(@RequestBody Tables tables) {
        tableService.saveTable(tables);
        return new ResponseEntity<>(tables, HttpStatus.OK);
    }
//    public void saveTable(@RequestBody Tables table) {
//        tableService.saveTable(table);
//    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteTable(@PathVariable ("id") int id) {
        tableService.deleteTable(id);
        return new ResponseEntity<>("Table deleted Succesfully",HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateTable(@PathVariable ("id") int id, @RequestBody Tables tables) {
        tableService.updateTable(tables,id);
        return new ResponseEntity<>("Table updated Successfully", HttpStatus.OK);
    }
}
