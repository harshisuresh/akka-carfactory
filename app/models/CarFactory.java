package models;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import models.actors.*;
import models.domain.CountRequest;
import models.domain.Message;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

/**
 * Created by harshitha.suresh on 27/06/2015.
 */
public class CarFactory {

    public static void main(String args[]) throws Exception {
        ActorSystem system = startCarFactory();
        Thread.sleep(5000);
        ActorRef actor = system.actorFor("akka://carfactory/user/dataFetcher");
        Timeout timeout = new Timeout(Duration.create(5000, "seconds"));
        Future<Object> ask = Patterns.ask(actor, new CountRequest(), timeout);
        Object result = Await.result(ask, timeout.duration());
        System.out.println(result.toString());

    }

    public static ActorSystem startCarFactory() {
        final ActorSystem system = ActorSystem.create("carfactory");

        final ActorRef engineCreator = system.actorOf(Props.create(CreateEngineActor.class), "engineCreator");
        final ActorRef coachworkCreator = system.actorOf(Props.create(CreateCoachworkActor.class), "coachworkCreator");
        final ActorRef wheelCreator = system.actorOf(Props.create(CreateWheelActor.class), "wheelCreator");

        system.actorOf(Props.create(FilterEngineActor.class), "engineFilter");
        system.actorOf(Props.create(FilterCoachWorksActor.class), "coachworkFilter");
        system.actorOf(Props.create(FilterWheelActor.class), "wheelFilter");


        system.actorOf(Props.create(PaintBlueActor.class), "paintBlueActor");
        system.actorOf(Props.create(PaintGreenActor.class), "paintGreenActor");
        system.actorOf(Props.create(PaintRedActor.class), "paintRedActor");

        system.actorOf(Props.create(AssembleCarActor.class), "carAssembler");
        system.actorOf(Props.create(DataFetcher.class), "dataFetcher");
        system.actorOf(Props.create(MergerActor.class), "carMerger");
        engineCreator.tell(Message.START, ActorRef.noSender());
        coachworkCreator.tell(Message.START, ActorRef.noSender());
        wheelCreator.tell(Message.START, ActorRef.noSender());
        return system;
    }
}
