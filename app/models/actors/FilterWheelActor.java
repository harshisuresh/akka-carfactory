package models.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Inbox;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import models.domain.CountRequest;
import models.domain.CountResponse;
import models.domain.Wheel;

import java.util.concurrent.atomic.AtomicLong;


public class FilterWheelActor extends AbstractActor {

    private final LoggingAdapter LOG = Logging.getLogger(context().system(), this);

    private AtomicLong faultlessWheels = new AtomicLong();

    public FilterWheelActor(){
        receive(ReceiveBuilder.match(Wheel.class, this::filterWheel).
                match(CountRequest.class, this::sendCount).build());
    }

    private void sendCount(CountRequest countRequest) {
        getContext().sender().tell(new CountResponse(faultlessWheels.longValue()), getContext().self());
    }

    private void filterWheel(Wheel wheel){
        if(wheel.isFaulty()){
            LOG.info("Faulty wheel {}", wheel);
            return;
        } else {
            LOG.info("Faultless wheel {}", wheel);
            faultlessWheels.incrementAndGet();
            final ActorRef carAssembler = getContext().actorFor("akka://carfactory/user/carAssembler");
            final Inbox inbox = Inbox.create(getContext().system());
            inbox.send(carAssembler, wheel);
        }
    }
}
