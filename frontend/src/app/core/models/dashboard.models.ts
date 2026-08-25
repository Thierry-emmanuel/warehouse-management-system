export interface ActivityHeatmapCell {
  date: string;
  dayOfWeek: number;
  weekOfYear: number;
  activityCount: number;
  intensityLevel: number;
}

export interface AdminDashboardResponse {
  totalUsers: number;
  activeUsers: number;
  totalRoles: number;
  totalPermissions: number;
  userRoleDistribution: Record<string, number>;
  facilityStatus: {
    facilityCode?: string;
    activeGateways?: number;
    connectedScanners?: number;
    systemStatus?: string;
    [key: string]: unknown;
  };
  systemHealth: {
    jvmUptimeSeconds?: number;
    dbConnectionPool?: string;
    lockContentionRate?: string;
    [key: string]: unknown;
  };
}

export interface ManagerDashboardResponse {
  warehouseId: number;
  totalSkus: number;
  totalInventoryValue: number;
  pendingPurchaseOrders: number;
  activeWavePicks: number;
  dockUtilizationRate: number;
  warehouseCapacityUsage: number;
  activityHeatmap: ActivityHeatmapCell[];
  weeklyMovementTrends: Record<string, number>;
  operationalAlerts: string[];
}

export interface EmployeeDashboardResponse {
  employeeName: string;
  warehouseId: number;
  pendingPicksCount: number;
  completedPicksToday: number;
  pendingPutawayTasks: number;
  pickAccuracyRate: number;
  picksPerHour: number;
  urgentTasks: Array<Record<string, unknown>>;
  scannerShortcuts: string[];
}
