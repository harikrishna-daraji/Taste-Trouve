const router = require("express").Router();
const admin = require("firebase-admin");
const Product = require("../models/Product");
const Cart = require("../models/Cart");

router.post("/add", async (req, res) => {
  try {
    const { userId, productId, quantity, restaurantId } = req.body;

    const checkOldEntry = await Cart.find({
      userId,
      productId,
    });

    const compareCart = await Cart.find({
      userId,
    });

    if (compareCart.length > 0) {
      if (compareCart[0].restaurantId == restaurantId) {
        if (checkOldEntry.length > 0) {
          var myquery = { _id: checkOldEntry[0]._id };
          var newvalues = {
            $set: { quantity: checkOldEntry[0].quantity + parseInt(quantity) },
          };
          await Cart.updateOne(myquery, newvalues, function (err, res) {
            if (err) throw err;
          });
          return res.json({ message: "Item quantity is updated" });
        } else {
          const newCart = new Cart({
            userId,
            productId,
            quantity,
            restaurantId,
          });
          const savedCart = await newCart.save();
          return res.json({ message: "Item added to cart" });
        }
      } else {
        return res.json({
          message:
            "Please choose the item from same restaurant or clear/proceed the cart",
        });
      }
    } else {
      const newCart = new Cart({
        userId,
        productId,
        quantity,
        restaurantId,
      });
      const savedCart = await newCart.save();
      return res.json({ message: "Item added to cart" });
    }
  } catch (err) {
    console.log(err.message);
    res.status(500).json({ error: err.message });
  }
});

router.post("/getCartByUser", async (req, res) => {
  const { userId } = req.body;

  const cart = await Cart.find({
    userId,
  }).populate("productId");
  res.json(cart);
});

router.post("/updateQuantity", async (req, res) => {
  const { cartId, quantity } = req.body;

  var myquery = { _id: cartId };
  var newvalues = {
    $set: { quantity: parseInt(quantity) },
  };

  if (parseInt(quantity) == 0) {
    await Cart.findByIdAndDelete(cartId);
  } else {
    await Cart.updateOne(myquery, newvalues, function (err, res) {
      if (err) throw err;
    });
  }
  return res.json({ data: "cartUpdated" });
});

router.delete("/delete", async (req, res) => {
  const { cartId } = req.body;
  try {
    const deleteCart = await Cart.findByIdAndDelete(cartId);
    res.json(deleteCart);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

module.exports = router;
