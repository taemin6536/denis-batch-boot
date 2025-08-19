package com.system.batch.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

@Slf4j
public class ZombieProdcessCleanupTasklet implements Tasklet {
    private final int processesToKill = 10; // 예시로 5개 프로세스가 종료되면 시스템 종료
    private int killedProcesses = 0;

    @Override
    public RepeatStatus execute(
            final StepContribution contribution,
            final ChunkContext chunkContext
    ) throws Exception {
        killedProcesses++;
        log.info("☠️  프로세스 강제 종료... ({}/{})", killedProcesses, processesToKill);

        if (killedProcesses >= processesToKill) {
            log.info("시스템 종료를 위한 프로세스 {}개를 모두 강제 종료했습니다. 시스템을 종료합니다.", processesToKill);
            return RepeatStatus.FINISHED;
        }
        return RepeatStatus.CONTINUABLE;
    }
}
