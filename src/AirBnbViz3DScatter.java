// DS6390 Data Visualization Assignment #10: Final 3D Java Visualization
// Andy Nguyen
// 3-D Scatter Plot of AirBnb listings in Major U.S. Cities
// Section 403

import processing.core.*;
import processing.data.*;
// import processing.event.*; 
// import processing.opengl.*; 

// import java.util.*;
import java.util.List;
import java.util.Random;
// import java.util.HashMap; 
import java.util.ArrayList;
// import java.io.File; 
// import java.io.BufferedReader; 
// import java.io.PrintWriter; 
// import java.io.InputStream; 
// import java.io.OutputStream; 
// import java.io.IOException; 

/* Assignment Notes
 This 3-D Visualization is 3-Dimensional Scatter Plot of AirBnb listings in 6 major U.S.
 cities (LA, SF, NYC, DC, CHI, BOS). At start-up, the visualization will display a random
 sample of 1,000 from the 49,738 listings provided in the data set. This was done to prevent
 memory issues so that the application would not crash with that amount of data, but still be
 representative of the stored information.
 
 This data set on AirBnb listings in the major U.S. cities was taken from the Kaggle competition
 and can be found at the link: https://www.kaggle.com/rudymizrahi/airbnb-listings-in-major-us-cities-deloitte-ml.
 Since I was interested in visualizing the price distribution, accommodation number, and rating of
 the listings, I manipulated the original data set to keep only the values I was interested in
 visualizing.
 
 The X-axis (scale 0-16) will positively increase going left from the origin of the scatter base, the Y-axis 
 (scale 3.5-8) will positively increase going up from the origin, and the Z-axis will positively increase
 coming out of the screen from the origin (scale 70-100). The X-axis will represent the accommodation number 
 of a listing, the Y-Axis will represent the value of the ln(price) of the listing, the Z-axis will
 represent the reviews rating score of the listings, and the color of the listing will represent the city
 in which the city can be found.
 
 Using the left and right arrows on the keyboard, the visualization can change which listings are
 displayed based on city. All listings will be shown at start up (keyCount index = 0) , but can be
 cycled to show only listings from LA, SF, NYC, DC, CHI, or BOS with each press of the right key arrow.
 
 A mouse drag interactivity is employed to change the angles along the x and y axes where the mouse button 
 must be held down and moved in a direction to change either the rotation angle on either the x or y axis.
 */

public class AirBnbViz3DScatter extends PApplet {

	AirBnb_Listing AirBnb_Listing;
	ArrayList<AirBnb_Listing> ListingsSample;
	dataProcessor AirBnbData;

	CustomCamera cam;
	ScatterBase base;
	Samples samples;

	ArrayList<Integer> randomSub;
	List<Integer> RandomSample;

	String csvFile = "AirBnb-DataViz.csv";
	int[] accomodationNumber;
	float[] log_price;
	float[] rating;
	String[] city;
	String[] grade;

	ArrayList<AirBnb_Listing> LA;
	ArrayList<AirBnb_Listing> SF;
	ArrayList<AirBnb_Listing> NYC;
	ArrayList<AirBnb_Listing> DC;
	ArrayList<AirBnb_Listing> CHI;
	ArrayList<AirBnb_Listing> BOS;

	int keyCount = 0;

