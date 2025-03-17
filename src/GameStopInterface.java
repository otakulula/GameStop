import java.util.Comparator;

/*
 * This comparator will compare Integer objects using compare().
 */
class IntComparator implements Comparator<Integer> {
    @Override
    public int compare(Integer a, Integer b) {
        int result = 0;
        if( a.equals(b)){
            return result;
        } else if ( a.compareTo(b) > 0){
            result = 1;
        }else{
            result = -1;
        }
        return result;
    }
}

/*
 * This comparator will compare String objects using compare().
 */
class StrComparator implements Comparator<String> {
    @Override
    public int compare(String a, String b) {
        int result = 0;
        if (a.equals(b)) {
            return result;
        } else if ( a.compareTo(b) > 0 ){
            result = 1;
        } else {
            result = -1;
        }
        return result;
    }
}

public class GameStopInterface {

    private StrComparator strCmp = new StrComparator();
    private IntComparator intCmp = new IntComparator();

    public static void main(String[] args) throws Exception {
       System.out.println("Welcome to Game Stop!");
    }
}
