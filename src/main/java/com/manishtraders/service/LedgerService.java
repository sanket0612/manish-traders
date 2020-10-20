package com.manishtraders.service;

import com.manishtraders.model.TallyLedger;
import com.manishtraders.model.TallyResponse.Ledger;
import com.manishtraders.repository.LedgerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LedgerService {

  private final LedgerRepository ledgerRepository;

  public String extractAndSaveLedger(List<Ledger> ledgerList) {
    List<TallyLedger> tallyLedgers = ledgerList.stream()
        .map(this::buildTallyLegder)
        .collect(Collectors.toList());
    ledgerRepository.saveAll(tallyLedgers);
    return "Success";
  }

  public List<TallyLedger> getAllLedgers(){
    return ledgerRepository.findAll();
  }

  private TallyLedger buildTallyLegder(Ledger ledger){
    return TallyLedger.builder()
        .name(ledger.getName())
        .build();
  }

}
