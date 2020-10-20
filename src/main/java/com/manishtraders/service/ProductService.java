package com.manishtraders.service;

import com.manishtraders.model.TallyResponse.Ledger;
import com.manishtraders.model.TallyResponse.StockItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
  public String extractAndSaveProduct(List<StockItem> stockItems) {
    return "Success";
  }
}

