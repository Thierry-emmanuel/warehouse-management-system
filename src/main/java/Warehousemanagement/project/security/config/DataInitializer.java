package Warehousemanagement.project.security.config;

import Warehousemanagement.project.security.enums.PermissionCategory;
import Warehousemanagement.project.security.enums.SystemRoleType;
import Warehousemanagement.project.security.model.Permission;
import Warehousemanagement.project.security.model.Role;
import Warehousemanagement.project.security.model.User;
import Warehousemanagement.project.security.repository.PermissionRepository;
import Warehousemanagement.project.security.repository.RoleRepository;
import Warehousemanagement.project.security.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(PermissionRepository permissionRepository, RoleRepository roleRepository,
                           UserRepository userRepository, PasswordEncoder passwordEncoder) {
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

        log.info("Bootstrapping default permissions, system roles, and seed users...");

        Map<String, Permission> permMap = new HashMap<>();
        String[][] defaultPerms = {
            {"USER_MANAGE", "Manage platform users and account status", "ADMINISTRATION"},
            {"ROLE_MANAGE", "Manage roles and permission bindings", "ADMINISTRATION"},
            {"PERMISSION_MANAGE", "Manage dynamic system permissions", "ADMINISTRATION"},
            {"FACILITY_MANAGE", "Configure warehouses, zones, aisles, and racks", "ADMINISTRATION"},
            {"INVENTORY_READ", "View inventory balances and bin allocations", "INVENTORY"},
            {"INVENTORY_WRITE", "Create, update, and manage SKU catalog items", "INVENTORY"},
            {"STOCK_ADJUST", "Perform manual stock adjustments and write-offs", "INVENTORY"},
            {"CYCLE_COUNT", "Initiate and reconcile blind cycle counts", "INVENTORY"},
            {"PO_CREATE", "Create and submit supplier purchase orders", "PROCUREMENT"},
            {"PO_APPROVE", "Approve pending purchase orders", "PROCUREMENT"},
            {"SO_CREATE", "Create customer sales orders", "OPERATIONS"},
            {"SO_APPROVE", "Approve and release sales orders for wave picking", "OPERATIONS"},
            {"DOCK_RECEIVE", "Process inbound shipments at the receiving dock", "OPERATIONS"},
            {"PUTAWAY_EXECUTE", "Confirm directed putaway to storage bins", "OPERATIONS"},
            {"PICK_EXECUTE", "Execute wave and zone picking assignments", "OPERATIONS"},
            {"PACK_DISPATCH", "Verify cartons and generate shipping labels", "OPERATIONS"},
            {"REPORTS_VIEW", "Access managerial KPI metrics and audit heatmaps", "ANALYTICS"}
        };

        for (String[] p : defaultPerms) {
            Permission perm = new Permission(p[0], p[1], PermissionCategory.valueOf(p[2]), true);
            permMap.put(p[0], permissionRepository.save(perm));
        }

        Role adminRole = new Role(SystemRoleType.ROLE_ADMIN.name(), "Full administrative platform access", true);
        adminRole.setPermissions(new HashSet<>(permMap.values()));
        roleRepository.save(adminRole);

        Role managerRole = new Role(SystemRoleType.ROLE_MANAGER.name(), "Warehouse operations and supply chain management", true);
        Set<Permission> managerPerms = new HashSet<>();
        for (String permName : new String[]{"INVENTORY_READ", "INVENTORY_WRITE", "STOCK_ADJUST", "CYCLE_COUNT", "PO_CREATE", "PO_APPROVE", "SO_CREATE", "SO_APPROVE", "DOCK_RECEIVE", "REPORTS_VIEW", "USER_MANAGE"}) {
            if (permMap.containsKey(permName)) managerPerms.add(permMap.get(permName));
        }
        managerRole.setPermissions(managerPerms);
        roleRepository.save(managerRole);

        Role employeeRole = new Role(SystemRoleType.ROLE_EMPLOYEE.name(), "Warehouse floor scanning and execution", true);
        Set<Permission> employeePerms = new HashSet<>();
        for (String permName : new String[]{"INVENTORY_READ", "DOCK_RECEIVE", "PUTAWAY_EXECUTE", "PICK_EXECUTE", "PACK_DISPATCH"}) {
            if (permMap.containsKey(permName)) employeePerms.add(permMap.get(permName));
        }
        employeeRole.setPermissions(employeePerms);
        roleRepository.save(employeeRole);

        User adminUser = new User("admin", "admin@wms.com", "System Administrator", passwordEncoder.encode("Admin@12345"), 1L);
        adminUser.setRoles(Set.of(adminRole));
        userRepository.save(adminUser);

        User managerUser = new User("manager", "manager@wms.com", "Warehouse Operations Manager", passwordEncoder.encode("Manager@12345"), 1L);
        managerUser.setRoles(Set.of(managerRole));
        userRepository.save(managerUser);

        User employeeUser = new User("employee", "employee@wms.com", "Floor Operations Specialist", passwordEncoder.encode("Employee@12345"), 1L);
        employeeUser.setRoles(Set.of(employeeRole));
        userRepository.save(employeeUser);

        log.info("RBAC initialization completed successfully.");
    }
}
