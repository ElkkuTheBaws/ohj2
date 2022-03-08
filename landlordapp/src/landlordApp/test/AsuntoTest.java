package landlordApp.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import landlordApp.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2020.04.24 14:21:42 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class AsuntoTest {



  // Generated by ComTest BEGIN
  /** testRekisteroi55 */
  @Test
  public void testRekisteroi55() {    // Asunto: 55
    Asunto asunto = new Asunto(); 
    assertEquals("From: Asunto line: 57", 0, asunto.getId()); 
    asunto.rekisteroi(); 
    Asunto asunto2 = new Asunto(); 
    asunto2.rekisteroi(); 
    int n1 = asunto.getId(); 
    int n2 = asunto2.getId(); 
    assertEquals("From: Asunto line: 63", n2-1, n1); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testParse149 */
  @Test
  public void testParse149() {    // Asunto: 149
    Asunto ab = new Asunto(); 
    ab.parse("3|2|A3|28|10.04.1983|389.0|"); 
    assertEquals("From: Asunto line: 152", 3, ab.getId()); 
    ab.toString().equals("3|2|A3|28|10.04.1983|389.0|"); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testAnna240 */
  @Test
  public void testAnna240() {    // Asunto: 240
    Asunto asu = new Asunto(); 
    asu.parse("3|2|A3|28|10.04.1983|389.0|"); 
    assertEquals("From: Asunto line: 243", "3", asu.anna(0)); 
    assertEquals("From: Asunto line: 244", "2", asu.anna(1)); 
    assertEquals("From: Asunto line: 245", "A3", asu.anna(2)); 
    assertEquals("From: Asunto line: 246", "28", asu.anna(3)); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testAseta272 */
  @Test
  public void testAseta272() {    // Asunto: 272
    Asunto as = new Asunto(); 
    assertEquals("From: Asunto line: 274", null, as.aseta(3,"533")); 
    assertEquals("From: Asunto line: 275", null, as.aseta(4,"19.5.1995")); 
    assertEquals("From: Asunto line: 276", false, as.aseta(4, "kissa") == null); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testEquals334 */
  @Test
  public void testEquals334() {    // Asunto: 334
    Asunto jasen1 = new Asunto(); 
    jasen1.parse("3|2|A3|28|10.04.1983|389.0|"); 
    Asunto jasen2 = new Asunto(); 
    jasen2.parse("3|2|A3|28|10.04.1983|389.0|"); 
    Asunto jasen3 = new Asunto(); 
    jasen3.parse("3|5|A3|28|10.04.1983|389.0|"); 
    assertEquals("From: Asunto line: 342", true, jasen1.equals(jasen2)); 
    assertEquals("From: Asunto line: 343", true, jasen2.equals(jasen1)); 
    assertEquals("From: Asunto line: 344", false, jasen1.equals(jasen3)); 
    assertEquals("From: Asunto line: 345", false, jasen3.equals(jasen2)); 
  } // Generated by ComTest END
}