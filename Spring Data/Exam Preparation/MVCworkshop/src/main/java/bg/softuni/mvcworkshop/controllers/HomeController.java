package bg.softuni.mvcworkshop.controllers;

import bg.softuni.mvcworkshop.services.interfaces.CompanyService;
import bg.softuni.mvcworkshop.services.interfaces.EmployeeService;
import bg.softuni.mvcworkshop.services.interfaces.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;


@Controller
@RequiredArgsConstructor
public class HomeController {
    private final CompanyService companyService;
    private final EmployeeService employeeService;
    private final ProjectService projectService;

    @GetMapping("")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/home")
    public ModelAndView homePage() {
        ModelAndView modelAndView = new ModelAndView("home");
        long companiesCount = companyService.findCount();
        long employeesCount = employeeService.findCount();
        long projectsCount = projectService.findCount();

        modelAndView.addObject("areImported",
companiesCount > 0 &&
            employeesCount > 0 &&
            projectsCount > 0
        );

        return modelAndView;
    }

    @GetMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    private Map<String, String> test() {
        return Map.of("name", "Peter");
    }
}
