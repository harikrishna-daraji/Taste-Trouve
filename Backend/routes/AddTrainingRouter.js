const router = require("express").Router();

const AddTraining = require("../models/AddTrainingModel")
router.post("/add", async (req, res) => {
  try {


    const { Exercise } = req.body;
    const { Sets } = req.body;
    const { Reps } = req.body;
    const { Tempo } = req.body;
    const { Rest } = req.body;
    const { userId } = req.body;





    const newAddTraining = new AddTraining({

      Exercise,
      Sets,
      Reps,
      Tempo,
      Rest,
      userId,

    })
    const savedAddTraining = await newAddTraining.save();
    res.json(savedAddTraining);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

router.post("/getWorkout", async (req, res) => {
  const { user } = req.body;
  const AddTrainings = await AddTraining.find({ userId: user });
  res.json(AddTrainings);
})
router.post("/getWorkoutDetail", async (req, res) => {
  const { workoutdate } = req.body;

  const { userId } = req.body;

  const AddTrainings = await AddTraining.find({ workoutDate: workoutdate, userId: userId });
  console.log(AddTrainings);
  res.json(AddTrainings);
})

router.delete("/delete", async (req, res) => {
  // const Workout = await AddTraining.findOne({userId:req.user,_id:});
  // console.log('here in delete');
  const { workoutId } = req.body;
  const deletedWorkout = await AddTraining.findOneAndDelete({ _id: workoutId });
  res.json(deletedWorkout);

})
router.post("/edit", async (req, res) => {


  const { workoutId } = req.body;

  //console.log(workoutDate);
  const updateTraining = { // <-- Here
    Exercise: req.body.Exercise,
    Sets: req.body.Sets,
    Reps: req.body.Reps,
    Tempo: req.body.Tempo,
    Rest: req.body.Rest,
    userId: req.body.userId,

  }

  AddTraining.findByIdAndUpdate(workoutId, updateTraining)
    .then(updatedTrain => {
      res.json(updatedTrain.toJSON())
    })
})

module.exports = router;




