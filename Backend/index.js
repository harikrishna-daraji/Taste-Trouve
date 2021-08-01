const express = require("express");
const ConnectDB = require("./DB/Connection");
const cors = require("cors");
const admin = require("firebase-admin");

var serviceAccount = require("./firebaseKey.json");

if (admin.apps.length === 0) {
  admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    //  databaseURL: "https://taste-trouve-default-rtdb.firebaseio.com"
  });
}

require("dotenv").config();
//setting up express
const app = express();
app.use(express.json());
app.use(cors());

ConnectDB();

app.use(express.json()); // support json encoded bodies
app.use(express.urlencoded({ extended: true })); // support encoded bodies

// app.use(express.json({ extended: false }));

//starting up the server
const PORT = process.env.PORT || 5000; //grabbing the assigned port

//setup the routes

app.use("/api/restaurantUsers", require("./routes/Restaurants"));
app.use("/api/clientUser", require("./routes/userRouter"));
app.use("/api/category", require("./routes/Category"));
app.use("/api/subCategory", require("./routes/SubCategory"));
app.use("/api/product", require("./routes/Product"));
app.use("/api/HomeScreen", require("./routes/Home"));
app.use("/api/Cart", require("./routes/Cart"));
app.use("/api/address", require("./routes/Address"));
app.use("/api/Order", require("./routes/Order"));
app.use("/api/trackOrder", require("./routes/TrackOrder"));
app.use("/api/favourite", require("./routes/Favourite"));

app.listen(PORT, () => console.log(`the server has started on port :${PORT}`));
// setting up the mongodb connection with mongoose
