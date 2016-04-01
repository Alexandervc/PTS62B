

import data.ResourceProducer;
import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
import javax.enterprise.context.Dependent;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Edwin
 */
public class DBtest {
    
    private EntityManager em;
    private ResourceProducer producer;

    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws NamingException {
        //producer = new ResourceProducer();
        this.em = Persistence.createEntityManagerFactory("Proftaak").createEntityManager();  
    }

    @Test
    public void DatabaseTest() {
        this.em.getTransaction().begin();
    }
    /*
    @Test(expected = IllegalArgumentException.class)
    public void getKweetsForKweetererNullTest()
    {
        Kweeterer emptyKweeterer = null;
        List<Kweet> kweets = kwetterSession.getKweetsFromKweeterer(emptyKweeterer);
    }
    
    @Test
    public void getFollowersForKweetererTest() {
        em.getTransaction().begin();
        Kweeterer kweeterer = kwetterSession.registerKweeterer("TaylorSwift13",
                "Taylor Swift",
                "New York",
                "TaylorSwift.com",
                "Born in 1989.",
                "TaylorSwift13");  
        kwetterSession.getFollowersForKweeterer(kweeterer);
        
        Kweeterer kweetererAlexander = kwetterSession.registerKweeterer("Vampander",
                "Alexander",
                "Veghel",
                "Alexander.nl",
                "Alexander = goed",
                "Alexander");
        em.getTransaction().commit();
        em.getTransaction().begin();
        kwetterSession.followKweeterer(kweeterer, kweetererAlexander);
        em.getTransaction().commit();
        List<Kweeterer> followersList = kwetterSession.getFollowersForKweeterer(kweetererAlexander);
        Assert.assertEquals(1, followersList.size());
        Assert.assertEquals(kweeterer, followersList.get(0));
    }
       
    @Test(expected = IllegalArgumentException.class)
    public void getFollowersForKweetererNullTest() {
        Kweeterer emptyKweeterer = null;
        kwetterSession.getFollowersForKweeterer(emptyKweeterer);
    }
    
    @Test
    public void getFollowsForKweetererTest() {
        em.getTransaction().begin();
        Kweeterer kweeterer = kwetterSession.registerKweeterer("TaylorSwift13",
                "Taylor Swift",
                "New York",
                "TaylorSwift.com",
                "Born in 1989.",
                "TaylorSwift13");  
        kwetterSession.getFollowersForKweeterer(kweeterer);
        
        Kweeterer kweetererAlexander = kwetterSession.registerKweeterer("Vampander",
                "Alexander",
                "Veghel",
                "Alexander.nl",
                "Alexander = goed",
                "Alexander");
        em.getTransaction().commit();
        em.getTransaction().begin();
        kwetterSession.followKweeterer(kweeterer, kweetererAlexander);
        em.getTransaction().commit();
        List<Kweeterer> followersList = kwetterSession.getFollowsForKweeterer(kweeterer);
        Assert.assertEquals(1, followersList.size());
        Assert.assertEquals(kweetererAlexander, followersList.get(0));
    }
       
    @Test(expected = IllegalArgumentException.class)
    public void getFollowsForKweetererNullTest() {
        Kweeterer emptyKweeterer = null;
        kwetterSession.getFollowsForKweeterer(emptyKweeterer);
    }
    
    @Test
    public void getKweetererByUserName() {
        em.getTransaction().begin();
        assertNull(kwetterSession.getKweetererByUserName("TaylorSwift13"));
        assertNull(kwetterSession.getKweetererByUserName(""));
        Kweeterer kweeterer = kwetterSession.registerKweeterer("TaylorSwift13",
                "Taylor Swift",
                "New York",
                "TaylorSwift.com",
                "Born in 1989.",
                "TaylorSwift13");  
        em.getTransaction().commit();
        assertEquals(kwetterSession.getKweetererByUserName("TaylorSwift13"),kweeterer);
    }
    
    @Test
    public void getMentionsForKweeterer() {
        em.getTransaction().begin();
        Kweeterer kweeterer = kwetterSession.registerKweeterer("TaylorSwift13",
                "Taylor Swift",
                "New York",
                "TaylorSwift.com",
                "Born in 1989.",
                "TaylorSwift13");  
        Kweeterer kweetererAlexander = kwetterSession.registerKweeterer("Vampander",
                "Alexander",
                "Veghel",
                "Alexander.nl",
                "Alexander = goed",
                "Alexander");
        em.getTransaction().commit();
        kwetterSession.postKweet(kweetererAlexander, "@TaylorSwift13 yayayayaya");
        List<Kweet> mentioned_Kweets = kwetterSession.getMentionsForKweeterer(kweeterer);
        assertEquals(1, mentioned_Kweets.size());
    }
    
    @Test
    public void searchKweets() {
        em.getTransaction().begin();
        Kweeterer kweeterer = kwetterSession.registerKweeterer("TaylorSwift13",
                "Taylor Swift",
                "New York",
                "TaylorSwift.com",
                "Born in 1989.",
                "TaylorSwift13");  
        kwetterSession.postKweet(kweeterer, "aaa");
        kwetterSession.postKweet(kweeterer, "bab");
        kwetterSession.postKweet(kweeterer, "cbc");
        assertEquals(2,kwetterSession.searchKweets("a").size());
        assertEquals(2,kwetterSession.searchKweets("b").size());
        assertEquals(1,kwetterSession.searchKweets("c").size());
        em.getTransaction().commit();
    }
    
    @Test
    public void postKweet() {
        em.getTransaction().begin();
        Kweeterer kweeterer = kwetterSession.registerKweeterer("TaylorSwift13",
                "Taylor Swift",
                "New York",
                "TaylorSwift.com",
                "Born in 1989.",
                "TaylorSwift13");  
        kwetterSession.getFollowersForKweeterer(kweeterer);
        Kweeterer kweetererAlexander = kwetterSession.registerKweeterer("Vampander",
                "Alexander",
                "Veghel",
                "Alexander.nl",
                "Alexander = goed",
                "Alexander");
        assertEquals(0,kwetterSession.getKweetsFromKweeterer(kweeterer).size());
        kwetterSession.postKweet(kweeterer, "disgrace");
        assertEquals(1,kwetterSession.getKweetsFromKweeterer(kweeterer).size());
        
        em.getTransaction().commit();
    }
    
    @Test
    public void FollowTest() {
        em.getTransaction().begin();
        Kweeterer kweeterer = kwetterSession.registerKweeterer("TaylorSwift13",
                "Taylor Swift",
                "New York",
                "TaylorSwift.com",
                "Born in 1989.",
                "TaylorSwift13");  
        kwetterSession.getFollowersForKweeterer(kweeterer);
        
        Kweeterer kweetererAlexander = kwetterSession.registerKweeterer("Vampander",
                "Alexander",
                "Veghel",
                "Alexander.nl",
                "Alexander = goed",
                "Alexander");
        em.getTransaction().commit();
        em.getTransaction().begin();
        Assert.assertEquals(0, kweeterer.getFollows().size());
        Assert.assertEquals(0, kweetererAlexander.getFollowees().size());
        kwetterSession.followKweeterer(kweeterer, kweetererAlexander);
        em.getTransaction().commit();
        kweetererAlexander = kwetterSession.getKweetererByUserName(kweetererAlexander.getUsername());
        Assert.assertEquals(1, kweeterer.getFollows().size());
        Assert.assertEquals(1, kweetererAlexander.getFollowees().size());
    }
*/
    
}
