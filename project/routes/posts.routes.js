const { Router } = require("express");

const router = Router();

router

    .get("/forumpage", (req, res, next) => {
        res.render('forumpage');
    })

module.exports = router;