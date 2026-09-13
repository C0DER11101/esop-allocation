package com.jsp.esop_allocation.esopallocation.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface AllocationService {

    void getGrantsForAllocation();
    void processAllocation(Map<String, Object> modelMap); // create AllocationModel from the data for frequency (from GrantModel) times
    void processUpdateAllocationStatus(List<BigInteger> altKeys);
    List<Map<String, Object>> processSumAllocationNumberByGrantId(BigInteger planId);
}