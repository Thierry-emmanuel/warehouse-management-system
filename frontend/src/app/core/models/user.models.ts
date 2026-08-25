export interface UserSummary {
  id: number;
  username: string;
  email: string;
  fullName: string;
  phoneNumber?: string;
  isActive: boolean;
  warehouseId: number;
  roleNames: string[];
  createdAt: string;
}

export interface UserDetail {
  id: number;
  username: string;
  email: string;
  fullName: string;
  phoneNumber?: string;
  isActive: boolean;
  warehouseId: number;
  roles: RoleSummary[];
  effectivePermissions: string[];
  createdAt: string;
  updatedAt: string;
}

export interface RoleSummary {
  id: number;
  name: string;
  description: string;
  isSystemRole: boolean;
  permissionCount: number;
  userCount: number;
  createdAt: string;
}

export interface PermissionSummary {
  id: number;
  name: string;
  description: string;
  category: string;
  isSystemPermission: boolean;
  assignedRoleCount: number;
  createdAt: string;
}
