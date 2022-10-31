import java.io.PrintWriter;
import java.security.PublicKey;
import java.text.NumberFormat;
import java.util.*;

public class Invoice implements TroupeTheatrale{
  public Customer customer;
  public List<Performance> performances;
  public final String HEADER_PAGE_HTML = "<html><head><title>Facture du client</title><meta charset='utf-8'><meta name='viewport' content='width=device-width, initial-scale=1'><link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css'><script src='https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.slim.min.js'></script><script src='https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js'></script><script src='https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js'></script></head> ";
  public final String DIV_CONTAINER = "<div class='container'><h1>Invoice</h1>";
  public final String HEADER_OF_TABLE = "<table class='table table-dark table-striped'><thead><tr><th>Piece</th><th>Seats sold</th><th>Price</th></tr></thead><tbody>";
  public TroupeTheatrale objectProxy;

  public Invoice(Customer customer, List<Performance> performances) {
    this.customer = customer;
    this.performances = performances;
    this.objectProxy = new StatementPrinter();
  }

  public void toHTML(Invoice invoice, Map<String, Play> plays){
    StringBuffer result  =  new StringBuffer(HEADER_PAGE_HTML);
    result.append(DIV_CONTAINER).append("<h4 class='text-center'><strong>Client :</strong>").append(customer.name).append("</h4>");
    result.append(HEADER_OF_TABLE);

    int thisAmount =0;
    this.reInitialiseAttributs();
    
    for(Performance perf : invoice.performances){

      Play play = plays.get(perf.playID);
      thisAmount = 0;
      thisAmount = this.calculValueAmountPlay(perf, play);

      this.addVolumeCredits(perf, play);
      result.append("<tr><th>");
      result.append(toTEXT(play.name)).append("</th>");
      result.append("<th>").append(toTEXT(perf.audience)).append("</th>");
      result.append("<th>").append(toTEXT((thisAmount/100),FRMT)).append("<t/h></tr>");

      this.objectProxy.addCurrentAmountOnTotalAmount(thisAmount);
      objectProxy.updatePointFideliteGreat150();
    }
  
    result.append("<tr><th></th>").append("<th>").append(TOTAL_OWED).append("</th><th>").append(toTEXT((this.getTotalAmount()/100),FRMT)).append("</th></tr>");
    result.append("<tr><th></th>").append("<th>").append(POINTS_EARNED).append("</th><th>").append(toTEXT(this.getVolumeCredits())).append("</th></tr>");
    result.append("</tbody></table></div></html>");
    
    printToFileHtml(result);
  
    invoice.customer.setNumclient(getVolumeCredits());
  }

  public int getTotalAmount() { return objectProxy.getTotalAmount();}
  public int getVolumeCredits() { return objectProxy.getVolumeCredits();}

  public String toTEXT(int value){
    return String.format("%s",value);
  }
  public String toTEXT(String value){
    return String.format("%s",value);
  }

  public String toTEXT(int value,NumberFormat frmt){
    return String.format("%s",frmt.format(value));
  }

  public  void addCurrentAmountOnTotalAmount(int thisAmount){
    objectProxy.addCurrentAmountOnTotalAmount(thisAmount);
  }

  public void addVolumeCredits(Performance perf,Play play){
    objectProxy.addVolumeCredits(perf, play);
  }

  public int calculValueAmountPlay(Performance perf,Play play){
    return objectProxy.calculValueAmountPlay(perf, play);
  }

  public void reInitialiseAttributs(){
    objectProxy.reInitialiseAttributs();
  }

  public void printToFileHtml(StringBuffer result){
    try{ 
      PrintWriter fileToHtml = new PrintWriter("toHtml.html");
      fileToHtml.write(result.toString());
      fileToHtml.close();

    }catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void updatePointFideliteGreat150(){
     objectProxy.updatePointFideliteGreat150();
  }
}
