package com.manishtraders.model.TallyResponse;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@lombok.Data
public class Body {

  @JacksonXmlProperty(localName = "DATA")
  private Data data;

//  @JacksonXmlProperty(localName = "DESC")
  private Desc desc;
}
