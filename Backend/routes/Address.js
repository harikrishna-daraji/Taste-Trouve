//creating the routes
const router = require("express").Router();
const User = require("../models/userModels");
const Address = require("../models/Address");
// connection for registering a user

router.post("/add", async (req, res) => {
  try {
    let { userId, address, lat, long } = req.body;

    const newAddress = new Address({
      userId,
      address,
      lat,
      long,
    });

    const savedAddress = await newAddress.save();
    res.json(savedAddress);
  } catch (err) {
    res.status(500).json({ AppError: err.message });
  }
});

//token validation

router.post("/getAddresByUser", async (req, res) => {
  const { userId } = req.body;

  const product = await Address.find({
    userId,
  });
  res.json(product);
});

// router.put("/update", async (req, res) => {
//   const data = req.body;
//   console.log(data);

//   var myquery = { phoneNumber: data.phoneNumber };
//   var newvalues = { $set: { ...data } };
//   await User.updateOne(myquery, newvalues, function (err, res) {
//     if (err) throw err;
//   });

//   return res.send({ data: "updatedUser" });
// });

module.exports = router;
