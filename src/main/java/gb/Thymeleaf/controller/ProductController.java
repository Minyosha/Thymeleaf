package gb.Thymeleaf.controller;

import gb.Thymeleaf.model.Product;
import gb.Thymeleaf.services.FileGateWay;
import gb.Thymeleaf.services.ProductService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final Counter productsAdded = Metrics.counter("products_added");

    private FileGateWay fileGateWay;


    @GetMapping("/products")
    public String viewProducts(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products",products);
        return "products";
    }

//    @PostMapping("/products")
//    public String addProduct(Product p,Model model)
//    {
//        productService.addProduct(p);
//        List<Product> products = productService.findAll();
//        model.addAttribute("products",products);
//        return "products";
//    }

//    @PostMapping("/products")
//    public String addProduct(@RequestParam("name") String name, @RequestParam("quantity") double quantity, @RequestParam("pricePerElement") double pricePerElement, Model model) {
//        Product p = new Product(name, pricePerElement, quantity);
//        productService.addProduct(p);
//        List<Product> products = productService.findAll();
//        model.addAttribute("products", products);
//        return "products";
//    }

    @PostMapping("/products")
    public String addProduct(Product p, Model model) {
        p.calculatePrice();
        System.out.println(p.getName() + " + " + p.getQuantity() + " + " + p.getPricePerElement() + " + " + p.getPrice());
        productService.addProduct(p);
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        productsAdded.increment();
        fileGateWay.writeToFile("products.txt", p.toString());
//        fileGateWay.writeToFile(p.getName() + " " + LocalDateTime.now() + ".txt", p.toString());
        return "products";
    }


}