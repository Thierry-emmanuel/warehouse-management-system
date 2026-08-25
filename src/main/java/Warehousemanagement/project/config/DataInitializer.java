package Warehousemanagement.project.config;

import Warehousemanagement.project.enums.PermissionCategory;
import Warehousemanagement.project.enums.SystemRoleType;
import Warehousemanagement.project.model.Permission;
import Warehousemanagement.project.model.Role;
import Warehousemanagement.project.model.User;
import Warehousemanagement.project.repository.PermissionRepository;
import Warehousemanagement.project.repository.RoleRepository;
import Warehousemanagement.project.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(PermissionRepository permissionRepository, RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (permissionRepository.count() > 0) {
            return;
        }

        // 1. Seed Permissions
        Map<String, Permission> permissions = new HashMap<>();
        seedPermission(permissions, "USER_MANAGE", "Create, update, and manage users", PermissionCategory.ADMINISTRATION);
        seedPermission(permissions, "ROLE_MANAGE", "Create, update, and manage roles and permissions", PermissionCategory.ADMINISTRATION);
        seedPermission(permissions, "PERMISSION_MANAGE", "Create, modify, and delete permissions", PermissionCategory.ADMINISTRATION);
        seedPermission(permissions, "INVENTORY_READ", "View inventory catalog and stock levels", PermissionCategory.INVENTORY);
        seedPermission(permissions, "INVENTORY_WRITE", "Modify SKU catalog and thresholds", PermissionCategory.INVENTORY);
        seedPermission(permissions, "STOCK_ADJUST", "Perform manual stock adjustments and corrections", PermissionCategory.INVENTORY);
        seedPermission(permissions, "PO_CREATE", "Create inbound supplier purchase orders", PermissionCategory.PROCUREMENT);
        seedPermission(permissions, "PO_APPROVE", "Approve purchase orders and supplier invoices", PermissionCategory.PROCUREMENT);
        seedPermission(permissions, "DOCK_RECEIVE", "Dock receiving, carton scans, and initial QC", PermissionCategory.OPERATIONS);
        seedPermission(permissions, "PUTAWAY_EXECUTE", "Confirm directed putaway tasks to bins", PermissionCategory.OPERATIONS);
        seedPermission(permissions, "PICK_EXECUTE", "Execute wave and batch order picking routes", PermissionCategory.OPERATIONS);
        seedPermission(permissions, "PACK_DISPATCH", "Weigh cartons and print shipping labels", PermissionCategory.OPERATIONS);
        seedPermission(permissions, "REPORTS_VIEW", "Access inventory turnover and valuation reports", PermissionCategory.ANALYTICS);

        // 2. Seed Roles
        Role adminRole = createRole(SystemRoleType.ROLE_ADMIN.name(), "Full administrative system access", true, new HashSet<>(permissions.values()));
        
        Set<Permission> managerPerms = Set.of(
            permissions.get("INVENTORY_READ"), permissions.get("INVENTORY_WRITE"), permissions.get("STOCK_ADJUST"),
            permissions.get("PO_CREATE"), permissions.get("PO_APPROVE"), permissions.get("DOCK_RECEIVE"),
            permissions.get("REPORTS_VIEW"), permissions.get("USER_MANAGE")
        );
        Role managerRole = createRole(SystemRoleType.ROLE_MANAGER.name(), "Warehouse operations and inventory management", true, managerPerms);

        Set<Permission> employeePerms = Set.of(
            permissions.get("INVENTORY_READ"), permissions.get("DOCK_RECEIVE"),
            permissions.get("PUTAWAY_EXECUTE"), permissions.get("PICK_EXECUTE"), permissions.get("PACK_DISPATCH")
        );
        Role employeeRole = createRole(SystemRoleType.ROLE_EMPLOYEE.name(), "Warehouse floor scanning and execution", true, employeePerms);

        // 3. Seed Users
        createUser("admin", "admin@wms.com", "System Administrator", "Admin@12345", 1L, Set.of(adminRole));
        createUser("manager", "manager@wms.com", "Johnny Watson", "Manager@12345", 1L, Set.of(managerRole));
        createUser("employee", "employee@wms.com", "Jesse Irizarry", "Employee@12345", 1L, Set.of(employeeRole));
    }

    private void seedPermission(Map<String, Permission> map, String name, String desc, PermissionCategory cat) {
        Permission p = new Permission(name, desc, cat, true);
        p = permissionRepository.save(p);
        map.put(name, p);
    }

    private Role createRole(String name, String desc, boolean isSystem, Set<Permission> perms) {
        Role role = new Role(name, desc, isSystem);
        role.setPermissions(new HashSet<>(perms));
        return roleRepository.save(role);
    }

    private void createUser(String username, String email, String fullName, String rawPass, Long whId, Set<Role> roles) {
        User user = new User(username, email, fullName, passwordEncoder.encode(rawPass), whId);
        user.setRoles(new HashSet<>(roles));
        userRepository.save(user);
    }
}
