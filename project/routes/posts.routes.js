const { Router } = require("express");

const router = Router();

router

    //Forum page
    .get("/forumpage", (req, res, next) => {
        res.render('forumpage');
    })

    //Creating posts
    .get("/createpost", (req, res, next) => {
        res.render('createthread');
    })

    .post("/createpost", (req, res, next) => {
        console.log(req.body);
    })


module.exports = router;