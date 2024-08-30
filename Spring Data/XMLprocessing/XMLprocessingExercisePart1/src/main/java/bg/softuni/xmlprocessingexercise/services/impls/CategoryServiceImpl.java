package bg.softuni.xmlprocessingexercise.services.impls;

import bg.softuni.xmlprocessingexercise.DTOs.querying.CategoryQueryDTO;
import bg.softuni.xmlprocessingexercise.DTOs.querying.CategoryRootQueryDTO;
import bg.softuni.xmlprocessingexercise.DTOs.repositoryQueries.CategoryStatisticsDTO;
import bg.softuni.xmlprocessingexercise.DTOs.seeding.CategoryRootSeedDTO;
import bg.softuni.xmlprocessingexercise.DTOs.seeding.CategorySeedDTO;
import bg.softuni.xmlprocessingexercise.entities.Category;
import bg.softuni.xmlprocessingexercise.repositories.CategoryRepository;
import bg.softuni.xmlprocessingexercise.services.interfaces.CategoryService;
import bg.softuni.xmlprocessingexercise.utils.ValidationUtil;
import jakarta.xml.bind.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private static final String FILE_PATH = "src/main/resources/static/XML/categories.xml";

    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Override
    public void seedCategories() throws JAXBException {
        if (this.categoryRepository.count() > 0) return;

        JAXBContext jaxbContext = JAXBContext.newInstance(CategoryRootSeedDTO.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        CategoryRootSeedDTO categoryRootSeedDTO = (CategoryRootSeedDTO) unmarshaller.unmarshal(new File(FILE_PATH));

        for (CategorySeedDTO categorySeedDTO : categoryRootSeedDTO.getCategories()) {
            if (this.validationUtil.isValid(categorySeedDTO)) {
                // if valid -> save to the DB
                Category categoryE = this.modelMapper.map(categorySeedDTO, Category.class);
                this.categoryRepository.saveAndFlush(categoryE);

            } else {
                // if invalid -> print the error message
                this.validationUtil
                    .getViolations(categorySeedDTO)
                    .forEach(validation -> System.out.println(validation.getMessage()));
            }
        }
    }

    @Override
    public long getLastPossibleId() {
        return categoryRepository.count();
    }

    @Override
    public Category findById(long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public boolean exportToXMLCategoriesStatistics() {
        String EXPORT_RESULT_XML_FILE_PATH =
            "src/main/resources/static/resultXML/categoriesStatistics.xml";

        try {
            CategoryRootQueryDTO categoryRootQueryDTO = new CategoryRootQueryDTO(
            this.categoryRepository
                    .getCategoriesStatistics()
                    .stream()
                    .map((Object[] obj) -> CategoryStatisticsDTO
                        .builder()
                        .name((String) obj[0])
                        .numberOfProducts((Long) obj[1])
                        .averagePrice((BigDecimal) obj[2])
                        .totalRevenue((BigDecimal) obj[3])
                        .build()
                    )
                    .map((CategoryStatisticsDTO categoryStat) -> CategoryQueryDTO
                        .builder()
                        .name(categoryStat.getName())
                        .productsCount(categoryStat.getNumberOfProducts())
                        .averagePrice(categoryStat.getAveragePrice())
                        .totalRevenue(categoryStat.getTotalRevenue())
                        .build())
                    .toList()
            );

            JAXBContext jaxbContext = JAXBContext.newInstance(CategoryRootQueryDTO.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(categoryRootQueryDTO, new File(EXPORT_RESULT_XML_FILE_PATH));

            System.out.println("Successful operation!");
            return true;

        } catch (JAXBException e) {
            System.out.println("An Error occurred while Writing the Data!");
            return false;
        }
    }
}
