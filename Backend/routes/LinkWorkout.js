
const router = require("express").Router();

const LinkWorkout = require("../models/LinkWorkout")
router.post("/add", async (req, res) => {
    try {


        const { CoachId } = req.body;
        const { WorkoutId } = req.body;
        const { Date } = req.body;

        console.log(CoachId);
        console.log(WorkoutId);
        console.log(Date);



        const link = new LinkWorkout({
            CoachId,
            WorkoutId,
            Date

        })
        const savedAddTraining = await link.save();
        res.json(savedAddTraining);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

router.post("/getWorkoutDetail", async (req, res) => {
    const { CoachId } = req.body;

    const { Date } = req.body;

    const AddTrainings = await LinkWorkout.find({ CoachId: CoachId, Date: Date }).populate('WorkoutId');

    res.json(AddTrainings);
})





module.exports = router;




