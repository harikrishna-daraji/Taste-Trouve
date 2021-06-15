const router = require("express").Router();

const Category = require("../models/Category");

router.post("/add", async (req, res) => {
  try {
    const { name, image } = req.body;

    const newCategory = new Category({
      name,
      image,
    });
    const savedCategory = await newCategory.save();
    res.json(savedCategory);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

router.get("/get", async (req, res) => {
  const category = await Category.find();
  res.json(category);
});

module.exports = router;
