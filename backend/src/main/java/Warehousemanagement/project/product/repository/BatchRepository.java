package Warehousemanagement.project.product.repository;

import Warehousemanagement.project.product.enums.QcStatus;
import Warehousemanagement.project.product.model.Batch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Long> {

    Optional<Batch> findByProductIdAndLotNumber(Long productId, String lotNumber);

    Page<Batch> findByProductId(Long productId, Pageable pageable);

    List<Batch> findByProductIdAndQcStatus(Long productId, QcStatus qcStatus);

    List<Batch> findByExpiryDateBeforeAndQcStatusNot(LocalDate threshold, QcStatus status);
}
