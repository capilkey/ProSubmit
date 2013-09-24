package prosubmit.ldap;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.NamingException;

import java.util.Hashtable;

import prosubmit.config.Config;

public class LDAPAuthenticate {

    private String authenticated;
    private Hashtable<Object, Object> env;
    private DirContext ldapContextNone;
    private SearchControls searchCtrl;
    private String url;
    private String position;
    private String userID;
    private String givenName;
    private String title;
    private String placeholder;
    private String emailAddress;
    private boolean logout;

    public LDAPAuthenticate() {
        authenticated = "false";
        logout = false;
        
        url = Config.getProperty("ldapurl");

        env = new Hashtable<Object, Object>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");

        // specify where the ldap server is running
        env.put(Context.PROVIDER_URL, url);
        env.put(Context.SECURITY_AUTHENTICATION, "none");

        // Create the initial directory context
        try {
            ldapContextNone = new InitialDirContext(env);
        } catch (NamingException e) {
        }

        searchCtrl = new SearchControls();
        searchCtrl.setSearchScope(SearchControls.SUBTREE_SCOPE);
    }

    public boolean search(String user, String pass) {
        if (user.equals("admin") && pass.equals("prosubmit1")) {
            userID = user;
            givenName = "Administrator";
            position = "Employee";
            title = "Admin";
            authenticated = "true";
            return true;
        }

        if (user.equals("teacher") && pass.equals("prosubmit1")) {
            userID = user;
            givenName = "Teacher";
            position = "Employee";
            title = "Professor";
            authenticated = "true";
            return true;
        }

        if (user.equals("student") && pass.equals("prosubmit1")) {
            userID = user;
            givenName = "Student";
            position = "Student";
            title = "Student";
            authenticated = "true";
            return true;
        }

        search(user);

        if (authenticated.equals("true") && user.toLowerCase().equals(userID.toLowerCase())) {
            try {
                env = new Hashtable<Object, Object>();
                env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");

                // specify where the ldap server is running
                env.put(Context.PROVIDER_URL, url);
                env.put(Context.SECURITY_AUTHENTICATION, "simple");
                String userIDString = "uid=" + user;
                String positionString = "ou=" + position;

                env.put(Context.SECURITY_PRINCIPAL, userIDString + ", " + positionString + ", o=sene.ca");
                env.put(Context.SECURITY_CREDENTIALS, pass);

                // this command will throw an exception if the password is incorrect
                DirContext ldapContext = new InitialDirContext(env);
                NamingEnumeration<SearchResult> results = ldapContext.search("o=sene.ca", "(&(" + userIDString + "))", searchCtrl);

                if (!results.hasMore()) // search failed
                {
                    throw new NamingException();
                }

                SearchResult sr = results.next();
                Attributes at = sr.getAttributes();

                givenName = at.get("cn").toString().split(": ")[1];

                title = position;

                //prints out all possible attributes
//                for (NamingEnumeration<?> i = at.getAll(); i.hasMore();) {
//                    System.out.println((Attribute) i.next());
//                }
            	emailAddress = at.get("mail").toString().split(": ")[1];
                authenticated = "true";
                
                return true;
            } catch (NamingException e) {
                authenticated = "failed";
                e.printStackTrace();
            } catch (Exception e) {
                //e.printStackTrace();
                authenticated = "error";
                e.printStackTrace();
            }
        }

        return false;
    }

    public boolean search(String user) {

        if (ldapContextNone != null) { // if the initial context was created fine
            try {
                NamingEnumeration<SearchResult> results = ldapContextNone.search("o=sene.ca", "(&(" + "uid=" + user + "))", searchCtrl);

                if (!results.hasMore()) // search failed
                {
                  System.out.println("test");
                    throw new Exception();
                }

                SearchResult sr = results.next();
                Attributes at = sr.getAttributes();
              
                position = ((sr.getName().split(","))[1].split("="))[1];
                if (position.equals("Employee"))
                  givenName = at.get("cn").toString().split(": ")[1];
                userID = at.get("uid").toString().split(": ")[1];

                authenticated = "true";
                return true;
            } catch (NamingException e) {
            } catch (Exception e) {
                System.out.println("User " + user + " not found in LDAP. Checking local database for user.");
            }
        }

        authenticated = "failed";

        return false;
    }

    // Methods for getting the user's details as fetched by LDAP
    public String getUserID() {
        // getUID()
        return userID;
    }

    public String getGivenName() {
        //getCN()
        return givenName;
    }

    public String getTitle() {
        if (title.equals("")) {
            title = placeholder;
        }
        return title;
    }

    public String getPosition() {
        //getOU()
        return position;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public String getAuthenticated() {
        return authenticated;
    }

    public void resetAuthenticated() {
        authenticated = "false";
    }

    public boolean isLogout() {
        return logout;
    }

    public void setLogout(boolean l) {
        if (true) {
            reset();
        }
        logout = l;
    }

    private void reset() {
        position = userID = givenName = title = null;
    }
    
	public String getEmailAddress() {
		return emailAddress;
	}
}