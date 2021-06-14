const mongoose = require("mongoose");
//setting up the user schema - format of how the user objects are going to stored in the database.
const RestaurantsSchema = new mongoose.Schema({
  restaurantName: { type: String, required: true },
  email: { type: String, required: true, unique: true },
  password: { type: String, required: true },
  fcmToken: { type: String },
  phoneNumber: { type: String },
  status: { type: Boolean },
  userType: {
    type: String,
    enum: ["admin", "restaurantOwner"],
  },
});
module.exports = User = mongoose.model("Restaurants", RestaurantsSchema);
