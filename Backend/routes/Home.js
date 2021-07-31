const router = require("express").Router();

const Product = require("../models/Product");
const Category = require("../models/Category");
const Restaurants = require("../models/Restaurants");
const Favourite = require("../models/favourite");

router.post("/getHomeProduct", async (req, res) => {
  const { userId } = req.body;
  try {
    const categoryObject = await Category.find();

    const kidsSection = await Product.find({ kidSection: true });
    for (var key in kidsSection) {
      const fav = await Favourite.findOne({
        userId,
        productId: kidsSection[key]._id,
      });

      kidsSection[key] = {
        ...kidsSection[key]._doc,
        favourite: fav == null ? "false" : "true",
      };
    }

    const popular = await Product.find({ popular: true });
    for (var key in popular) {
      const fav = await Favourite.findOne({
        userId,
        productId: popular[key]._id,
      });

      popular[key] = {
        ...popular[key]._doc,
        favourite: fav == null ? "false" : "true",
      };
    }

    const cart = await Cart.find({
      userId,
    });
    let restaurants = await Restaurants.find({
      status: "accepted",
      userType: "restaurantOwner",
    });

    for (var key in restaurants) {
      const restroImages = await Product.findOne({
        restaurantId: restaurants[key]._id,
      }).select("image");

      restaurants[key] = {
        ...restaurants[key]._doc,
        image: restroImages.image,
      };
    }
    const HomeData = {
      categoryObject,
      restaurants,
      kidsSection,
      popular,
      cart: cart.length,
    };
    res.send(HomeData);
  } catch (err) {
    console.log(err.message);
    res.status(500).json({ error: err.message });
  }
});

module.exports = router;
