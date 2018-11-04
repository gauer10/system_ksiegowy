package pl.wojtek.system_ksiegowy.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.wojtek.system_ksiegowy.GeneratePdf.InvoiceTemplate;
import pl.wojtek.system_ksiegowy.model.*;
import pl.wojtek.system_ksiegowy.repository.*;

import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
public class InvoicesController
{
    private InvoiceRepository invoiceRepository;

    private ContractorRepository contractorRepository;

    private InvoiceTemplate invoiceTemplate;

    private CompanyRepository companyRepository;

    private ServiceRepository serviceRepository;

    private ContractRepository contractRepository;

    @Autowired
    public InvoicesController(InvoiceRepository invoiceRepository)
    {
        this.invoiceRepository = invoiceRepository;
    }

    @Autowired
    public void setInvoiceTemplate(InvoiceTemplate invoiceTemplate)
    {
        this.invoiceTemplate = invoiceTemplate;
    }

    @Autowired
    public void setContractorRepository(ContractorRepository contractorRepository) {this.contractorRepository = contractorRepository;}

    @Autowired
    public void setCompanyRepository(CompanyRepository companyRepository) {this.companyRepository = companyRepository;}

    @Autowired
    public void setServiceRepository(ServiceRepository serviceRepository) {this.serviceRepository = serviceRepository;}

    @Autowired
    public void setContractRepository(ContractRepository contractRepository) {this.contractRepository = contractRepository;}

    @RequestMapping("/invoices/list")
    public String invoicesList(Model model)
    {
        List<Invoice> invoices = invoiceRepository.findAll();
        model.addAttribute("invoices", invoices);
        return "invoicesList";
    }

    @GetMapping("/invoices/list/invoice={id}")
    public String invoiceOverview(@PathVariable(value = "id") Long id, Model model)
    {
        Invoice invoice = invoiceRepository.getById(id);
        model.addAttribute("invoice", invoice);
        return "invoiceOverview";
    }

    @PostMapping("/invoices/list/invoice={id{")
    public String updateInvoice(@PathVariable(value = "id") Long id, Model model, @ModelAttribute Invoice invoice)
    {
        invoiceRepository.save(invoice);
        model.addAttribute("invoice", invoice);
        return "invoiceOverview";
    }

    @GetMapping("/invoices/issue")
    public String addContractor(Model model)
    {
        Invoice invoice = new Invoice();
        Contractor contractor = new Contractor();
        model.addAttribute("invoice", invoice);
        model.addAttribute("contractor", contractor);
        return "issueInvoice";
    }

    @PostMapping("/invoices/issue")
    public String issueInvoice(@ModelAttribute Contract contract, BindingResult result, Model model) throws Exception
    {
        Contractor contractorRepositoryById = contractorRepository.getById(contract.getContractor().getId());
        Service serviceRepositoryById = serviceRepository.getById(contract.getServices().getId());
        Contract contractRepositoryById = contractRepository.getById(contract.getId());

        Invoice invoice = new Invoice();

        invoice.setContractor(contractorRepositoryById);

        invoice.setNetPrice(contractRepositoryById.getSalary());
        invoice.setGrossPrice(contractRepositoryById.getSalary() * 1.23);

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        invoice.setIssueDate(dateFormat.parse(dateFormat.format(currentDate)));
        Date dueDate = new Date(currentDate.getTime() + TimeUnit.DAYS.toMillis(14));
        invoice.setIssueDate(dateFormat.parse(dateFormat.format(currentDate)));
        invoice.setDueDate(dateFormat.parse(dateFormat.format(dueDate)));

        Company companyRepositoryById = companyRepository.getById(1l);
        invoice.setInvoiceNumber("");
        invoiceRepository.save(invoice);
        invoice.setInvoiceNumber(buildInvoiceNumber(invoice));
        invoiceRepository.save(invoice);
        invoiceTemplate.GenerateInvoice(contractorRepositoryById, companyRepositoryById, invoice, serviceRepositoryById, contractRepositoryById);
        List<Invoice> invoices = invoiceRepository.findAll();
        model.addAttribute("invoices", invoices);
        model.addAttribute("Complete", "Faktua została wystawiona");
        return "redirect:/invoices/list";
    }

    @GetMapping(value = "/invoices/invoice={year}/{month}/{day}/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> showInvoice(@PathVariable ("year") int year, @PathVariable ("month") int month, @PathVariable ("day") int day, @PathVariable ("id") Long id) throws Exception
    {
        String yeartoString = String.valueOf(year);
        String monthToString = String.valueOf(month);
        String dayToString = String.valueOf(day);
        if (month > 0 && month < 10)
            monthToString = "0" + monthToString;
        if (day > 0 && day < 10)
            dayToString = "0" + dayToString;
        FileInputStream fileInputStream = new FileInputStream(new File("faktury/" + year + "/" + monthToString  + "/" + dayToString+ "/" + "FR" + year + "." + monthToString + "." + dayToString+ "_" + id) + ".pdf");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=FR" + year + "." + monthToString + "." + dayToString + "_" + id + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(fileInputStream));
    }

    private String buildInvoiceNumber(Invoice invoice) throws ParseException
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date currentDate = new Date();
        StringBuilder invoiceNumber = new StringBuilder();
        invoiceNumber.append("FR/");
        invoiceNumber.append((dateFormat.format(currentDate)) + "/");
        invoiceNumber.append(invoice.getId());

        return invoiceNumber.toString();
    }
}
