const Artigo = require("../models/Artigo");

module.exports = {
  async index(req, res) {
    console.log("index");

    const artigo = await Artigo.aggregate([
      {
        $group: {
          _id: {
            _artigo_id: "$_artigo_id",
            arquivo_qtd_revisores: "$arquivo_qtd_revisores",
            artigo_titulo: "$artigo_titulo",
            artigo_resumo: "$artigo_resumo"
          }
        }
      },

      { $sort: { artigo_media: -1 } },

      { $limit: 40 }
    ]);

    console.log(artigo);
    return res.json(artigo);
  }
};
