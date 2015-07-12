package models.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Inbox;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import models.domain.CarPart;
import models.domain.CoachWork;
import models.domain.CountRequest;
import models.domain.CountResponse;
import models.domain.Engine;
import models.domain.Message;
import models.domain.Wheel;
import scala.concurrent.java8.FuturesConvertersImpl.P;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.util.ClassUtils;

import com.typesafe.config.ConfigFactory;

/**
 * Created by harshitha.suresh on 27/06/2015.
 */
public class CarPartCreateActor<T extends CarPart> extends AbstractActor {
    private AtomicLong count = new AtomicLong(0);
    private static long MAX;
    private static int faultPercent;
    private final LoggingAdapter LOG = Logging.getLogger(context().system(), this);
    private volatile boolean stop;
    private Class<T> carPartClass;
    private String receiverActor;
    static {
        MAX = ConfigFactory.load("application.conf").getLong("car.part.count");
        faultPercent = ConfigFactory.load("application.conf").getInt("fault.percent");
    }
    public CarPartCreateActor(Class<T> carPartClass, String receiverActor){
        this.carPartClass = carPartClass;
        this.receiverActor = receiverActor;
        receive(
                ReceiveBuilder.match(Message.class, this::handleMessage).
                        match(CountRequest.class, this::sendCount).build());
    }

    private void sendCount(CountRequest countRequest) {
        getContext().sender().tell(new CountResponse(count.longValue()), getContext().self());
    }

    private void handleMessage(Message message){
        if(Message.START.equals(message)){
            LOG.info("Start producing {}", ClassUtils.getShortName(carPartClass));
            produceParts();
        } else{
            LOG.info("Stop producing {}", ClassUtils.getShortName(carPartClass));
            stop = true;
        }
    }
    private void produceParts(){
        final ActorRef filter = getContext().actorFor(receiverActor);
        final Inbox inbox = Inbox.create(getContext().system());
        while(!stop && count.incrementAndGet() < MAX){
            inbox.send(filter, CarPartFactory.createPart(carPartClass));
        }
    }

    static class CarPartFactory {
        private static final Random random = new Random();

        static CarPart createPart(Class<? extends CarPart> carPartClass) {
            if(Engine.class.equals(carPartClass)) {
                return new Engine("Engine-"+ UUID.randomUUID().toString(), generateRandomBoolean(100 - faultPercent ,faultPercent));
            } else if(CoachWork.class.equals(carPartClass)) {
                return new CoachWork("Coachwork-"+ UUID.randomUUID().toString(), generateRandomBoolean(100 - faultPercent ,faultPercent));
            } else if(Wheel.class.equals(carPartClass)) {
                return new Wheel("Wheel-"+ UUID.randomUUID().toString(), generateRandomBoolean(100 - faultPercent ,faultPercent));
            } else {
                throw new IllegalArgumentException("Cannot create car part of class [" + carPartClass + "]");
            }
        }

        private static boolean generateRandomBoolean(double probability_true, double probability_false)
        {
            if(Math.random() * 100 > probability_true)
                return true;
            else
                return false;
        }
    }
}
