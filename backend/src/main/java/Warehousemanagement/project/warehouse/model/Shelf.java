package Warehousemanagement.project.warehouse.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "shelves", indexes = {
    @Index(name = "idx_shelf_rack_level", columnList = "rack_id, level_number", unique = true)
})
public class Shelf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "rack_id", nullable = false)
    private Rack rack;

    @Column(name = "level_number", nullable = false)
    private Integer levelNumber;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Shelf() {}

    public Shelf(Rack rack, Integer levelNumber) {
        this.rack = rack;
        this.levelNumber = levelNumber;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Rack getRack() { return rack; }
    public void setRack(Rack rack) { this.rack = rack; }

    public Integer getLevelNumber() { return levelNumber; }
    public void setLevelNumber(Integer levelNumber) { this.levelNumber = levelNumber; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}
