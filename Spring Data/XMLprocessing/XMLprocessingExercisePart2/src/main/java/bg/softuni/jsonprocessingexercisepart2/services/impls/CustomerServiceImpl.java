package bg.softuni.jsonprocessingexercisepart2.services.impls;

import bg.softuni.jsonprocessingexercisepart2.DTOs.exports.CustomerExportDTO;
import bg.softuni.jsonprocessingexercisepart2.DTOs.exports.CustomerStatisticsExportDTO;
import bg.softuni.jsonprocessingexercisepart2.DTOs.exports.SaleExportDTO;
import bg.softuni.jsonprocessingexercisepart2.DTOs.seeds.CustomerSeedDTO;
import bg.softuni.jsonprocessingexercisepart2.entities.Customer;
import bg.softuni.jsonprocessingexercisepart2.entities.Part;
import bg.softuni.jsonprocessingexercisepart2.entities.Sale;
import bg.softuni.jsonprocessingexercisepart2.repositories.CustomerRepository;
import bg.softuni.jsonprocessingexercisepart2.services.interfaces.CustomerService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private static final String FILE_PATH = "src/main/resources/static/JSON/customers.json";

    private final Gson gson;

    @Override
    public void seedCustomers() throws IOException {
        if (customerRepository.count() > 0) return;

        String jsonData = new String(Files.readAllBytes(Path.of(FILE_PATH)));

        CustomerSeedDTO[] customerSeedDTOS = gson.fromJson(jsonData, CustomerSeedDTO[].class);

        for (CustomerSeedDTO customerSeedDTO : customerSeedDTOS) {
            Customer customerEntity = mapCustomerSeedDTOtoCustomerEntity(customerSeedDTO);

            customerRepository.saveAndFlush(customerEntity);
        }
    }

    @Override
    public long findLastId() {
        return customerRepository.count();
    }

    @Override
    public Customer findById(long id) {
        return customerRepository.findById(id).get();
    }

    @Override
    public boolean exportToJSONCustomersOrderedByBirthdateAndIsNotYoungDriver() {
        String EXPORT_RESULT_JSON_FILE_PATH =
            "src/main/resources/static/resultsJSON/customersOrderedByBirthDateAndIsNotYoungDriver.json";

        Set<Customer> customers = customerRepository.getAllOrderByBirthDateAndIsYoungDriver();

        try (FileWriter writer = new FileWriter(EXPORT_RESULT_JSON_FILE_PATH)) {

            List<String> jsonObjectsData = new ArrayList<>();

            for (Customer customer : customers) {
                CustomerExportDTO customerExportDTO = mapCustomerEntityToCustomerExportDTO(customer);
                jsonObjectsData.add(gson.toJson(customerExportDTO));
            }

            writer.write(jsonObjectsData.toString());
            System.out.println("Successful operation!");
            return true;

        } catch (IOException e) {
            System.out.println("An Error occurred while Writing the Data!");
            return false;
        }
    }

    @Override
    public boolean exportToJSONCustomerStatistics() {
         String EXPORT_RESULT_JSON_FILE_PATH =
            "src/main/resources/static/resultsJSON/customersStatistics.json";

        Set<Customer> customers = customerRepository.findAllBySalesIsNotEmpty();

        try (FileWriter writer = new FileWriter(EXPORT_RESULT_JSON_FILE_PATH)) {

            List<String> jsonObjectsData = new ArrayList<>();

            for (Customer customer : customers) {
                CustomerStatisticsExportDTO customerStatisticsExportDTO = CustomerStatisticsExportDTO
                    .builder()
                    .fullName(customer.getName())
                    .boughtCars(customer.getSales().size())
                    .spendMoney(calculateSpendMoneyForCustomer(customer.getSales()))
                    .build();
                jsonObjectsData.add(gson.toJson(customerStatisticsExportDTO));
            }

            writer.write(jsonObjectsData.toString());
            System.out.println("Successful operation!");
            return true;

        } catch (IOException e) {
            System.out.println("An Error occurred while Writing the Data!");
            return false;
        }
    }

    private Customer mapCustomerSeedDTOtoCustomerEntity(CustomerSeedDTO customerSeedDTO) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDate localDateBirthDate = LocalDate.parse(customerSeedDTO.getBirthDate(), formatter);

        return new Customer(
            customerSeedDTO.getName(),
            localDateBirthDate,
            customerSeedDTO.isYoungDriver(),
            Set.of()
        );
    }

    private CustomerExportDTO mapCustomerEntityToCustomerExportDTO(Customer customer) {
        return CustomerExportDTO
                .builder()
                .Id(customer.getId())
                .Name(customer.getName())
                .IsYoungDriver(customer.isYoungDriver())
                .Sales(mapSaleEntitiesToSaleExportDTOs(customer.getSales()))
                .build();
    }

    private List<SaleExportDTO> mapSaleEntitiesToSaleExportDTOs(Set<Sale> sales) {
        List<SaleExportDTO> saleExportDTOS = new ArrayList<>();
        for (Sale sale : sales) {
            saleExportDTOS.add(
                SaleExportDTO.builder()
                    .CarName(sale.getCar().getMake() + " " + sale.getCar().getModel())
                    .Discount(sale.getDiscount())
                    .build());
        }

        return saleExportDTOS;
    }

    private BigDecimal calculateSpendMoneyForCustomer(Set<Sale> sales) {
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (Sale sale : sales) {
            BigDecimal currentCarTotalAmount = BigDecimal.ZERO;

            for (Part part : sale.getCar().getParts()) {
                currentCarTotalAmount = currentCarTotalAmount.add(part.getPrice());
            }

            BigDecimal discountAmount = currentCarTotalAmount
                    .multiply(BigDecimal.valueOf(sale.getDiscount())).divide(BigDecimal.valueOf(100));
            BigDecimal finalAmount = currentCarTotalAmount.subtract(discountAmount);

            totalAmount = totalAmount.add(finalAmount);
        }

        return totalAmount;
    }
}
