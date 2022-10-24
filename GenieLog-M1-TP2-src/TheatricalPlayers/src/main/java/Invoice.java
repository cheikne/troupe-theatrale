import java.io.PrintWriter;
import java.security.PublicKey;
import java.text.NumberFormat;
import java.util.*;

public class Invoice extends TroupeTheatrale{

  public String customer;
  public List<Performance> performances;


  public Invoice(String customer, List<Performance> performances) {
    this.customer = customer;
    this.performances = performances;
  }


  public void toHTML(Invoice invoice, Map<String, Play> plays){
    StringBuffer result  =  new StringBuffer(HEADER_PAGE_HTML);
    result.append(HEADER_OF_TABLE);

    int totalAmount = 0;
    int volumeCredits = 0;
    int thisAmount =0;

    
    for(Performance perf : invoice.performances){

      Play play = plays.get(perf.playID);
       thisAmount = 0;

      thisAmount = calculValueAmountPlay(perf, play);

      volumeCredits += Math.max(perf.audience - 30, 0);

      // add extra credit for every ten comedy attendees
      if (COMEDY.equals(play.type)) volumeCredits += Math.floor(perf.audience / 5);

      result.append("<tr><th>");
      result.append(toTEXT(play.name)).append("</th>");
      result.append("<th>").append(toTEXT(perf.audience)).append("</th>");
      result.append("<th>").append(toTEXT((thisAmount/100),FRMT)).append("<t/h></tr>");

      totalAmount += thisAmount;


    }

    result.append("<tr><th></th>").append("<th>").append(TOTAL_OWED).append("</th><th>").append(toTEXT((totalAmount/100),FRMT)).append("</th></tr>");

    result.append("<tr><th></th>").append("<th>").append(POINTS_EARNED).append("</th><th>").append(toTEXT(volumeCredits)).append("</th></tr>");
    result.append("</tbody></html>");
  
    try{ 
      PrintWriter fileToHtml = new PrintWriter("toHtml.html");
      fileToHtml.write(result.toString());
      fileToHtml.close();
    }catch (Exception e) {
      e.printStackTrace();
    }
  }

  public int calculValueAmountPlay(Performance perf, Play play){

    int thisAmount=0;

    switch (play.type) {
      case TRAGEDY:
        thisAmount = 40000;
        if (perf.audience > 30) {
          thisAmount += 1000 * (perf.audience - 30);
        }
        break;
      case COMEDY:
        thisAmount = 30000;
        if (perf.audience > 20) {
          thisAmount += 10000 + 500 * (perf.audience - 20);
        }
        thisAmount += 300 * perf.audience;
        break;
      default:
        throw new Error("unknown type: ${play.type}");
    }

    return thisAmount;
  }


  public String toTEXT(int value){

    return String.format("%s",value);
  }
  public String toTEXT(String value){

    return String.format("%s",value);
  }

  public String toTEXT(int value,NumberFormat frmt){

    return String.format("%s",frmt.format(value));
  }

}
