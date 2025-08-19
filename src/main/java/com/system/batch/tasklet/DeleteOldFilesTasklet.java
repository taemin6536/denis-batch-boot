package com.system.batch.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.File;

@Slf4j
public class DeleteOldFilesTasklet implements Tasklet {

    private final String path;
    private final int daysOld;

    public DeleteOldFilesTasklet(
            final String path,
            final int daysOld
    ) {
        this.path = path;
        this.daysOld = daysOld;
    }


    @Override
    public RepeatStatus execute(
            final StepContribution contribution,
            final ChunkContext chunkContext
    ) throws Exception {
        File dir = new File(path);
        long cutoffTime = System.currentTimeMillis() - (daysOld * 24 * 60 * 60 * 1000L);

        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.lastModified() < cutoffTime) {
                    if (file.delete()) {
                        log.info("🔥 파일 삭제: {}", file.getName());
                    } else {
                        log.info("⚠️  파일 삭제 실패: {}", file.getName());
                    }
                }
            }
        }
        return RepeatStatus.FINISHED;
    }
}
