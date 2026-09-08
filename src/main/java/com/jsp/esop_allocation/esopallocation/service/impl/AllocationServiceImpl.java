package com.jsp.esop_allocation.esopallocation.service.impl;

import com.jsp.esop_allocation.esopallocation.model.AllocationModel;
import com.jsp.esop_allocation.esopallocation.repository.AllocationRepository;
import com.jsp.esop_allocation.esopallocation.service.AllocationService;
import com.jsp.esop_allocation.esopallocation.util.SequenceGeneratorUtil;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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
                .uri("http://localhost:8080/fetchModelWithPlanId/approved/pending/2025")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        Map<String, Object> map = (Map) response;

        //System.out.println(map);

        processAllocation(map);
    }

    @Override
    //@PostConstruct
    public void processAllocation(Map<String, Object> modelMap) {
        List<Object> modelList = (List) modelMap.get("data");

        AllocationModel allocModel = null;
        //int year = Calendar.YEAR;

        for(Object map : modelList) {


            Integer frequency = (Integer)((Map)map).get("frequency");
            Integer grantNumber = (Integer)((Map)map).get("grantNumbers");
            //Date acceptedDate = (Date) ((Map)map).get("acceptedDate");

            //year++;


            for(Integer freq = frequency; freq > 0; freq--) {
                allocModel = new AllocationModel();
                allocModel.setAltKey(SequenceGeneratorUtil.generateAltKey());
                allocModel.setAllocationNumber(frequency > 0 ? (double) grantNumber / frequency : grantNumber / 5.0);
                allocModel.setAllocationDate(null);
                allocModel.setAllocationYear(null);
                allocModel.setAllocationStatus("pending");
                allocModel.setCreatedBy(null);
                allocModel.setModifiedBy(null);
                allocModel.setCreatedDate(null);
                allocModel.setModifiedDate(null);

                allocationRepo.save(allocModel);
            }
        }
    }
}