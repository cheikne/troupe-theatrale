import java.util.*;

public class StatementPrinter extends TroupeTheatrale{

  public int totalAmount;
  public int VolumeCredits;

  public String print(Invoice invoice, Map<String, Play> plays) {
    
    StringBuffer result = new StringBuffer(String.format("Statement for %s\n", invoice.customer.name));
  
    for (Performance perf : invoice.performances) {
      Play play = plays.get(perf.playID);
      int thisAmount = 0;
      thisAmount = super.calculValueAmountPlay(perf,play);
    
      addVolumeCredits(perf,play);

      // print line for this order
      result.append(toFormatStringOfPlayConcerned(perf,play.name,thisAmount));

      addCurrentAmountOnTotalAmount(thisAmount);
      super.updatePointFideliteGreat150();
    }

    result.append(toFormatStringOfAmountOwed()).append(toFormatStringOfCreditsEarned());

    invoice.customer.setNumclient(super.VolumeCredits);

    return result.toString();
  }

  public  void addCurrentAmountOnTotalAmount(int thisAmount){
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
