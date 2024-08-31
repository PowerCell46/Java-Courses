package bg.softuni.mvcworkshop.controllers;

import bg.softuni.mvcworkshop.services.interfaces.CompanyService;
import bg.softuni.mvcworkshop.services.interfaces.EmployeeService;
import bg.softuni.mvcworkshop.services.interfaces.ProjectService;
import jakarta.xml.bind.JAXBException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;


@Controller
@RequiredArgsConstructor
public class ImportController {
    private final CompanyService companyService;
    private final EmployeeService employeeService;
    private final ProjectService projectService;

    @GetMapping("/import/xml")
    public String importXMLpage(Model model) {
        boolean[] areImported = new boolean[]
            {companyService.findCount() > 0, projectService.findCount() > 0, employeeService.findCount() > 0};
        model.addAttribute("areImported", areImported);
        return "xml/import-xml";
    }

    @GetMapping("/import/companies")
    public String importCompaniesPage(Model model) throws IOException {
        model.addAttribute("companies", companyService.getXMLdata());
        return "xml/import-companies";
    }

    @PostMapping("/import/companies")
    public String importCompaniesHandler(String companies) throws JAXBException {
        companyService.seedXMLdata(companies);

        return "redirect:/import/xml";
    }

    @GetMapping("/import/projects")
    public String importProjectsPage(Model model) throws IOException {
        model.addAttribute("projects", projectService.getXMLdata());
        return "xml/import-projects";
    }

    @PostMapping("/import/projects")
    public String importProjectsHandler(String projects) throws JAXBException {
        projectService.seedXMLdata(projects);

        return "redirect:/import/xml";
    }

    @GetMapping("/import/employees")
    public String importEmployeesPage(Model model) throws IOException {
        model.addAttribute("employees", employeeService.getXMLdata());
        return "xml/import-employees";
    }

    @PostMapping("/import/employees")
    public String importEmployeesHandler(String employees) throws JAXBException {
        employeeService.seedXMLdata(employees);

        return "redirect:/import/xml";
    }
}
