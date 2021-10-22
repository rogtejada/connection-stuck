package com.example.connectionstuck;

import java.math.BigInteger;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "users")
public class User implements Persistable<BigInteger> {

  @Id
  @Column("id")
  private BigInteger id;

  @Column("name")
  private String name;

  @Transient
  private boolean newEntity;

  @Override
  public BigInteger getId() {
    return id;
  }

  public void setId(BigInteger id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setNew(boolean newEntity) {
      this.newEntity = newEntity;
  }

  @Override
  public boolean isNew() {
    return this.newEntity;
  }
}
