package com.manishtraders.controller;

import com.manishtraders.model.Company;
import com.manishtraders.model.PartyDetails;
import com.manishtraders.model.TallyResponse.Envelope;
import com.manishtraders.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/sync")
@RequiredArgsConstructor
public class SyncController {

    private final CompanyService companyService;

    @PostMapping("/echo")
    public ResponseEntity<String> getParties(@RequestBody PartyDetails partyDetails){
        return ResponseEntity.ok().body(partyDetails.getRawData());
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping(){
        return ResponseEntity.ok().body("Able to connect to sync service");
    }

    @PostMapping(value = "/tally", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getCompany(@RequestBody Envelope envelope, @RequestParam(required = false) String dataType){
        if(!StringUtils.isEmpty(dataType) && dataType.equalsIgnoreCase("company")){
            System.out.println(envelope);
            String response  = companyService.extractAndSaveCompanies(envelope.getBody().getData().getCollection().getCompanyOnDisk());
            return ResponseEntity.ok().body(response);
        }else
            return ResponseEntity.ok().body(envelope.toString());
    }

    @GetMapping("/company")
    public ResponseEntity<List<Company>> getAllCompanies(){
        List<Company> companyList = companyService.getAllCompanies();
        return ResponseEntity.ok().body(companyList);
    }
}
