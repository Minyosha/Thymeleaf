package gb.Thymeleaf.model;

import lombok.*;
import lombok.extern.java.Log;

@Data
public class Product {
    private String name;
    private double pricePerElement;
    private double quantity;
    private double price;


    public void calculatePrice() {
        this.price = this.pricePerElement * this.quantity;
        setPrice(price);
    }
}
