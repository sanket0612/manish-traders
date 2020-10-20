package com.manishtraders.model.TallyResponse;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Collection {

  @JacksonXmlProperty(localName = "COMPANYONDISK")
  @JacksonXmlElementWrapper(useWrapping=false)
  private List<CompanyOnDisk> CompanyOnDisk;
}