	public void settings() {
		size(800, 800, P3D);

		cam = new CustomCamera(this);
		base = new ScatterBase(this, width * (0.4f), height * (0.4f), width * (0.4f));
		samples = new Samples();

		accomodationNumber = new int[49738];
		log_price = new float[49738];
		rating = new float[49738];
		city = new String[49738];
		grade = new String[49738];

		randomSub = new ArrayList<Integer>();
		for (int i = 0; i < 49738; i++) {
			randomSub.add(i);
		}

		// Constructor Signature: dataProcessor(int[] accomodationNumber, float[]
		// log_price, String[] rating, String[] city, String[] grade)
		AirBnbData = new dataProcessor(this, accomodationNumber, log_price, rating, city, grade);
		AirBnbData.processAirBnbData(csvFile);

		ListingsSample = new ArrayList<AirBnb_Listing>();
		LA = new ArrayList<AirBnb_Listing>();
		NYC = new ArrayList<AirBnb_Listing>();
		BOS = new ArrayList<AirBnb_Listing>();
		DC = new ArrayList<AirBnb_Listing>();
		CHI = new ArrayList<AirBnb_Listing>();
		SF = new ArrayList<AirBnb_Listing>();

		// Obtain random samples from the Data (program takes too long to visualize all
		// data points)
		// 1000 random samples are chosen and ran successfuly, but if the program takes
		// too long or crashes then reduce the number of random samples
		RandomSample = samples.RandomSample(randomSub, 1000);
		for (int l : RandomSample) {
			AirBnb_Listing = new AirBnb_Listing(this, accomodationNumber[l], log_price[l], rating[l], city[l],
					grade[l]);
			ListingsSample.add(AirBnb_Listing);

			switch (AirBnb_Listing.getCity()) {
			case "LA": // Purple
				LA.add(AirBnb_Listing);
				break;
			case "NYC": // Navy
				NYC.add(AirBnb_Listing);
				break;
			case "Boston": // Green
				BOS.add(AirBnb_Listing);
				break;
			case "DC": // Pink
				DC.add(AirBnb_Listing);
				break;
			case "Chicago": // Orange
				CHI.add(AirBnb_Listing);
				break;
			case "SF": // Red
				SF.add(AirBnb_Listing);
				break;
			}
		}
	}

	public void draw() {
		
		background(120);
		
		// Display Visualization Title
		textSize(20);
		textAlign(CENTER);
		camera();
		hint(DISABLE_DEPTH_TEST);
		noLights();
		fill(255);
		text("3-D Scatter Plot of AirBnb listings in Major U.S. Cities", width / 2, 25.0f);
		hint(ENABLE_DEPTH_TEST);
		
		// Display Legend of Visualization
		AirBnb_Listing.displayLegend();

		// Display 3-D space for Scatter-plot visualization
		cam.use();
		lights();
		stroke(0);
		strokeWeight(3);
		fill(200);
		base.render();

		// Interactivity to only display listings for a specific city 
		pushMatrix();
		translate(width * (0.2f), -2, -width * (0.2f));
		switch (keyCount) {
		case 0:
			for (int i = 0; i < ListingsSample.size(); i++) {
				ListingsSample.get(i).display();
			}
			break;
		case 1:
			for (int i = 0; i < LA.size(); i++) {
				LA.get(i).display();
			}
			break;
		case 2:
			for (int i = 0; i < SF.size(); i++) {
				SF.get(i).display();
			}
			break;
		case 3:
			for (int i = 0; i < NYC.size(); i++) {
				NYC.get(i).display();
			}
			break;
		case 4:
			for (int i = 0; i < DC.size(); i++) {
				DC.get(i).display();
			}
			break;
		case 5:
			for (int i = 0; i < CHI.size(); i++) {
				CHI.get(i).display();
			}
			break;
		case 6:
			for (int i = 0; i < BOS.size(); i++) {
				BOS.get(i).display();
			}
			break;
		}
		popMatrix();
	}

	public void mouseDragged() {
		cam.dragged();
	}

	public void keyPressed() {
		if (key == CODED) {
			if (keyCode == UP) {
				keyCount += 1;
				// Keep teamIndexLeft within bounds of 0-6
				if (keyCount > 6) {
					keyCount = 6;
				}
			} else if (keyCode == DOWN) {
				keyCount -= 1;
				// Keep teamIndexLeft within bounds of 0-6
				if (keyCount < 0) {
					keyCount = 0;
				}
			} else if (keyCode == RIGHT) {
				keyCount += 1;
				// Keep teamIndexRight within bounds of 0-6
				if (keyCount > 6) {
					keyCount = 6;
				}
			} else if (keyCode == LEFT) {
				keyCount -= 1;
				// Keep teamIndexRight within bounds of 0-6
				if (keyCount < 0) {
					keyCount = 0;
				}
			}
		}
	}

	public class AirBnb_Listing {

		/* Fields/Attribtues */
		PApplet p;
		PVector loc;
		int cityColor;

		int accomodationNumber;
		float log_price;
		float rating;
		String city;
		String grade;

		/* Constructor */
		AirBnb_Listing() {
		}

