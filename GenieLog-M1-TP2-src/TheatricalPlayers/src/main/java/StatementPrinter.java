import java.util.*;

public class StatementPrinter implements TroupeTheatrale{

  private double totalAmount;
  private int VolumeCredits;

  public String print(Invoice invoice, Map<String, Play> plays) {
    
    StringBuffer result = new StringBuffer(String.format("Statement for %s\n", invoice.customer.name));
  
    for (Performance perf : invoice.performances) {
      Play play = plays.get(perf.playID);
      double thisAmount = 0;
      thisAmount = this.calculValueAmountPlay(perf,play);
    
      this.addVolumeCredits(perf,play);

      result.append(toFormatStringOfPlayConcerned(perf,play.name,thisAmount));

      addCurrentAmountOnTotalAmount(thisAmount);
      this.updatePointFideliteGreat150();
    }

    result.append(toFormatStringOfAmountOwed()).append(toFormatStringOfCreditsEarned());

    invoice.customer.setNumclient(this.VolumeCredits);

    return result.toString();
  }

  public double getTotalAmount(){ return this.totalAmount;}
  public int getVolumeCredits(){ return this.VolumeCredits;}

  public  void addCurrentAmountOnTotalAmount(double thisAmount){
      this.totalAmount += thisAmount;
  }

  public void addVolumeCredits(Performance perf,Play play){
    
    this.VolumeCredits += Math.max(perf.audience - 30, 0);

     addExtraCreditForEveryTenComedy(play.type,perf.audience);
  }

  public void addExtraCreditForEveryTenComedy(String typePlay,int audience){
    if (COMEDY.equals(typePlay)) this.VolumeCredits += Math.floor(audience / 5);
  }

  public double priceThisAmountToFloat(double thisAmount){
    return thisAmount/100;
  }

  public double priceTotalAmountToFLOAT(double totalAmount){
    return totalAmount/100;
  }

  public String toFormatStringOfPlayConcerned(Performance perf, String namePlay, double thisAmount){

    return String.format("  %s: %s (%s seats)\n",namePlay, FRMT.format(priceThisAmountToFloat(thisAmount)), perf.audience);
  }

  public String toFormatStringOfAmountOwed(){

    return String.format("Amount owed is %s\n", FRMT.format(priceTotalAmountToFLOAT(totalAmount)));
  }

  public String toFormatStringOfCreditsEarned(){

    return String.format("You earned %s credits\n", this.VolumeCredits);
  }

  public double calculValueAmountPlay(Performance perf,Play play){
        
    double thisAmount=0;

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

  public void  updatePointFideliteGreat150(){
      if(this.VolumeCredits > 150){
        this.VolumeCredits = this.VolumeCredits - 150;
        this.totalAmount = this.totalAmount - 15;
      }
  }
  public void reInitialiseAttributs(){
    this.totalAmount = 0;
    this.VolumeCredits = 0;
  }
}
