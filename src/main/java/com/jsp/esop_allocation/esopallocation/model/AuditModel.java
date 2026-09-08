package com.jsp.esop_allocation.esopallocation.model;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.util.Date;

@MappedSuperclass
@Data
public class AuditModel {
    private Date createdDate;
    private Date modifiedDate;
    private String createdBy;
    private String modifiedBy;
}