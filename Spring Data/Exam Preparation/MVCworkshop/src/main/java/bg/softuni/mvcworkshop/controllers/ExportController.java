package bg.softuni.mvcworkshop.controllers;

import bg.softuni.mvcworkshop.repositories.ProjectRepository;
import bg.softuni.mvcworkshop.services.interfaces.EmployeeService;
import bg.softuni.mvcworkshop.services.interfaces.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ExportController {
    private final ProjectService projectService;
    private final EmployeeService employeeService;


    @GetMapping("/export/project-if-finished")
    private String getFinishedProjectsPage(Model model) {
        model.addAttribute("projectsIfFinished", projectService.getFinishedProjects());
        return "export/export-project-if-finished";
    }

    @GetMapping("/export/employees-above-25")
    public String getEmployeesOlderThan25Page(Model model) {
        model.addAttribute("employeesAbove", employeeService.getOlderThan25());
        return "export/export-employees-with-age";
    }
}
