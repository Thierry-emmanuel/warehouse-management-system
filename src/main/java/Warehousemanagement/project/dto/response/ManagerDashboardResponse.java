package Warehousemanagement.project.dto.response;

import java.util.List;
import java.util.Map;

public class ManagerDashboardResponse {

    private double totalInventoryValuation;
    private long totalSalesVolume;
    private long totalShipmentsUnits;
    private double orderFulfillmentRate;
    private long lowStockCount;
    private long openPurchaseOrdersCount;
    private List<ActivityHeatmapCell> activityHeatmap;
    private Map<String, Double> monthlySalesTrends;
    private List<String> operationalAlerts;

    public ManagerDashboardResponse() {
    }

    public double getTotalInventoryValuation() {
        return totalInventoryValuation;
    }

    public void setTotalInventoryValuation(double totalInventoryValuation) {
        this.totalInventoryValuation = totalInventoryValuation;
    }

    public long getTotalSalesVolume() {
        return totalSalesVolume;
    }

    public void setTotalSalesVolume(long totalSalesVolume) {
        this.totalSalesVolume = totalSalesVolume;
    }

    public long getTotalShipmentsUnits() {
        return totalShipmentsUnits;
    }

    public void setTotalShipmentsUnits(long totalShipmentsUnits) {
        this.totalShipmentsUnits = totalShipmentsUnits;
    }

    public double getOrderFulfillmentRate() {
        return orderFulfillmentRate;
    }

    public void setOrderFulfillmentRate(double orderFulfillmentRate) {
        this.orderFulfillmentRate = orderFulfillmentRate;
    }

    public long getLowStockCount() {
        return lowStockCount;
    }

    public void setLowStockCount(long lowStockCount) {
        this.lowStockCount = lowStockCount;
    }

    public long getOpenPurchaseOrdersCount() {
        return openPurchaseOrdersCount;
    }

    public void setOpenPurchaseOrdersCount(long openPurchaseOrdersCount) {
        this.openPurchaseOrdersCount = openPurchaseOrdersCount;
    }

    public List<ActivityHeatmapCell> getActivityHeatmap() {
        return activityHeatmap;
    }

    public void setActivityHeatmap(List<ActivityHeatmapCell> activityHeatmap) {
        this.activityHeatmap = activityHeatmap;
    }

    public Map<String, Double> getMonthlySalesTrends() {
        return monthlySalesTrends;
    }

    public void setMonthlySalesTrends(Map<String, Double> monthlySalesTrends) {
        this.monthlySalesTrends = monthlySalesTrends;
    }

    public List<String> getOperationalAlerts() {
        return operationalAlerts;
    }

    public void setOperationalAlerts(List<String> operationalAlerts) {
        this.operationalAlerts = operationalAlerts;
    }
}
