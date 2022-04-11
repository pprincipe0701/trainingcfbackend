package com.incloud.hcp.service;

import com.incloud.hcp.dao.NativeSQL;
import com.incloud.hcp.model.JobExecutionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class NativeSQLRunner {
    private static int MAX_RUM = 100;

    @Autowired
    NativeSQL nativeSQL;

    @Async
    public void startTest() {
        nativeSQL.createConnection();
        for (int i = 0; i < MAX_RUM; i++) {
            JobExecutionStatus job = new JobExecutionStatus();
            job.setJobId(UUID.randomUUID().toString());
            job.setJobName("InstanceCreationJob");
            job.setResult("");
            job.setStartTime(System.currentTimeMillis());
            job.setStatus("In Progress");

            nativeSQL.insertJob(job);

            job.setResult("Instance creation successfully completed");
            job.setStatus("Succeeded");

            nativeSQL.updateJob(job);

            nativeSQL.getJob(job.getJobId());
        }
        nativeSQL.closeConnection();
    }
}
