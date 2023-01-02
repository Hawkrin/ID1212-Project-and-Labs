const { Router } = require("express");

const router = Router();

router

    .get("/forumpage", (req, res, next) => {
        res.render('forumpage');
    })

    .get("/createpost", (req, res, next) => {
        res.render('createthread');
    })


module.exports = router;