const { Router } = require("express");

const router = Router();

router

    .get("/account", (req, res, next) => {
        res.render('account');
    })

module.exports = router;