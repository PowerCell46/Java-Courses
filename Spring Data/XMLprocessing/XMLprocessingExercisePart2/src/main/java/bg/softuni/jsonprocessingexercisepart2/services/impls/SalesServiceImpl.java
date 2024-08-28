package bg.softuni.jsonprocessingexercisepart2.services.impls;

import bg.softuni.jsonprocessingexercisepart2.DTOs.exports.CarStatisticsExportDTO;
import bg.softuni.jsonprocessingexercisepart2.DTOs.exports.SalesStatisticsExportDTO;
import bg.softuni.jsonprocessingexercisepart2.entities.Car;
import bg.softuni.jsonprocessingexercisepart2.entities.Part;
import bg.softuni.jsonprocessingexercisepart2.entities.Sale;
import bg.softuni.jsonprocessingexercisepart2.repositories.SaleRepository;
import bg.softuni.jsonprocessingexercisepart2.services.interfaces.CarService;
import bg.softuni.jsonprocessingexercisepart2.services.interfaces.CustomerService;
import bg.softuni.jsonprocessingexercisepart2.services.interfaces.SalesService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class SalesServiceImpl implements SalesService {
    private final SaleRepository saleRepository;
    private final CarService carService;
    private final CustomerService customerService;

    private final Gson gson;

    @Override
    public void seedSales() {
        if (saleRepository.count() > 0) return;
        Random random = new Random();

        int numberOfSaleEntries = random.nextInt(20, 35);

        for (int i = 0; i < numberOfSaleEntries; i++) {
            long carId = random.nextLong(1, carService.findLastId() + 1);
            long customerId = random.nextLong(1, customerService.findLastId() + 1);

            List<Integer> discountPercentages = List.of(0, 5, 10, 15, 20, 30, 40, 50);

            Sale saleEntity = new Sale(
                carService.findById(carId),
                customerService.findById(customerId),
                discountPercentages.get(random.nextInt(discountPercentages.size())
            ));

            saleRepository.saveAndFlush(saleEntity);
        }
    }

    @Override
    public boolean exportToJSONSalesStatistics() {
            String EXPORT_RESULT_JSON_FILE_PATH =
            "src/main/resources/static/resultsJSON/salesStatistics.json";

        List<Sale> sales = saleRepository.findAll();

        try (FileWriter writer = new FileWriter(EXPORT_RESULT_JSON_FILE_PATH)) {

            List<String> jsonObjectsData = new ArrayList<>();

            for (Sale sale : sales) {
                // calculate All Parts Total Price
                BigDecimal priceWithoutDiscount = BigDecimal.ZERO;
                for (Part part : sale.getCar().getParts()) {
                    priceWithoutDiscount = priceWithoutDiscount.add(part.getPrice());
                }
                // calculate the final price
                BigDecimal priceWithDiscount = priceWithoutDiscount.subtract(
                    priceWithoutDiscount.multiply(BigDecimal.valueOf(sale.getDiscount())).divide(BigDecimal.valueOf(100))
                );

                SalesStatisticsExportDTO salesStatisticsExportDTO = SalesStatisticsExportDTO
                        .builder()
                        .car(mapCarEntityToCarStatistics(sale.getCar()))
                        .customerName(sale.getCustomer().getName())
                        .Discount(sale.getDiscount() / 100.0)
                        .price(priceWithoutDiscount)
                        .priceWithDiscount(priceWithDiscount)
                        .build();

                jsonObjectsData.add(gson.toJson(salesStatisticsExportDTO));
            }

            writer.write(jsonObjectsData.toString());
            System.out.println("Successful operation!");
            return true;

        } catch (IOException e) {
            System.out.println("An Error occurred while Writing the Data!");
            return false;
        }
    }

    private CarStatisticsExportDTO mapCarEntityToCarStatistics(Car car) {
        return CarStatisticsExportDTO
            .builder()
            .Make(car.getMake())
            .Model(car.getModel())
            .TravelDistance(car.getTravelledDistance())
            .build();
    }
}
