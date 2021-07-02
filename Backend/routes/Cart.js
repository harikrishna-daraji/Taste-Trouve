const router = require("express").Router();
const admin = require("firebase-admin");
const Product = require("../models/Product");
const Category = require("../models/Category");
const SubCategory = require("../models/SubCategory");
const Cart = require("../models/Cart");

router.post("/add", async (req, res) => {
  try {
    const { userId, productId, quantity } = req.body;

    const cart = await Cart.find({
      userId,
    });

    const newCart = new Cart({
      userId,
      productId,
      quantity,
    });

    const savedCart = await newCart.save();
    res.json(savedCart);
  } catch (err) {
    console.log(err.message);
    res.status(500).json({ error: err.message });
  }
});

router.post("/getCartByUser", async (req, res) => {
  const { userId } = req.body;

  const cart = await Cart.find({
    userId,
  });
  res.json(cart);
});

router.put("/deleteProduct", async (req, res) => {
  let { productId } = req.body;
  Product.updateOne(
    { _id: productId },
    { visibleStatus: false },
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
  let {
    productId,
    name,
    image,
    price,
    description,
    calories,
    quantity,
    kidSection,
    popular,
    DeliveryTime,
  } = req.body;

  Product.updateOne(
    { _id: productId },
    {
      name,
      image,
      price,
      description,
      calories,
      quantity,
      kidSection,
      popular,
      DeliveryTime,
    },
    function (err, docs) {
      if (err) {
        res.send(err);
      } else {
        var payload = {
          data: {
            title: "Product Updated",
            message: "This product has been Updated",
          },
        };
        admin
          .messaging()
          .sendToDevice(
            "fCcVB03BQfW6oQvz-NkC8F:APA91bFIfTmzIwzvct2rEp5PQAclGmF1By3FBDdtq4rssLPVwEp-LOtLrVIStWjcJgX6LPlC2y-tfjgQx37iDD08l0hmFaWCWr_Wp8ggLzerHWJ5MlTgqLbHr4BvLFwVpCLJBbKI82CZ",
            payload
          );
        res.send("Updated");
      }
    }
  );
});

module.exports = router;
