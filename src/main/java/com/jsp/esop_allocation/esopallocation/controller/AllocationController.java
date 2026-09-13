package com.jsp.esop_allocation.esopallocation.controller;

import com.jsp.esop_allocation.esopallocation.dto.AllocationDTO;
import com.jsp.esop_allocation.esopallocation.service.AllocationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Map;
import java.util.List;

@RestController
@AllArgsConstructor
public class AllocationController {

    AllocationService allocationService;

    @PatchMapping(value = "/updateAllocationStatus")
    public void updateAllocationStatus(@RequestBody List<BigInteger> altKeys) {
        allocationService.processUpdateAllocationStatus(altKeys);
    }

    @GetMapping(value = "/sumAllocationNumber/{planId}")
    public @ResponseBody List<Map<String, Object>> sumAllocationNumberByGrantId(@PathVariable("planId") BigInteger planId) {

        return allocationService.processSumAllocationNumberByGrantId(planId);

    }
}