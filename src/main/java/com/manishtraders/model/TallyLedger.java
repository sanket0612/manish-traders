package com.manishtraders.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor
public class TallyLedger {

  @Id
  @GeneratedValue
  private Long id;

  @NonNull
  private String name;
}
