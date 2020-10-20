package com.manishtraders.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Company {

  @GeneratedValue
  @Id
  private Long id;

  private String endingAt;

  private String startingFrom;

  private String name;

  private String number;
}
