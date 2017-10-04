import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

public class Creator {
    public static void main(String args[]) {
        Creator creator = new Creator();
        creator.createWorkOrders();
    }

    private final String WORK_ORDER_DIRECTORY = "./work-orders/";

    private void createJsonFile(WorkOrder order, ObjectMapper mapper) {
        File filename = new File(WORK_ORDER_DIRECTORY + String.valueOf(order.getId()) + ".json");
        System.out.print(".");

        try (FileWriter fileWriter = new FileWriter(filename)) {
            String json = mapper.writeValueAsString(order);
            fileWriter.write(json);
            System.out.print("Work order created!\n");
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void createWorkOrders() {
        Scanner scanner = new Scanner(System.in);
        ObjectMapper mapper = new ObjectMapper();
        while (true) {
            waitForUser(scanner);
            WorkOrder newWorkOrder = getWorkOrderFromUser(scanner);
            System.out.print(".");
            createJsonFile(newWorkOrder, mapper);
        }
    }

    private void waitForUser(Scanner sc) {
        System.out.println("Click enter to add work orders!\n");
        sc.nextLine();
    }

    private WorkOrder getWorkOrderFromUser(Scanner sc) {
        System.out.println("Create new work order!");
        WorkOrder order = new WorkOrder();
        order.setId(Math.abs((int) System.currentTimeMillis()));
        System.out.printf("Work Order: Id# "+ order.getId() + "\n");
        System.out.print("Description: ");
        order.setDescription(sc.nextLine());
        System.out.print("Name of Creator: ");
        order.setSenderName(sc.nextLine());
        order.setStatus(Status.INITIAL);
        System.out.print("Creating work order....");

        return order;
    }



}

