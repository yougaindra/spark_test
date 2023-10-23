Trying out spark job to calculate time used to watch TV using imdb dataset

Download: 


- https://datasets.imdbws.com/name.basics.tsv.gz,

- https://datasets.imdbws.com/title.episode.tsv.gz
           


USAGE:

uncompress downloaded file and then

place uncompressed files int src/main/resources folder and then run directly from IDE

OR

spark-submit --class Main --master <spark_master_url> <location_of_jar> <location_of_title.basic.tsv> <location_of_title.episode.tsv> <location_of_your_list.csv>

for yourList format see src/main/reosources/myList.csv
title should match exactly. Pay attention to '.',',',don't change '&' with 'and'
