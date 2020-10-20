package com.manishtraders.model.TallyResponse;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockItem {

  @JacksonXmlProperty(isAttribute=true, localName = "NAME")
  private String name;

  @JacksonXmlProperty(localName = "PARENT")
  @JacksonXmlElementWrapper(useWrapping=false)
  private String parent;

  @JacksonXmlProperty(localName = "HASMFGDATE")
  @JacksonXmlElementWrapper(useWrapping=false)
  private String hasManufacturingDate;

  @JacksonXmlProperty(localName = "CLOSINGBALANCE")
  @JacksonXmlElementWrapper(useWrapping=false)
  private String closingBalance;
}
