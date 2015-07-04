package models.actors;

import static org.springframework.util.ClassUtils.getShortName;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Inbox;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import models.domain.CarPart;
import models.domain.CountRequest;
import models.domain.CountResponse;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.util.ClassUtils;

/**
 * Created by harshitha.suresh on 04/07/2015.
 */
public abstract class AbstractFilterActor<T extends CarPart> extends AbstractActor {

    private final LoggingAdapter LOG = Logging.getLogger(context().system(), this);

    private AtomicLong faultlessParts = new AtomicLong();

    public AbstractFilterActor(){
        receive(ReceiveBuilder.match(getReference(), this::filterCarPart)
                .match(CountRequest.class, this::sendCount).build());
    }

    abstract Class<T> getReference();

    abstract String receiveActorName();

    private void sendCount(CountRequest countRequest) {
        getContext().sender().tell(new CountResponse(faultlessParts.longValue()), getContext().self());
    }

    private void filterCarPart(T carPart){
        if(carPart.isFaulty()){
            LOG.info("Faulty {} {}", getShortName(getReference()), carPart);
            return;
        } else {
            LOG.info("Faultless {} {}", getShortName(getReference()), carPart);
            faultlessParts.incrementAndGet();
            final ActorRef carAssembler = getContext().actorFor(receiveActorName());
            final Inbox inbox = Inbox.create(getContext().system());
            inbox.send(carAssembler, carPart);
        }
    }
}
