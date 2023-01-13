const { Router } = require("express");
const _ = require("lodash"); 

const Comment = require("../model/comment.model");
const CommentController = require("../controller/comment.controller");

const authenticated = require("../middleware/auth.middleware");

const router = Router();

router

    .post("/add/:id", authenticated, (req, res) => {
        const {id} = _.pick(req.params, ["id"]); //Id on post
        const {body} = _.pick(req.body, ["body"]);


        CommentController.addComment(id, body, req.user._id)
            .then((comment) => {
                res.redirect("/posts/post/" + id);
            })
            .catch((error) => {
                res.redirect("/posts/post/" + id);
            })
    })

module.exports = router;