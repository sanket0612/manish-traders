package com.manishtraders.model.TallyResponse;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Envelope {

    @JacksonXmlProperty(localName = "HEADER")
    private Header header;

    @JacksonXmlProperty(localName = "BODY")
    private Body body;
}
