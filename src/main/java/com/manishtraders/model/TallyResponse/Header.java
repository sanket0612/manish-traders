package com.manishtraders.model.TallyResponse;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Header {
  @Id
  private Long id;

  @JacksonXmlProperty(localName = "STATUS")
  private String status;

  @JacksonXmlProperty(localName = "VERSION")
  private String version;
}
