package cuong.com.pharmacyservice.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WarehouseClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @CircuitBreaker(name = "warehouseCB", fallbackMethod = "checkWarehouseFallback")
    public String checkStock(String productId) {
        // Gọi sang kho tổng (warehouse-service)
        return restTemplate.getForObject("http://localhost:8082/api/v1/warehouse/" + productId, String.class);
    }

    // Hàm Fallback nhận vào ngoại lệ để xử lý khi mạch ngắt hoặc service lỗi
    public String checkWarehouseFallback(String productId, Exception e) {
        return "Không thể kết nối kho tổng. Hệ thống sẽ sử dụng dữ liệu tồn kho cục bộ để tiếp tục giao dịch";
    }
}