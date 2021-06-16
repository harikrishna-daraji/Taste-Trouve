const express = require("express");
const ConnectDB = require("./DB/Connection");
const cors = require("cors");

require("dotenv").config();
//setting up express
const app = express();
app.use(express.json());
app.use(cors());

ConnectDB();

app.use(express.json({ extended: false }));

//starting up the server
const PORT = process.env.PORT || 5000; //grabbing the assigned port

//setup the routes

app.use("/api/restaurantUsers", require("./routes/Restaurants"));
app.use("/api/clientUser", require("./routes/userRouter"));
app.use("/api/category", require("./routes/Category"));
app.use("/api/subCategory", require("./routes/SubCategory"));
app.use("/api/product", require("./routes/Product"));

app.listen(PORT, () => console.log(`the server has started on port :${PORT}`));
// setting up the mongodb connection with mongoose