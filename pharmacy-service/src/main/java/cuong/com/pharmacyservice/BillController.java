package cuong.com.pharmacyservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RefreshScope // BẮT BUỘC PHẢI CÓ ĐỂ LÀM MỚI CẤU HÌNH ĐỘNG
public class BillController {

    // Lấy giá trị VAT từ Git thông qua Config Server
    @Value("${pharmacy.vat-rate}")
    private double vatRate;

    @PostMapping("/bill")
    public BillResponse calculateBill(@RequestBody BillRequest request) {
        double subtotal = request.getTotalAmount();
        double vatAmount = subtotal * vatRate;
        double grandTotal = subtotal + vatAmount;

        return new BillResponse(subtotal, vatRate, vatAmount, grandTotal);
    }

    // Dùng DTO nội bộ cho Request/Response
    public static class BillRequest {
        private double totalAmount;
        public double getTotalAmount() { return totalAmount; }
        public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    }

    public static class BillResponse {
        private double subtotal;
        private double vatRate;
        private double vatAmount;
        private double grandTotal;

        public BillResponse(double subtotal, double vatRate, double vatAmount, double grandTotal) {
            this.subtotal = subtotal;
            this.vatRate = vatRate;
            this.vatAmount = vatAmount;
            this.grandTotal = grandTotal;
        }

        // Getter và Setter
        public double getSubtotal() { return subtotal; }
        public double getVatRate() { return vatRate; }
        public double getVatAmount() { return vatAmount; }
        public double getGrandTotal() { return grandTotal; }
    }
}
