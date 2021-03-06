package hci.gnomex.security.tomcat;

import hci.gnomex.security.EncrypterService;
import hci.gnomex.security.EncryptionUtility;

import java.security.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.catalina.realm.GenericPrincipal;
import org.apache.catalina.realm.RealmBase;

public class GNomExRealm extends RealmBase {


  private String username;
  private String password;

  private String datasource_lookup_name;

  public GNomExRealm() {
    super();
    System.out.println("GNomExRealm created");
  }

  @Override
  public Principal authenticate(String username, String credentials) {
    this.username = username;
    this.password = credentials;
    System.out.println("In authenticate -- usrname=" + username);
    if (isAuthenticated()) {
      return getPrincipal(username);
    } else {
      return null;
    }
  }

  @Override
  protected Principal getPrincipal(String username) {

    List<String> roles = new ArrayList<String>();
    roles.add("GNomExUser");
    return new GenericPrincipal(username, password, roles);
  }

  @Override
  protected String getPassword(String string) {
    return password;
  }

  @Override
  protected String getName() {
    return username;
  }

  private boolean isAuthenticated() {
    boolean isAuthenticated = false;

    if (this.isAuthenticatedGNomExUser()) {
      // If this is a GNomEx external user, check credentials 
      // against the GNomEx encrypted password
      isAuthenticated =  true;
    } 
    return isAuthenticated;
  }



  private boolean isAuthenticatedGNomExUser() {

    boolean isAuthenticated = false;

    Connection con = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    EncryptionUtility passwordEncrypter = new EncryptionUtility();

    try {
      con = this.getConnection();
      stmt = con.prepareStatement("SELECT isActive, userNameExternal, passwordExternal, salt FROM AppUser WHERE userNameExternal = ?");
      stmt.setString(1, username);

      rs = stmt.executeQuery();

      while (rs.next()) {
        String isActive = rs.getString("isActive");
        String gnomexPasswordEncrypted = rs.getString("passwordExternal");
        String salt = rs.getString("salt");
        String thePasswordEncryptedNew = "";

        //Uncomment this conditional if you want to prevent inactive users from logging in
        //        if (isActive != null && isActive.equalsIgnoreCase("Y")) {
        if(salt != null) {
          thePasswordEncryptedNew = passwordEncrypter.createPassword(password, salt);
        }
        String thePasswordEncryptedOld = EncrypterService.getInstance().encrypt(password);
        if (thePasswordEncryptedNew.equals(gnomexPasswordEncrypted)) {
          isAuthenticated = true;
        } else if(thePasswordEncryptedOld.equals(gnomexPasswordEncrypted)) {
          isAuthenticated = true;
        }
        // }
      }

    } catch (NamingException ne) {
      System.err.println("FATAL: Naming exception while trying to get connection \n" + ne.getMessage());
      return false;
    } catch (ClassNotFoundException cnfe) {
      System.err.println("FATAL: The JDBC driver was not found on the classpath \n" + cnfe.getMessage());
      return false;
    } catch (SQLException ex) {
      System.err.println("FATAL: Unable to run query on AppUser in hci.gnomex.security.tomcat.GNomExRealm");
      System.err.println(ex.toString());
      return false;
    } finally {
      this.closeConnection(con);
    }

    return isAuthenticated;
  }



  public String getDatasource_lookup_name() {
    return datasource_lookup_name;
  }

  public void setDatasource_lookup_name(String datasource_lookup_name) {
    this.datasource_lookup_name = datasource_lookup_name;
  }

  protected Connection getConnection() throws SQLException, ClassNotFoundException, NamingException {
    Context initCtx = new InitialContext();
    DataSource ds = (DataSource)initCtx.lookup(datasource_lookup_name);

    return ds.getConnection();
  }

  protected void closeConnection(Connection con) {
    try {
      if (con != null && !con.isClosed()) {
        con.close();
      }
    } catch (SQLException ex) {
      System.err.println("FATAL: Unable to close db connection in hci.gnomex.security.tomcat.GNomExRealm");
    }
  }
}





