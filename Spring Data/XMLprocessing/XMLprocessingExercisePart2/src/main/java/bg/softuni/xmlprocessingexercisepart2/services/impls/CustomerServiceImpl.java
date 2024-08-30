package bg.softuni.xmlprocessingexercisepart2.services.impls;

import bg.softuni.xmlprocessingexercisepart2.DTOs.queries.CustomerQueryDTO;
import bg.softuni.xmlprocessingexercisepart2.DTOs.queries.CustomerRootQueryDTO;
import bg.softuni.xmlprocessingexercisepart2.DTOs.queries.CustomerRootSecondQueryDTO;
import bg.softuni.xmlprocessingexercisepart2.DTOs.queries.CustomerSecondQueryDTO;
import bg.softuni.xmlprocessingexercisepart2.DTOs.seedings.CustomerRootSeedDTO;
import bg.softuni.xmlprocessingexercisepart2.DTOs.seedings.CustomerSeedDTO;
import bg.softuni.xmlprocessingexercisepart2.entities.Customer;
import bg.softuni.xmlprocessingexercisepart2.entities.Part;
import bg.softuni.xmlprocessingexercisepart2.entities.Sale;
import bg.softuni.xmlprocessingexercisepart2.repositories.CustomerRepository;
import bg.softuni.xmlprocessingexercisepart2.services.interfaces.CustomerService;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private static final String FILE_PATH = "src/main/resources/static/XML/customers.xml";

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
    public boolean exportToXMLCustomerStatistics() {
         String EXPORT_RESULT_XML_FILE_PATH =
            "src/main/resources/static/resultsXML/customersStatistics.xml";

        Set<Customer> customers = customerRepository.findAllBySalesIsNotEmpty();

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(CustomerRootSecondQueryDTO.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(new CustomerRootSecondQueryDTO(
                customers
                        .stream()
                        .map(customer ->
                            mapCustomerEntityToCustomerSecondQueryDTO(customer)).toList()),
                new File(EXPORT_RESULT_XML_FILE_PATH));

            System.out.println("Successful operation!");
            return true;

        } catch (JAXBException e) {
            System.out.println("An Error occurred while Writing the Data!");
            return false;
        }
    }

    private CustomerSecondQueryDTO mapCustomerEntityToCustomerSecondQueryDTO(Customer customer) {
        return CustomerSecondQueryDTO
            .builder()
            .fullName(customer.getName())
            .spentMoney(calculateSpendMoneyForCustomer(customer.getSales()))
            .boughtCars(customer.getSales().size())
            .build();
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
