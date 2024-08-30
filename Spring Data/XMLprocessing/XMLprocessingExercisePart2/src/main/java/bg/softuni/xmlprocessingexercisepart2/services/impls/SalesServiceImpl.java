package bg.softuni.xmlprocessingexercisepart2.services.impls;

import bg.softuni.xmlprocessingexercisepart2.DTOs.queries.SaleCarQueryDTO;
import bg.softuni.xmlprocessingexercisepart2.DTOs.queries.SaleQueryDTO;
import bg.softuni.xmlprocessingexercisepart2.DTOs.queries.SaleRootQueryDTO;
import bg.softuni.xmlprocessingexercisepart2.entities.Car;
import bg.softuni.xmlprocessingexercisepart2.entities.Part;
import bg.softuni.xmlprocessingexercisepart2.entities.Sale;
import bg.softuni.xmlprocessingexercisepart2.repositories.SaleRepository;
import bg.softuni.xmlprocessingexercisepart2.services.interfaces.CarService;
import bg.softuni.xmlprocessingexercisepart2.services.interfaces.CustomerService;
import bg.softuni.xmlprocessingexercisepart2.services.interfaces.SalesService;
import com.google.gson.Gson;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
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
    public boolean exportToXMLSalesStatistics() {
            String EXPORT_RESULT_XML_FILE_PATH =
            "src/main/resources/static/resultsXML/salesStatistics.xml";

        List<Sale> sales = saleRepository.findAll();

        try {

            List<SaleQueryDTO> saleQueryDTOS = new ArrayList<>();

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

                saleQueryDTOS.add(SaleQueryDTO
                    .builder()
                    .car(mapCarEntityToSaleCarQueryDTO(sale.getCar()))
                    .customerName(sale.getCustomer().getName())
                    .discount(sale.getDiscount() / 100.0)
                    .price(priceWithoutDiscount)
                    .priceWithDiscount(priceWithDiscount)
                    .build());
            }

            JAXBContext jaxbContext = JAXBContext.newInstance(SaleRootQueryDTO.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(
                new SaleRootQueryDTO(saleQueryDTOS),
                new File(EXPORT_RESULT_XML_FILE_PATH)
            );

            System.out.println("Successful operation!");
            return true;

        } catch (JAXBException e) {
            System.out.println("An Error occurred while Writing the Data!");
            return false;
        }
    }

    private SaleCarQueryDTO mapCarEntityToSaleCarQueryDTO(Car car) {
        return SaleCarQueryDTO
            .builder()
            .make(car.getMake())
            .model(car.getModel())
            .travelledDistance(car.getTravelledDistance())
            .build();
    }
}
