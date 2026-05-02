package Warehousemanagement.project.model;

import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
@Entity
public class Record {
    @Id
    private int recordid;
    private String recordname;
    private int productid;
    private String productname;
    private String Managerid;

}
