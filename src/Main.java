import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.data.Table;
import processing.data.TableRow;

import java.awt.*;
import java.lang.Math;
//import processing.pdf.*;

public class Main extends PApplet {
    // Actual values
//    private static final int SCREEN_WIDTH = 1123;
//    private static final int SCREEN_HEIGHT = 794;

//    void settings() {
//        final int width = displayWidth/6*5;
//        final int height = displayHeight/6*5;
//        minTempY = height - height/15;
//        maxTempY = height - height/4;
//        minTempX = width/5;
//        maxTempX = width - width/20;
//
//        size(width, height);
//    }

    // 3508 x 2480 px
    // 1500, 855

//    private static final int SCREEN_WIDTH =  1490; // 1600 //842 // 3508 / 3;
//    private static final int SCREEN_HEIGHT =  855; // 800 //595 // 2480 / 3;

    private static final int SCREEN_WIDTH =  1920; // 1600 //842 // 3508 / 3;
    private static final int SCREEN_HEIGHT =  1080; // 800 //595 // 2480 / 3;

    // map is 85 ->, 40 down
    // image is 1320 x 475 px
    // 1320 + 85 + 85 = 1490
    private static final int MAP_IMAGE_SIDE_PADDING =  85; //300;
    private static final int MAP_IMAGE_TOP_PADDING = 40; // 190;

    private static final int WIDTH_PADDING = SCREEN_WIDTH / 10;
    private static final int GRAPH_TOP_PADDING = (int) Math.ceil(SCREEN_HEIGHT / 4);
    private static final int GRAPH_BOTTOM_PADDING = (int) Math.ceil(SCREEN_HEIGHT / 2);
    private static final int GRAPH_WIDTH = SCREEN_WIDTH - (WIDTH_PADDING * 2) - 55;
    private static final int GRAPH_HEIGHT = SCREEN_HEIGHT - (GRAPH_TOP_PADDING + GRAPH_BOTTOM_PADDING);

    private static final int TABLE_TOP_PADDING = MAP_IMAGE_TOP_PADDING - 1;//(int) Math.ceil(SCREEN_HEIGHT / 16);
    private static final int TABLE_BOTTOM_PADDING = (int) Math.ceil(SCREEN_HEIGHT / 5);
    private static final int TABLE_WIDTH_PADDING = MAP_IMAGE_SIDE_PADDING - 1;//(int) Math.ceil(SCREEN_WIDTH / 25);

    private static final int TABLE_WIDTH = SCREEN_WIDTH - (TABLE_WIDTH_PADDING * 2);
    private static final int TABLE_HEIGHT = GRAPH_TOP_PADDING + GRAPH_HEIGHT + 40;
    // SCREEN_HEIGHT - (TABLE_TOP_PADDING); //+ TABLE_BOTTOM_PADDING

    private static final int LOWER_TABLE_TOP_PADDING = TABLE_HEIGHT + TABLE_TOP_PADDING;
    private static final int LOWER_TABLE_WIDTH_PADDING = TABLE_WIDTH_PADDING;
    private static final int LOWER_TABLE_HEIGHT = SCREEN_HEIGHT - (TABLE_TOP_PADDING + TABLE_HEIGHT + 85);
    private static final int LOWER_TABLE_WIDTH = TABLE_WIDTH;

    private static final int LOWER_TABLE_TEXT_HEIGHT = 70;



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

    float tempMin = Float.MAX_VALUE;
    float tempMax = Float.MIN_VALUE;
    float tempLongMin = Float.MAX_VALUE;
    float tempLongMax = Float.MIN_VALUE;

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

        AddMap();

        drawBackTable();
        drawLowerTable();
        drawTemperatureTable();