		AirBnb_Listing(PApplet p, int accomodationNumber, float log_price, float rating, String city, String grade) {
			this.p = p;
			this.accomodationNumber = accomodationNumber;
			this.log_price = log_price;
			this.rating = rating;
			this.city = city;
			this.grade = grade;

			// this.loc = new PVector(float(this.accomodationNumber), this.log_price);

		}

		/* Methods */

		public void display() {

			// Color each sphere by city
			switch (this.city) {
			case "LA": // Purple
				this.cityColor = p.color(229, 204, 255);
				break;
			case "NYC": // Navy
				this.cityColor = p.color(0, 0, 102);
				break;
			case "Boston": // Green
				this.cityColor = p.color(0, 255, 0);
				break;
			case "DC": // Pink
				this.cityColor = p.color(255, 204, 229);
				break;
			case "Chicago": // Orange
				this.cityColor = p.color(255, 128, 0);
				break;
			case "SF": // Red
				this.cityColor = p.color(255, 0, 0);
				break;
			}

			// Map the values for each attribute on their appropriate scale
			float x = PApplet.map(this.accomodationNumber, 0.0f, 16.0f, 0.0f, -p.width * (0.4f));
			float y = PApplet.map(this.log_price, 3.5f, 8.0f, 0.0f, -p.height * (0.4f));
			float z = PApplet.map(this.rating, 70.0f, 100.0f, 0.0f, p.width * (0.4f));
			p.fill(this.cityColor);

			p.pushMatrix();
			p.translate(x, y, z);
			p.lights();
			p.noStroke();
			p.sphere(1.2f);
			p.popMatrix();

		}

		public void displayLegend() {
			p.pushMatrix();
			p.camera();
			p.translate(p.width / 8.0f, p.height - 75.0f);
			p.rectMode(CENTER);
			p.hint(DISABLE_DEPTH_TEST);
			p.noLights();
			p.noFill();
			p.stroke(255);
			p.rect(0, 0, 70, 100);
			p.translate(-20, -32);
			String[] cities = { "LA", "SF", "NYC", "DC", "CHI", "BOS" };
			int[] cityColors = { p.color(229, 204, 255), p.color(255, 0, 0), p.color(0, 0, 102), p.color(255, 204, 229),
					p.color(255, 128, 0), p.color(0, 255, 0) };
			for (int i = 0; i < 6; i++) {
				p.fill(255);
				p.textSize(14);
				p.text(cities[i], 5, 20 * i);
				p.noStroke();
				p.fill(cityColors[i]);
				p.translate(0, -5);
				p.ellipse(40, 20 * i, 10, 10);
			}
			p.hint(ENABLE_DEPTH_TEST);
			p.popMatrix();
		}

		/* Setters & Getters */
		public void setLogPrice(float log_price) {
			this.log_price = log_price;
		}

		public float getLogPrice() {
			return log_price;
		}

		public void setAccomdNum(int accomodationNumber) {
			this.accomodationNumber = accomodationNumber;
		}

		public int getAccomdNum() {
			return accomodationNumber;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getCity() {
			return city;
		}

		public void setCityFillCol(int cityColor) {
			this.cityColor = cityColor;
		}

		public int getCityFillCol() {
			return cityColor;
		}

	}
	
	/* The code for this CustomCamera class was taken from Professor Ibarra's 3-D Scatter sketch example code
	   from Live Session 13 and adapted for the purposes of this visualization. The minimum and maximum angles 
	   to view different perspectives of the the 3-D scatter plot have not been altered. A mouse drag interactivity 
	   is employed to change the angles along the x and y axes where the mouse button must be held down and moved in
	   a direction to change the rotation angle on either the x or y axis.
	 */

	public class CustomCamera {

		/* Fields/Attributes */
		PApplet p;
		private PVector position;
		private PVector direction;
		private float angleX;
		private float angleY;

		private static final float MIN_ANGLE_X = -45.0f;
		private static final float MAX_ANGLE_X = 0.0f;
		private static final float MIN_ANGLE_Y = -90.0f;
		private static final float MAX_ANGLE_Y = 90.0f;
		private static final float ANGLE_Y_SPEED = 1.72f;
		private static final float ANGLE_X_SPEED = 1.72f;

		private float lastDragX;
		private float lastDragY;

		/* Constructors */
		public CustomCamera(PApplet p) {
			this.p = p;
			position = new PVector(0, -100, 500);
			direction = new PVector(0, -100, 0);
			angleX = 0;
			angleY = 0;
		}

