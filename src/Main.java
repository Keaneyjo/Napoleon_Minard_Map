import processing.core.PApplet;
import processing.core.PImage;
import processing.data.Table;
import processing.data.TableRow;
import java.lang.Math;
//import processing.pdf.*;

public class Main extends PApplet {
    // Actual values
//    private static final int SCREEN_WIDTH = 1123;
//    private static final int SCREEN_HEIGHT = 794;

    // 3508 x 2480 px
    // 1500, 855
    private static final int SCREEN_WIDTH =  1500; // 1600 //842 // 3508 / 3;
    private static final int SCREEN_HEIGHT =  855; // 800 //595 // 2480 / 3;

    private static final int WIDTH_PADDING = SCREEN_WIDTH / 10;
    private static final int GRAPH_TOP_PADDING = (int) Math.ceil(SCREEN_HEIGHT / 4);
    private static final int GRAPH_BOTTOM_PADDING = (int) Math.ceil(SCREEN_HEIGHT / 2);
    private static final int GRAPH_WIDTH = SCREEN_WIDTH - (WIDTH_PADDING * 2);
    private static final int GRAPH_HEIGHT = SCREEN_HEIGHT - (GRAPH_TOP_PADDING + GRAPH_BOTTOM_PADDING);

    private static final int TABLE_TOP_PADDING = (int) Math.ceil(SCREEN_HEIGHT / 16);
    private static final int TABLE_BOTTOM_PADDING = (int) Math.ceil(SCREEN_HEIGHT / 5);
    private static final int TABLE_WIDTH_PADDING = (int) Math.ceil(SCREEN_WIDTH / 25);

    private static final int TABLE_WIDTH = SCREEN_WIDTH - (TABLE_WIDTH_PADDING * 2);
    private static final int TABLE_HEIGHT = SCREEN_HEIGHT - (TABLE_TOP_PADDING + TABLE_BOTTOM_PADDING);

    public static PApplet processing;
    public static Table table;

    public static PImage fortIcon;

    float [] cityLongitude, cityLatitude, temperatureLongitude, temperature, longitude , latitude;
    String [] cityNames;
    int [] survivors;
    String [] direction;
    int [] division;
    int [] days, dayOfMonth;
    String [] months;
    int N;
    //int i = 0;

    float longitudeMin = Float.MAX_VALUE;
    float longitudeMax = Float.MIN_VALUE;
    float latitudeMin = Float.MAX_VALUE;
    float latitudeMax = Float.MIN_VALUE;

    int currentDivision = 0;
    int strokeColor = 0;


    public static void main (String[] args) {
        System.out.print("Hello World!");
        PApplet.main("Main", args);
    }

    public void setup() {
        table = loadTable("minard-data.csv", "header");
        setTable(table);
        fortIcon = loadImage("simpleWhiteFort23.png");
        background(255);
        drawBackTable();
        AddMap();
        stroke(0);
        strokeWeight(1);
    }

    public void settings() {
        processing = this;
        processing.size(SCREEN_WIDTH, SCREEN_HEIGHT);
        //processing.size(SCREEN_WIDTH, SCREEN_HEIGHT,  "processing.pdf.PGraphicsPDF", "assignment1.2.pdf");
    }

    public void drawBackTable() {
        rect(TABLE_WIDTH_PADDING, TABLE_TOP_PADDING, TABLE_WIDTH, TABLE_HEIGHT);
    }

    public void drawLowerTable() {

    }

    public void setTable(Table table) {
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

        for (int i=0; i<N; i++)
        {
            // LONT	TEMP	DAYS	MON	DAY	LONP	LATP	SURV	DIR	DIV
            TableRow row = table.getRow(i);
            cityLatitude[i] = 90 - row.getFloat("LATC");
            cityLongitude[i] = row.getFloat("LONC");
            cityNames[i] = row.getString("CITY");

            temperatureLongitude[i] = row.getFloat("LONT");
            temperature[i] = row.getFloat("TEMP");
            days[i] = row.getInt("DAYS");
            months[i] = row.getString("MON");
            dayOfMonth[i] = row.getInt("DAY");

            longitude[i] = row.getFloat("LONP");
            latitude[i] = 90 - row.getFloat("LATP");
            survivors[i] = row.getInt("SURV");
            direction[i] = row.getString("DIR");
            division[i] = row.getInt("DIV");

            if(longitude[i] < longitudeMin){
                longitudeMin = longitude[i];
            }

            if(longitude[i] > longitudeMax){
                longitudeMax = longitude[i];
            }

            if(latitude[i] < latitudeMin){
                latitudeMin = latitude[i];
            }

            if(latitude[i] > latitudeMax){
                latitudeMax = latitude[i];
            }
        }


    }



    public void draw()
    {

        for(int i = 0; i < N-1; i++)
        {
            if(direction[i].equals("R"))
            {
                stroke(170, 170, 170);
            }
            else
            {
                stroke(252, 174, 30, 50);
            }
            //strokeJoin(MITER);
            strokeWeight((survivors[i] / 3000) + 5);
            if(division[i] == division[i+1]) {

                float x = normalise(longitude[i], longitudeMin, longitudeMax);
                float x2 = normalise(longitude[i+1], longitudeMin, longitudeMax);
                float y = normalise(latitude[i], latitudeMin, latitudeMax);
                float y2 = normalise(latitude[i+1], latitudeMin, latitudeMax);

                line(scaleToGraph(x, GRAPH_WIDTH, WIDTH_PADDING),
                        scaleToGraph(y, GRAPH_HEIGHT, GRAPH_TOP_PADDING),
                        scaleToGraph(x2, GRAPH_WIDTH, WIDTH_PADDING),
                        scaleToGraph(y2, GRAPH_HEIGHT, GRAPH_TOP_PADDING)
                );
            }
        }




        if(frameCount % 10 == 0) {
            System.out.println(frameCount);
        }

        if(frameCount == 10) {
            drawCities(cityLatitude, cityLongitude, cityNames);
            drawLowerTable();
        }
        if(frameCount == 11) {


            while(true) {

            }
        }

//        if (frameCount == 7) {
//            exit();
//        }

    }

    public void AddMap() {
        // Accurate, just a few towns got cut
        //        PImage map = loadImage("map1500.png");
        //        image(map, 50, 75);
        PImage map = loadImage("maps/satelite1320_400.PNG");
        image(map, 85, 100);
    }
    public void keyPressed() {
        //i++;
    }

    public float normalise(float value, float min, float max) {
        // zi = (xi – min(x)) / (max(x) – min(x))
        return (value - min) / (max - min);
    }

    public void drawCities(float[] lat, float[] lon, String[] names) {
        for(int i = 0; i < CONSTANTS.TOTAL_NUMBER_OF_CITIES; i++)
        {
            float tempX = normalise(lon[i], longitudeMin, longitudeMax);
            float tempY = normalise(lat[i], latitudeMin, latitudeMax);
            tempX = scaleToGraph(tempX, GRAPH_WIDTH, WIDTH_PADDING);
            tempY = scaleToGraph(tempY, GRAPH_HEIGHT, GRAPH_TOP_PADDING);
            image(fortIcon, tempX, tempY - (fortIcon.height/2));

            fill(255);
            text(cityNames[i], tempX - (cityNames[i].length() * 2), tempY + (fortIcon.height/2) + 10);

        }
    }

    public float scaleToGraph(float value, int scale, int padding) {
        return (value * scale) + padding;
    }
}
