const { Router } = require("express");
const authenticated = require("../middleware/auth.middleware");

const User = require("../model/user.model");

const router = Router();

router

    .get("/", authenticated, (req, res) => {
       
        User.findById(req.user)
            .then((user) => {
                res.render('home', {
                    user
                });
            })
            .catch((error) => {

            })       

       
    })

module.exports = router;