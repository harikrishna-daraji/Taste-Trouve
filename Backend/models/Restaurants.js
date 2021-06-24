const mongoose = require("mongoose");
const RestaurantsSchema = new mongoose.Schema({
  restaurantName: { type: String, required: true },
  email: { type: String, required: true, unique: true },
  password: { type: String, required: true },
  fcmToken: { type: String },
  phoneNumber: { type: String },
  address: { type: String },
  status: {
    type: String,
    enum: ["pending", "accepted", "declined"],
  },
  userType: {
    type: String,
    enum: ["admin", "restaurantOwner"],
  },
});
module.exports = Restaurants = mongoose.model("Restaurants", RestaurantsSchema);
