package bg.softuni.jsonprocessingexercisepart2.services.impls;

import bg.softuni.jsonprocessingexercisepart2.DTOs.queries.CustomerQueryDTO;
import bg.softuni.jsonprocessingexercisepart2.DTOs.exports.CustomerStatisticsExportDTO;
import bg.softuni.jsonprocessingexercisepart2.DTOs.exports.SaleExportDTO;
import bg.softuni.jsonprocessingexercisepart2.DTOs.queries.CustomerRootQueryDTO;
import bg.softuni.jsonprocessingexercisepart2.DTOs.seedings.CustomerRootSeedDTO;
import bg.softuni.jsonprocessingexercisepart2.DTOs.seedings.CustomerSeedDTO;
import bg.softuni.jsonprocessingexercisepart2.entities.Customer;
import bg.softuni.jsonprocessingexercisepart2.entities.Part;
import bg.softuni.jsonprocessingexercisepart2.entities.Sale;
import bg.softuni.jsonprocessingexercisepart2.repositories.CustomerRepository;
import bg.softuni.jsonprocessingexercisepart2.services.interfaces.CustomerService;
import com.google.gson.Gson;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private static final String FILE_PATH = "src/main/resources/static/XML/customers.xml";

    private final Gson gson;

    @Override
    public void seedCustomers() throws JAXBException {
        if (customerRepository.count() > 0) return;

        JAXBContext jaxbContext = JAXBContext.newInstance(CustomerRootSeedDTO.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        CustomerRootSeedDTO customerRootSeedDTO = (CustomerRootSeedDTO) unmarshaller.unmarshal(new File(FILE_PATH));

        customerRootSeedDTO
            .getCustomers()
            .stream()
            .forEach(customerSeedDTO ->
                    customerRepository.saveAndFlush(mapCustomerSeedDTOtoCustomerEntity(customerSeedDTO)
                )
            );
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
    public boolean exportToXMLCustomersOrderedByBirthdateAndIsNotYoungDriver() {
        String EXPORT_RESULT_XML_FILE_PATH =
            "src/main/resources/static/resultsXML/customersOrderedByBirthDateAndIsNotYoungDriver.xml";

        Set<Customer> customers = customerRepository.getAllOrderByBirthDateAndIsYoungDriver();

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(CustomerRootQueryDTO.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(
                new CustomerRootQueryDTO(
                        customers
                        .stream()
                        .map(customer ->
                            mapCustomerEntityToCustomerQueryDTO(customer)).toList()
                ),
                new File(EXPORT_RESULT_XML_FILE_PATH));

            System.out.println("Successful operation!");
            return true;

        } catch (JAXBException e) {
            System.out.println("An Error occurred while Writing the Data!");
            return false;
        }
    }

    @Override
    public boolean exportToJSONCustomerStatistics() {
         String EXPORT_RESULT_JSON_FILE_PATH =
            "src/main/resources/static/resultsXML/customersStatistics.xml";

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
        LocalDate localDateBirthDate = LocalDate.parse(customerSeedDTO.getBirthdate(), formatter);

        return new Customer(
            customerSeedDTO.getName(),
            localDateBirthDate,
            customerSeedDTO.isYoungDriver(),
            Set.of()
        );
    }

    private CustomerQueryDTO mapCustomerEntityToCustomerQueryDTO(Customer customer) {
        return CustomerQueryDTO
                .builder()
                .id(customer.getId())
                .name(customer.getName())
                .isYoungDriver(customer.isYoungDriver())
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
