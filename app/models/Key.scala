
package models

import java.nio.ByteBuffer
import java.security.SecureRandom
import net.glxn.qrgen._
import org.apache.commons.codec.binary.{Base32, Base64}
import play.api.Play

object Key {

   val sr      = new SecureRandom
   val config  = Play.current.configuration
   val size    = config.getInt("qrCode.size").getOrElse(160)

   def next: String = {
      var bb = ByteBuffer.allocate(8)
      sr.nextBytes(bb.array)
      val token = bb.getLong
      token.abs.toHexString
   }

  def urlImage(key: String) : String = {

      // data uris require base64 encoding of data
      // ie: <img src="data:image/png;base64,data">
      val url        = s"http://www.onceness.com/msg/$key"
      val data    = new String(Base64.encodeBase64(QRCode.from(url).withSize(size,size).stream().toByteArray))
      data
   }

  def msgImage(msg: String) : String = {

      // data uris require base64 encoding of data
      // ie: <img src="data:image/png;base64,data">

      val data    = new String(Base64.encodeBase64(QRCode.from(msg).withSize(size,size).stream().toByteArray))
    data
   }
}

