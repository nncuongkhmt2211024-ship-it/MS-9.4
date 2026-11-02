package cuong.com.pharmacyservice.controller;

import cuong.com.pharmacyservice.client.WarehouseClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pharmacy")
public class PharmacyController {

    @Autowired
    private WarehouseClient warehouseClient;

    @GetMapping("/check/{productId}")
    public String checkStock(@PathVariable String productId) {
        return warehouseClient.checkStock(productId);
    }
}