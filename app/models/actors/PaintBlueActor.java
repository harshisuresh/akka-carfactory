package models.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Inbox;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import models.domain.Car;
import models.domain.Colour;
import models.domain.CountRequest;
import models.domain.CountResponse;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by harshitha.suresh on 30/06/2015.
 */
public class PaintBlueActor extends AbstractActor {
    private AtomicLong count = new AtomicLong();
    private final LoggingAdapter LOG = Logging.getLogger(context().system(), this);
    public PaintBlueActor(){
        receive(ReceiveBuilder.match(Car.class, this::paint).match(CountRequest.class, this::sendCount).build());
    }

    private void sendCount(CountRequest countRequest) {
        getContext().sender().tell(new CountResponse(count.longValue()), getContext().self());
    }

    private void paint(Car car){
        LOG.info("Paint car");
        car.setColour(Colour.BLUE);
        count.incrementAndGet();
        final ActorRef carMerger = getContext().actorFor("akka://carfactory/user/carMerger");
        final Inbox inbox = Inbox.create(getContext().system());
        inbox.send(carMerger, car);
    }
}
