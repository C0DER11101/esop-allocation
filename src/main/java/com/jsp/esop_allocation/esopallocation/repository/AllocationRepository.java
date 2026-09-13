package com.jsp.esop_allocation.esopallocation.repository;

import com.jsp.esop_allocation.esopallocation.model.AllocationModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.math.BigInteger;
import java.util.Map;

public interface AllocationRepository extends JpaRepository<AllocationModel, BigInteger> {

    @Transactional
    @Modifying
    @Query(value = "update AllocationModel set allocationStatus = 'approved' where altKey in :altKeys")
    void updateAllocationStatusToApproved(List<BigInteger> altKeys);

    @Query(value = "select a.grantId as grantId, SUM(a.allocationNumber) as total_alloc from AllocationModel a where a.planId = :planId and a.allocationStatus = 'approved' group by a.grantId")
    List<Map<String, Object>> sumAllocationNumberByGrantId(@Param("planId") BigInteger planId);
}