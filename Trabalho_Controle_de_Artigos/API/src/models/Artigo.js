const mongoose = require("mongoose");
const Schema = mongoose.Schema;

const artigoSchema = new Schema({
  _artigo_id: Number,
  artigo_resumo: String,
  artigo_arquivo: String,
  arquivo_qtd_revisores: Number,
  artigo_media: Number,
  artigo_titulo: String  
});

module.exports = mongoose.model("Artigos", artigoSchema,"artigo");
