package com.manishtraders.model.TallyResponse;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ledger {
  @JacksonXmlProperty(isAttribute=true, localName = "NAME")
  private String name;
}
