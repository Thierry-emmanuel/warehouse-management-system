package Warehousemanagement.project.warehouse.dto.response;

import Warehousemanagement.project.warehouse.enums.LocationStatus;
import Warehousemanagement.project.warehouse.enums.LocationType;

public class LocationResponse {
    private Long id;
    private Long warehouseId;
    private String warehouseCode;
    private String code;
    private String binNumber;
    private LocationType locationType;
    private LocationStatus status;
    private Double maxWeightKg;
    private Double maxVolumeCbm;

    public LocationResponse() {}

    public LocationResponse(Long id, Long warehouseId, String warehouseCode, String code, String binNumber,
                            LocationType locationType, LocationStatus status, Double maxWeightKg, Double maxVolumeCbm) {
        this.id = id;
        this.warehouseId = warehouseId;
        this.warehouseCode = warehouseCode;
        this.code = code;
        this.binNumber = binNumber;
        this.locationType = locationType;
        this.status = status;
        this.maxWeightKg = maxWeightKg;
        this.maxVolumeCbm = maxVolumeCbm;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getWarehouseId() { return warehouseId; }
    public void setWarehouseId(Long warehouseId) { this.warehouseId = warehouseId; }
    public String getWarehouseCode() { return warehouseCode; }
    public void setWarehouseCode(String warehouseCode) { this.warehouseCode = warehouseCode; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getBinNumber() { return binNumber; }
    public void setBinNumber(String binNumber) { this.binNumber = binNumber; }
    public LocationType getLocationType() { return locationType; }
    public void setLocationType(LocationType locationType) { this.locationType = locationType; }
    public LocationStatus getStatus() { return status; }
    public void setStatus(LocationStatus status) { this.status = status; }
    public Double getMaxWeightKg() { return maxWeightKg; }
    public void setMaxWeightKg(Double maxWeightKg) { this.maxWeightKg = maxWeightKg; }
    public Double getMaxVolumeCbm() { return maxVolumeCbm; }
    public void setMaxVolumeCbm(Double maxVolumeCbm) { this.maxVolumeCbm = maxVolumeCbm; }
}
