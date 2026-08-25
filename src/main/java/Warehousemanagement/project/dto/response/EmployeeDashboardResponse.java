package Warehousemanagement.project.dto.response;

import java.util.List;

public class EmployeeDashboardResponse {

    private long assignedPickTasks;
    private long pendingDockReceipts;
    private long pendingPutawayTasks;
    private int completedPicksToday;
    private int dailyPickTarget;
    private double scanAccuracyRate;
    private List<String> priorityTaskQueue;
    private List<String> activeScannerShortcuts;

    public EmployeeDashboardResponse() {
    }

    public long getAssignedPickTasks() {
        return assignedPickTasks;
    }

    public void setAssignedPickTasks(long assignedPickTasks) {
        this.assignedPickTasks = assignedPickTasks;
    }

    public long getPendingDockReceipts() {
        return pendingDockReceipts;
    }

    public void setPendingDockReceipts(long pendingDockReceipts) {
        this.pendingDockReceipts = pendingDockReceipts;
    }

    public long getPendingPutawayTasks() {
        return pendingPutawayTasks;
    }

    public void setPendingPutawayTasks(long pendingPutawayTasks) {
        this.pendingPutawayTasks = pendingPutawayTasks;
    }

    public int getCompletedPicksToday() {
        return completedPicksToday;
    }

    public void setCompletedPicksToday(int completedPicksToday) {
        this.completedPicksToday = completedPicksToday;
    }

    public int getDailyPickTarget() {
        return dailyPickTarget;
    }

    public void setDailyPickTarget(int dailyPickTarget) {
        this.dailyPickTarget = dailyPickTarget;
    }

    public double getScanAccuracyRate() {
        return scanAccuracyRate;
    }

    public void setScanAccuracyRate(double scanAccuracyRate) {
        this.scanAccuracyRate = scanAccuracyRate;
    }

    public List<String> getPriorityTaskQueue() {
        return priorityTaskQueue;
    }

    public void setPriorityTaskQueue(List<String> priorityTaskQueue) {
        this.priorityTaskQueue = priorityTaskQueue;
    }

    public List<String> getActiveScannerShortcuts() {
        return activeScannerShortcuts;
    }

    public void setActiveScannerShortcuts(List<String> activeScannerShortcuts) {
        this.activeScannerShortcuts = activeScannerShortcuts;
    }
}
