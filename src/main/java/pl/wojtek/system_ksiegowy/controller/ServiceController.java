package pl.wojtek.system_ksiegowy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.wojtek.system_ksiegowy.model.Service;
import pl.wojtek.system_ksiegowy.repository.ServiceRepository;

import java.util.List;

@Controller
public class ServiceController
{
    ServiceRepository serviceRepository;

    @Autowired
    public ServiceController(ServiceRepository serviceRepository) {this.serviceRepository = serviceRepository;}

    @RequestMapping("/services/list")
    public String showServices(Model model)
    {
        List<Service> services = serviceRepository.findAll();
        model.addAttribute("services", services);
        return "servicesList";
    }

    @GetMapping("services/add")
    public String addService(Model model)
    {
        model.addAttribute("service", new Service());
        return "addService";
    }

    @PostMapping("services/add")
    public String addService(Model model, @ModelAttribute Service service)
    {
        List<Service> services = serviceRepository.findAll();
        serviceRepository.save(service);
        model.addAttribute("services", services);
        model.addAttribute("serviceAdded", "Usługa dodana pomyślnie do biblioteki");
        return "servicesList";
    }

    @GetMapping("/services/list/service={id}")
    public String showService(Model model, @PathVariable(value = "id") Long id)
    {
        Service serviceRepositoryById = serviceRepository.getById(id);
        model.addAttribute("service", serviceRepositoryById);
        return "serviceOverview";
    }

    @PostMapping("/services/list/service={id}")
    public String showService(Model model, @PathVariable(value = "id") Long id, @ModelAttribute Service service)
    {
        serviceRepository.save(service);
        Service serviceRepositoryById = serviceRepository.getById(id);
        model.addAttribute("service", serviceRepositoryById);
        return "serviceOverview";
    }
}
