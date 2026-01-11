package com.vit.lmd.tripMS.ExternalService;

import com.vit.lmd.tripMS.Entity.TaskDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="TASK-SERVICE")
public interface taskMS {

    //create task
    @PostMapping("/tasks/create")
    TaskDTO createTask(@RequestBody TaskDTO taskDTO);
}
