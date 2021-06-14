const express = require("express");
const ConnectDB = require("./DB/Connection");
const cors = require("cors");

require("dotenv").config();
//setting up express
const app = express();
app.use(express.json());
app.use(cors());
ConnectDB();
//starting up the server
const PORT = process.env.PORT || 5000; //grabbing the assigned port
app.listen(PORT, () => console.log(`the server has started on port :${PORT}`));
// setting up the mongodb connection with mongoose

//setup the routes

// app.use("/users", require("./routes/userRouter"));
// app.use("/todos", require("./routes/todoRouter"));
// app.use("/linkworkout", require("./routes/LinkWorkout"));

// app.use("/AddTrainings", require("./routes/AddTrainingRouter"));
