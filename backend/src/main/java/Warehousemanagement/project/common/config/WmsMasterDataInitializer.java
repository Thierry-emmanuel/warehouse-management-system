package Warehousemanagement.project.common.config;

import Warehousemanagement.project.category.model.Category;
import Warehousemanagement.project.category.repository.CategoryRepository;
import Warehousemanagement.project.inventory.model.InventoryItem;
import Warehousemanagement.project.inventory.repository.InventoryItemRepository;
import Warehousemanagement.project.order.enums.PickTaskStatus;
import Warehousemanagement.project.order.enums.PurchaseOrderStatus;
import Warehousemanagement.project.order.enums.SalesOrderStatus;
import Warehousemanagement.project.order.model.PickTask;
import Warehousemanagement.project.order.model.PurchaseOrder;
import Warehousemanagement.project.order.model.PurchaseOrderItem;
import Warehousemanagement.project.order.model.SalesOrder;
import Warehousemanagement.project.order.model.SalesOrderItem;
import Warehousemanagement.project.order.repository.PickTaskRepository;
import Warehousemanagement.project.order.repository.PurchaseOrderRepository;
import Warehousemanagement.project.order.repository.SalesOrderRepository;
import Warehousemanagement.project.product.enums.UnitOfMeasure;
import Warehousemanagement.project.product.model.Product;
import Warehousemanagement.project.product.repository.ProductRepository;
import Warehousemanagement.project.warehouse.enums.LocationType;
import Warehousemanagement.project.warehouse.enums.ZoneType;
import Warehousemanagement.project.warehouse.model.*;
import Warehousemanagement.project.warehouse.repository.LocationRepository;
import Warehousemanagement.project.warehouse.repository.WarehouseRepository;
import Warehousemanagement.project.warehouse.repository.ZoneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@Order(2)
public class WmsMasterDataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(WmsMasterDataInitializer.class);

    private final WarehouseRepository warehouseRepository;
    private final ZoneRepository zoneRepository;
    private final LocationRepository locationRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final InventoryItemRepository inventoryItemRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final SalesOrderRepository salesOrderRepository;
    private final PickTaskRepository pickTaskRepository;

    public WmsMasterDataInitializer(WarehouseRepository warehouseRepository,
                                    ZoneRepository zoneRepository,
                                    LocationRepository locationRepository,
                                    CategoryRepository categoryRepository,
                                    ProductRepository productRepository,
                                    InventoryItemRepository inventoryItemRepository,
                                    PurchaseOrderRepository purchaseOrderRepository,
                                    SalesOrderRepository salesOrderRepository,
                                    PickTaskRepository pickTaskRepository) {
        this.warehouseRepository = warehouseRepository;
        this.zoneRepository = zoneRepository;
        this.locationRepository = locationRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.inventoryItemRepository = inventoryItemRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.salesOrderRepository = salesOrderRepository;
        this.pickTaskRepository = pickTaskRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (warehouseRepository.count() > 0) return;

        log.info("Bootstrapping WMS Master Physical Locations, SKUs, and Inventory...");

        Warehouse wh1 = new Warehouse("WH-MAIN-01", "Central Logistics Distribution Center", "100 Logistics Pkwy", "Chicago", "USA", 15000);
        wh1 = warehouseRepository.save(wh1);

        Zone zoneA = new Zone(wh1, "Z01-AMB", "Zone A — Ambient Bulk Pallets", ZoneType.AMBIENT_STORAGE);
        Zone zoneB = new Zone(wh1, "Z02-CLD", "Zone B — Cold Storage Racks (4°C)", ZoneType.COLD_STORAGE);
        Zone zoneC = new Zone(wh1, "Z03-QAR", "Zone C — QC Quarantine & Damaged", ZoneType.QUARANTINE_DAMAGED);
        Zone zoneD = new Zone(wh1, "Z04-STG", "Zone D — Receiving Dock & Staging", ZoneType.RECEIVING_DOCK);
        zoneA = zoneRepository.save(zoneA);
        zoneRepository.save(zoneB);
        zoneRepository.save(zoneC);
        zoneRepository.save(zoneD);

        Location bin1 = new Location(wh1, null, "WH1-Z01-A02-S1-B03", "B03", LocationType.STORAGE_BIN);
        Location dock1 = new Location(wh1, null, "WH1-DOCK-BAY-01", "BAY-01", LocationType.RECEIVING_DOCK);
        bin1 = locationRepository.save(bin1);
        dock1 = locationRepository.save(dock1);

        Category electronics = new Category("Electronics & Audio", "ELEC", "Consumer and industrial electronic hardware");
        Category machinery = new Category("Industrial Machinery", "MECH", "Mechanical components and parts");
        electronics = categoryRepository.save(electronics);
        machinery = categoryRepository.save(machinery);

        Product p1 = new Product("ELEC-AUDIO-01", "Industrial Noise-Canceling Headset", "012345678901", UnitOfMeasure.PCS, new BigDecimal("85.00"), new BigDecimal("149.99"));
        p1.setCategory(electronics);
        p1 = productRepository.save(p1);

        Product p2 = new Product("MECH-GEAR-08", "Precision Steel Bevel Gear Assembly", "012345678902", UnitOfMeasure.PCS, new BigDecimal("240.00"), new BigDecimal("380.00"));
        p2.setCategory(machinery);
        p2 = productRepository.save(p2);

        InventoryItem inv1 = new InventoryItem(p1, bin1, null, 150);
        inv1.setQuantityAllocated(12);
        inventoryItemRepository.save(inv1);

        PurchaseOrder po = new PurchaseOrder("PO-9481", wh1, "Apex Industrial Supplies", LocalDate.now().plusDays(2));
        po.setStatus(PurchaseOrderStatus.CONFIRMED);
        PurchaseOrderItem poi = new PurchaseOrderItem(p1, 50, new BigDecimal("85.00"));
        po.addItem(poi);
        purchaseOrderRepository.save(po);

        SalesOrder so = new SalesOrder("SO-8819", wh1, "Global Logistics Corp", "742 Evergreen Terr, Springfield");
        so.setStatus(SalesOrderStatus.ALLOCATED);
        SalesOrderItem soi = new SalesOrderItem(p1, 12, new BigDecimal("149.99"));
        soi.setAllocatedQuantity(12);
        so.addItem(soi);
        so = salesOrderRepository.save(so);

        PickTask pickTask = new PickTask("WAVE-091", so, p1, bin1, 12, "employee");
        pickTask.setStatus(PickTaskStatus.PENDING);
        pickTaskRepository.save(pickTask);

        log.info("WMS master data seeding completed successfully.");
    }
}
