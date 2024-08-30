package bg.softuni.jsonprocessingexercisepart2;

import bg.softuni.jsonprocessingexercisepart2.services.interfaces.*;
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

//        boolean result = customerService.exportToXMLCustomersOrderedByBirthdateAndIsNotYoungDriver();
//        boolean result = carService.exportToXMLCarsByToyotaMakerOrderedModelAscTravelledDistanceDesc();
//        boolean result = supplierService.exportToXMLSuppliersWithPartsNotFromAbroad();
        boolean result = carService.exportToXMLCarsAndTheirParts();
//        boolean result = customerService.exportToJSONCustomerStatistics();
//        boolean result = salesService.exportToJSONSalesStatistics();
        System.out.println(result);
    }
}
