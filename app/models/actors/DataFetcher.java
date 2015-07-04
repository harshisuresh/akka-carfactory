package models.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;
import models.domain.CarFactoryStats;
import models.domain.CountRequest;
import models.domain.CountResponse;

import java.util.Arrays;
import java.util.List;

public class DataFetcher extends AbstractActor {
    private ActorRef requester;
    private CarFactoryStats carFactoryStats = new CarFactoryStats();
    List<String> actors = Arrays.asList("coachworkCreator", "engineCreator", "wheelCreator",
            "coachworkFilter", "engineFilter", "wheelFilter",
            "paintRedActor", "paintBlueActor", "paintGreenActor", "carMerger");
    public DataFetcher() {
        receive(ReceiveBuilder
                .match(CountRequest.class, this::fetchData)
                .match(CountResponse.class, this::reactToResponse)
                .build());
    }

    private void reactToResponse(CountResponse countResponse) {
        switch (getContext().sender().path().name()){
            case "coachworkCreator":
                carFactoryStats.setCoachworksCreated(countResponse.getCount());
                break;
            case "engineCreator":
                carFactoryStats.setEnginesCreated(countResponse.getCount());
                break;
            case "wheelCreator":
                carFactoryStats.setWheelsCreated(countResponse.getCount());
                break;
            case "coachworkFilter":
                carFactoryStats.setFaultlessCoachworks(countResponse.getCount());
                break;
            case "engineFilter":
                carFactoryStats.setFaultlessEngines(countResponse.getCount());
                break;
            case "wheelFilter":
                carFactoryStats.setFaultlessWheels(countResponse.getCount());
                break;
            case "paintRedActor":
                carFactoryStats.setRedCars(countResponse.getCount());
                break;
            case "paintGreenActor":
                carFactoryStats.setGreenCars(countResponse.getCount());
                break;
            case "paintBlueActor":
                carFactoryStats.setBlueCars(countResponse.getCount());
                break;
            case "carMerger":
                carFactoryStats.setCarsCreated(countResponse.getCount());
                break;
            default:
                break;
        }
        if(carFactoryStats.isComplete()) {
            requester.tell(carFactoryStats, getContext().self());
        }
    }

    private void fetchData(CountRequest countRequest) {
        requester = getContext().sender();
        for(String actor : actors) {
            ActorRef actorRef = getContext().actorFor("akka://carfactory/user/"+actor);
            actorRef.tell(new CountRequest(), getContext().self());
        }
    }
}