		/* Methods */
		public void use() {
			p.beginCamera();
			p.camera(position.x, position.y, position.z, direction.x, direction.y, direction.z, 0, 1, 0);
			p.rotateY(angleY);
			p.rotateX(angleX);
			p.endCamera();
		}

		public void dragged() {
			rotateCamera();
		}

		private void rotateCamera() {

			if (p.mouseX > lastDragX) {
				lastDragX = p.mouseX;
				angleY += ANGLE_Y_SPEED * (PI / 180);
				if (angleY > MAX_ANGLE_Y * (PI / 180)) {
					angleY = MAX_ANGLE_Y * (PI / 180);
				}
			} else if (p.mouseX < lastDragX) {
				lastDragX = p.mouseX;
				angleY -= ANGLE_Y_SPEED * (PI / 180);
				if (angleY < MIN_ANGLE_Y * (PI / 180)) {
					angleY = MIN_ANGLE_Y * (PI / 180);
				}
			}

			if (p.mouseY > lastDragY) {
				lastDragY = p.mouseY;
				angleX += ANGLE_X_SPEED * (PI / 180);
				if (angleX > MAX_ANGLE_X * (PI / 180)) {
					angleX = MAX_ANGLE_X * (PI / 180);
				}
			} else if (p.mouseY < lastDragY) {
				lastDragY = p.mouseY;
				angleX -= ANGLE_X_SPEED * (PI / 180);
				if (angleX < MIN_ANGLE_X * (PI / 180)) {
					angleX = MIN_ANGLE_X * (PI / 180);
				}
			}
		}
	}

	/* The code to randomly sample the AirBnb listings from an Item list without 
	   replacement was adapted from the example code at this link: 
	   https://www.geeksforgeeks.org/randomly-select-items-from-a-list-in-java/
	 */
	
	public class Samples {
		public List<Integer> RandomSample(List<Integer> Data, int SampleSize) {
			Random r = new Random();

			ArrayList<Integer> RandomSample = new ArrayList<Integer>();
			for (int i = 0; i < SampleSize; i++) {
				int randomIndex = r.nextInt(Data.size());
				RandomSample.add(Data.get(randomIndex));
				Data.remove(randomIndex);
			}
			return RandomSample;
		}
	}

	/* The code for this ScatterBase class was taken from Professor Ibarra's 3-D Scatter sketch example code
	   from Live Session 13 and adapted for the purposes of this visualization. Rather than extending the
	   Abstract3DObject superclass, I found it more efficient for my purposes to not use ScatterBase as a subclass.
	   
	   Additionally, I created axes for the x, y, and z axis that extend from the origin of the ScatterBase 
	   so that the visualization could be more clearly defined within a 3-D space. For the purposes of this 
	   specific visualization, I included the attribute name that represents each axis along with the scale in which 
	   it is being visualized. For the scope of different data sources, the labels and scales can easily be changed by
	   adjusting certain parameters nested within the ScatterBase.createObject() method. These lines of codes can
	   be easily identified with the axis label and scale comment headers.
	 */
	
	public class ScatterBase {

		/* Fields/Attributes */
		PApplet p;
		private float sizeWidth;
		private float sizeHeight;
		private float sizeDepth;
		// private ArrayList<DataObject> dataObjects;

		/* Constructors */
		public ScatterBase() {
			super();
			sizeWidth = 300;
			sizeDepth = 500;
			sizeHeight = 700;
			// dataObjects = new ArrayList<DataObject>();
		}

		public ScatterBase(PApplet p, float _width, float _height, float _depth) {
			this();
			this.p = p;
			sizeWidth = _width;
			sizeDepth = _depth;
			sizeHeight = _height;
		}

		/* Methods */
		public void addPoint(float w, float d, float h) {
			// DataObject newPoint = new DataObject(w,d,h);
			// dataObjects.add(newPoint);
		}

