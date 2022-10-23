import java.text.NumberFormat;
import java.util.*;

public class StatementPrinter {


  public int totalAmount;
  public int VolumeCredits;
  public final NumberFormat FRMT = NumberFormat.getCurrencyInstance(Locale.US);
  public final String TRAGEDY = "tragedy";
  public final String COMEDY = "comedy";

  public StatementPrinter(){
    this.totalAmount = 0;
    this.VolumeCredits = 0;
  }

  public String print(Invoice invoice, Map<String, Play> plays) {
    
    StringBuffer result = new StringBuffer(String.format("Statement for %s\n", invoice.customer));
    

    for (Performance perf : invoice.performances) {
      Play play = plays.get(perf.playID);
      int thisAmount = 0;
      thisAmount = this.calculValueAmountPlay(perf,play);
      

      this.addVolumeCredits(perf,play);


      // print line for this order
      result.append(toFormatStringOfPlayConcerned(perf,play.name,thisAmount));

      this.addCurretAmountOnTotalAmount(thisAmount);
    }

    result.append(toFormatStringOfAmountOwed()).append(toFormatStringOfCreditsEarned());

    invoice.toHTML(invoice, plays);
    return result.toString();
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

  public  void addCurretAmountOnTotalAmount(int thisAmount){
      this.totalAmount += thisAmount;
  }

  public void addVolumeCredits(Performance perf,Play play){
    
    this.VolumeCredits += Math.max(perf.audience - 30, 0);

     // add extra credit for every ten comedy attendees
     if (COMEDY.equals(play.type)) this.VolumeCredits += Math.floor(perf.audience / 5);
  }


  public String toFormatStringOfPlayConcerned(Performance perf, String namePlay, int thisAmount){

    return String.format("  %s: %s (%s seats)\n",namePlay, FRMT.format(thisAmount / 100), perf.audience);

  }

  public String toFormatStringOfAmountOwed(){

    return String.format("Amount owed is %s\n", FRMT.format(totalAmount / 100));

  }

  public String toFormatStringOfCreditsEarned(){

    return String.format("You earned %s credits\n", this.VolumeCredits);

  }

}
