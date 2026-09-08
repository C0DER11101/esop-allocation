package com.jsp.esop_allocation.esopallocation.repository;

import com.jsp.esop_allocation.esopallocation.model.AllocationModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface AllocationRepository extends JpaRepository<AllocationModel, BigInteger> {
}