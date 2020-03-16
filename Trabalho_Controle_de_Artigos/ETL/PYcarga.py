import mysql.connector
import logging
from pymongo import MongoClient

port = 27017

logging.basicConfig(filename='C:\\Users\\Lucas\\Desktop\\teste.log', level=logging.DEBUG,format='%(asctime)s %(levelname)s %(funcName)s => %(message)s')
class Carga:
    global cursor
    def Conexao(self):
        global cursor
        contador = 0
        dados = []
        convert = ""
        tabela ="atuacao"

        scriptInserir = []

        cnx = mysql.connector.connect(user='root', password='',
                                    host='127.0.0.1',
                                    database='sc_sac')

        cliente = MongoClient("mongodb+srv://lucas:investimentos@cluster0-qwqoz.mongodb.net/test?retryWrites=true&w=majority")
        cursor = cnx.cursor()
        

        # ARTIGOS #
        cursor.execute("SELECT * FROM sc_sac.artigo")
        numrows = int(cursor.rowcount)
        
        db=cliente.Cluster0
        for row in cursor.fetchall():
            dados2 = {
                "_artigo_id": row[0],
                "artigo_resumo": row[1],
                "artigo_arquivo": row[2],
                "arquivo_qtd_revisores": row[3],
                "artigo_media": row[4],
                "artigo_titulo": row[5],
                }
            album = db.artigo
            
            try:
                dados_id = album.insert_one(dados2).inserted_id
                logging.debug('documento inserido com sucesso: {}'.format(dados_id))
            except:
                logging.exception('Falha durante o processo de inserção: {}'.format(dados_id))
                break
            

        # ARTIGO_AUTOR #
        cursor.execute("SELECT * FROM sc_sac.artigo_autor")
        numrows = int(cursor.rowcount)
        for row in cursor.fetchall():
            dados2 = {
                "_artigo_autor_id": str(row[0]),
                "_usuario_id": row[1],
                "_artigo_id": row[2],
                "email_autor": row[3]
                }
            album = db.artigo_autor
            try:
                dados_id = album.insert_one(dados2).inserted_id
                logging.debug('documento inserido com sucesso: {}'.format(dados_id))
            except:
                logging.exception('Falha durante o processo de inserção: {}'.format(dados_id))
                break

        # CARTAO #
        cursor.execute("SELECT * FROM sc_sac.cartao")
        numrows = int(cursor.rowcount)
        for row in cursor.fetchall():
            dados2 = {
                "_cartao_id": str(row[0]),
                "_cartao_numero": str(row[1]),
                "_cartao_data_vencimento": row[2],
                "_cartao_marca": row[3],
                "_usuario_id": str(row[4])
                }
            album = db.cartao

            try:
                dados_id = album.insert_one(dados2).inserted_id
                logging.debug('documento inserido com sucesso: {}'.format(dados_id))
            except:
                logging.exception('Falha durante o processo de inserção: {}'.format(dados_id))
                break

        # REVISAO

        cursor.execute("SELECT * FROM sc_sac.revisao")
        numrows = int(cursor.rowcount)
        for row in cursor.fetchall():
            dados2 = {
                "_revisao_id": str(row[0]),
                "_revisao_nota": str(row[1]),
                "_revisao_data_envio": str(row[2]),
                "_revisao_comentario": row[3],
                "_artigo_id": row[4],
                "_usuario_id": str(row[5])
                }
            album = db.revisao

            try:
                dados_id = album.insert_one(dados2).inserted_id
                logging.debug('documento inserido com sucesso: {}'.format(dados_id))
            except:
                logging.exception('Falha durante o processo de inserção: {}'.format(dados_id))
                break

        #USUARIO    

        cursor.execute("SELECT * FROM sc_sac.usuario")
        numrows = int(cursor.rowcount)
        for row in cursor.fetchall():
            dados2 = {
                "_usuario_id": str(row[0]),
                "_usuario_nome": row[1],
                "_usuario_endereco": row[2],
                "_usuario_telefone": row[3],
                "_usuaro_email": row[4],
                "_usuario_localtrabalho": str(row[5]),
                "_usuario_revisor": str(row[6]),
                "_usuario_autor": str(row[7])
                }
            album = db.usuario

            try:
                dados_id = album.insert_one(dados2).inserted_id
                logging.debug('documento inserido com sucesso: {}'.format(dados_id))
            except:
                logging.exception('Falha durante o processo de inserção: {}'.format(dados_id))
                break
        
        logging.debug('Carga completa com sucesso')
        cnx.close()

    def loop(self):
            while True:
                cmd = int(input('\nExecutar compilação: ')) 
                if cmd == 1:      
                    self.Conexao()
    
  
if __name__ == '__main__':
    carregamento = Carga()
    carregamento.loop()