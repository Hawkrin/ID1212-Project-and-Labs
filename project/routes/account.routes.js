const { Router } = require("express");
const authenticated = require("../middleware/auth.middleware");

const router = Router();

router

    .get("/account", authenticated, (req, res) => { 
        res.render('account', {
            user: req.user
        }); 
    })

module.exports = router;