Trying out spark job to calculate time used to watch TV using imdb dataset

Download : 
https://datasets.imdbws.com/name.basics.tsv.gz,

https://datasets.imdbws.com/title.episode.tsv.gz
           


USAGE:
uncompress downloaded file and then


Directly run main method inside intellij


OR


spark-submit --class Main --master <spark_master_url> <location_of_jar> <location_of_title.basic.tsv> <location_of_title.episode.tsv> <location_of_your_list.csv>

 
