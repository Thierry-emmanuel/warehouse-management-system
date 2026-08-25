package Warehousemanagement.project.dashboard.dto.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class ManagerDashboardResponse {

    private Long warehouseId;
    private long totalSkus;
    private BigDecimal totalInventoryValue;
    private long pendingPurchaseOrders;
    private long activeWavePicks;
    private double dockUtilizationRate;
    private double warehouseCapacityUsage;
    private List<ActivityHeatmapCell> activityHeatmap;
    private Map<String, Long> weeklyMovementTrends;
    private List<String> operationalAlerts;

    public ManagerDashboardResponse() {
    }

    public ManagerDashboardResponse(Long warehouseId, long totalSkus, BigDecimal totalInventoryValue, long pendingPurchaseOrders, long activeWavePicks, double dockUtilizationRate, double warehouseCapacityUsage, List<ActivityHeatmapCell> activityHeatmap, Map<String, Long> weeklyMovementTrends, List<String> operationalAlerts) {
        this.warehouseId = warehouseId;
        this.totalSkus = totalSkus;
        this.totalInventoryValue = totalInventoryValue;
        this.pendingPurchaseOrders = pendingPurchaseOrders;
        this.activeWavePicks = activeWavePicks;
        this.dockUtilizationRate = dockUtilizationRate;
        this.warehouseCapacityUsage = warehouseCapacityUsage;
        this.activityHeatmap = activityHeatmap;
        this.weeklyMovementTrends = weeklyMovementTrends;
        this.operationalAlerts = operationalAlerts;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public long getTotalSkus() {
        return totalSkus;
    }

    public void setTotalSkus(long totalSkus) {
        this.totalSkus = totalSkus;
    }

    public BigDecimal getTotalInventoryValue() {
        return totalInventoryValue;
    }

    public void setTotalInventoryValue(BigDecimal totalInventoryValue) {
        this.totalInventoryValue = totalInventoryValue;
    }

    public long getPendingPurchaseOrders() {
        return pendingPurchaseOrders;
    }

    public void setPendingPurchaseOrders(long pendingPurchaseOrders) {
        this.pendingPurchaseOrders = pendingPurchaseOrders;
    }

    public long getActiveWavePicks() {
        return activeWavePicks;
    }

    public void setActiveWavePicks(long activeWavePicks) {
        this.activeWavePicks = activeWavePicks;
    }

    public double getDockUtilizationRate() {
        return dockUtilizationRate;
    }

    public void setDockUtilizationRate(double dockUtilizationRate) {
        this.dockUtilizationRate = dockUtilizationRate;
    }

    public double getWarehouseCapacityUsage() {
        return warehouseCapacityUsage;
    }

    public void setWarehouseCapacityUsage(double warehouseCapacityUsage) {
        this.warehouseCapacityUsage = warehouseCapacityUsage;
    }

    public List<ActivityHeatmapCell> getActivityHeatmap() {
        return activityHeatmap;
    }

    public void setActivityHeatmap(List<ActivityHeatmapCell> activityHeatmap) {
        this.activityHeatmap = activityHeatmap;
    }

    public Map<String, Long> getWeeklyMovementTrends() {
        return weeklyMovementTrends;
    }

    public void setWeeklyMovementTrends(Map<String, Long> weeklyMovementTrends) {
        this.weeklyMovementTrends = weeklyMovementTrends;
    }

    public List<String> getOperationalAlerts() {
        return operationalAlerts;
    }

    public void setOperationalAlerts(List<String> operationalAlerts) {
        this.operationalAlerts = operationalAlerts;
    }
}
