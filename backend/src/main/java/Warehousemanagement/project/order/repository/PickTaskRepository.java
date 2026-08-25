package Warehousemanagement.project.order.repository;

import Warehousemanagement.project.order.enums.PickTaskStatus;
import Warehousemanagement.project.order.model.PickTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PickTaskRepository extends JpaRepository<PickTask, Long> {

    Optional<PickTask> findByTaskCode(String taskCode);

    Page<PickTask> findByAssignedOperatorUsername(String username, Pageable pageable);

    List<PickTask> findByAssignedOperatorUsernameAndStatus(String username, PickTaskStatus status);

    long countByAssignedOperatorUsernameAndStatus(String username, PickTaskStatus status);

    Page<PickTask> findBySalesOrderWarehouseId(Long warehouseId, Pageable pageable);
}
