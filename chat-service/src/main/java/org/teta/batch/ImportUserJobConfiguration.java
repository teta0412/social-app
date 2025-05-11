package org.teta.batch;

import org.teta.constants.BatchJobConstants;
import org.teta.event.UpdateUserEvent;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageImpl;
import org.springframework.transaction.PlatformTransactionManager;
import org.teta.client.UserClient;
import org.teta.model.User;
import org.teta.service.UserHandlerService;

@Configuration
@RequiredArgsConstructor
public class ImportUserJobConfiguration {

    @Value("${batch.chunkSize}")
    private int chunkSize;

    @Value("${batch.periodOfDays}")
    private int periodOfDays;

    private final UserClient userClient;
    private final UserHandlerService userHandlerService;
    private final PlatformTransactionManager transactionManager;
    private final EntityManagerFactory entityManagerFactory;

    @Bean(BatchJobConstants.IMPORT_USER_JOB)
    public Job importUserDataJob(JobRepository jobRepository) {
        return new JobBuilder(BatchJobConstants.IMPORT_USER_JOB, jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(importUserStep(jobRepository))
                .build();
    }

    private Step importUserStep(JobRepository jobRepository) {
        return new StepBuilder("importUserStep", jobRepository)
                .<UpdateUserEvent, User>chunk(chunkSize, transactionManager)
                .reader(userItemReader())
                .processor(userHandlerService::handleNewOrUpdateUser)
                .writer(userItemWriter())
                .build();
    }

    @Bean
    @StepScope
    UserItemReader userItemReader() {
        return new UserItemReader(pageable -> new PageImpl<>(
                userClient.getBatchUsers(periodOfDays, pageable.getPageNumber(), pageable.getPageSize())),
                chunkSize
        );
    }

    @Bean
    @StepScope
    JpaItemWriter<User> userItemWriter() {
        JpaItemWriter<User> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }
}
