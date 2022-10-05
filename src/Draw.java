import processing.data.Table;
import processing.core.PApplet;
import processing.data.TableRow;

public class Draw extends PApplet{
    public Table table;
    float [] cityLongitude, cityLatitude, temperatureLongitude, temperature, longitude , latitude;
    String [] cityNames;
    int [] survivors;
    String [] direction;
    int [] division;
    int [] days, dayOfMonth;
    String [] months;
    int N;

    public void getTable() {
        table = loadTable("minard-data.csv", "header");
        N = table.getRowCount() - 1;

        // LONC	LATC	CITY	LONT	TEMP	DAYS	MON	DAY	LONP	LATP	SURV	DIR	DIV
        cityLongitude = new float[N];
        cityLatitude = new float[N];
        cityNames = new String[N];

        temperatureLongitude = new float[N];
        temperature = new float[N];
        days = new int [N];
        months = new String[N];
        dayOfMonth = new int[N];

        longitude = new float[N];
        latitude = new float[N];
        survivors = new int [N];
        direction = new String[N];
        division = new int [N];

        N = table.getRowCount();
        for (int i=0; i<N; i++)
        {
            TableRow row = table.getRow(i);
            cityLongitude[i] = row.getFloat("LONC");
        }
        System.out.print("done");
    }
}
