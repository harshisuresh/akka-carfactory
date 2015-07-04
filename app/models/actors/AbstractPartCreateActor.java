package models.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Inbox;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import models.domain.CountRequest;
import models.domain.CountResponse;
import models.domain.Message;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.util.ClassUtils;

/**
 * Created by harshitha.suresh on 27/06/2015.
 */
public abstract class AbstractPartCreateActor<T> extends AbstractActor {
    private AtomicLong count = new AtomicLong(0);
    private long MAX = 20;
    private final LoggingAdapter LOG = Logging.getLogger(context().system(), this);
    private volatile boolean stop;
    private final Random random = new Random();
    public AbstractPartCreateActor(){
        receive(
                ReceiveBuilder.match(Message.class, this::handleMessage).
                        match(CountRequest.class, this::sendCount).build());
    }

    private void sendCount(CountRequest countRequest) {
        getContext().sender().tell(new CountResponse(count.longValue()), getContext().self());
    }

    private void handleMessage(Message message){
        if(Message.START.equals(message)){
            LOG.info("Start producing {}", ClassUtils.getShortName(getReference()));
            produceParts();
        } else{
            LOG.info("Stop producing {}", ClassUtils.getShortName(getReference()));
            stop = true;
        }
    }

    abstract Class<T> getReference();

    abstract T createPart();

    abstract String receiveActorName();

    private void produceParts(){
        final ActorRef filter = getContext().actorFor(receiveActorName());
        final Inbox inbox = Inbox.create(getContext().system());
        while(!stop && count.incrementAndGet() < MAX){
            inbox.send(filter, createPart());
        }
    }
}
