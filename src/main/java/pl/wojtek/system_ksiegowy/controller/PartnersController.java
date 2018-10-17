package pl.wojtek.system_ksiegowy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PartnersController
{
    @GetMapping("partners/add")
    public String addPartner()
    {
        return null;
    }
}
