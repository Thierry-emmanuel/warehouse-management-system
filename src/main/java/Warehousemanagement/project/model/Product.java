package Warehousemanagement.project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.w3c.dom.Text;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;
    private String productname;
    private Integer quantity;
    private double price;

    public Product(Integer productId, String productname, Integer quantity, double price) {
        this.productId = productId;
        this.productname = productname;
        this.quantity = quantity;
        this.price = price;
    }

    public Integer getProductId() {
        return productId;
    }

    public double getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
