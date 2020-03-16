select case when (`artigo_media`) <= 3 then
         'ate 3'
          when (`artigo_media`) between 3 and 6 then
         'de 3 a 6'
         when  (`artigo_media`) between 6 and 10 then
         'de 6 a 10'
         end as faixa_media
,  count(*), `artigo_titulo`, `artigo_media`, `artigo_qtd_revisores`, `artigo_id`
from artigo
where `artigo_qtd_revisores` > 2
group by `artigo_id`  
ORDER BY `artigo`.`artigo_media`  DESC