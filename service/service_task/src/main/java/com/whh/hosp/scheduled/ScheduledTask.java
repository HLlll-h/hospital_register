package com.whh.hosp.scheduled;

import com.whh.hosp.rabbit.constant.MqConst;
import com.whh.hosp.rabbit.service.RabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class ScheduledTask {

    @Autowired
    private RabbitService rabbitService;

    //每天8点执行方法，就医提醒
    //cron表达式，设置执行间隔
    //0 0 8 * * ?
    @Scheduled(cron = "0/30 * * * * ?")
    public void taskPatient() {
        rabbitService.sendMessage(MqConst.EXCHANGE_DIRECT_TASK,MqConst.ROUTING_TASK_8,"");
    }

}