        stroke(0);
        strokeWeight(1);
    }

    public void settings() {
        processing = this;
        processing.size(SCREEN_WIDTH, SCREEN_HEIGHT);
        //processing.size(SCREEN_WIDTH, SCREEN_HEIGHT,  "processing.pdf.PGraphicsPDF", "assignment1.2.pdf");
    }

    public void drawBackTable() {
        noFill();
        rect(TABLE_WIDTH_PADDING, TABLE_TOP_PADDING, TABLE_WIDTH, TABLE_HEIGHT);
    }

    public void drawLowerTable() {
        stroke(1);
        strokeWeight(1); // 4 is the default
        noFill();
        rect(LOWER_TABLE_WIDTH_PADDING, LOWER_TABLE_TOP_PADDING, LOWER_TABLE_WIDTH, LOWER_TABLE_HEIGHT);
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

            if(temperature[i] < tempMin) {
                tempMin = temperature[i];
            }

            if(temperature[i] > tempMax){
                tempMax = temperature[i];
            }

            if(temperatureLongitude[i] < tempLongMin) {
                tempLongMin = temperatureLongitude[i];
            }

            if(temperatureLongitude[i] > tempLongMax){
                tempLongMax = temperatureLongitude[i];
            }
        }


    }

    public void drawTemperatureTable() {
        drawTemperatureRows();
        drawTemperatureLine();
        drawVerticalTemperatureLines();
    }

    public void drawTemperatureRows() {
        int top = (LOWER_TABLE_TOP_PADDING + LOWER_TABLE_TEXT_HEIGHT);
        int lineEnd = WIDTH_PADDING + GRAPH_WIDTH;
        line(WIDTH_PADDING, top, lineEnd, top);
        line(WIDTH_PADDING, top + 33, lineEnd, top + 33);
        line(WIDTH_PADDING, top + 66, lineEnd, top + 66);
        line(WIDTH_PADDING, top + 100, lineEnd, top + 100);

        fill(0);
        top += 5;
        text(" °R ", lineEnd + 10, top - 20);
        text("  0°", lineEnd + 10, top);
        text("-10°", lineEnd + 10, top + 33);
        text("-20°", lineEnd + 10, top + 66);
        text("-30°", lineEnd + 10, top + 100);

        fill(0);
        lineEnd += 25;
        text(" °C ", lineEnd + 10, top - 20);
        text("  0°", lineEnd + 10, top);
        text("-13°", lineEnd + 10, top + 33);
        text("-25°", lineEnd + 10, top + 66);
        text("-38°", lineEnd + 10, top + 100);


    }

    public void drawTemperatureLine() {
        stroke(0, 0, 255);
        strokeWeight(3); // 4 is the default
        fill(150);

        float x, x2, y, y2;

        for(int i = 0; i < temperature.length - 1; i++)
        {
//            x = (normalise(temperatureLongitude[i], tempLongMin, tempLongMax) * LOWER_TABLE_WIDTH) + LOWER_TABLE_WIDTH_PADDING;
//            y = LOWER_TABLE_TOP_PADDING + LOWER_TABLE_TEXT_HEIGHT + (normalise(temperature[i], tempMin, tempMax) * 100);
//            x2 = (normalise(temperatureLongitude[i+1], tempLongMin, tempLongMax) * LOWER_TABLE_WIDTH) + LOWER_TABLE_WIDTH_PADDING;
//            y2 = LOWER_TABLE_TOP_PADDING + LOWER_TABLE_TEXT_HEIGHT + (normalise(temperature[i+1], tempMin, tempMax) * 100);

            // tempMin and tempMax are reverse, since we are dealing with negatives
//            x = normalise(temperatureLongitude[i], longitudeMin, longitudeMax);
//            y = LOWER_TABLE_TOP_PADDING + LOWER_TABLE_TEXT_HEIGHT + (normalise(temperature[i], tempMax, tempMin) * 100);
//            x2 = (normalise(temperatureLongitude[i+1], longitudeMin, longitudeMax) * GRAPH_WIDTH) + LOWER_TABLE_WIDTH_PADDING;
//            y2 = LOWER_TABLE_TOP_PADDING + LOWER_TABLE_TEXT_HEIGHT + (normalise(temperature[i+1], tempMax, tempMin) * 100);

            x = normalise(temperatureLongitude[i], longitudeMin, longitudeMax);
            y =  normalise(temperature[i], tempMax, tempMin);
            x2 = normalise(temperatureLongitude[i+1], longitudeMin, longitudeMax);
            y2 = normalise(temperature[i+1], tempMax, tempMin);

            //line(x, y, x2, y2);
            line(scaleToGraph(x, GRAPH_WIDTH, WIDTH_PADDING),
                    scaleToGraph(y, 100, (LOWER_TABLE_TOP_PADDING + LOWER_TABLE_TEXT_HEIGHT)),
                    scaleToGraph(x2, GRAPH_WIDTH, WIDTH_PADDING),
                    scaleToGraph(y2, 100, (LOWER_TABLE_TOP_PADDING + LOWER_TABLE_TEXT_HEIGHT))
            );
        }

        stroke(1);
    }

    public void drawVerticalTemperatureLines() {

        strokeWeight(1);

        // 9 points total
        float [] verticalLinesLength = new float [] {115, 90, 85, 95, 75, 75, 95, 90};
        float x, x2, y, y2;

        for(int i = 0; i < verticalLinesLength.length; i++)
        {
            x = normalise(temperatureLongitude[i], longitudeMin, longitudeMax);
            y =  normalise(temperature[i], tempMax, tempMin);

            y2 = normalise(temperature[i] + verticalLinesLength[i], tempMax, tempMin);

            //line(x, y, x2, y2);
            line(scaleToGraph(x, GRAPH_WIDTH, WIDTH_PADDING),
                    scaleToGraph(y, 100, (LOWER_TABLE_TOP_PADDING + LOWER_TABLE_TEXT_HEIGHT)),
                    scaleToGraph(x, GRAPH_WIDTH, WIDTH_PADDING),
                    scaleToGraph(y2, 100, (LOWER_TABLE_TOP_PADDING + LOWER_TABLE_TEXT_HEIGHT))
            );
        }
    }

    public void drawLastVerticalTemperatureLine() {
        fill(0);
        strokeWeight(1);
        stroke(0);

        int i = 8;
        float x = normalise(temperatureLongitude[i], longitudeMin, longitudeMax);
        float y =  normalise(temperature[i], tempMax, tempMin);

        float y2 = normalise(temperature[i] + 87, tempMax, tempMin);

        //line(x, y, x2, y2);
        line(scaleToGraph(x, GRAPH_WIDTH, WIDTH_PADDING),
                scaleToGraph(y, 100, (LOWER_TABLE_TOP_PADDING + LOWER_TABLE_TEXT_HEIGHT)),
                scaleToGraph(x, GRAPH_WIDTH, WIDTH_PADDING),
                scaleToGraph(y2, 100, (LOWER_TABLE_TOP_PADDING + LOWER_TABLE_TEXT_HEIGHT))
        );
    }

    final int TITLE_PADDING = TABLE_TOP_PADDING + 40;
    public void drawTitleAndDescription() {
        String title = "Figurative map";
        String title2 = " of the successive losses in men of the French Army in the";
        String title3 = " Russian Campaign of 1812-1813";



        PFont myFont = createFont("Georgia Bold Italic", 32);
        textFont(myFont);

        stroke(0);
        fill(0);
        textSize(32);
        text(title, WIDTH_PADDING, TITLE_PADDING);
        float titleWidth = textWidth(title);

        textSize(12);
        text(title2, WIDTH_PADDING + titleWidth, TITLE_PADDING);
        titleWidth += textWidth(title2);

        textSize(32);
        text(title3, WIDTH_PADDING + titleWidth, TITLE_PADDING);
        titleWidth += textWidth(title3);

        strokeWeight(2);
        line(WIDTH_PADDING, TITLE_PADDING + 5, WIDTH_PADDING + titleWidth, TITLE_PADDING + 5);


        // Now for description

        String author = "Created using Processing via IntelliJ.\n" +
                "Name: John Keaney \n" +
                "Student ID: 18328855";

        String description = "Drawn by M. Minard, Inspector General of Bridges and Roads (retired). Paris, November 20, 1869.\n" +
                "The numbers of men present are represented by the widths of the colored zones at a rate of one millimeter for every ten thousand men; they are further written across the zones. The red designates the men who enter Russia, the black those who leave it. \n" +
                "— The information which has served to draw up the map has been extracted from the works of M. M. Thiers, de Ségur, de Fezensac, de Chambray and the unpublished diary of Jacob, the pharmacist of the Army since October 28th.\n" +
                "In order to better judge with the eye the diminution of the army, I have assumed that the troops of Prince Jérôme and of Marshal Davout, who had been detached at Minsk and Mogilev and have rejoined near Orsha and Vitebsk, had always marched with the army.\n" +
                "\n";

        description += author;

        textSize(10);
        text(description, WIDTH_PADDING, TITLE_PADDING + 25);





    }

    public void drawChart() {
        for(int i = 0; i < N-1; i++)
        {
            if(direction[i].equals("R"))
            {
                stroke(170, 170, 170);
            }
            else
            {
                stroke(252, 174, 30, 70);
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

            if(direction[i].equals("A") && division[i] == 2) {
                drawLastVerticalTemperatureLine();
            }
        }
    }

    public void draw()
    {
        if(frameCount < 13) {
            drawChart();
        }

        if(frameCount == 13) {
            drawCities(cityLatitude, cityLongitude, cityNames);
            drawTitleAndDescription();
        }

        if(frameCount == 14) {
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

//        PImage map = loadImage("maps/satelite1320_400.PNG");
//        image(map, 85, 100);

//        PImage map = loadImage("maps/newmaps/labellessMap1300.PNG");
//        image(map, 85, 100);

        // Gold standard when i was using 1490 x 855
        //        PImage map = loadImage("maps/newmaps/greylabellessMapAdjusted1320.png");
        //        image(map, MAP_IMAGE_SIDE_PADDING, MAP_IMAGE_TOP_PADDING);

            PImage map = loadImage("maps/newmaps/greylabellessMap1750.PNG");
            image(map, MAP_IMAGE_SIDE_PADDING, MAP_IMAGE_TOP_PADDING);
    }

    public void keyPressed() {
        //i++;
    }

    public float normalise(float value, float min, float max) {
        // zi = (xi – min(x)) / (max(x) – min(x))
        return (value - min) / (max - min);
    }

    public void drawCities(float[] lat, float[] lon, String[] names) {
        textSize(14);

        for(int i = 0; i < CONSTANTS.TOTAL_NUMBER_OF_CITIES; i++)
        {
            float tempX = normalise(lon[i], longitudeMin, longitudeMax);
            float tempY = normalise(lat[i], latitudeMin, latitudeMax);
            tempX = scaleToGraph(tempX, GRAPH_WIDTH, WIDTH_PADDING);
            tempY = scaleToGraph(tempY, GRAPH_HEIGHT, GRAPH_TOP_PADDING);

            if(cityNames[i].equals("Smorgoni"))
            {
                tempX -= 10;
            }

            image(fortIcon, tempX, tempY - (fortIcon.height/2));

            drawTextStroke(cityNames[i], tempX - (cityNames[i].length() * 2), tempY + (fortIcon.height/2) + 10);
            fill(255);
            text(cityNames[i], tempX - (cityNames[i].length() * 2), tempY + (fortIcon.height/2) + 10);
        }
    }

    public void drawTextStroke(String text, float x, float y) {
        fill(0);
        for(int i = -1; i < 2; i++)
        {
            text(text, x+i,y);
            text(text, x,y+i);
        }
        //noFill();
    }

    public float scaleToGraph(float value, int scale, int padding) {
        return (value * scale) + padding;
    }
}
