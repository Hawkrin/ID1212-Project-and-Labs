const { Router } = require("express");
const authenticated = require("../middleware/auth.middleware");

const router = Router();

router

    .get("/account", authenticated,  (req, res, next) => {
        res.render('account');
    })

module.exports = router;