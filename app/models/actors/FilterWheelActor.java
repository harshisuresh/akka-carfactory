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
import models.domain.Wheel;

import java.util.concurrent.atomic.AtomicLong;


public class FilterWheelActor extends AbstractFilterActor<Wheel> {

    public FilterWheelActor(){}

    @Override
    Class<Wheel> getReference() {
        return Wheel.class;
    }

    @Override
    String receiveActorName() {
        return "akka://carfactory/user/carAssembler";
    }
}
