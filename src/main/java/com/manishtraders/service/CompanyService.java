package com.manishtraders.service;

import com.manishtraders.model.Company;
import com.manishtraders.model.TallyResponse.CompanyOnDisk;
import com.manishtraders.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {

  private final CompanyRepository companyRepository;

  public String extractAndSaveCompanies(List<CompanyOnDisk> companyOnDisk) {
    List<Company> companyList = companyOnDisk
        .stream()
        .map(this::buildCompany).collect(Collectors.toList());
    companyRepository.deleteAll();
    companyRepository.saveAll(companyList);
    return "Success";
  }

  private Company buildCompany(CompanyOnDisk companyOnDisk) {
    return Company.builder()
        .endingAt(companyOnDisk.getEndingAt().getValue())
        .name(companyOnDisk.getName().getCompanyName())
        .number(companyOnDisk.getCompanynumber().getContent())
        .startingFrom(companyOnDisk.getStartingFrom().getCompanyStartingFrom())
        .build();

  }

  public List<Company> getAllCompanies() {
    return companyRepository.findAll();
  }
}
