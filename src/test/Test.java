import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by neal on 9/22/16.
 */
public class Test {
    public static void main(String[] args) throws ParseException {
//        try {
//            System.out.println("p 1");
//            if(true) throw new Exception("1");
//
//            System.out.println("p 2");
//        }catch (Exception e){
//            System.out.println("p 3");
////            e.printStackTrace();
//            System.out.println("p 4");
//        }

        xx();

    }
    public static void xx() throws ParseException {
        String startDate = "2016-10-13 01:39:41";
        String endDate = "2016-10-13 02:39:41";


        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = f.parse(startDate);
        long lStartDate = d.getTime()/1000;

        f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        d = f.parse(endDate);
        long lEndDate = d.getTime()/1000;

        long count = lEndDate - lStartDate;
        for (int i = 0; i < count; i++) {
            f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println(f.format(new Date((lStartDate + 1 + i) * 1000)));
        }
    }
}
