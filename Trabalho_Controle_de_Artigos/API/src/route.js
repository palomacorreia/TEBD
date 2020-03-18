const { Router } = require("express");
const routes = Router();
const ArtigoController= require("./controller/ArtigoController")

routes.get("/artigos",ArtigoController.index);
module.exports = routes;
