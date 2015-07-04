package models.actors;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import models.domain.Car;
import models.domain.CountRequest;
import models.domain.CountResponse;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by harshitha.suresh on 30/06/2015.
 */
public class MergerActor extends AbstractActor {
    private final LoggingAdapter LOG = Logging.getLogger(context().system(), this);
    private final AtomicLong count = new AtomicLong();
    public MergerActor(){
        receive(ReceiveBuilder.match(Car.class, this::merge).match(CountRequest.class, this::sendCount).build());
    }

    private void sendCount(CountRequest countRequest) {
        getContext().sender().tell(new CountResponse(count.longValue()), getContext().self());
    }


    private void merge(Car car){
        LOG.info("Rolling out" + car);
        count.incrementAndGet();
    }
}
