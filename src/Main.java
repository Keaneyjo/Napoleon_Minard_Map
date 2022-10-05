import processing.core.PApplet;

import processing.data.Table;
import processing.data.TableRow;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Main extends PApplet {
    private static final int SCREEN_WIDTH = 1123;
    private static final int SCREEN_HEIGHT = 794;
    private static final int PADDING = 150;
    private static final int GRAPH_WIDTH = SCREEN_WIDTH - (PADDING * 2);
    private static final int GRAPH_HEIGHT = SCREEN_HEIGHT - (PADDING * 2);

    public static PApplet processing;
    public static Table table;

    float [] cityLongitude, cityLatitude, temperatureLongitude, temperature, longitude , latitude;
    String [] cityNames;
    int [] survivors;
    String [] direction;
    int [] division;
    int [] days, dayOfMonth;
    String [] months;
    int N;
    int i = 0;

    float longitudeMin = Float.MAX_VALUE;
    float longitudeMax = Float.MIN_VALUE;
    float latitudeMin = Float.MAX_VALUE;
    float latitudeMax = Float.MIN_VALUE;

    int currentDivision = -1;
    int strokeColor = 0;

    public static void main (String[] args) {
        System.out.print("Hello World!");
        PApplet.main("Main", args);

    }

    public void setup() {
        table = loadTable("minard-data.csv", "header");
        setTable(table);
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
            cityLatitude[i] = row.getFloat("LATC");
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

    public void settings() {
        processing = this;
        processing.size(SCREEN_WIDTH, SCREEN_HEIGHT);


    }

    public void draw() {


        //background(0, 0, 0);
        //for(int i = 0; i < N; i++)
        if(i == N - 1) {
            i = 0;
            background(255);
        }

//        if(currentDivision != division[i]) {
//            currentDivision = division[i];
//            background(255, 255, 255);
//            textSize(120);
//            fill(0, 408, 612);
//            text(division[i], 40, 120);
//        }

        if(currentDivision != division[i]) {
            currentDivision = division[i];
            endShape();
            beginShape();
        }

        //strokeColor = (255 - (direction[i] == "R" ? 100 : 0)) / division[i];
        strokeColor = direction[i] == "R" ? 0 : 50;
        stroke(strokeColor);
        strokeJoin(MITER);
        strokeWeight((survivors[i] / 3000) + 15);


//        rect((z * 800),
//                latitude[i] * 10, survivors[i] / 12000, survivors[i] / 3000);
        //i++;

        if(division[i] == division[i+1]) {
            // zi = (xi – min(x)) / (max(x) – min(x))
            float x = ((longitude[i] - longitudeMin) / (longitudeMax - longitudeMin));
            //float x2 = ((longitude[i+1] - longitudeMin) / (longitudeMax - longitudeMin));
            float y = ((latitude[i] - latitudeMin) / (latitudeMax - latitudeMin));
            //float y2 = ((latitude[i+1] - latitudeMin) / (latitudeMax - latitudeMin));
            //line((x * GRAPH_WIDTH) + PADDING, (y * GRAPH_HEIGHT) + PADDING, (x2 * GRAPH_WIDTH) + PADDING, (y2 * GRAPH_HEIGHT) + PADDING);
            vertex((x * GRAPH_WIDTH) + PADDING, (y * GRAPH_HEIGHT) + PADDING);
        }



        //i++;
//        int upperbound = 500;
//        //generate random values from 0-24
//        int int_random = rand.nextInt(upperbound);
//        line(5,5,100, int_random);



    }

    public void keyPressed() {
        i++;
    }
}
