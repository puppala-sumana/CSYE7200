import scala.xml.Node
import scala.xml.XML
import scala.xml.NodeSeq.fromSeq
import scala.xml.NodeSeq
import java.net.{URLConnection, URL}
import org.xml.sax.InputSource
import scala.xml.parsing.NoBindingFactoryAdapter
import org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl

val sUrl = "https://www1.coe.neu.edu/~rhillyard/indexSafe.html"
val parserFactory = new org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl
val parser = parserFactory.newSAXParser()
val source = new org.xml.sax.InputSource(sUrl)
val adapter = new scala.xml.parsing.NoBindingFactoryAdapter
val ns = adapter.loadXML(source, parser)
//for (n <- ns \\ "a") yield n
for ( n <- ns \\ "a") println((n \@ "href"))
//val res = for ( n <- ns \\ "a") yield((n \@ "href"))
//val ls = for(r <- res) yield r.toString()
//println(ls.length)
//ls foreach println

