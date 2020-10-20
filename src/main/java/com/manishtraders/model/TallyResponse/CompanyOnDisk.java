package com.manishtraders.model.TallyResponse;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyOnDisk {

  @JacksonXmlProperty(localName = "ENDINGAT")
  private EndingAt endingAt;

  @JacksonXmlProperty(localName = "COMPANYNUMBER")
  private CompanyNumber Companynumber;

  @JacksonXmlProperty(localName = "STARTINGFROM")
  private StartingFrom StartingFrom;

  @JacksonXmlProperty(localName = "TALLYKEY")
  private TallyKey tallyKey;

  @JacksonXmlProperty(localName = "ISAGGREGATE")
  private IsAggregate isAggregate;

  @JacksonXmlProperty(localName = "NAME")
  private CompanyName name;

}
