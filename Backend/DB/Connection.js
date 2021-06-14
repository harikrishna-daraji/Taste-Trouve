const mongoose = require("mongoose");

const URI =
  "mongodb+srv://dilroop:dilroop@cluster0.nv3iu.mongodb.net/tastetrouve?retryWrites=true&w=majority";

const ConnectDB = async () => {
  await mongoose.connect(
    URI,
    {
      useNewUrlParser: true,
      useUnifiedTopology: true,
      useCreateIndex: true,
    },
    (err) => {
      if (err) throw err;
      console.log("Mongodb connection established");
    }
  );
};

module.exports = ConnectDB;
