package com.jsp.esop_allocation.esopallocation.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

@Data
@Entity
@Table(name = "esop_allocation")
public class AllocationModel extends AuditModel {

    @Id
    @Column(name = "alt_key")
    private BigInteger altKey;

    @Column(name = "allocation_number")
    private Double allocationNumber; // (grantNumber / frequency); if frequency is 0 then frequency is 5 by default

    @Column(name = "allocation_date")
    private Date allocationDate;

    @Column(name = "allocation_year")
    private String allocationYear; // starts from the next year, and increments by 1 year for each allocation model

    @Column(name = "allocation_status")
    private String allocationStatus;

}