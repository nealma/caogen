/**
 * Created by neal on 9/22/16.
 */
public class Test {
    public static void main(String[] args) {
        try {
            System.out.println("p 1");
            if(true){
                throw new Exception("1");
            }
            System.out.println("p 2");
        }catch (Exception e){
            System.out.println("p 3");
//            e.printStackTrace();
            System.out.println("p 4");
        }
    }
}
