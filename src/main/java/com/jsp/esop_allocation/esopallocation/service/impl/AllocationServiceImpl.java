package com.jsp.esop_allocation.esopallocation.service.impl;

import com.jsp.esop_allocation.esopallocation.model.AllocationModel;
import com.jsp.esop_allocation.esopallocation.repository.AllocationRepository;
import com.jsp.esop_allocation.esopallocation.service.AllocationService;
import com.jsp.esop_allocation.esopallocation.util.SequenceGeneratorUtil;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@AllArgsConstructor
public class AllocationServiceImpl implements AllocationService {

    WebClient webClient;

    AllocationRepository allocationRepo;

    @Override
    @PostConstruct
    public void getGrantsForAllocation() {
        Object response = webClient.get()
                .uri("http://localhost:8080/fetchModelWithPlanId/approved/pending/130")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        Map<String, Object> map = (Map) response;

        System.out.println(map);

        processAllocation(map);
    }

    @Override
    //@PostConstruct
    public void processAllocation(Map<String, Object> modelMap) {
        List<Object> modelList = (List) modelMap.get("data");

        AllocationModel allocModel = null;
        //int year = Calendar.YEAR;

        List<BigInteger> grantIdList = new ArrayList<>();

        for(Object map : modelList) {

            Integer frequency = (Integer)((Map)map).get("frequency");
            Integer grantNumber = (Integer)((Map)map).get("grantNumbers");
            String grantDate = (String)((Map)map).get("grantDate");
            Integer grantId = (Integer)(((Map)map).get("altKey"));

            int incYear = 1;

            for(Integer freq = frequency; freq > 0; freq--) {

                String[] components = grantDate.split("T");

                int year = Integer.parseInt(components[0].split("-")[0]) + incYear;
                int month = Integer.parseInt(components[0].split("-")[1]);
                int day = Integer.parseInt(components[0].split("-")[2]);

                incYear++;

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                StringBuilder dateBuilder = new StringBuilder();

                Date date = null;

                try {
                    date = sdf.parse(dateBuilder.append(year).append("-").append(month).append("-").append(day).toString());
                } catch(ParseException e) {
                    e.printStackTrace();
                }

                allocModel = new AllocationModel();
                allocModel.setAltKey(SequenceGeneratorUtil.generateAltKey());
                allocModel.setAllocationNumber(frequency > 0 ? (double) grantNumber / frequency : grantNumber / 5.0);
                allocModel.setAllocationDate(date);
                allocModel.setAllocationYear("" + year);
                allocModel.setAllocationStatus("pending");
                allocModel.setCreatedBy(null);
                allocModel.setModifiedBy(null);
                allocModel.setCreatedDate(null);
                allocModel.setModifiedDate(null);
                allocModel.setGrantId(BigInteger.valueOf(grantId));

                allocationRepo.save(allocModel);
            }

            grantIdList.add(BigInteger.valueOf(grantId));

        }

        webClient.patch()
                .uri("http://localhost:8080/updateAllocationStatus")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(grantIdList)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }

    @Override
    public void processUpdateAllocationStatus(List<BigInteger> altKeys) {
        allocationRepo.updateAllocationStatusToApproved(altKeys);
    }

    @Override
    public List<Map<String, Object>> processSumAllocationNumberByGrantId(BigInteger planId) {
        return allocationRepo.sumAllocationNumberByGrantId(planId);
    }

}