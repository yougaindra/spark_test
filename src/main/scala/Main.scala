

import java.lang.management.ManagementFactory

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{count, isnull, lower, sum}
import org.apache.log4j.Logger
import org.apache.log4j.Level

object Main {
  def main(args: Array[String]): Unit = {

    val isIDE = ManagementFactory.getRuntimeMXBean.getInputArguments.toString.contains("JetBrains")
    val titlesFile= if(isIDE) getClass.getResource("/title.basics.tsv").getPath else args(0)
    val episodesFile = if(isIDE) getClass.getResource("/title.episode.tsv").getPath else args(1)
    val myList = if(isIDE) getClass.getResource("/myList.csv").getPath else  args(2)

    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    val spark = SparkSession
      .builder()
      .appName("wasted Time calculator")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    val  allTitles = spark
      .read
      .format("csv")
      .option("header",true)
      .option("delimiter","\t")
      .option("inferSchema",true)
      .load("file://"+titlesFile)

    val allEpisodes = spark
      .read
      .format("csv")
      .option("header",true)
      .option("delimiter","\t")
      .option("inferSchema",true)
      .load("file://"+episodesFile)

    val episodeWithRuntime  = allTitles
      .join(allEpisodes.withColumnRenamed("tconst", "epconst"), allTitles("tconst")===allEpisodes("parentTconst")).cache()

    val watchedSeries = spark
      .read
      .format("csv")
      .option("header",true)
      .option("inferSchema",true)
      .load("file://"+myList)

    val watchedEpisodes = watchedSeries.
      join(episodeWithRuntime, lower(episodeWithRuntime("primaryTitle")) === lower(watchedSeries("title"))
        && episodeWithRuntime("startYear") === watchedSeries("released"))
      .where('seasonNumber <= 'uptoSeason || 'uptoSeason.isNull)
      .cache()
    val uniqueSeriesWatched = watchedEpisodes
      .groupBy('primaryTitle)
      .agg(count('primaryTitle).as("numberOfEpisodes"))
      .withColumnRenamed("primaryTitle","SeriesName")

//    val schema = ScalaReflection.schemaFor[String].dataType.asInstanceOf[StructType]

    val totalTimeWasted = watchedEpisodes.agg(sum('runtimeMinutes)).collect()(0)(0)
    print("Congrats. you have wasted : "+totalTimeWasted + " minutes ")
    uniqueSeriesWatched.repartition(1).write.format("csv").option("header",true).save("unique Series")
    watchedEpisodes.repartition(1).write.format("csv").option("header",true).save("allEpisodes")

  }
}