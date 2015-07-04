package models.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Inbox;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import models.domain.CountRequest;
import models.domain.CountResponse;
import models.domain.Engine;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by harshitha.suresh on 27/06/2015.
 */
public class FilterEngineActor extends AbstractActor {

    private final LoggingAdapter LOG = Logging.getLogger(context().system(), this);

    private AtomicLong faultlessEngines = new AtomicLong();

    public FilterEngineActor(){
        receive(ReceiveBuilder.match(Engine.class, this::filterEngine).
                match(CountRequest.class, this::sendCount).build());
    }

    private void sendCount(CountRequest countRequest) {
        getContext().sender().tell(new CountResponse(faultlessEngines.longValue()), getContext().self());
    }

    private void filterEngine(Engine engine){
        if(engine.isFaulty()){
            LOG.info("Faulty engine {}", engine);
            return;
        } else {
            LOG.info("Faultless engine {}", engine);
            faultlessEngines.incrementAndGet();
            final ActorRef carAssembler = getContext().actorFor("akka://carfactory/user/carAssembler");
            final Inbox inbox = Inbox.create(getContext().system());
            inbox.send(carAssembler, engine);
        }
    }
}

