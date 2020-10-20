package com.manishtraders.model.TallyResponse;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@lombok.Data
public class Data {

  @JacksonXmlProperty(localName = "COLLECTION")
  private Collection Collection;
}
