package com.jsp.esop_allocation.esopallocation.service;

import java.util.Map;

public interface AllocationService {

    void getGrantsForAllocation();
    void processAllocation(Map<String, Object> modelMap); // create AllocationModel from the data for frequency (from GrantModel) times
}