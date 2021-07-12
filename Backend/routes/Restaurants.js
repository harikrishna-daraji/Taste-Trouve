//creating the routes
const router = require("express").Router();
const Restaurants = require("../models/Restaurants");
// connection for registering a user

router.post("/register", async (req, res) => {
  try {
    let {
      restaurantName,
      email,
      password,
      fcmToken,
      phoneNumber,
      address,
      status,
      userType,
    } = req.body;

    // validation

    if (!email || !password) {
      return res.status(400).json({ msg: "Not all fields have been entered." });
    }

    const existingUser = await Restaurants.findOne({ email: email });
    if (existingUser)
      return res
        .status(400)
        .json({ msg: "An account with this email already exists." });

    const newUser = new Restaurants({
      restaurantName,
      email,
      password,
      fcmToken,
      phoneNumber,
      address,
      status: "pending",
      userType,
    });

    const savedUser = await newUser.save();
    res.json(savedUser);
  } catch (err) {
    res.status(500).json({ sporterror: err.message });
  }
});
//login
router.post("/login", async (req, res) => {
  try {
    const { email, password } = req.body;

    // validate
    if (!email || !password)
      return res.status(400).json({ msg: "Not all fields have been entered." });

    const user = await Restaurants.findOne({
      email: email,
      password: password,
    });

    res.json(user);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

router.get("/getRestaurants", async (req, res) => {
  try {
    const restaurant = await Restaurants.find({ status: "pending" });

    res.send(restaurant);
  } catch (err) {
    console.log(err.message);
    res.status(500).json({ error: err.message });
  }
});

router.post("/getRestaurantsById", async (req, res) => {
  try {
    const { resId } = req.body;

    const user = await Restaurants.findOne({
      _id: resId,
    });

    res.json(user);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

router.put("/UpdateRestuarantStatus", async (req, res) => {
  let { restaurantId, updateStatus } = req.body;

  
  Restaurants.updateOne(
    { _id: restaurantId },
    { status: updateStatus },
    function (err, docs) {
      if (err) {
        res.send(err);
      } else {
        res.send("Updated");
      }
    }
  );
});

router.put("/update", async (req, res) => {
  const data = req.body;

  var myquery = { _id: data.resId };
  var newvalues = { $set: { ...data } };

  await Restaurants.updateOne(myquery, newvalues, function (err, res) {
    if (err)
console.log(err);
		throw err;
  });

  return res.send({ data: "updatedUser" });
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
