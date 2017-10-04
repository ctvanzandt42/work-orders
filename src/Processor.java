public class Processor {
    public static void main(String args[]) {
        Util util = new Util();
        util.init();
        while (true) {
            util.printWorkOrders();
            util.moveWorkOrders();
            util.printWorkOrders();
            util.readWorkOrders();
            util.sleep();
        }
    }
}
