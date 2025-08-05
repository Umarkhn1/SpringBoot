package WebSocket.Java.Component;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SendDate {
    private final SimpMessagingTemplate template;

    public SendDate(SimpMessagingTemplate template) {
        this.template = template;
    }

    @Scheduled(fixedDelay = 5000L)
    public void getTime() {
        Date date = new Date();
        template.convertAndSend("/topic/date",date);
    }

    public void type(){
        template.convertAndSend("/topic/type","typing...");
    }
    public void online(){
        template.convertAndSend("/topic/online","online");
    }

}
