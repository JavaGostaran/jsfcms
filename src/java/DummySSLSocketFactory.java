




import com.sun.net.ssl.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;


/**
 * DummySSLSocketFactory
 *
 * @author Eugen Kuleshov
 */
public class DummySSLSocketFactory extends SSLSocketFactory {
  private SSLSocketFactory factory;

  public DummySSLSocketFactory() {
    System.out.println( "DummySocketFactory instantiated");
    try {
      SSLContext sslcontext = SSLContext.getInstance( "TLS");
      sslcontext.init( null,
                       // new KeyManager[] { new DummyKeyManager()},
                       new TrustManager[] { new DummyTrustManager()},
                       new java.security.SecureRandom());
      factory = ( SSLSocketFactory) sslcontext.getSocketFactory();

    } catch( Exception ex) {
      ex.printStackTrace();
    }
  }

  public static SocketFactory getDefault() {
    System.out.println( "DummySocketFactory.getDefault()");
    return new DummySSLSocketFactory();
  }

  public Socket createSocket( Socket socket, String s, int i, boolean flag)
      throws IOException {
    System.out.println( "DummySocketFactory.createSocket()");
    return factory.createSocket( socket, s, i, flag);
  }

  public Socket createSocket( InetAddress inaddr, int i,
                              InetAddress inaddr1, int j) throws IOException {
    System.out.println( "DummySocketFactory.createSocket()");
    return factory.createSocket( inaddr, i, inaddr1, j);
  }

  public Socket createSocket( InetAddress inaddr, int i)
      throws IOException {
    System.out.println( "DummySocketFactory.createSocket()");
    return factory.createSocket( inaddr, i);
  }

  public Socket createSocket( String s, int i, InetAddress inaddr, int j)
      throws IOException {
    System.out.println( "DummySocketFactory.createSocket()");
    return factory.createSocket( s, i, inaddr, j);
  }

  public Socket createSocket( String s, int i) throws IOException {
    System.out.println( "DummySocketFactory.createSocket()");
    return factory.createSocket( s, i);
  }

  public String[] getDefaultCipherSuites() {
    System.out.println( "DummySocketFactory.getDefaultCipherSuites()");
    return factory.getSupportedCipherSuites();
  }

  public String[] getSupportedCipherSuites() {
    System.out.println( "DummySocketFactory.getSupportedCipherSuites()");
    return factory.getSupportedCipherSuites();
  }
}

