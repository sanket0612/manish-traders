package com.manishtraders.model.TallyResponse;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Desc {
  @JacksonXmlProperty(localName = "CMPINFO")
  private Cmpinfog Cmpinfog;
}
