package org.teta.controller;

import constants.BatchJobConstants;
import constants.PathConstants;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(PathConstants.API_V1_CHAT)
public class ChatJobController {

    @Qualifier(BatchJobConstants.IMPORT_USER_JOB)
    private final Job job;
    private final JobLauncher jobLauncher;

    @SneakyThrows
    @PostMapping(PathConstants.USER_BATCH_JOB)
    public ResponseEntity<Void> runImportUsersBatchJob() {
        jobLauncher.run(job, new JobParameters());
        return ResponseEntity.noContent().build();
    }
}
