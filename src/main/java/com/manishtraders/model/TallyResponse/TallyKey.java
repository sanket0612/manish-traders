package com.manishtraders.model.TallyResponse;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TallyKey {

  @Id
  private Long id;

  @JacksonXmlProperty(isAttribute=true,localName = "TYPE")
  private String type;

  @JacksonXmlText
  private String content;
}
