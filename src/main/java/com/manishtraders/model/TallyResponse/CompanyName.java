package com.manishtraders.model.TallyResponse;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyName {
  @JacksonXmlProperty(isAttribute = true,localName = "TYPE")
  private String type;

  @JacksonXmlText
  private String companyName;
}
