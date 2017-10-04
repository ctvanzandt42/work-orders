import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;


import java.io.File;
import java.io.IOException;
import java.util.*;

import static java.util.Collections.EMPTY_SET;

public class Util {

    private Map<Status, Set<WorkOrder>> workOrders = new LinkedHashMap<>();
    private Set<Status> workFlow = new LinkedHashSet<>();
    private static final long MAX_SLEEP_TIME = 5000l;
    private static final String WORK_ORDER_DIRECTORY = "./work-orders/";

    void printWorkOrders() {
        System.out.println("Current work orders:");
        for (Status status : workOrders.keySet()) {
            System.out.println(status);
            System.out.println(workOrders.get(status));
        }
    }

    void moveWorkOrders() {
        System.out.println("Moving work orders");
        for (Status status : workFlow) {
            Set<WorkOrder> pile = workOrders.get(status);
            moveToNextPile(pile, status);
        }
    }

    void moveToNextPile(Set<WorkOrder> pile, Status status) {
        Status nextStatus = status.next();
        for (WorkOrder order : pile) {
            order.setStatus(nextStatus);
        }
        Set<WorkOrder> nextPile = workOrders.get(nextStatus);
        if (nextPile.isEmpty()) {
            workOrders.replace(nextStatus, pile);
        } else {
            nextPile.addAll(pile);
            workOrders.replace(nextStatus, nextPile);
        }
        workOrders.replace(status, EMPTY_SET);
    }

     void readWorkOrders() {
        // read the json files into WorkOrders and put in map
        System.out.println("Searching for new work orders...");
        ObjectMapper mapper = new ObjectMapper();
        File workOrderDirectory = new File(WORK_ORDER_DIRECTORY);
        File files[] = workOrderDirectory.listFiles();
        if (files.length != 0) {
            Set<WorkOrder> initialWorkOrders = new HashSet<>();
            for (File f : files) {
                if (f.getName().endsWith(".json")) {
                    // f is a reference to a json file
                    WorkOrder newWorkOrder = convertJsonFileToObject(f, mapper);
                    // f.delete(); will delete the file
                    f.delete();
                    System.out.println("Work order found:");
                    System.out.println(newWorkOrder);
                    initialWorkOrders.add(newWorkOrder);
                }
            }
            workOrders.put(Status.INITIAL, initialWorkOrders);
        } else {
            System.out.println("No work orders found!");
        }
    }

     static WorkOrder convertJsonFileToObject(File json, ObjectMapper mapper) {
        WorkOrder wo = new WorkOrder();
        try {
            wo = mapper.readValue(json, WorkOrder.class);
        } catch (JsonProcessingException ex) {
            System.out.println("Couldn't convert from JSON");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Couldn't read from file");
            ex.printStackTrace();
        }
        return wo;
    }

     void sleep() {
        try {
            Thread.sleep(MAX_SLEEP_TIME);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

     void init() {
        workOrders.put(Status.INITIAL, EMPTY_SET);
        workOrders.put(Status.ASSIGNED, EMPTY_SET);
        workOrders.put(Status.IN_PROGRESS, EMPTY_SET);
        workOrders.put(Status.DONE, EMPTY_SET);

        workFlow.add(Status.IN_PROGRESS);
        workFlow.add(Status.ASSIGNED);
        workFlow.add(Status.INITIAL);
    }
}
