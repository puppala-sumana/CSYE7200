package edu.neu.coe.csye7200.csv

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.apache.log4j.Logger
import org.apache.spark.sql.DataFrame
import org.apache.log4j.Level

import scala.util.{Success, Try}


class MovieRatingSpec extends AnyFlatSpec with Matchers {

  Logger.getLogger(("org")).setLevel(Level.OFF)

  val filename = "/movie_metadata.csv"
  val absPathY = Try(Option(MovieRating.getPath(filename)))

  behavior of "parse path"
  it should "get absolute path" in {
    absPathY should matchPattern {
      case Success(Some(s)) =>
    }
  }

  val absPath = absPathY match{
    case Success(Some(aoy)) => aoy
    case _ => throw new Exception("Parse Error")
  }
  val df = MovieRating(absPath).dataframe

  behavior of "fetch movie_metadata.csv"
  it should "fetch movie_metadata.csv from given class path" in {
    Try(Option(df)) should matchPattern {
      case Success(Some(df: org.apache.spark.sql.DataFrame)) =>
    }
  }
  it should "return correct dataframe length" in {
    df.count() shouldBe 1609
  }

  behavior of "get movies rating statistics"
  it should "have movies mean rating = 6.453200745804848" in {
    val mean_m = (for (mo <- MovieRating.meanRatings(df, "imdb_score")) yield mo) match{
      case Some(i) => i.get(0)
      case _ => throw new Exception("Mean evaluation error")
    }
    mean_m shouldBe 6.453200745804848
  }
  it should "have movies rating standard deviation = 0.9988071293753289" in {
    val std_dev = (for (mo <- MovieRating.stdDevRatings(df, "imdb_score")) yield mo) match {
      case Some(i) => i.get(0)
      case _ => throw new Exception("Mean evaluation error")
    }
    std_dev shouldBe 0.9988071293753289
  }

}
