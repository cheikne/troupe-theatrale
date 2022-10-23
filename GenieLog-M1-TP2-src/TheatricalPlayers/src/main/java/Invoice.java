import java.io.PrintWriter;
import java.security.PublicKey;
import java.text.NumberFormat;
import java.util.*;

public class Invoice {

  public String customer;
  public List<Performance> performances;

  public final String HEADER_PAGE_HTML = "<html><head><title>Bootstrap Example</title><meta charset='utf-8'><meta name='viewport' content='width=device-width, initial-scale=1'><link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css'><script src='https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.slim.min.js'></script><script src='https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js'></script><script src='https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js'></script></head> ";
  public final String PIECE = "Piece";
  public final String SEATS_SOLD = "Seats sold";
  public final String PRICE = "Price";
  public final String TOTAL_OWED = "Total owed";
  public final String POINTS_EARNED = "Fidelity points earned";
  
  public final String HEADER_OF_TABLE = "<table class='table table-dark table-striped'><thead><tr><th>Piece</th><th>Seats sold</th><th>Price</th></tr></thead><tbody>";
  public final NumberFormat FRMT = NumberFormat.getCurrencyInstance(Locale.US);

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
      if ("comedy".equals(play.type)) volumeCredits += Math.floor(perf.audience / 5);

      result.append("<tr><th>");
      result.append(toTEXT(play.name)).append("</th>");
      result.append("<th>").append(toTEXT(perf.audience)).append("</th>");
      result.append("<th>").append(toTEXT((thisAmount/100),FRMT)).append("<t/h></tr>");

      totalAmount += thisAmount;


    }

    result.append("<tr><th></th>").append("<th>").append(TOTAL_OWED).append("</th><th>").append(toTEXT((totalAmount/100),FRMT)).append("</th></tr>");

    result.append("<tr><th></th>").append("<th>").append(POINTS_EARNED).append("</th><th>").append(toTEXT(volumeCredits)).append("</th></tr>");
    result.append("</tbody></html>");
    System.out.println(result.toString());
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
      case "tragedy":
        thisAmount = 40000;
        if (perf.audience > 30) {
          thisAmount += 1000 * (perf.audience - 30);
        }
        break;
      case "comedy":
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
