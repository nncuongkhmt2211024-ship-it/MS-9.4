package cuong.com.pharmacyservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PharmacyServiceApplication implements CommandLineRunner {

    // Kéo giá trị từ Git thông qua Config Server
    @Value("${app.branch-name}")
    private String branchName;

    @Value("${app.hotline}")
    private String hotline;

    public static void main(String[] args) {
        SpringApplication.run(PharmacyServiceApplication.class, args);
    }

    // Hàm run này sẽ tự động chạy ngay sau khi Spring Boot khởi động xong
    @Override
    public void run(String... args) throws Exception {
        System.out.println("=========================================");
        System.out.println("Pharmacy Service đã khởi động thành công!");
        System.out.println("Chi nhánh lấy từ Git: " + branchName);
        System.out.println("Hotline lấy từ Git: " + hotline);
        System.out.println("=========================================");
    }
}