//creating the routes
const router = require("express").Router();
const TrackOrder = require("../models/TrackOrder");
// connection for registering a user

router.post("/add", async (req, res) => {
  try {
    let { deliveryUser, orderId } = req.body;

    const newtrack = new TrackOrder({
      deliveryUser,
      orderId,
    });

    const savedtrack = await newtrack.save();
    res.json(savedtrack);
  } catch (err) {
    res.status(500).json({ sporterror: err.message });
  }
});

// router.get("/getRestaurants", async (req, res) => {
//   try {
//     const restaurant = await Restaurants.find({ status: "pending" });

//     res.send(restaurant);
//   } catch (err) {
//     console.log(err.message);
//     res.status(500).json({ error: err.message });
//   }
// });

// router.put("/UpdateRestuarantStatus", async (req, res) => {
//   let { restaurantId, updateStatus } = req.body;

//   Restaurants.updateOne(
//     { _id: restaurantId },
//     { status: updateStatus },
//     function (err, docs) {
//       if (err) {
//         res.send(err);
//       } else {
//         res.send("Updated");
//       }
//     }
//   );
// });

// //delete
// router.delete("/delete", auth, async (req, res) => {
//   try {
//     const deletedUser = await Restaurants.findByIdAndDelete(req.user);
//     res.json(deletedUser);
//   } catch (err) {
//     res.status(500).json({ error: err.message });
//   }
// });

module.exports = router;
