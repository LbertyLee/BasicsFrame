package com.lh.frame.application.demo.mq;

import com.lh.frame.common.entity.Result;
import com.lh.frame.demo.mq.service.DirectSourceService;
import com.lh.frame.rabbitmq.entity.MqMessage;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/demo")
public class MqSourceController {
    @Resource
    private DirectSourceService directSourceService;


    /**
     * 直接发送消息。
     *
     * @param message 请求体中的消息对象，包含发送的消息内容。
     * @return 返回一个结果对象，表示消息发送的状态。
     */
    @PostMapping("/direct")
    public Result directSendMessage(@RequestBody MqMessage message) {
        // 调用服务，直接发送消息
        directSourceService.sendDirectMessage(message);
        return Result.ok("发送成功");
    }

    /**
     * 生产者消费者模式发送消息。
     * @param message
     * @return
     */
    @PostMapping("/produce")
    public Result produceSendMessage(@RequestBody MqMessage message) {
        directSourceService.ProducerConsumerSentMessage(message);
        return Result.ok("发送成功");
    }

    /**
     * 发布订阅模式发送消息。
     * @param message
     * @return
     */
    @PostMapping("/publish")
    public Result publishSendMessage(@RequestBody MqMessage message) {
        directSourceService.PublishSubscribeSentMessage(message);
        return Result.ok("发送成功");

    }

    /**
     * 向指定主题发送消息。
     *
     * @param message 请求体中的消息内容，类型为MqMessage，包含了消息的具体信息。
     * @return 返回一个Result对象，其中包含了操作结果的状态码和消息，
     *
     */
    @PostMapping("/topic")
    public Result topicSendMessage(@RequestBody MqMessage message) {
        // 直接调用服务层方法，将消息发送到指定主题
        directSourceService.TopicSentMessage(message);
        return Result.ok("发送成功");
    }
}
