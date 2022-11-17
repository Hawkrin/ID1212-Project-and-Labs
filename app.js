const express = require('express')

const app = express()

app.set('view engine', 'ejs')
app.listen(3000)

app.get('/', (req, res) => {
    res.send("<p>HELLO</p> ")
})

app.get('/about', (req, res) => {
    res.send("<p>HELLO</p> ")
})

app.get('/posts/create', (req, res) => {
    res.send("<p>HELLO</p> ")
})

app.use((req, res) => {
    res.status(404).send("<p>ERROR</p>")
})






