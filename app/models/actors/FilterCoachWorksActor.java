package models.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Inbox;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import models.domain.CoachWork;
import models.domain.CountRequest;
import models.domain.CountResponse;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by harshitha.suresh on 27/06/2015.
 */
public class FilterCoachWorksActor extends AbstractActor {
    private final LoggingAdapter LOG = Logging.getLogger(context().system(), this);

    private AtomicLong faultlessCoachworks = new AtomicLong();

    public FilterCoachWorksActor(){
        receive(ReceiveBuilder.match(CoachWork.class, this::filterCoachwork)
                .match(CountRequest.class, this::sendCount).build());
    }

    private void sendCount(CountRequest countRequest) {
        getContext().sender().tell(new CountResponse(faultlessCoachworks.longValue()), getContext().self());
    }

    private void filterCoachwork(CoachWork coachwork){
        if(coachwork.isFaulty()){
            LOG.info("Faulty coachwork {}", coachwork);
            return;
        } else {
            LOG.info("Faultless coachwork {}", coachwork);
            faultlessCoachworks.incrementAndGet();
            final ActorRef carAssembler = getContext().actorFor("akka://carfactory/user/carAssembler");
            final Inbox inbox = Inbox.create(getContext().system());
            inbox.send(carAssembler, coachwork);
        }
    }
}
