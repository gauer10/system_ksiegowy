package pl.wojtek.system_ksiegowy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.wojtek.system_ksiegowy.Excel.GenerateReport;
import pl.wojtek.system_ksiegowy.model.Contractor;
import pl.wojtek.system_ksiegowy.repository.ContractorRepository;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Controller
public class ContractorsController
{
    private ContractorRepository contractorRepository;

    private GenerateReport generateReport;

    @Autowired
    public ContractorsController(ContractorRepository contractorRepository) {this.contractorRepository = contractorRepository;}

    @Autowired
    public void setGenerateReport(GenerateReport generateReport) {this.generateReport = generateReport;}

    @GetMapping("contractors/add")
    public String addContractor(Model model)
    {
        Contractor contractor = new Contractor();
        model.addAttribute("contractor", contractor);
        return "addContractor";
    }

    @PostMapping("/contractors/add")
    public String addContractor(@ModelAttribute Contractor contractor, BindingResult result, Model model)
    {
        contractorRepository.save(contractor);
        model.addAttribute("contractorAdded", "Kontrahent dodany");
        return "listContractors";
    }

    @RequestMapping("/contractors/list")
    public String contractorsList(Model model)
    {
        List<Contractor> contractors = contractorRepository.findAll();
        model.addAttribute("contractors", contractors);
        return "listContractors";
    }

    @GetMapping("/contractors/list/contractor={id}")
    public String getContractor(@PathVariable(value = "id") Long id, Model model)
    {
        Contractor contractor = contractorRepository.getById(id);
        model.addAttribute("contractor", contractor);

        return "contractorOverview";
    }
    @PostMapping("/contractors/list/contractor={id}")
    public String updateContractor(@PathVariable(value = "id") Long id, Model model, @ModelAttribute Contractor contractor)
    {
        contractorRepository.save(contractor);
        Contractor contractorRepositoryById = contractorRepository.getById(contractor.getId());
        model.addAttribute("contractor", contractorRepositoryById);

        return "contractorOverview";
    }

    @PostMapping("/contractors/list/report")
    public ResponseEntity<InputStreamResource> report() throws Exception
    {
        Date current = new Date();
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        LocalTime time = LocalTime.now();
        String timeToString = time.toString().replace(':', '_');
        String date = formater.format(current) + " " + timeToString;

        List<Contractor> contractors = contractorRepository.findAll();

        generateReport.contractorsReport(contractors, date);

        FileInputStream fis = new FileInputStream(new File("raporty/" + date + ".xlsx"));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=Raport.xlsx");

        return ResponseEntity.ok().contentLength(fis.)
    }
}
