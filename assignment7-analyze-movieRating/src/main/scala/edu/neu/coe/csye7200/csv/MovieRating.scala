package edu.neu.coe.csye7200.csv

import org.apache.spark.sql.{Column, DataFrame, Row, SparkSession}
import org.apache.spark.sql.functions._

import scala.util.{Failure, Success, Try}
import org.apache.spark.rdd.RDD
import org.apache.log4j.Logger
import org.apache.log4j.Level

case class MovieRating(resource: String){

  val spark: SparkSession = SparkSession
    .builder()
    .appName("MovieRating")
    .master("local[*]")
    .getOrCreate()
  spark.sparkContext.setLogLevel("ERROR")
  val dataframe = spark.read.option("header",true).csv(resource)
}


object MovieRating extends App{

  Logger.getLogger(("org")).setLevel(Level.OFF)

  def apply(resource: String): MovieRating = new MovieRating(resource)

  def getPath(fname: String): String = Try(Option((getClass.getResource(fname).getPath))) match{
    case Success(Some(p)) => p
    case _ => throw new Exception(s"cannot get resource for class movie metadata: $fname")
  }

  def meanRatings(df: DataFrame, movieCol: String): Option[Row] = {
    Option(df.select(mean(df(movieCol))).first())
  }

  def stdDevRatings(df: DataFrame, movieCol: String): Option[Row]  = {
    Option(df.select(stddev(df(movieCol))).first())
  }

  def processDF(df: DataFrame, movieCol: String): DataFrame = {
    df.select(mean(movieCol).alias("mean"), stddev(movieCol).alias("stdDev"))
  }

  val filename = "/movie_metadata.csv"
  val absPath = MovieRating.getPath(filename)
  val movies_df = MovieRating(absPath).dataframe
  processDF(movies_df, "imdb_score").show()

}
