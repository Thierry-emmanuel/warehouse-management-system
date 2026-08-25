package Warehousemanagement.project.dashboard.dto.response;

import java.util.List;
import java.util.Map;

public class EmployeeDashboardResponse {

    private String employeeName;
    private Long warehouseId;
    private long pendingPicksCount;
    private long completedPicksToday;
    private long pendingPutawayTasks;
    private double pickAccuracyRate;
    private double picksPerHour;
    private List<Map<String, Object>> urgentTasks;
    private List<String> scannerShortcuts;

    public EmployeeDashboardResponse() {
    }

    public EmployeeDashboardResponse(String employeeName, Long warehouseId, long pendingPicksCount, long completedPicksToday, long pendingPutawayTasks, double pickAccuracyRate, double picksPerHour, List<Map<String, Object>> urgentTasks, List<String> scannerShortcuts) {
        this.employeeName = employeeName;
        this.warehouseId = warehouseId;
        this.pendingPicksCount = pendingPicksCount;
        this.completedPicksToday = completedPicksToday;
        this.pendingPutawayTasks = pendingPutawayTasks;
        this.pickAccuracyRate = pickAccuracyRate;
        this.picksPerHour = picksPerHour;
        this.urgentTasks = urgentTasks;
        this.scannerShortcuts = scannerShortcuts;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public long getPendingPicksCount() {
        return pendingPicksCount;
    }

    public void setPendingPicksCount(long pendingPicksCount) {
        this.pendingPicksCount = pendingPicksCount;
    }

    public long getCompletedPicksToday() {
        return completedPicksToday;
    }

    public void setCompletedPicksToday(long completedPicksToday) {
        this.completedPicksToday = completedPicksToday;
    }

    public long getPendingPutawayTasks() {
        return pendingPutawayTasks;
    }

    public void setPendingPutawayTasks(long pendingPutawayTasks) {
        this.pendingPutawayTasks = pendingPutawayTasks;
    }

    public double getPickAccuracyRate() {
        return pickAccuracyRate;
    }

    public void setPickAccuracyRate(double pickAccuracyRate) {
        this.pickAccuracyRate = pickAccuracyRate;
    }

    public double getPicksPerHour() {
        return picksPerHour;
    }

    public void setPicksPerHour(double picksPerHour) {
        this.picksPerHour = picksPerHour;
    }

    public List<Map<String, Object>> getUrgentTasks() {
        return urgentTasks;
    }

    public void setUrgentTasks(List<Map<String, Object>> urgentTasks) {
        this.urgentTasks = urgentTasks;
    }

    public List<String> getScannerShortcuts() {
        return scannerShortcuts;
    }

    public void setScannerShortcuts(List<String> scannerShortcuts) {
        this.scannerShortcuts = scannerShortcuts;
    }
}
