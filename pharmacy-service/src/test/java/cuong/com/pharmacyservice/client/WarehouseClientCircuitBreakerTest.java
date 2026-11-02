package cuong.com.pharmacyservice.client;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class WarehouseClientCircuitBreakerTest {

    private MockWebServer mockWebServer;

    @Autowired
    private WarehouseClient warehouseClient;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start(8082); // Giả lập warehouse-service chạy ở cổng 8082
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void testCircuitBreakerOpensOnFailure() {
        // Cấu hình MockWebServer trả về lỗi 500 liên tục để kích hoạt failure rate threshold
        for (int i = 0; i < 10; i++) {
            mockWebServer.enqueue(new MockResponse().setResponseCode(500));
        }

        // Gọi liên tục để vượt qua ngưỡng failureRateThreshold (50%) và minimumNumberOfCalls (5)
        for (int i = 0; i < 6; i++) {
            String response = warehouseClient.checkStock("P001");
            System.out.println("Response lần " + (i + 1) + ": " + response);
        }

        // Kiểm tra xem phản hồi cuối cùng có phải đã chuyển sang trạng thái Fallback (Mạch đã OPEN) không
        String fallbackResponse = warehouseClient.checkStock("P001");
        assertTrue(fallbackResponse.contains("Fallback"), "Mạch phải chuyển sang OPEN và trả về thông điệp Fallback!");
    }
}