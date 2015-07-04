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
import models.domain.Engine;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by harshitha.suresh on 27/06/2015.
 */
public class FilterEngineActor extends AbstractFilterActor<Engine> {

    public FilterEngineActor(){}

    @Override
    Class<Engine> getReference() {
        return Engine.class;
    }

    @Override
    String receiveActorName() {
        return "akka://carfactory/user/carAssembler";
    }
}

