package pl.wojtek.system_ksiegowy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.wojtek.system_ksiegowy.model.Contract;
import pl.wojtek.system_ksiegowy.model.Contractor;
import pl.wojtek.system_ksiegowy.model.Invoice;
import pl.wojtek.system_ksiegowy.model.Service;
import pl.wojtek.system_ksiegowy.repository.ContractRepository;
import pl.wojtek.system_ksiegowy.repository.ContractorRepository;
import pl.wojtek.system_ksiegowy.repository.InvoiceRepository;
import pl.wojtek.system_ksiegowy.repository.ServiceRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class ContractController
{
    ContractRepository contractRepository;

    ContractorRepository contractorRepository;

    ServiceRepository serviceRepository;

    InvoiceRepository invoiceRepository;

    @Autowired
    public ContractController(ContractRepository contractRepository) {this.contractRepository = contractRepository;}

    @Autowired
    public void setContractorRepository(ContractorRepository contractorRepository) {this.contractorRepository = contractorRepository;}

    @Autowired
    public void setServiceRepository(ServiceRepository serviceRepository) {this.serviceRepository = serviceRepository;}

    @Autowired
    public void setInvoiceRepository(InvoiceRepository invoiceRepository) {this.invoiceRepository = invoiceRepository;}

    @RequestMapping("/contracts/list")
    public String getContractsList(Model model)
    {
        List<Contract> contract = contractRepository.findAll();
        model.addAttribute("allContracts", contract);
        return "listContracts";
    }

    @GetMapping("contracts/add")
    public String addContract(Model model, HttpServletRequest request)
    {
        String id = request.getParameter("contractor_id");
        Contractor contractor = null;
        if (id != null)
            contractor = contractorRepository.getById(Long.parseLong(id));
        System.out.println(contractor);
        List<Service> services = serviceRepository.findAll();
        Contract contract = new Contract();
        model.addAttribute("contract", contract);
        model.addAttribute("contractor", contractor);
        model.addAttribute("allServices", services);
        return "addContract";
    }

    @PostMapping("contracts/add")
    public String addContract(Model model, @ModelAttribute Contract contract, @RequestParam("services") Long id, @ModelAttribute Contractor contractor)
    {
        contract.setContractor(contractor);
        contractRepository.save(contract);
        List<Contract> allContracts = contractRepository.findAll();
        model.addAttribute("contract", contract);
        model.addAttribute("allContracts", allContracts);
        model.addAttribute("contractAdded", "Umowa dodana pomyślnie");
        return "redirect:/contracts/list";
    }

    @GetMapping("/contracts/list/contract={id}")
    public String contractOverview(Model model, @PathVariable("id") Long id)
    {
        Contract contract = contractRepository.getById(id);
        model.addAttribute("contract", contract);
        model.addAttribute("invoice", new Invoice());
        return "contractOverview";
    }

    @PostMapping("/contracts/list/contract={id}")
    public String contractOverview(Model model, @PathVariable("id") Long id, @ModelAttribute Contract contract)
    {
        System.out.println(contract.getStartDate());
        /*contractRepository.save(contract);*/
        Contract contractRepositoryById = contractRepository.getById(id);
        model.addAttribute("contractList", contractRepositoryById);
        return "contractOverview";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
}
