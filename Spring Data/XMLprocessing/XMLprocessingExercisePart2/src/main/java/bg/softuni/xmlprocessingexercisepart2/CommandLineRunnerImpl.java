package bg.softuni.xmlprocessingexercisepart2;

import bg.softuni.xmlprocessingexercisepart2.services.interfaces.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomerService customerService;
    private final SalesService salesService;

    @Override
    public void run(String... args) throws Exception {
        supplierService.seedSuppliers();
        partService.seedParts();
        carService.seedCars();
        customerService.seedCustomers();
        salesService.seedSales();

        customerService.exportToXMLCustomersOrderedByBirthdateAndIsNotYoungDriver();
        carService.exportToXMLCarsByToyotaMakerOrderedModelAscTravelledDistanceDesc();
        supplierService.exportToXMLSuppliersWithPartsNotFromAbroad();
        carService.exportToXMLCarsAndTheirParts();
        customerService.exportToXMLCustomerStatistics();
        salesService.exportToXMLSalesStatistics();
//        System.out.println(result);
    }
}
