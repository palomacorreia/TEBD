const Express = require("express");
const routes = require("./route");
const mongoose = require("mongoose");

const port = process.env.PORT || 3000;
const app = Express();
mongoose.connect(
  "mongodb://root:1234@cluster0-shard-00-00-qwqoz.mongodb.net:27017,cluster0-shard-00-01-qwqoz.mongodb.net:27017,cluster0-shard-00-02-qwqoz.mongodb.net:27017/Cluster0?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true&w=majority",
  { useNewUrlParser: true, useUnifiedTopology: true ,dbName: "Cluster0"}
);
const artigo = require("./models/Artigo");
const db = mongoose.connection;
  
db.on('connected', () => {
    console.log('Mongoose default connection is open');
});

db.on('error', err => {
    console.log(`Mongoose default connection has occured \n${err}`);
});

app.use(Express.json());
app.use(routes);

app.listen(port);
