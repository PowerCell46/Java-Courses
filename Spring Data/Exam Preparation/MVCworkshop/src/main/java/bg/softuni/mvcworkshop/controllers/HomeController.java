package bg.softuni.mvcworkshop.controllers;

import bg.softuni.mvcworkshop.services.interfaces.CompanyService;
import bg.softuni.mvcworkshop.services.interfaces.EmployeeService;
import bg.softuni.mvcworkshop.services.interfaces.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


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
    public String homePage(Model model) {
        long companiesCount = companyService.findCount();
        long employeesCount = employeeService.findCount();
        long projectsCount = projectService.findCount();
        model.addAttribute("areImported",
        companiesCount > 0 && employeesCount > 0 && projectsCount > 0);
        return "home";
    }
}
