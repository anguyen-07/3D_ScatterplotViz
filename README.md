# 3D_ScatterplotViz
A scatterplot visualizing AirBnb listings from major U.S. cities in a 3-D space to explore clusterings and relationships between 3 chosen feature variables (i.e. price, max occupancy, rating).

This visualization is 3-Dimensional Scatterplot of AirBnb listings from 6 major U.S.
 cities (LA, SF, NYC, DC, CHI, BOS). The idea was to explore clusterings and relationships between 3 chosen feature variables (i.e. max occupancy, price, rating). At start-up, the visualization will display a random
 sample of 1,000 from the 49,738 listings provided in the data set. This was done to prevent
 memory issues so that the application would not crash with that amount of data, but still be
 representative of the stored information.
 
 This data set on AirBnb listings in the major U.S. cities was taken from the ~Kaggle~ competition
 and can be found at the link: https://www.kaggle.com/rudymizrahi/airbnb-listings-in-major-us-cities-deloitte-ml.
 Since I was interested in visualizing the price distribution (y), accommodation number (x), and rating of
 the listings (z), I manipulated the original data set to keep only the values I was interested in
 visualizing.
 
 The X-axis (scale 0-16) will positively increase going left from the origin of the scatter base, the Y-axis 
 (scale 3.5-8) will positively increase going up from the origin, and the Z-axis will positively increase
 coming out of the screen from the origin (scale 70-100). The X-axis will represent the accommodation number 
 of a listing, the Y-Axis will represent the value of the ~ln~(price) of the listing, the Z-axis will
 represent the reviews rating score of the listings, and the color of the listing will represent the city
 in which the city can be found.
 
 Using the left and right arrows on the keyboard, the visualization can change which listings are
 displayed based on city. All listings will be shown at start up (keyCount index = 0) , but can be
 cycled to show only listings from LA, SF, NYC, DC, CHI, or BOS with each press of the right key arrow.
