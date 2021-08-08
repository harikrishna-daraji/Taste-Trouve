//creating the routes
const admin = require("firebase-admin");
const router = require("express").Router();
const TrackOrder = require("../models/TrackOrder");
const User = require("../models/userModels");
// connection for registering a user

router.post("/add", async (req, res) => {
  try {
    let { deliveryUser, orderId, restroId } = req.body;

    const newtrack = new TrackOrder({
      deliveryUser,
      orderId,
      restroId,
      status: "assigned",
    });

     console.log(newtrack);

    await Order.updateOne(
      { _id: orderId },
      { orderStatus: "assigned" },
      function (err, docs) {
        if (err) {
          res.send(err);
        } else {
          //res.send("Updated");
        }
      }
    );

    const savedtrack = await newtrack.save();
    res.json(savedtrack);
  } catch (err) {
    res.status(500).json({ sporterror: err.message });
  }
});

router.post("/getCurrentOrderForDriver", async (req, res) => {
  try {
    let { deliveryUser } = req.body;
    const trackOrder = await TrackOrder.find({
      deliveryUser,
      status: "assigned",
    })
      .populate({
        path: "orderId restroId",
        populate: {
          path: "addressId",
        },
      })
      .sort({ createdAt: "desc" });

    res.send(trackOrder[0]);
  } catch (err) {
    console.log(err.message);
    res.status(500).json({ error: err.message });
  }
});

router.post("/getDriverForOrder", async (req, res) => {
  try {
    let { orderId } = req.body;
    const trackOrder = await TrackOrder.find({
      orderId,
    }).select("deliveryUser");

    res.send(trackOrder);
  } catch (err) {
    console.log(err.message);
    res.status(500).json({ error: err.message });
  }
});

router.put("/update", async (req, res) => {
  let { orderId, updateStatus } = req.body;

  TrackOrder.updateOne(
    { _id: orderId },
    { status: updateStatus },
    function (err, docs) {
      if (err) {
        res.send(err);
      } else {
      }
    }
  );

  const currentOrder = await TrackOrder.findOne({
    _id: orderId,
  });

  await Order.updateOne(
    { _id: currentOrder.orderId },
    { orderStatus: updateStatus },
    function (err, docs) {
      if (err) {
        res.send(err);
      } else {
      }
    }
  );

  await User.updateOne(
    { _id: currentOrder.deliveryUser },
    { isBussy: true },
    function (err, docs) {
      if (err) {
        res.send(err);
      } else {
      }
    }
  );

  const order = await Order.find({
    _id: currentOrder.orderId,
  }).populate("userId");

  var payload = {
    data: {
      title: "Driver Accepted your Order",
      message: "Next Step : Your order is now processed by Driver",
    },
  };

  admin.messaging().sendToDevice(order[0].userId.fcmToken, payload);

  res.send("Updated");
});

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