		protected void createObject() {

			p.box(sizeWidth, 2, sizeDepth);
			// X-Axis Label
			p.pushMatrix();
			p.translate(0, 30.0f, sizeDepth / 2);
			p.textAlign(CENTER);
			p.textSize(12);
			p.text("Accommodates", 0, 0);
			// X-Scale
			p.translate(sizeWidth / 2, -15.0f, 0.0f);
			p.text("0", 0, 0);
			float x_scale = PApplet.map(2, 0, 16, 0, -sizeWidth);
			p.pushMatrix();
			for (int x = 2; x < 17; x += 2) {
				p.translate(x_scale, 0, 0);
				p.text(PApplet.str(x), 0, 0);
			}
			p.popMatrix();
			p.popMatrix();

			// Y-Axis Label
			p.pushMatrix();
			p.translate(sizeWidth / 2 + 7.0f, -sizeHeight / 2, -sizeDepth / 2);
			p.rotate(PI / 2);
			p.textAlign(CENTER);
			p.textSize(12);
			p.text("Log Price", 0.0f, -25.0f);
			p.rotate(-PI / 2);
			// Y-Scale
			p.translate(7.0f, sizeHeight / 2, 0.0f);
			p.text("3.5", 0.0f, 0.0f);
			float y_scale = PApplet.map(4.5f / 11.0f, 0.0f, 8.0f, 0.0f, -sizeHeight * 2);
			p.pushMatrix();
			for (float y = 4; y < 8.5; y += 0.5) {
				p.translate(0, y_scale, 0);
				p.text(PApplet.str(y), 0.0f, 0.0f);
			}
			p.popMatrix();
			p.popMatrix();

			// Z-Axis Label
			p.pushMatrix();
			p.translate(sizeWidth / 2 + 7.0f, 25.0f, 0.0f);
			p.rotateY(PI / 2);
			p.textAlign(CENTER);
			p.hint(DISABLE_DEPTH_TEST);
			p.noLights();
			p.textSize(12);
			p.text("Rating", 0.0f, 0.0f);
			p.hint(ENABLE_DEPTH_TEST);
			p.rotate(-PI / 2);

			p.translate(15.0f, sizeHeight / 2, 0.0f);
			float z_scale = PApplet.map(30.0f / 7.0f, 0.0f, 100.0f, 0.0f, -sizeWidth * 4);
			p.pushMatrix();
			p.rotate(PI / 2);
			p.text("70", 0.0f, 0.0f);
			for (int z = 75; z < 105; z += 5) {
				p.hint(DISABLE_DEPTH_TEST);
				p.noLights();
				p.translate(z_scale, 0.0f, 0.0f);
				p.text(PApplet.str(z), 0.0f, 0.0f);
				p.hint(ENABLE_DEPTH_TEST);
			}
			p.popMatrix();
			p.popMatrix();

			p.pushMatrix();
			p.translate(sizeWidth / 2, -2.0f, -sizeDepth / 2);
			p.line(0.0f, 0.0f, 0.0f, 0.0f, -sizeHeight, 0.0f);
			p.popMatrix();

			p.pushMatrix();
			p.translate(sizeWidth / 2, -2.0f, -sizeDepth / 2);
			p.popMatrix();
		}

		public void render() {
			p.pushMatrix();
			createObject();
			p.popMatrix();
		}

	}

	public class dataProcessor {

		/* Fields and Attributes */
		PApplet p;
		int[] accomodationNumber;
		float[] log_price;
		float[] rating;
		String[] city;
		String[] grade;

		/* Constructors */
		dataProcessor(PApplet p, int[] accomodationNumber, float[] log_price, float[] rating, String[] city,
				String[] grade) {
			this.p = p;
			this.accomodationNumber = accomodationNumber;
			this.log_price = log_price;
			this.rating = rating;
			this.city = city;
			this.grade = grade;
		}

		/* Methods */
		public void processAirBnbData(String csvFile) {
			Table data = p.loadTable(csvFile, "header");

			for (int i = 0; i < data.getRowCount(); i++) {

				log_price[i] = data.getFloat(i, "log_price");
				accomodationNumber[i] = data.getInt(i, "accommodates");
				rating[i] = data.getFloat(i, "review_scores_rating");
				city[i] = data.getString(i, "city");
				grade[i] = data.getString(i, "grade");
			}
		}
	}

	static public void main(String[] passedArgs) {
	    String[] appletArgs = new String[] { "AirBnbViz3DScatter" };
	    if (passedArgs != null) {
	      PApplet.main(concat(appletArgs, passedArgs));
	    } else {
	      PApplet.main(appletArgs);
	    }
	  }
}
//}