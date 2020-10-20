package com.manishtraders.controller;

import com.manishtraders.model.Company;
import com.manishtraders.model.PartyDetails;
import com.manishtraders.model.TallyLedger;
import com.manishtraders.model.TallyResponse.Envelope;
import com.manishtraders.service.CompanyService;
import com.manishtraders.service.LedgerService;
import com.manishtraders.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sync")
@RequiredArgsConstructor
public class SyncController {

    private final CompanyService companyService;
    private final LedgerService ledgerService;
    private final ProductService productService;

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
        if(StringUtils.isEmpty(dataType))
            return ResponseEntity.ok().body(envelope.toString());
        else if(dataType.equalsIgnoreCase("company")){
            String response  = companyService.extractAndSaveCompanies(envelope.getBody().getData().getCollection().getCompanyOnDisk());
            return ResponseEntity.ok().body(response);
        }
        else if(dataType.equalsIgnoreCase("ledger")){
            String response = ledgerService.extractAndSaveLedger(envelope.getBody().getData().getCollection().getLedgerList());
            return ResponseEntity.ok().body(response);
        }else if(dataType.equalsIgnoreCase("product")){
            String response = productService.extractAndSaveProduct(envelope.getBody().getData().getCollection().getStockItemList());
            return ResponseEntity.ok().body(response);
        }
        return ResponseEntity.ok().body("Could Not Parse");
    }

    @GetMapping("/ledger")
    public ResponseEntity<List<TallyLedger>> getAllLedgers(){
        List<TallyLedger> tallyLedgers = ledgerService.getAllLedgers();
        return ResponseEntity.ok().body(tallyLedgers);
    }

    @DeleteMapping("/company")
    public ResponseEntity<String> deleteAllCompanies(){
        String response = companyService.deleteAllCompanies();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/company")
    public ResponseEntity<List<Company>> getAllCompanies(){
        List<Company> companyList = companyService.getAllCompanies();
        return ResponseEntity.ok().body(companyList);
    }
}
